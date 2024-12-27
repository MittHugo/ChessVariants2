package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.gui.Table;
import com.chess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.variants.JesterChess.Jester;

public abstract class Move {
	protected final Board board;
	protected final Piece movedPiece;
	protected final int destinationCoordinate;

	public static final Move NULL_MOVE = new NullMove();

	protected Move(final Board board, final Piece piece, final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = piece;
		this.destinationCoordinate = destinationCoordinate;
	}

	public Piece getMovedPiece() {
		return this.movedPiece;
	}

	public static final class MajorMove extends Move {

		public MajorMove(final Board board, final Piece piece, final int destinationCoordinate) {
			super(board, piece, destinationCoordinate);
		}
	}

	public static class AttackMove extends Move {
		final Piece attackedPiece;

		public AttackMove(final Board board, final Piece piece, final int destinationCoordinate,
				final Piece attackedPiece) {
			super(board, piece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}

		@Override
		public Board execute(BuildHandler handler) {
			Builder builder = handler.createBuilder();
			for (final Piece piece : this.board.getPieces()) {
				if (!this.movedPiece.equals(piece) && !this.attackedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			if(Table.get() != null) {
				if(Table.get().isRebirth()) {
					for(Piece piece: Table.get().getStartBoard().get().getPieces()) {
						if(attackedPiece.getPieceType() == PieceType.PAWN){
							if((attackedPiece.getPiecePosition()%board.getNumberColumns())==(piece.getPiecePosition()%board.getNumberColumns())
									&& piece.getAlliance() == attackedPiece.getAlliance() && 
									   piece.getPieceType() == attackedPiece.getPieceType() &&
									   !board.getTile(piece.getPiecePosition()).isTileOccupied()) {
								builder.setPiece(new Pawn(piece.getAlliance(), piece.getPiecePosition(), true));
								// attackedPiece.resetFirstMove();
							}
						}
						if(piece.getAlliance() == attackedPiece.getAlliance() && 
								piece.getPieceType() == attackedPiece.getPieceType()
								&& attackedPiece.getPieceType() != PieceType.PAWN) {
							if(!board.getTile(piece.getPiecePosition()).isTileOccupied()) {
								builder.setPiece(attackedPiece.movePiece(new MajorMove(board, attackedPiece, piece.getPiecePosition())));
								attackedPiece.resetFirstMove();
							}
						}
					}
				}
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			if (this.movedPiece.getPieceType() != PieceType.JESTER) {
				builder.lastPieceMoved(this.movedPiece.movePiece(this));
				builder.setIsLastMoveAttack(true);
			} else {
				Jester jester = (Jester) this.movedPiece;
				builder.lastPieceMoved(jester.replicatedMovedPiece());
				builder.setIsLastMoveAttack(builder.isLastMoveAttack);
			}
			if(Table.get().isChesireCat()) {
				builder.setDisapeared(this.movedPiece.getPiecePosition());
			}
			builder.forwardPropagateData(board);
			if(Table.get().isGryphon()) {
				BoardUtils.buildPiece(Table.get().getNextPieceForGryphonChess(movedPiece), builder, movedPiece.getAlliance(), destinationCoordinate);
			} else {
				builder.setMovedPiece(this.movedPiece.movePiece(this));
			}
			builder.setMoveMaker(this.board.currentPlayer().getNextPlayer().getAlliance(), board);
			
			return builder.build();
		}

		@Override
		public boolean isAttack() {
			return true;
		}

		@Override
		public Piece getAttackedPiece() {
			return this.attackedPiece;
		}

		@Override
		public int hashCode() {
			return this.attackedPiece.hashCode() + super.hashCode();
		}

		@Override
		public boolean equals(final Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof AttackMove)) {
				return false;
			}

			final AttackMove otherAttackMove = (AttackMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
		}
	}

	public static final class PawnMove extends Move {

		public PawnMove(final Board board, final Piece piece, final int destinationCoordinate) {
			super(board, piece, destinationCoordinate);
		}
	}

	public static final class PawnAttackMove extends AttackMove {

		public PawnAttackMove(final Board board, final Piece piece, final int destinationCoordinate,
				final Piece attackedPiece) {
			super(board, piece, destinationCoordinate, attackedPiece);
		}
	}

	public static final class PawnEnPassantAttackMove extends AttackMove {

		public PawnEnPassantAttackMove(final Board board, final Piece piece, final int destinationCoordinate,
				final Piece attackedPiece) {
			super(board, piece, destinationCoordinate, attackedPiece);
		}
	}

	public static final class PawnPromotion extends Move {

		final Move move;
		final Piece promotedPawn;

		public PawnPromotion(final Move move) {
			super(move.board, move.getMovedPiece(), move.getDestinationCoordinate());
			this.move = move;
			this.promotedPawn = move.getMovedPiece();
		}

		@Override
		public Board execute(BuildHandler handler) {
			Builder builder = handler.createBuilder();
			// ???
			final Board board = this.move.execute(handler);
			for (final Piece piece : this.board.getPieces()) {
				if (!this.promotedPawn.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			builder.setIsLastMoveAttack(true);
			builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
			if(this.movedPiece.getPieceType() != PieceType.JESTER) {
				builder.lastPieceMoved(this.movedPiece.movePiece(this));
				builder.setIsLastMoveAttack(false);
			} else {
				Jester jester = (Jester) this.movedPiece;
				builder.lastPieceMoved(jester.replicatedMovedPiece());
				builder.setIsLastMoveAttack(builder.isLastMoveAttack);
			}
			if(Table.get().isChesireCat()) {
				builder.setDisapeared(this.movedPiece.getPiecePosition());
			}
			builder.forwardPropagateData(board);
			if(Table.get().isGryphon()) {
				BoardUtils.buildPiece(Table.get().getNextPieceForGryphonChess(movedPiece), builder, movedPiece.getAlliance(), destinationCoordinate);
			} else {
				builder.setMovedPiece(this.movedPiece.movePiece(this));
			}
			builder.setMoveMaker(board.currentPlayer().getAlliance(), board);
			return builder.build();
		}

	}

	public static final class PawnJump extends Move {

		public PawnJump(final Board board, final Piece piece, final int destinationCoordinate) {
			super(board, piece, destinationCoordinate);
		}

		@Override
		public Board execute(BuildHandler handler) {
			Builder builder = handler.createBuilder();
			for (final Piece piece : this.board.getPieces()) {
				if (!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			if(Table.get().isChesireCat()) {
				builder.setDisapeared(this.movedPiece.getPiecePosition());
			}
			builder.forwardPropagateData(board);
			if(Table.get().isGryphon()) {
				BoardUtils.buildPiece(Table.get().getNextPieceForGryphonChess(movedPiece), builder, movedPiece.getAlliance(), destinationCoordinate);
			} else {
				builder.setMovedPiece(this.movedPiece.movePiece(this));
			}
			builder.setMoveMaker(this.board.currentPlayer().getNextPlayer().getAlliance(), board);
			if (this.movedPiece.getPieceType() != PieceType.JESTER) {
				builder.lastPieceMoved(this.movedPiece.movePiece(this));
				builder.setIsLastMoveAttack(false);
			} else {
				Jester jester = (Jester) this.movedPiece;
				builder.lastPieceMoved(jester.replicatedMovedPiece());
				builder.setIsLastMoveAttack(builder.isLastMoveAttack);
			}
			return builder.build();
		}
	}

	public static abstract class CastleMove extends Move {
		protected final Rook castleRook;
		protected final int caslteRookStart;
		protected final int castleRookDestination;

		public CastleMove(final Board board, final Piece piece, final int destinationCoordinate, final Rook castleRook,
				final int castleRookStart, final int castleRookDestination) {
			super(board, piece, destinationCoordinate);
			this.castleRook = castleRook;
			this.caslteRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}

		public Rook getCastleRook() {
			return this.castleRook;
		}

		@Override
		public boolean isCastlingMove() {
			return true;
		}

		@Override
		public Board execute(BuildHandler handler) {
			Builder builder = handler.createBuilder();
			for (final Piece piece : this.board.getPieces()) {
				if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
					builder.setPiece(piece);
				}
			}

			builder.setPiece(this.movedPiece.movePiece(this));
			if (this.movedPiece.getPieceType() != PieceType.JESTER) {
				builder.lastPieceMoved(this.movedPiece.movePiece(this));
				builder.setIsLastMoveAttack(false);
			} else {
				Jester jester = (Jester) this.movedPiece;
				builder.lastPieceMoved(jester.replicatedMovedPiece());
				builder.setIsLastMoveAttack(builder.isLastMoveAttack);
			}
			builder.setPiece(new Rook(this.castleRook.getAlliance(), this.castleRookDestination, false));
			if(Table.get().isChesireCat()) {
				builder.setDisapeared(this.movedPiece.getPiecePosition());
			}
			builder.forwardPropagateData(board);
			if(Table.get().isGryphon()) {
				BoardUtils.buildPiece(Table.get().getNextPieceForGryphonChess(movedPiece), builder, movedPiece.getAlliance(), destinationCoordinate);
			} else {
				builder.setMovedPiece(this.movedPiece.movePiece(this));
			}
			builder.setMoveMaker(this.board.currentPlayer().getNextPlayer().getAlliance(), board);
			return builder.build();
		}
	}

	public static final class KingSideCastleMove extends CastleMove {

		public KingSideCastleMove(final Board board, final Piece piece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, piece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
	}

	public static final class QueenSideCastleMove extends CastleMove {

		public QueenSideCastleMove(final Board board, final Piece piece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, piece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
	}

	public static final class NullMove extends Move {

		public NullMove() {
			super(null, null, -1);
		}

		@Override
		public Board execute(BuildHandler handler) {
			throw new RuntimeException("Cannot Execute Null Move");
		}
	}

	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}

	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePosition();
	}

	public Board execute(BuildHandler handler) {
//		System.out.println(board.currentPlayer().getAlliance());
		Builder builder = handler.createBuilder();
//		for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
//			if (!this.movedPiece.equals(piece)) {
//				builder.setPiece(piece);
//			}
//		}
//
//		for (final Piece piece : this.board.currentPlayer().getNextPlayer().getActivePieces()) {
//			builder.setPiece(piece);
//		}
		for (final Piece piece : this.board.getPieces()) {
			if (!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}

		builder.setPiece(this.movedPiece.movePiece(this));
		if (this.movedPiece.getPieceType() != PieceType.JESTER) {
			builder.lastPieceMoved(this.movedPiece.movePiece(this));
			builder.setIsLastMoveAttack(false);
		} else {
			Jester jester = (Jester) this.movedPiece;
			builder.lastPieceMoved(jester.replicatedMovedPiece());
			builder.setIsLastMoveAttack(builder.isLastMoveAttack);
		}
		if(Table.get().isChesireCat()) {
			builder.setDisapeared(this.movedPiece.getPiecePosition());
		}
		builder.forwardPropagateData(board);
			if(Table.get().isGryphon()) {
				BoardUtils.buildPiece(Table.get().getNextPieceForGryphonChess(movedPiece), builder, movedPiece.getAlliance(), destinationCoordinate);
			} else {
				builder.setMovedPiece(this.movedPiece.movePiece(this));
			}		builder.setMoveMaker(this.board.currentPlayer().getNextPlayer().getAlliance(), board);
		return builder.build();
	}

	public static class MoveFactory {
		private MoveFactory() {
			throw new RuntimeException("Not instantiable");
		}

		public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
			for (final Move move : board.getAllLegalMoves()) {
				if (move.getCurrentCoordinate() == currentCoordinate
						&& move.getDestinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}

	public static class AtomicMove extends Move {
    	public AtomicMove(final Board board, final Piece piece, final int destinationCoordinate) {
    		super(board, piece, destinationCoordinate);
    	}
    	
    	@Override 
    	public Board execute(BuildHandler handler) {
    		final int[] bombArray = {1, -1, board.getNumberColumns(), -board.getNumberColumns(), 
    				board.getNumberColumns()-1, board.getNumberColumns()+1, -board.getNumberColumns()-1, 
    				-board.getNumberColumns()+1, 0
    		};
    		List<Piece> affectedPieces = new ArrayList<>();
    		for(int canidatePostionOffset: bombArray) {
    			int canidatePosion = this.destinationCoordinate+ canidatePostionOffset;
    			if(board.isValidTileCoordinate(canidatePosion)) {
    				if((board.FIRST_COLUMN[this.destinationCoordinate] && (canidatePostionOffset == -board.getNumberColumns()-1 || 
    							canidatePostionOffset == -1 || canidatePostionOffset == board.getNumberColumns()-1))||
    						(board.LAST_COLUMN[this.destinationCoordinate] && (canidatePostionOffset == -board.getNumberColumns()+1 
    						|| canidatePostionOffset == 1 || canidatePostionOffset == board.getNumberColumns()+1))) {
    					continue;
    					
    				}
    				if(board.getTile(canidatePosion).isTileOccupied()) {
    					if(!board.getTile(canidatePosion).getPiece().getPieceType().isKing()) {
    						affectedPieces.add(board.getTile(canidatePosion).getPiece());
    					}
    				}
    			}
    		}
    		Builder builder = handler.createBuilder();
    		for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
    			boolean isExploded = false;
    			for(Piece pieceAffected: affectedPieces) {
    				if(pieceAffected.equals(piece)) {
    					isExploded = true;
    				}
    			}
    			if(!this.movedPiece.equals(piece) && !isExploded) {
    				builder.setPiece(piece);
    			}
    		}
    		
    		for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
    			boolean isExploded = false;
    			for(Piece pieceAffected: affectedPieces) {
    				if(pieceAffected.equals(piece)) {
    					isExploded = true;
    				}
    			}
    			if(!isExploded) {
    				builder.setPiece(piece);
    			}
    		}
			if(Table.get() != null) {
				if(Table.get().isRebirth()) {
					for(Piece piece: Table.get().getStartBoard().get().getPieces()) {
						for(Piece attackedPiece: affectedPieces) {
							if(attackedPiece.getPieceType() == PieceType.PAWN){
								if((attackedPiece.getPiecePosition()%board.getNumberColumns())==(piece.getPiecePosition()%board.getNumberColumns())
										&&piece.getAlliance() == attackedPiece.getAlliance() && 
										piece.getPieceType() == attackedPiece.getPieceType() &&
										!board.getTile(piece.getPiecePosition()).isTileOccupied()) {
									// builder.setPiece(attackedPiece.movePiece(new MajorMove(board, attackedPiece, piece.getPiecePosition())));
									// attackedPiece.resetFirstMove();
									builder.setPiece(new Pawn(piece.getAlliance(), piece.getPiecePosition(), true));
								}
							}
							if(piece.getAlliance() == attackedPiece.getAlliance() && 
									piece.getPieceType() == attackedPiece.getPieceType()
									&& attackedPiece.getPieceType() != PieceType.PAWN) {
								if(!board.getTile(piece.getPiecePosition()).isTileOccupied()) {
									builder.setPiece(attackedPiece.movePiece(new MajorMove(board, attackedPiece, piece.getPiecePosition())));
									attackedPiece.resetFirstMove();
								}
							}
						}
					}
				}
			}
			if(Table.get().isChesireCat()) {
				builder.setDisapeared(this.movedPiece.getPiecePosition());
			}
			builder.forwardPropagateData(board);
			if(Table.get().isGryphon()) {
				BoardUtils.buildPiece(Table.get().getNextPieceForGryphonChess(movedPiece), builder, movedPiece.getAlliance(), destinationCoordinate);
			} else {
				builder.setMovedPiece(this.movedPiece.movePiece(this));
			}
    		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance(), board);
    		return builder.build();
    	}
    }

    public boolean isAttack() {
		return false;
	}

	public boolean isCastlingMove() {
		return false;
	}

	public Piece getAttackedPiece() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}

		final Move otherMove = (Move) other;
		return destinationCoordinate == otherMove.getDestinationCoordinate()
				&& getMovedPiece().equals(otherMove.getMovedPiece());
	}



}
