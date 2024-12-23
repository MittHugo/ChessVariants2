package com.chess.engine.board.variants;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.Vectors.MotionType;
import com.chess.gui.Table;

public class ChessWithDifferentArmies extends Board {

	public ChessWithDifferentArmies(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}

	public static Board createBoard() {
		final ChessWithDifferentArmiesBuilder builder = new ChessWithDifferentArmiesBuilder();
		// Black Layout
		new ChessWithDifferentArmiesSetup(Table.get().getGameFrame(), true);
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Rook(Alliance.BLACK, 7, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		// White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
		builder.setPiece(new Knight(Alliance.WHITE, 57, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
		builder.setPiece(new Rook(Alliance.WHITE, 63, true));

		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class ChessWithDifferentArmiesBuilder extends Builder {
		@Override
		public Board build() {
			return new ChessWithDifferentArmies(this);
		}
	}

	public static class Bede extends Piece {

		protected Bede(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.ROOK, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Bede movePiece(Move move) {
			return new Bede(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = { new Vectors(board.getNumberColumns() - 1, MotionType.Itterative),
					new Vectors(board.getNumberColumns() + 1, MotionType.Itterative),
					new Vectors(-board.getNumberColumns() - 1, MotionType.Itterative),
					new Vectors(-board.getNumberColumns() + 1, MotionType.Itterative),
					new Vectors(board.getNumberColumns() * 2, MotionType.Jump),
					new Vectors(-board.getNumberColumns() * 2, MotionType.Jump), new Vectors(2, MotionType.Jump),
					new Vectors(-2, MotionType.Jump), };
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}

	public static class Waffle extends Piece {

		protected Waffle(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.KNIGHT, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Waffle movePiece(Move move) {
			return new Waffle(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = { new Vectors(board.getNumberColumns() * 2 - 2, MotionType.Jump),
					new Vectors(-board.getNumberColumns() * 2 - 2, MotionType.Jump),
					new Vectors(board.getNumberColumns() * 2 + 2, MotionType.Jump),
					new Vectors(-board.getNumberColumns() * 2 + 2, MotionType.Jump), new Vectors(1, MotionType.Jump),
					new Vectors(-1, MotionType.Jump), new Vectors(-board.getNumberColumns(), MotionType.Jump),
					new Vectors(board.getNumberColumns(), MotionType.Jump), };
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}

	public static class FAD extends Piece {

		protected FAD(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.BISHOP, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public FAD movePiece(Move move) {
			return new FAD(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			Vectors[] vectors = { new Vectors(board.getNumberColumns() - 1, MotionType.Jump),
					new Vectors(board.getNumberColumns() + 1, MotionType.Jump),
					new Vectors(-board.getNumberColumns() - 1, MotionType.Jump),
					new Vectors(-board.getNumberColumns() + 1, MotionType.Jump),
					new Vectors(board.getNumberColumns() * 2 - 2, MotionType.Jump),
					new Vectors(board.getNumberColumns() * 2 + 2, MotionType.Jump),
					new Vectors(-board.getNumberColumns() * 2 + 2, MotionType.Jump),
					new Vectors(-board.getNumberColumns() * 2 - 2, MotionType.Jump),
					new Vectors(board.getNumberColumns() * 2, MotionType.Jump),
					new Vectors(-board.getNumberColumns() * 2, MotionType.Jump), new Vectors(2, MotionType.Jump),
					new Vectors(-2, MotionType.Jump), };
			return PieceUtils.moveMaker(vectors, board, this);
		}
	}

	public static class ChessWithDifferentArmiesSetup extends JDialog {
		Alliance alliance;

		public enum ArmyTypes {
			ColorboundClobberers, NuttyKnights, RemarkableRookies, FIDE,
		}

		ArmyTypes whiteArmy;
		ArmyTypes blackArmy;

		public ChessWithDifferentArmiesSetup(final JFrame frame, final boolean modal) {
			super(frame, modal);
			alliance = Alliance.WHITE;
			setVisible(true);
			final JPanel myPanel = new JPanel(new GridLayout(0, 1));
			final JButton clobberers = new JButton("Colorbound Clobberers");
			final JButton knights = new JButton("Nutty Knights");
			final JButton rookies = new JButton("Remarkable Rookies");
			final JButton FIDE = new JButton("Nutty Knights");

			clobberers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (alliance.isWhite()) {
						whiteArmy = ArmyTypes.ColorboundClobberers;
						alliance = Alliance.BLACK;
					} else {
						blackArmy = ArmyTypes.ColorboundClobberers;
						ChessWithDifferentArmiesSetup.this.setVisible(false);
					}
				}
			});

			knights.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (alliance.isWhite()) {
						whiteArmy = ArmyTypes.NuttyKnights;
						alliance = Alliance.BLACK;
					} else {
						blackArmy = ArmyTypes.NuttyKnights;
						ChessWithDifferentArmiesSetup.this.setVisible(false);
					}
				}
			});

			rookies.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (alliance.isWhite()) {
						whiteArmy = ArmyTypes.RemarkableRookies;
						alliance = Alliance.BLACK;
					} else {
						blackArmy = ArmyTypes.RemarkableRookies;
						ChessWithDifferentArmiesSetup.this.setVisible(false);
					}
				}
			});

			FIDE.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	            	if(alliance.isWhite()) {
	            		whiteArmy = ArmyTypes.FIDE;
	            		alliance= Alliance.BLACK;
	            	} else {
	            		blackArmy = ArmyTypes.FIDE;
	            		ChessWithDifferentArmiesSetup.this.setVisible(false);
	            	}
				}
			});

			myPanel.add(clobberers);
			myPanel.add(knights);

			setLocationRelativeTo(frame);
			pack();
			setVisible(false);
		}

		void promptUser() {
			setVisible(true);
			repaint();
		}
	}
}
