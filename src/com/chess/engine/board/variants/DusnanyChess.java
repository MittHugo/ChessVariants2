package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
//import com.chess.engine.board.variants.DusnanyChess.DusnanyPlayer;
import com.chess.engine.board.variants.GrasshoperChess.GrasshoperBuilder;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.BlackPlayerHandler;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.player.WhitePlayerHandler;
import com.chess.engine.player.variants.DusnanyPlayer;

public class DusnanyChess extends Board{
	final static WhitePlayerHandler whiteHandler = new WhitePlayerHandler<>(DusnanyPlayer.class);
	final static BlackPlayerHandler blackHandler = new BlackPlayerHandler<>(BlackPlayer.class);
	public DusnanyChess(Builder builder) {
		super(builder, 8, 8, true, true, true, true, 
				whiteHandler,blackHandler);
		
	}
	
	public static Board createBoard() {
		final DusnanyBuilder builder = new DusnanyBuilder();

        // Define major piece classes for an 8x8 board
        Class<? extends Piece>[] majorPieceClasses = new Class[] {
            Rook.class, Knight.class, Bishop.class, Queen.class,
            King.class, Bishop.class, Knight.class, Rook.class
        };

        // Use buildBoard for Black and White pieces
        BoardUtils.buildBoard(
            majorPieceClasses,       // Major piece classes for first and last rows
            builder,                 // Builder instance
            8,                       // Number of columns (8x8 board)
            8,                       // Number of rows
            2,                       // Black pawn row (row index 1)
            DusnanyPawn.class        // Use DusnanyPawn for White pawns
        );

        // Replace the DusnanyPawn setup dynamically
        for (int position = 40; position < 64; position++) {
            builder.setPiece(new DusnanyPawn(Alliance.WHITE, position, true));
        }
		
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class DusnanyBuilder extends Builder {
		@Override
		public DusnanyChess build() {
			return new DusnanyChess(this);
		}
	}
	
	public static class DusnanyPawn extends Pawn{

		public DusnanyPawn(final Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
			super(PieceType.DUSNANY_PAWN, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Collection<Move> calculateLegalMoves(final Board board) {
			int[] CANIDATE_MOVE_COORDINATE = {board.getNumberColumns()-1, board.getNumberColumns(), board.getNumberColumns()+1,
					board.getNumberColumns()*2};
			final List<Move> legalMoves = new ArrayList<>();
			for(final int currentCanidateOffset: CANIDATE_MOVE_COORDINATE) {
				int canidateDestinationCoordinate = this.piecePosition + currentCanidateOffset*pieceAlliance.getDirection();
				if(!board.isValidTileCoordinate(canidateDestinationCoordinate)) {
					continue;
				} 	
				if(currentCanidateOffset == board.getNumberColumns()) {	
					if(!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) &&
								board.canPromote()) {
							legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
						} else {
							legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
						}
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()*2) && this.isFirstMove() && board.canPawnJump()) {
					final int behindCanidateDestinationCoordinate = this.piecePosition +(this.pieceAlliance.getDirection()*board.getNumberColumns());
					if(!board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() &&
							!board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new PawnJump(board, this, canidateDestinationCoordinate));
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()-1) &&
						!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isWhite() || 
						 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isBlack())))) {
					if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance != pieceOnCanidate.getAlliance()) {
							if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board) && 
									board.canPromote()) {
								legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
								legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
							}
						}
					} 
					if(board.getEnPassantPawn() !=null) {
						if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getOppositeDirection()))) {
							final Piece pieceOnCanidate = board.getEnPassantPawn();
//							System.out.println(pieceOnCanidate.getPieceType());
							if(this.pieceAlliance != pieceOnCanidate.getAlliance()&& board.canEnPassant()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
							}
						}
					}
				} else if(currentCanidateOffset == (board.getNumberColumns()+1) &&
						!((board.LAST_COLUMN[piecePosition] && this.pieceAlliance.isBlack() || 
						 (board.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite())))) {
					if(board.getTile(canidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCanidate = board.getTile(canidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance != pieceOnCanidate.getAlliance()) {
							if(this.pieceAlliance.isPawnPromotionSquare(canidateDestinationCoordinate, board)
									&& board.canPromote()) {
								legalMoves.add(new PawnPromotion(new PawnMove(board, this, canidateDestinationCoordinate)));
							} else {
								legalMoves.add(new MajorMove(board, this, canidateDestinationCoordinate));
							}
						}
					}
					if(board.getEnPassantPawn() !=null) {
						if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.getAlliance().getDirection()))) {
							final Piece pieceOnCanidate = board.getEnPassantPawn();
//							System.out.println(pieceOnCanidate.getPieceType());
							if(this.pieceAlliance != pieceOnCanidate.getAlliance() && board.canEnPassant()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this,canidateDestinationCoordinate, pieceOnCanidate));
							}
						}
					}
				}
			}
			return Collections.unmodifiableList(legalMoves);
		}
		
		
		@Override 
		public String toString() {
			return PieceType.DUSNANY_PAWN.toString();
		}
		
		@Override
		public DusnanyPawn movePiece(final Move move) {
			return new DusnanyPawn(move.getMovedPiece().getAlliance(), move.getDestinationCoordinate(), false);
		}

		public Piece getPromotionPiece() {
			return new Queen(this.pieceAlliance, this.piecePosition, false);
		}

	}
	
}
