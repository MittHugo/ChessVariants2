package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.board.Tile.DisappearedTile;
import com.chess.engine.board.Tile.NullTile;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.pieces.PieceUtils.NullPiece;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.BlackPlayerHandler;
import com.chess.engine.player.BluePlayer;
import com.chess.engine.player.BluePlayerHandler;
import com.chess.engine.player.Player;
import com.chess.engine.player.RedPlayer;
import com.chess.engine.player.RedPlayerHandler;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.player.WhitePlayerHandler;
import com.chess.engine.player.variants.DoubleBlackPlayer;
import com.chess.engine.player.variants.DoubleBluePlayer;
import com.chess.engine.player.variants.DoubleRedPlayer;
import com.chess.engine.player.variants.DoubleWhitePlayer;
import com.chess.gui.Table;
import com.chess.gui.Table.MoveType;
import com.chess.gui.Table.PlayerCount;

public class Board {
	protected final List<Tile> gameBoard;
	protected final Collection<Piece> whitePieces;
	protected final Collection<Piece> blackPieces;
	protected final Collection<Piece> redPieces;
	protected final Collection<Piece> bluePieces;
	
	private WhitePlayer whitePlayer;
	private BlackPlayer blackPlayer;
	private BluePlayer bluePlayer;
	private RedPlayer redPlayer;
	private final Player currentPlayer;
	private final Pawn enPassantPawn;
	private final Builder builder;
	
	private final int NUM_TILES_PER_ROW; 
	private final int NUM_TILES_PER_COLUMN;
	private final boolean canCastle;
	private final boolean canEnPassant;
	private final boolean canPromote;
	private final boolean canPawnJump;
	private final int NUM_TILES;
	
	public final boolean[] FIRST_COLUMN;
	public final boolean[] SECOND_COLUMN;
	public final boolean[] THIRD_COLUMN;
	public final boolean[] THIRD_TO_LAST_COLUMN;
	public final boolean[] SECOND_TO_LAST_COLUMN;
	public final boolean[] LAST_COLUMN;
	
