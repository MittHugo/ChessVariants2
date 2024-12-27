package com.chess.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.variants.StandardBoard;
import com.chess.engine.board.variants.Variants;
import com.chess.engine.board.variants.Variants.VariantType;
import com.chess.engine.board.variants.StandardBoard.StandardBuilder;
//import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.Minimax;
import com.chess.engine.player.ai.MonteCarloTreeSearch;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.TeamMinimax;

public class Table extends Observable {
	private final GameSetup gameSetup;
	private final JFrame gameFrame;
	private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
	private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

	public static final String defaultPieceImagesPath = "art/pieces/warriors/";

	public static final Color lightTileColor = Color.decode("#FFFACD");
	public static final Color darkTileColor = Color.decode("#593E1A");
	private BoardPanel boardPanel;
	public Board chessBoard;
	public Supplier<Board> startBoard;
	public Variants currentVariant = Variants.STANDARD_BOARD;

	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	public BuildHandler handler;
	private static final Table INSTANCE = new Table();

	private Table() {

		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());

		this.handler = new BuildHandler<>(StandardBuilder.class);
		this.chessBoard = StandardBoard.createBoard();
		this.startBoard = () -> StandardBoard.createBoard();

		this.addObserver(new TableGameAIWatcher());
		this.boardPanel = new BoardPanel();
		this.gameSetup = new GameSetup(this.gameFrame, true);
		this.gameFrame.add(boardPanel);
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
//		final JMenuBar tableMenuBar = createVariantMenu();
		this.gameFrame.setJMenuBar(createVariantMenu());
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.gameFrame.setVisible(true);

	}

	public static Table get() {
		return INSTANCE;
	}

//	private JMenuBar createTableMenuBar() {
//		final JMenuBar tableMenuBar = new JMenuBar();
//		tableMenuBar.add(createFileMenu());
//		tableMenuBar.add(createVariantMenu());
//		return tableMenuBar;
//		
//	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open PGN");
			}
		});
		fileMenu.add(openPGN);

		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}

	private JMenuBar createVariantMenu() {
		final JMenuBar variantMenuBar = new JMenuBar();
		JMenu[] menus = { new JMenu("Home"), new JMenu("Home"), new JMenu("Home"), new JMenu("Home"), new JMenu("Home"),
				new JMenu("Home") };
		for (VariantType variantType : VariantType.values()) {
			menus[variantType.getIndex()] = new JMenu(variantType.toString());
		}
		for (Variants variant : Variants.values()) {
			final JMenuItem variantItem = new JMenuItem(variant.getName());
			variantItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					handler = variant.getHandler();
					chessBoard = variant.startBoard();
					startBoard = () -> variant.startBoard();
					currentVariant = variant;
					reset();
//					gameFrame.getContentPane().removeAll();
//					gameFrame.setLayout(new BorderLayout());
//					
//					
//					boardPanel = new BoardPanel();
//					gameFrame.add(boardPanel);
//					gameFrame.add(boardPanel, BorderLayout.CENTER);
//					final JMenuBar tableMenuBar = createVariantMenu();
//					gameFrame.setJMenuBar(tableMenuBar);
//					gameFrame.setSize(OUTER_FRAME_DIMENSION);
//					gameFrame.setVisible(true);
				}
			});
			menus[variant.varaintType().getIndex()].add(variantItem);
		}
		for (JMenu menu : menus) {
			variantMenuBar.add(menu);
		}

		JMenu optionsMenu = new JMenu("Options");
//		JMenuItem setUpGameMenuItem = new JMenu("Setup Game");
//		setUpGameMenuItem.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("here alpha");
//	            Table.get().getGameSetup().promptUser();
//	            Table.get().setupUpdate(Table.get().getGameSetup());
//			}
//			
//		});	
		final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game", KeyEvent.VK_S);
		setupGameMenuItem.addActionListener(e -> {
			Table.get().getGameSetup().promptUser();
			Table.get().setupUpdate(Table.get().getGameSetup());
		});
		optionsMenu.add(setupGameMenuItem);
