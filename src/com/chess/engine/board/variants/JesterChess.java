package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.BoardUtils.EmptyPiece;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.*;
import com.chess.engine.pieces.PieceUtils.NullPiece;

public class JesterChess extends Board {

	public JesterChess(Builder builder) {
		super(builder, 8, 8, true, true, true, true);
	}

	public static Board createBoard() {
		final JesterBuilder builder = new JesterBuilder();
		// Define a single array for the entire board layout
		Class<? extends Piece>[] boardSetup = new Class[] {
		    // Black Back Rank
		    Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class,
		    // Black Pawns and other pieces (Jesters)
		    Pawn.class, Pawn.class, Pawn.class, Jester.class, Jester.class, Pawn.class, Pawn.class, Pawn.class,
		    EmptyPiece.class, EmptyPiece.class, EmptyPiece.class, Pawn.class, 
		    Pawn.class, EmptyPiece.class, EmptyPiece.class, EmptyPiece.class,
		};

		// Assuming BoardUtils can handle the setup for arbitrary board sizes
		BoardUtils.buildBoard(boardSetup, builder, 8, 8, 100, Pawn.class);



		// white to move
		builder.setMoveMaker(Alliance.WHITE);

		return builder.build();
	}

	public static class JesterBuilder extends Builder {
		@Override
		public Board build() {
			return new StandardBoard(this);
		}
	}

	public static class Jester extends Piece {
		Piece replicatedMovedPiece;

		public Jester(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.JESTER, pieceAlliance, piecePosition, isFirstMove);
			replicatedMovedPiece = new NullPiece(this.pieceAlliance, 1, isFirstMove);
		}

		@Override
		public Jester movePiece(Move move) {
			return new Jester(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			replicatedMovedPiece = board.getBuilder().lastPieceMoved;
			Class pieceClass = board.getBuilder().lastPieceMoved.getClass();
			Piece piece;
			try {
				piece = (Piece) pieceClass.getDeclaredConstructor(Alliance.class, int.class, boolean.class)
						.newInstance(this.pieceAlliance, this.piecePosition, this.isFirstMove);
			} catch (Exception e) {
				throw new RuntimeException("Failed to create builder instance", e);
			}
			System.out.println(piece.getAlliance() + " " + this.pieceAlliance);
//			Piece piece = new Piece(board.getBuilder().lastPieceMoved.getPieceType(),this.pieceAlliance.getOppositeAlliance(), this.piecePosition, this.isFirstMove);
//			Piece piece = board.getBuilder().lastPieceMoved
//					.movePiece(new MajorMove(board, board.getBuilder().lastPieceMoved, this.piecePosition));
			List<Move> legalMoves = new ArrayList<>();
			Collection<Move> possibleMoves = piece.calculateLegalMoves(board);
			if (piece.getPieceType() != PieceType.PAWN) {
				for (Move move : possibleMoves) {
					if (!move.isAttack()) {
						legalMoves.add(new MajorMove(board, this, move.getDestinationCoordinate()));
					} else if (board.getBuilder().isLastMoveAttack) {
						legalMoves.add(
								new AttackMove(board, this, move.getDestinationCoordinate(), move.getAttackedPiece()));
					}
				}
			} else {
				Alliance alliance;
				if (piece.getAlliance() == Alliance.WHITE) {
					alliance = Alliance.BLACK;
				} else {
					alliance = Alliance.WHITE;
				}
				Piece pawnPiece = new Pawn(alliance, this.piecePosition, this.isFirstMove);
				for (Move move : pawnPiece.calculateLegalMoves(board)) {
					if (!move.isAttack()) {
						legalMoves.add(new MajorMove(board, this, move.getDestinationCoordinate()));
					} else if (board.getBuilder().isLastMoveAttack) {
						legalMoves.add(
								new AttackMove(board, this, move.getDestinationCoordinate(), move.getAttackedPiece()));
					}
				}
			}
			return legalMoves;
		}

		public Piece replicatedMovedPiece() {
			return replicatedMovedPiece;
		}
	}

	public static class Sage extends Piece {

		protected Sage(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(PieceType.SAGE, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Piece movePiece(Move move) {
			return new Sage(this.pieceAlliance, move.getDestinationCoordinate(), false);
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {

			return null;
		}
	}
}