	public final boolean[] FIRST_ROW;
	public  boolean[] SECOND_ROW;
	public boolean[] SECOND_LAST_ROW;
	public final boolean[] LAST_ROW;
	public boolean[][] isInDesiredColumn = new boolean[1000][1000];
	protected Collection<Move> whiteStandardLegalMoves = new ArrayList<>();
	protected Collection<Move> blackStandardLegalMoves = new ArrayList<>();
	protected Collection<Move> blueStandardLegalMoves = new ArrayList<>();
	protected Collection<Move> redStandardLegalMoves = new ArrayList<>();
	private final int numberOfRowsUsed;
	public Board(final Builder builder, final int NUM_TILES_PER_ROW, final int NUM_TILES_PER_COLUMN, final 
			boolean canCastle, final boolean canEnPassant, final boolean canPawnJump, final boolean canPromote) {
		numberOfRowsUsed = 0;
		this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN;
		this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
		this.canCastle = canCastle;
		this.canEnPassant = canEnPassant;
		this.canPromote = canPromote;
		this.canPawnJump = canPawnJump;
		this.NUM_TILES = NUM_TILES_PER_COLUMN * NUM_TILES_PER_ROW;
		this.builder = builder;
		
		for(int i = 0; i < NUM_TILES_PER_COLUMN; i++) {
			isInDesiredColumn[i] = initColumn(i);
		}
		
		FIRST_COLUMN = initColumn(0);
		SECOND_COLUMN = initColumn(1);
		THIRD_COLUMN = initColumn(2);
		THIRD_TO_LAST_COLUMN = initColumn(NUM_TILES_PER_COLUMN-3);
		SECOND_TO_LAST_COLUMN = initColumn(NUM_TILES_PER_COLUMN-2);
		LAST_COLUMN = initColumn(NUM_TILES_PER_COLUMN-1);
		
		FIRST_ROW = initRow(0);
		SECOND_ROW = initRow(NUM_TILES_PER_COLUMN);
		SECOND_LAST_ROW = initRow(NUM_TILES_PER_COLUMN*(NUM_TILES_PER_ROW-2));
		LAST_ROW = initRow(NUM_TILES_PER_COLUMN*(NUM_TILES_PER_ROW-1));
		
		this.gameBoard = createGameBoard(builder, this);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		this.redPieces = calculateActivePieces(this.gameBoard, Alliance.RED);
		this.bluePieces = calculateActivePieces(this.gameBoard, Alliance.BLUE);
		this.enPassantPawn = builder.enPassantPawn;
		setupPlayers();
		updatePieceValues();
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer, this.redPlayer, this.bluePlayer);
	}
	public Board(final Builder builder, final int NUM_TILES_PER_ROW, final int NUM_TILES_PER_COLUMN, final 
			boolean canCastle, final boolean canEnPassant, final boolean canPawnJump, final boolean canPromote,
			final int numberOfRowsUsed) {

		this.canCastle = canCastle;
		this.canEnPassant = canEnPassant;
		this.canPromote = canPromote;
		this.canPawnJump = canPawnJump;
		if(!(Table.get() == null)) {
			if(Table.get().getPlayerCount().isRegular()) {
				if(Table.get().isDoubleChess()) {
					this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN*2;
					this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
					this.NUM_TILES = this.NUM_TILES_PER_COLUMN * this.NUM_TILES_PER_ROW;
				} else {
					this.NUM_TILES = NUM_TILES_PER_COLUMN * NUM_TILES_PER_ROW;
					this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN;
					this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
				}
			} else {
				if(Table.get().isDoubleChess()) {
					this.NUM_TILES_PER_COLUMN = (numberOfRowsUsed+1)*2+2*NUM_TILES_PER_COLUMN;
					this.NUM_TILES_PER_ROW = (numberOfRowsUsed+1)*2+2*NUM_TILES_PER_COLUMN;
					this.NUM_TILES = this.NUM_TILES_PER_COLUMN * this.NUM_TILES_PER_ROW;
				} else {
					this.NUM_TILES = (int)Math.pow(((numberOfRowsUsed+1)*2+NUM_TILES_PER_COLUMN),2);
					this.NUM_TILES_PER_COLUMN = (numberOfRowsUsed+1)*2+NUM_TILES_PER_COLUMN;
					this.NUM_TILES_PER_ROW = (numberOfRowsUsed+1)*2+NUM_TILES_PER_COLUMN;
				}
			}
		} else {
			this.NUM_TILES = NUM_TILES_PER_COLUMN * NUM_TILES_PER_ROW;
			this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN;
			this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
		}
		this.builder = builder;
		this.numberOfRowsUsed = numberOfRowsUsed;
		
		for(int i = 0; i < this.NUM_TILES_PER_COLUMN; i++) {
			isInDesiredColumn[i] = initColumn(i);
		}
		
		FIRST_COLUMN = initColumn(0);
		SECOND_COLUMN = initColumn(1);
		THIRD_COLUMN = initColumn(2);
		THIRD_TO_LAST_COLUMN = initColumn(this.NUM_TILES_PER_COLUMN-3);
		SECOND_TO_LAST_COLUMN = initColumn(this.NUM_TILES_PER_COLUMN-2);
		LAST_COLUMN = initColumn(this.NUM_TILES_PER_COLUMN-1);
		
		FIRST_ROW = initRow(0);
		SECOND_ROW = initRow(this.NUM_TILES_PER_COLUMN);
		SECOND_LAST_ROW = initRow(this.NUM_TILES_PER_COLUMN*(this.NUM_TILES_PER_ROW-2));
		LAST_ROW = initRow(this.NUM_TILES_PER_COLUMN*(this.NUM_TILES_PER_ROW-1));
		
		
		this.gameBoard = createGameBoard(builder, this);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		this.redPieces = calculateActivePieces(this.gameBoard, Alliance.RED);
		this.bluePieces = calculateActivePieces(this.gameBoard, Alliance.BLUE);
		this.enPassantPawn = builder.enPassantPawn;
		setupPlayers();
		// System.out.println("The size is " + whiteStandardLegalMoves.size());
		updatePieceValues();
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer,this.redPlayer, this.bluePlayer);
	}
	public Board(final Builder builder, final int NUM_TILES_PER_ROW, final int NUM_TILES_PER_COLUMN, final 
			boolean canCastle, final boolean canEnPassant, final boolean canPawnJump, final boolean canPromote,
			final int numberOfRowsUsed, final WhitePlayerHandler whitePlayerHandler, final BlackPlayerHandler blackPlayerHandler, 
			final RedPlayerHandler redPlayerHandler, final BluePlayerHandler bluePlayerHandler) {

		this.canCastle = canCastle;
		this.canEnPassant = canEnPassant;
		this.canPromote = canPromote;
		this.canPawnJump = canPawnJump;
		if(!(Table.get() == null)) {
			if(Table.get().getPlayerCount() == PlayerCount.Regular) {
				this.NUM_TILES = NUM_TILES_PER_COLUMN * NUM_TILES_PER_ROW;
				this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN;
				this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
			} else {
				this.NUM_TILES = (int)Math.pow(((numberOfRowsUsed+1)*2+NUM_TILES_PER_COLUMN),2);
				this.NUM_TILES_PER_COLUMN = (numberOfRowsUsed+1)*2+NUM_TILES_PER_COLUMN;
				this.NUM_TILES_PER_ROW = (numberOfRowsUsed+1)*2+NUM_TILES_PER_COLUMN;
			}
		} else {
			this.NUM_TILES = NUM_TILES_PER_COLUMN * NUM_TILES_PER_ROW;
			this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN;
			this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
		}
		this.builder = builder;
		this.numberOfRowsUsed = numberOfRowsUsed;
		
		for(int i = 0; i < this.NUM_TILES_PER_COLUMN; i++) {
			isInDesiredColumn[i] = initColumn(i);
		}
		
		FIRST_COLUMN = initColumn(0);
		SECOND_COLUMN = initColumn(1);
		THIRD_COLUMN = initColumn(2);
		THIRD_TO_LAST_COLUMN = initColumn(this.NUM_TILES_PER_COLUMN-3);
		SECOND_TO_LAST_COLUMN = initColumn(this.NUM_TILES_PER_COLUMN-2);
		LAST_COLUMN = initColumn(this.NUM_TILES_PER_COLUMN-1);
		
		FIRST_ROW = initRow(0);
		SECOND_ROW = initRow(this.NUM_TILES_PER_COLUMN);
		SECOND_LAST_ROW = initRow(this.NUM_TILES_PER_COLUMN*(this.NUM_TILES_PER_ROW-2));
		LAST_ROW = initRow(this.NUM_TILES_PER_COLUMN*(this.NUM_TILES_PER_ROW-1));
		
		
		this.gameBoard = createGameBoard(builder, this);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		this.redPieces = calculateActivePieces(this.gameBoard, Alliance.RED);
		this.bluePieces = calculateActivePieces(this.gameBoard, Alliance.BLUE);
		this.enPassantPawn = builder.enPassantPawn;
//		whiteStandardLegalMoves.addAll(calculateLegalMoves(this.whitePieces));
//		blackStandardLegalMoves.addAll(calculateLegalMoves(this.blackPieces));
//		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
//		this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
		setupPlayers(whitePlayerHandler, blackPlayerHandler, bluePlayerHandler, redPlayerHandler);
		updatePieceValues();
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer,this.redPlayer, this.bluePlayer);
	}
	
	public Board(final Builder builder, final int NUM_TILES_PER_ROW, final int NUM_TILES_PER_COLUMN, final 
			boolean canCastle, final boolean canEnPassant, final boolean canPawnJump, final boolean canPromote, 
			final WhitePlayerHandler whitehandler, final BlackPlayerHandler blackPlayer) {
		numberOfRowsUsed = 0;
		this.NUM_TILES_PER_COLUMN = NUM_TILES_PER_COLUMN;
		this.NUM_TILES_PER_ROW = NUM_TILES_PER_ROW;
		this.canCastle = canCastle;
		this.canEnPassant = canEnPassant;
		this.canPromote = canPromote;
		this.canPawnJump = canPawnJump;
		this.NUM_TILES = NUM_TILES_PER_COLUMN * NUM_TILES_PER_ROW;
		this.builder = builder;
		for(int i = 0; i < NUM_TILES_PER_COLUMN; i++) {
			isInDesiredColumn[i] = initColumn(i);
		}
		
		FIRST_COLUMN = initColumn(0);
		SECOND_COLUMN = initColumn(1);
		THIRD_COLUMN = initColumn(2);
		THIRD_TO_LAST_COLUMN = initColumn(NUM_TILES_PER_COLUMN-3);
		SECOND_TO_LAST_COLUMN = initColumn(NUM_TILES_PER_COLUMN-2);
		LAST_COLUMN = initColumn(NUM_TILES_PER_COLUMN-1);
		
		FIRST_ROW = initRow(0);
		SECOND_ROW = initRow(NUM_TILES_PER_COLUMN);
		SECOND_LAST_ROW = initRow(NUM_TILES_PER_ROW*(NUM_TILES_PER_COLUMN-2));
		LAST_ROW = initRow(NUM_TILES_PER_ROW*(NUM_TILES_PER_COLUMN-1));
		
		this.gameBoard = createGameBoard(builder, this);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
		this.redPieces = calculateActivePieces(this.gameBoard, Alliance.RED);
		this.bluePieces = calculateActivePieces(this.gameBoard, Alliance.BLUE);
		this.enPassantPawn = builder.enPassantPawn;
		whiteStandardLegalMoves.addAll(calculateLegalMoves(this.whitePieces));
		blackStandardLegalMoves.addAll(calculateLegalMoves(this.blackPieces));
		this.blackPlayer = blackPlayer.createPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.whitePlayer = whitehandler.createPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer,this.redPlayer, this.bluePlayer);
		updatePieceValues();
	}
	
	private void setupPlayers() {
	    // Calculate legal moves for each set of pieces
	    whiteStandardLegalMoves.addAll(calculateLegalMoves(this.whitePieces));
	    blackStandardLegalMoves.addAll(calculateLegalMoves(this.blackPieces));

	    if (Table.get() != null) {
	        if (Table.get().getPlayerCount() != null) {
	            // Four Teams Mode (Red & Blue vs. White & Black)
				if(Table.get().isDoubleChess()) {
					if (Table.get().getPlayerCount() == PlayerCount.FourTeams) {
	        	    	redStandardLegalMoves.addAll(calculateLegalMoves(this.redPieces));
	        	    	blueStandardLegalMoves.addAll(calculateLegalMoves(this.bluePieces));
	                	this.whitePlayer = new DoubleWhitePlayer(this, whiteStandardLegalMoves, combineLegalMoves(redStandardLegalMoves, blueStandardLegalMoves));
	                	this.blackPlayer = new DoubleBlackPlayer(this, blackStandardLegalMoves, combineLegalMoves(redStandardLegalMoves, blueStandardLegalMoves));
	                	this.redPlayer = new DoubleRedPlayer(this, redStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves));
	                	this.bluePlayer = new DoubleBluePlayer(this, blueStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves));

		            // Four Opponents Mode (Each player is an individual opponent)
		            } else if (Table.get().getPlayerCount() == PlayerCount.FourOpponents) {
	    	    	    redStandardLegalMoves.addAll(calculateLegalMoves(this.redPieces));
	        		    blueStandardLegalMoves.addAll(calculateLegalMoves(this.bluePieces));
	            	    this.whitePlayer = new DoubleWhitePlayer(this, whiteStandardLegalMoves, combineLegalMoves(blackStandardLegalMoves, redStandardLegalMoves, blueStandardLegalMoves));
	                	this.blackPlayer = new DoubleBlackPlayer(this, blackStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, redStandardLegalMoves, blueStandardLegalMoves));
	                	this.redPlayer = new DoubleRedPlayer(this, redStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves, blueStandardLegalMoves));
	                	this.bluePlayer = new DoubleBluePlayer(this, blueStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves, redStandardLegalMoves));

		            // Default Two-Player Mode (White vs. Black)
		            } else {
	    	            this.whitePlayer = new DoubleWhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
	        	        this.blackPlayer = new DoubleBlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
	            	}
	        	} else{
					if (Table.get().getPlayerCount() == PlayerCount.FourTeams) {
	        	    	redStandardLegalMoves.addAll(calculateLegalMoves(this.redPieces));
	        	    	blueStandardLegalMoves.addAll(calculateLegalMoves(this.bluePieces));
	                	this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, combineLegalMoves(redStandardLegalMoves, blueStandardLegalMoves));
	                	this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, combineLegalMoves(redStandardLegalMoves, blueStandardLegalMoves));
	                	this.redPlayer = new RedPlayer(this, redStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves));
	                	this.bluePlayer = new BluePlayer(this, blueStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves));

		            // Four Opponents Mode (Each player is an individual opponent)
		            } else if (Table.get().getPlayerCount() == PlayerCount.FourOpponents) {
	    	    	    redStandardLegalMoves.addAll(calculateLegalMoves(this.redPieces));
	        		    blueStandardLegalMoves.addAll(calculateLegalMoves(this.bluePieces));
	            	    this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, combineLegalMoves(blackStandardLegalMoves, redStandardLegalMoves, blueStandardLegalMoves));
	                	this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, redStandardLegalMoves, blueStandardLegalMoves));
	                	this.redPlayer = new RedPlayer(this, redStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves, blueStandardLegalMoves));
	                	this.bluePlayer = new BluePlayer(this, blueStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves, redStandardLegalMoves));

		            // Default Two-Player Mode (White vs. Black)
		            } else {
	    	            this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
	        	        this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
	            	}
				}
			} else {
				if(Table.get().isGryphon()&&this.checkForDoubleKings(Alliance.WHITE)) {
					this.whitePlayer = new DoubleWhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
				} else {
					this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
				}
				if(Table.get().isGryphon()&&this.checkForDoubleKings(Alliance.WHITE)) {
					this.blackPlayer = new DoubleBlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
				} else {
					this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
				}
			}	            
	    } else {
			this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        	this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
	    }
	}
	private void setupPlayers(WhitePlayerHandler whitePlayerHandler,BlackPlayerHandler blackPlayerHandler,
						BluePlayerHandler bluePlayerHandler,RedPlayerHandler redPlayerHandler) {
	    // Calculate legal moves for each set of pieces
	    whiteStandardLegalMoves.addAll(calculateLegalMoves(this.whitePieces));
	    blackStandardLegalMoves.addAll(calculateLegalMoves(this.blackPieces));

	    if (Table.get() != null) {
	        if (Table.get().getPlayerCount() != null) {
	            // Four Teams Mode (Red & Blue vs. White & Black)
	            if (Table.get().getPlayerCount() == PlayerCount.FourTeams) {
	        	    redStandardLegalMoves.addAll(calculateLegalMoves(this.redPieces));
	        	    blueStandardLegalMoves.addAll(calculateLegalMoves(this.bluePieces));
	                this.whitePlayer = whitePlayerHandler.createPlayer(this, whiteStandardLegalMoves, combineLegalMoves(redStandardLegalMoves, blueStandardLegalMoves));
	                this.blackPlayer = blackPlayerHandler.createPlayer(this, blackStandardLegalMoves, combineLegalMoves(redStandardLegalMoves, blueStandardLegalMoves));
	                this.redPlayer = redPlayerHandler.createPlayer(this, redStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves));
	                this.bluePlayer = bluePlayerHandler.createPlayer(this, blueStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves));

	            // Four Opponents Mode (Each player is an individual opponent)
	            } else if (Table.get().getPlayerCount() == PlayerCount.FourOpponents) {
	        	    redStandardLegalMoves.addAll(calculateLegalMoves(this.redPieces));
	        	    blueStandardLegalMoves.addAll(calculateLegalMoves(this.bluePieces));
	                this.whitePlayer = whitePlayerHandler.createPlayer(this, whiteStandardLegalMoves, combineLegalMoves(blackStandardLegalMoves, redStandardLegalMoves, blueStandardLegalMoves));
	                this.blackPlayer = blackPlayerHandler.createPlayer(this, blackStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, redStandardLegalMoves, blueStandardLegalMoves));
	                this.redPlayer = redPlayerHandler.createPlayer(this, redStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves, blueStandardLegalMoves));
	                this.bluePlayer = bluePlayerHandler.createPlayer(this, blueStandardLegalMoves, combineLegalMoves(whiteStandardLegalMoves, blackStandardLegalMoves, redStandardLegalMoves));

	            // Default Two-Player Mode (White vs. Black)
	            } else {
	                this.whitePlayer=whitePlayerHandler.createPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
	                this.blackPlayer = blackPlayerHandler.createPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
	            }
	        }
	    } else {
            this.whitePlayer = whitePlayerHandler.createPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
            this.blackPlayer = blackPlayerHandler.createPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
	    }
	}
	private boolean checkForDoubleKings(Alliance alliance) {
    // Iterate over the pieces to check if there are two kings for any single alliance
    int kingCount = 0;
    for (Piece piece : this.getPieces()) {
        if (piece.getPieceType() == PieceType.KING) {
            if (piece.getAlliance() == alliance) {
                kingCount++;
            }
        }
    }
    return kingCount > 1;
}
	private Collection<Move> combineLegalMoves(Collection<Move>... moves) {
	    List<Move> combinedMoves = new ArrayList<>();
	    for (Collection<Move> moveList : moves) {
	        combinedMoves.addAll(moveList);
	    }
	    return combinedMoves;
	}

	public Map<Integer, Boolean> getDisappearedConfig() {
		return this.builder.disapereadConfig;
	}
	
	
	public void addWhiteMoves(Collection<Move> moves) {
		whiteStandardLegalMoves.addAll(moves);
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
	}
	public void addBlackMoves(Collection<Move> moves) {
		blackStandardLegalMoves.addAll(moves);
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
	}
	
	public boolean whiteInStartingPosition(Board initialBoard) {
		for(Piece piece: initialBoard.whitePieces) {
			Piece thisPiece = this.getTile(piece.getPiecePosition()).getPiece();
			if(thisPiece == null) {
				return false;
			} else if(!thisPiece.equals(piece)) {
				return false;
			}
		}
		return true;
	}
	public boolean blackInStartingPosition(Board initialBoard) {
		for(Piece piece: initialBoard.blackPieces) {
			Piece thisPiece = this.getTile(piece.getPiecePosition()).getPiece();
			if(thisPiece == null) {
				return false;
			} else if(!thisPiece.equals(piece)) {
				return false;
			}
		}
		return true;
	}
	
	public Builder getBuilder() {
		return builder;
	}
	
	public boolean isEvenRank(int position) {
		boolean result = false;
		final int startVal = 0;
		int val = startVal;
		for(int numRow = 0; numRow <= (NUM_TILES_PER_ROW/2); numRow++) {
			result = initRow(val)[position] || result;
			val = startVal + 2*NUM_TILES_PER_ROW*numRow;
		}
		return result;
	}
	
	public boolean isOddRank(int position) {
		boolean result = false;
		final int startVal = NUM_TILES_PER_ROW;
		int val = startVal;
		for(int numRow = 0; numRow <= (NUM_TILES_PER_ROW/2); numRow++) {
			result = initRow(val)[position] || result;
			val = startVal + 2*NUM_TILES_PER_ROW*numRow;
		}
		return result;
	}
	
	public boolean[] initColumn(int columnNumber) {
	    final boolean[] column = new boolean[NUM_TILES]; 
	    
	    // Ensure columnNumber starts within a valid column range
	    if (columnNumber >= NUM_TILES_PER_COLUMN) {
	        throw new IllegalArgumentException("Invalid column number.");
	    }

	    // Mark every tile in the column
	    while (columnNumber < NUM_TILES) {
	        column[columnNumber] = true;
	        columnNumber += NUM_TILES_PER_COLUMN;
	    }
	    
	    return column;
	}

	
	public boolean[] initRow(int rowNumber) {
	    final boolean[] row = new boolean[NUM_TILES];
	    do {
	        row[rowNumber] = true;
	        rowNumber++;
	    } while (rowNumber % this.NUM_TILES_PER_COLUMN != 0 && rowNumber < NUM_TILES);
	    return row;
	}

	
	public Pawn getEnPassantPawn() {
		return this.enPassantPawn;
	}
	
	public int getNumberTiles() {
		return this.NUM_TILES;
	}
	
	public int getNumberRows() {
		return this.NUM_TILES_PER_ROW;
	}
	
	public int getNumberColumns() {
		return NUM_TILES_PER_COLUMN;
	}
	
	public boolean canCastle() {
		return canCastle;
	}
	public boolean canPawnJump() {
		return canPawnJump;
	}
	
	public boolean canPromote() {
		return canPromote;
	}
	
	public boolean canEnPassant() {
		return canEnPassant;
	}
	public boolean isValidTileCoordinate(final int coordinate) {
		return coordinate >= 0 && coordinate < NUM_TILES;
	}
	
	public BluePlayer getBluePlayer() {
		return this.bluePlayer;
	} 
	public RedPlayer getRedPlayer() {
		return this.redPlayer;
	}
	
	public WhitePlayer getWhitePlayer() {
		return this.whitePlayer;
	}
	
	public BlackPlayer getBlackPlayer() {
		return this.blackPlayer;
	}
	
	
	public void updatePieceValues() {
		for(Piece piece: calculateActivePieces(gameBoard, Alliance.WHITE)) {
			piece.getPieceType().updatePieceValue();
		}
		for(Piece piece: calculateActivePieces(gameBoard, Alliance.BLACK)) {
			piece.getPieceType().updatePieceValue();
		}
		for(Piece piece: calculateActivePieces(gameBoard, Alliance.RED)) {
			piece.getPieceType().updatePieceValue();
		}
		for(Piece piece: calculateActivePieces(gameBoard, Alliance.BLUE)) {
			piece.getPieceType().updatePieceValue();
		}
	}
	@Override
 	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < this.NUM_TILES; i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if((i+1) % this.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	protected Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
		final List<Move> legalMoves = new ArrayList<>();
		if(Table.get() !=null) {
			if(Table.get().getMoveType() == MoveType.Swarm) {
				for(final Piece piece: pieces) {
					boolean hasBeenMoved = false;
					for(int i = 0; i < builder.movedPieces.size(); i++) {
						System.out.println(builder.movedPieces.get(i));
						if(piece.equals(builder.movedPieces.get(i))) {
							hasBeenMoved = true;
						}
					}
					if(!hasBeenMoved) {
						legalMoves.addAll(piece.calculateLegalMoves(this));
					}
				}
				return Collections.unmodifiableList(legalMoves);
			}
		}
		for(final Piece piece: pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return Collections.unmodifiableList(legalMoves);
	}

	protected static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance aliance) {
		final List<Piece> activePieces = new ArrayList<>();
		
		for(final Tile tile: gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getAlliance() == aliance) {
					activePieces.add(piece);
				}
			}
		}
		return Collections.unmodifiableList(activePieces);
	}

	public Tile getTile(final int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}

	
	protected static List<Tile> createGameBoard(final Builder builder, Board board) {
		final List<Tile> tiles = new ArrayList<Tile>(board.NUM_TILES);
		if(!(Table.get() == null)) {
			if(!(Table.get().getPlayerCount() == PlayerCount.Regular)) {
				for(int i = 0; i <board.NUM_TILES; i++) {
					if(board.isNullArea()[i]) {
						tiles.add(new NullTile(i));
					} else {
						if(builder.disapereadConfig.get(i) != null) {
							// tiles.add(Tile.DISAPPEARED_TILES.get(i));
							tiles.add(new NullTile(i));
						} else {
							tiles.add(Tile.createTile(i, builder.boardConfig.get(i)));
						}
					}
				}
			} else {
				for(int i = 0; i <board.NUM_TILES; i++) {
					// System.out.println(builder.disapereadConfig.get(i) + " " + i);
					if(builder.disapereadConfig.get(i) != null) {
						// tiles.add(new DisappearedTile(i));
						// System.out.println("here");
						tiles.add(new DisappearedTile(i));
						// System.out.println(new DisappearedTile(i).toString());
						// tiles.add(new DisappearedTile(i));
					} else {
						tiles.add(Tile.createTile(i, builder.boardConfig.get(i)));
					}
				}
			}
		} else {
			for(int i = 0; i <board.NUM_TILES; i++) {
				if(builder.disapereadConfig.get(i) != null) {
					// tiles.add(Tile.DISAPPEARED_TILES.get(i));
					tiles.add(new NullTile(i));
				} else {
					tiles.add(Tile.createTile(i, builder.boardConfig.get(i)));
				}
			}
		}
		return Collections.unmodifiableList(tiles);
	}
	protected boolean[] isNullArea() {
	    final boolean[] nullArea = new boolean[NUM_TILES];
	    if(numberOfRowsUsed == 0) {
	    	return nullArea;
	    }
	    int centerStartCol = NUM_TILES_PER_COLUMN - 
	    		(numberOfRowsUsed+1) - (NUM_TILES_PER_COLUMN - 2*(numberOfRowsUsed+1));
	    int centerEndCol = NUM_TILES_PER_COLUMN -(numberOfRowsUsed+1)-1;
	    int centerStartRow =NUM_TILES_PER_COLUMN - 
	    		(numberOfRowsUsed+1) - (NUM_TILES_PER_COLUMN - 2*(numberOfRowsUsed+1));
	    int centerEndRow = NUM_TILES_PER_COLUMN -(numberOfRowsUsed+1)-1;

	    for (int i = 0; i < NUM_TILES; i++) {
	        int row = i / NUM_TILES_PER_ROW;
	        int col = i % NUM_TILES_PER_ROW;

	        // Mark null areas outside the plus shape
	        if (!((col >= centerStartCol && col <= centerEndCol) || (row >= centerStartRow && row <= centerEndRow))) {
	            nullArea[i] = true; // Outside the cross
	        } else {
	            nullArea[i] = false; // Part of the cross
	        }
	    }
	    return nullArea;
	}

	
	public static void createPawnLine(Builder builder, Alliance alliance, int startRow, int numRow) {
		int startingTile = (startRow-1)*numRow;
		for(int i = startingTile; i < (startingTile+numRow); i++) {
			builder.setPiece(new Pawn(alliance, i, true));
		}
	}
	
	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public Player whitePlayer() {
		return this.whitePlayer;
	}

	public Player blackPlayer() {
		return this.blackPlayer;
	}

	public Player currentPlayer() {
		return this.currentPlayer;
	}
	
	// Maybe problem
	public List<Move> getAllLegalMoves() {
		List<Move> list = new ArrayList<>();
		list.addAll(this.whitePlayer.getLegalMoves());
		list.addAll(this.blackPlayer.getLegalMoves());
		if(redPlayer != null) {
			list.addAll(this.redPlayer.getLegalMoves());
			list.addAll(this.bluePlayer.getLegalMoves());
		}
		return Collections.unmodifiableList(list);
	}
	
	public static class Builder{
		Map<Integer, Piece> boardConfig;
		Map<Integer, Boolean> disapereadConfig;
		Map<Integer, Piece> movedPieces;
		public Alliance nextMoveMaker;
		public Pawn enPassantPawn;
		public Piece lastPieceMoved;
		public boolean isLastMoveAttack;
		public int countCurrentMoveMaker;
		public int countPreviousMoveMaker;

		public Builder() {
			this.boardConfig = new HashMap<>();
			this.disapereadConfig = new HashMap<>();
			this.movedPieces = new HashMap<>();
			countCurrentMoveMaker =1;
			countPreviousMoveMaker =0;
			if(lastPieceMoved == null) {
				lastPieceMoved = new NullPiece(Alliance.WHITE,1, true);
			}
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}

		private Builder placeOldDisappeared(final Board board) {
			for(int i = 0; i < board.getNumberTiles(); i++) {
				if(board.getDisappearedConfig().get(i) != null) {
					this.disapereadConfig.put(i, true);
				}
			}
			return this;
		}
		public Builder setMovedPiece(Piece movedPiece) {
			this.movedPieces.put(this.movedPieces.size(), movedPiece);
			return this;
		}

		private Builder forwardPropagateMovedPieces(final Board board) {
			for(int i = 0; i < board.builder.movedPieces.size(); i++) {
				this.movedPieces.put(i, board.builder.movedPieces.get(i));
			}
			return this;
		}
		public Builder forwardPropagateData(final Board board) {
			placeOldDisappeared(board);
			forwardPropagateMovedPieces(board);
			countCurrentMoveMaker = board.builder.countCurrentMoveMaker;
			countPreviousMoveMaker = board.builder.countPreviousMoveMaker;
			return this;
		}
		
		public Builder setDisapeared(final int tileId) {
			this.disapereadConfig.put(tileId, true);
			return this;
		}

		public Builder setMoveMaker(final Alliance nextMoveMaker, Board board) {
			if(board.builder.nextMoveMaker == nextMoveMaker) {
				countCurrentMoveMaker ++;
			} else {
				countPreviousMoveMaker = countCurrentMoveMaker;
				countCurrentMoveMaker = 1;
			}
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		public Builder setFirstMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		public Board build() {
			return new Board(this, 8, 8, true, true, true, true);
		}

		public void setEnPassantPawn(Pawn enPassantPawn) {
			this.enPassantPawn = enPassantPawn;
		}
		
		public void lastPieceMoved(Piece piece) {
			lastPieceMoved=piece;
		}
		public void setIsLastMoveAttack(boolean value) {
			isLastMoveAttack = value;
		}

		public void removePieces(Player player) {
		    // Iterate through the player's active pieces
		    for (Piece piece : player.getActivePieces()) {
		        // Remove each piece from the board configuration
		        this.boardConfig.remove(piece.getPiecePosition());
		    }
		}

	}

	public ArrayList<Piece> getPieces() {
		ArrayList<Piece> pieces = new ArrayList<>();
		pieces.addAll(calculateActivePieces(this.gameBoard, Alliance.BLACK));
		pieces.addAll(calculateActivePieces(this.gameBoard, Alliance.WHITE));
		pieces.addAll(calculateActivePieces(this.gameBoard, Alliance.RED));
		pieces.addAll(calculateActivePieces(this.gameBoard, Alliance.BLUE));
		return pieces;
	}
	public Player bluePlayer() {
		return this.bluePlayer;
	}
	public Player redPlayer() {
		return this.redPlayer;
	}
	public Collection<Piece> getRedPieces() {
		return this.redPieces;
	}
	public Collection<Piece> getBluePieces() {
		return this.bluePieces;
	}
}