//		optionsMenu.add(setUpGameMenuItem);
		variantMenuBar.add(optionsMenu);
		return variantMenuBar;
	}

	public void reset() {
		gameFrame.getContentPane().removeAll();
		gameFrame.setLayout(new BorderLayout());

		chessBoard = startBoard.get();

		boardPanel = new BoardPanel();
		gameFrame.add(boardPanel);
		gameFrame.add(boardPanel, BorderLayout.CENTER);
		final JMenuBar tableMenuBar = createVariantMenu();
		gameFrame.setJMenuBar(tableMenuBar);
		gameFrame.setSize(OUTER_FRAME_DIMENSION);
		gameFrame.setVisible(true);
	}

	private static class TableGameAIWatcher implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer())
					&& !Table.get().getGameBoard().currentPlayer().isInCheckmate(Table.get().getHandler())) {// &&!Table.get().getGameBoard().currentPlayer().isInStalemate()
				final AIThinkTank thinkTank = new AIThinkTank();
				thinkTank.execute();
			}

//			if (Table.get().getGameBoard().currentPlayer().isInCheckmate(Table.get().getHandler())) {
////				JOptionPane.showMessageDialog(Table.get().getBoar)
//				// Put game over stuff
//				Table.get().resetChessBoard();
//				
//			}
//			if (Table.get().getGameBoard().currentPlayer().isInStalemate(Table.get().getHandler())) {
//
//			}
            if (Table.get().getGameBoard().currentPlayer().isInCheckmate(Table.get().getHandler())) {
//                JOptionPane.showMessageDialog(Table.get().getGameFrame(),
//                        "Game Over: Player " + Table.get().getGameBoard().currentPlayer() + " is in checkmate!", "Game Over",
//                        JOptionPane.INFORMATION_MESSAGE);
            	Table.get().reset();
            }

            if (Table.get().getGameBoard().currentPlayer().isInStalemate(Table.get().getHandler())) {
//                JOptionPane.showMessageDialog(Table.get().getGameFrame(),
//                        "Game Over: Player " + Table.get().getGameBoard().currentPlayer() + " is in stalemate!", "Game Over",
//                        JOptionPane.INFORMATION_MESSAGE);
            	Table.get().reset();
            }
		}

	}

	public BuildHandler getHandler() {
		return handler;
	}

	private static class AIThinkTank extends SwingWorker<Move, String> {

		@Override
		protected Move doInBackground() throws Exception {
			if (Table.get().getPlayerCount() == PlayerCount.FourTeams) {
				MoveStrategy minimax = new TeamMinimax();
				final Move bestMove = minimax.execute(Table.get().getGameBoard(),
						(Integer) Table.get().getGameSetup().getSearchDepth(), Table.get().getHandler());
				return bestMove;
			} 
//			else if(Table.get().getPlayerCount() == PlayerCount.FourOpponents){
//				MoveStrategy minimax = new MonteCarloTreeSearch();
//				final Move bestMove = minimax.execute(Table.get().getGameBoard(),
//						(Integer) Table.get().getGameSetup().getSearchDepth(), Table.get().getHandler());
//				return bestMove;
//			}
			else {
				MoveStrategy minimax = new Minimax();
				final Move bestMove = minimax.execute(Table.get().getGameBoard(),
						(Integer) Table.get().getGameSetup().getSearchDepth(), Table.get().getHandler());
				return bestMove;
			}
		}

		@Override
		public void done() {
			try {
				final Move bestMove = get();
//				Table.get().updateComputerMove(bestMove);
				Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer()
						.makeMove(bestMove, Table.get().getHandler()).getTransitionBoard());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected void setupUpdate(GameSetup gameSetup) {
		setChanged();
		notifyObservers(gameSetup);
	}

	public void updateGameBoard(Board board) {
		this.chessBoard = board;
	}

//	public void updateComputerMove(Move bestMove) {
//		// TODO Auto-generated method stub
//		
//	}
	public Board getGameBoard() {
		return chessBoard;
	}

	private GameSetup getGameSetup() {
		return this.gameSetup;
	}

	private class BoardPanel extends JPanel {
		final List<TilePanel> boardTiles;

		BoardPanel() {
			
			super(new GridLayout(chessBoard.getNumberRows(), chessBoard.getNumberColumns()));
			this.boardTiles = new ArrayList<>();

			for (int i = 0; i < chessBoard.getNumberTiles(); i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();

		}

		public void drawBoard(final Board board) {
			removeAll();
			for (final TilePanel tilePanel : boardTiles) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();

		}

	}

	private class TilePanel extends JPanel {
		private final int tileId;

		TilePanel(final BoardPanel boardPanel, final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor(chessBoard);
			assignTilePieceIcon(chessBoard);
			validate();
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if(Table.get().isEndGame()) {
						if(Table.get().getPlayerCount().isRegular()) {
							Table.get().reset();
						} else {
							Table.get().getGameBoard().getBuilder().removePieces((Table.get().getGameBoard().currentPlayer()));
						}
						
						return; 
					}
					if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer())) {
						return;
					}
					if (SwingUtilities.isRightMouseButton(e)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
					} else if (SwingUtilities.isLeftMouseButton(e)) {
						if (sourceTile == null) {
							// first click
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if (humanMovedPiece == null) {
								sourceTile = null;
							}
						} else {
							// second click
//							System.out.println("I got here");
							destinationTile = chessBoard.getTile(tileId);
							final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
									destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move, handler);
							System.out.println(transition.getMoveStatus());
							if (transition.getMoveStatus().isDone()) {
								chessBoard = transition.getTransitionBoard();
								sourceTile = null;
								destinationTile = null;
								humanMovedPiece = null;
							} else {
								sourceTile = chessBoard.getTile(tileId);
								humanMovedPiece = sourceTile.getPiece();
								if (humanMovedPiece == null) {
									sourceTile = null;
								}
							}
						}
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								if (gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
									Table.get().moveMadeUpdate(PlayerType.HUMAN);
								}
								boardPanel.drawBoard(chessBoard);
							}
						});
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer())) {
						return;
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer())) {
						return;
					}

				}

			});
		}

		public void drawTile(Board board) {
			assignTileColor(board);
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
			repaint();
		}

		private void highlightLegals(final Board board) {
			if (true) {
				for (final Move move : pieceLegalMoves(board)) {
					if (move.getDestinationCoordinate() == this.tileId) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File("art/pieces/misc/green_dot.png")))));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		private Collection<Move> pieceLegalMoves(final Board board) {
			if (humanMovedPiece != null && humanMovedPiece.getAlliance() == board.currentPlayer().getAlliance()) {
				// return humanMovedPiece.calculateLegalMoves(board);
				return findLegalMovesAccordingToPlayer(board, humanMovedPiece);
			}
			return Collections.emptyList();
		}

		private Collection<Move> findLegalMovesAccordingToPlayer(Board board, Piece piece) {
			List<Move> legalMoves = new ArrayList<>();
			for(Move move: board.getAllLegalMoves()) {
				if(piece.equals(move.getMovedPiece())) {
					legalMoves.add(move);
				}
			}
			return legalMoves;
		}

		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if (board.getTile(this.tileId).isTileOccupied()) {
//				try {
//					BufferedImage img = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
//							.getAlliance().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".gif"));
//					add(new JLabel(new ImageIcon(img)));
//				} catch (IOException e) {
//					try {
////						System.out.println(defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
////								.getAlliance().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".png");
//						BufferedImage img = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
//								.getAlliance().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".png"));
//						add(new JLabel(new ImageIcon(img)));
//					}catch (IOException ex) {
//						e.printStackTrace();
//						ex.printStackTrace();
//					}
//				}
				add(new JLabel(new ImageIcon(ImageColoring.getImage(board.getTile(this.tileId).getPiece()))));
			}
		}

		private void assignTileColor(Board board) {
			if (!(board.getTile(tileId).isNullTile()||board.getTile(tileId).isDisappeared())) {
				int rows = chessBoard.getNumberRows(); // Get the total number of rows
				int columns = chessBoard.getNumberColumns(); // Get the total number of columns

				int row = this.tileId / columns; // Determine the row based on the tile ID
				int col = this.tileId % columns; // Determine the column based on the tile ID

				// Check if the sum of row and column indices is even or odd to decide the color
				if ((row + col) % 2 == 0) {
					setBackground(lightTileColor); // Even sum results in light color
				} else {
					setBackground(darkTileColor); // Odd sum results in dark color
				}
			} else {
				setBackground(new Color(255, 255, 255));
			}

		}
	}

	enum PlayerType {
		HUMAN, COMPUTER
	}

	public enum PlayerCount {
		FourOpponents {
			@Override
			public boolean isFourPlayerOpponents() {
				return true;
			}

			@Override
			public boolean isFourPlayerTeams() {
				return false;
			}

			@Override
			public boolean isRegular() {
				return false;
			}
		}, FourTeams {
			@Override
			public boolean isFourPlayerOpponents() {
				return false;
			}

			@Override
			public boolean isFourPlayerTeams() {
				return true;
			}

			@Override
			public boolean isRegular() {
				return false;
			}
		}, Regular {
			@Override
			public boolean isFourPlayerOpponents() {
				return false;
			}

			@Override
			public boolean isFourPlayerTeams() {
				return false;
			}

			@Override
			public boolean isRegular() {
				return true;
			}
		};
		public abstract boolean isFourPlayerOpponents();
		public abstract boolean isFourPlayerTeams();
		public abstract boolean isRegular();
		
	}
	
	public enum ChessType {
		Regular {
			@Override
			public boolean isRegular() {
				return true;
			}
		}, AnitChess {
			@Override
			public boolean isRegular() {
				return false;
			}
		}, KingPromotion {
			@Override
			public boolean isRegular() {
				return false;
			}
		}, ConquerAll {
			@Override
			public boolean isRegular() {
				return false;
			}
		}, TripleCheck {
			@Override
			public boolean isRegular() {
				return false;
			}
		};
		public abstract boolean isRegular();
	}
	public enum MoveType implements TypeEnum {
		Regular {
			@Override
			public boolean isRegular() {
				return true;
			}
			
			@Override
			public String toString() {
				return "Regular";
			}
		}, DoubleMove {
			@Override
			public boolean isRegular() {
				return false;
			}
			
			@Override
			public String toString() {
				return "Double Move";
			}
		}, KungFu {
			@Override
			public boolean isRegular() {
				return false;
			}
			
			@Override
			public String toString() {
				return "Kung Fu";
			}
		}, Progressive {
			@Override
			public boolean isRegular() {
				return false;
			}
			
			@Override
			public String toString() {
				return "Progressive";
			}
		}, Swarm {
			@Override
			public boolean isRegular() {
				return false;
			}

			@Override
			public String toString() {
				return "Swarm";
			}
		}, Meddle {
			@Override
			public boolean isRegular() {
				return false;
			}

			@Override
			public String toString() {
				return "Meedle";
			}
		};
	}

	public PlayerCount getPlayerCount() {
		if(Table.get() != null) {
			if (gameSetup.getPlayerCount() != null) {
				return gameSetup.getPlayerCount();
			}
		}
		return PlayerCount.Regular;
	}
	protected boolean isEndGame() {
		if(Table.get().getChessType().isRegular() || Table.get().getChessType() == ChessType.AnitChess) {
			return Table.get().getGameBoard().currentPlayer().isInCheckmate(Table.get().getHandler()) ||
					Table.get().getGameBoard().currentPlayer().isInStalemate(Table.get().getHandler());
		} else if(Table.get().getChessType() == ChessType.ConquerAll) {
			return Table.get().getGameBoard().currentPlayer().getActivePieces().isEmpty();
		}
		return false;
	}

	public Supplier<Board> getStartBoard() {
		if(startBoard != null) {
			return startBoard;
		} else {
			return ()->StandardBoard.createBoard();
		}
	}
	public void resetChessBoard() {
		chessBoard = getStartBoard().get();
	}
    public ChessType getChessType() {
    	if(Table.get() != null ) {
        	if(gameSetup.getChessType() != null) {
        		return gameSetup.getChessType();
        	} 
    	}
    	return ChessType.Regular;
    }

	public MoveType getMoveType() {
		if(Table.get() != null) {
			if(gameSetup.getMoveType() != null) {
				return gameSetup.getMoveType();
			}
		}
		return MoveType.Regular;
	}

	public boolean isDoubleChess() {
		if(Table.get() != null) {
			return gameSetup.isDoubleArmy();
		}
		return false;
	}
	public boolean isLookingGlass() {
		if(Table.get() != null) {
			return gameSetup.isLookingGlass();
		}
		return false;
	}
	public boolean isChesireCat() {
		if(Table.get() != null) {
			return gameSetup.isChesireCat();
		}
		return false;
	}
	public boolean noAdders() {
		if(Table.get() != null) {
			return gameSetup.noAdders();
		}
		return true;
	}
	public boolean isAtomic() {
		if(Table.get() != null) {
			return gameSetup.isAtomic();
		}
		return false;
	}
	public boolean isGryphon() {
		if(Table.get() != null) {
			return gameSetup.isGryphon();
		}
		return false;
	}



	protected void moveMadeUpdate(PlayerType playerType) {
		setChanged();
		notifyObservers(playerType);
	}

	public JFrame getGameFrame() {
		return gameFrame;
	}
	// handler = variant.getHandler();
	// chessBoard = variant.startBoard();
	// startBoard = () -> variant.startBoard();
	public Class<? extends Piece> getNextPieceForGryphonChess(Piece piece){
		if(currentVariant == Variants.STANDARD_BOARD) {
			if(piece.getPieceType() == PieceType.PAWN) {
				return Knight.class;
			} else if(piece.getPieceType() == PieceType.KNIGHT) {
				return Bishop.class;
			} else if(piece.getPieceType() == PieceType.BISHOP) {
				return Rook.class;
			} else if(piece.getPieceType() == PieceType.ROOK) {
				return Queen.class;
			} else if(piece.getPieceType() == PieceType.QUEEN) {
				return King.class;
			} else if(piece.getPieceType() == PieceType.KING) {
				if(Table.get() != null) {
					if(Table.get().getChessType() == ChessType.ConquerAll) {
						return Pawn.class;
					}
				}
				return King.class;
			}
		}
		int lowestSeenValue = Integer.MAX_VALUE;
		Class<? extends Piece> pieceClass = null;
		for(Piece instancePiece: startBoard.get().getPieces()) {
			if(piece.getPieceType().getPieceValue() < instancePiece.getPieceType().getPieceValue() 
					&& instancePiece.getPieceType().getPieceValue() < lowestSeenValue) {
				lowestSeenValue = instancePiece.getPieceType().getPieceValue();
				pieceClass = instancePiece.getClass();
			}
		}
		if(lowestSeenValue == Integer.MAX_VALUE) {
			if(Table.get() != null) {
				if(Table.get().getChessType() == ChessType.ConquerAll) {
					return Pawn.class;
				}
			}
			return King.class;
		}
		return pieceClass;
	}
}
