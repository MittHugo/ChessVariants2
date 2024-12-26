package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Piece.PieceType;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class Transcendental extends Board {
	
	public Transcendental(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
//		System.out.println(this.getAllLegalMoves().size());
		addWhiteMoves(calculateLegalTransposeMoves(whitePieces));
//		System.out.println(this.getAllLegalMoves().size());
		addBlackMoves(calculateLegalTransposeMoves(blackPieces));
	}
	
	public static Board createBoard() {
		final TranscendentalBuilder builder = new TranscendentalBuilder();
		
		createPawnLine(builder, Alliance.BLACK, 2,8);
		createPawnLine(builder, Alliance.WHITE, 7,8);
		
		ArrayList<Integer> whiteSquares = new ArrayList<>();
		whiteSquares.add(0);
		whiteSquares.add(2);
		whiteSquares.add(4);
		whiteSquares.add(6);
		
		Random random = new Random();
		int whiteBishopRandomIndex = random.nextInt(whiteSquares.size());
		builder.setPiece(new Bishop(Alliance.BLACK, whiteSquares.get(whiteBishopRandomIndex), true));
		whiteSquares.remove(whiteBishopRandomIndex);
		
		ArrayList<Integer> blackSquares = new ArrayList<>();
		blackSquares.add(1);
		blackSquares.add(3);
		blackSquares.add(5);
		blackSquares.add(7);
		int blackBishopRandomIndex = random.nextInt(blackSquares.size());
		builder.setPiece(new Bishop(Alliance.BLACK, blackSquares.get(blackBishopRandomIndex), true));
		blackSquares.remove(blackBishopRandomIndex);
		
		ArrayList<Integer> squares = new ArrayList<>();
		squares.addAll(blackSquares);
		squares.addAll(whiteSquares);
		
		int kingRandomIndex = random.nextInt(squares.size());
		builder.setPiece(new King(Alliance.BLACK, squares.get(kingRandomIndex), true));
		squares.remove(kingRandomIndex);
		
		int rook1Index = random.nextInt(squares.size());
		builder.setPiece(new Rook(Alliance.BLACK, squares.get(rook1Index), true));
		squares.remove(rook1Index);
		
		int rook2Index = random.nextInt(squares.size());
		builder.setPiece(new Rook(Alliance.BLACK, squares.get(rook2Index), true));
		squares.remove(rook2Index);
		
		int knight1Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.BLACK, squares.get(knight1Index), true));
		squares.remove(knight1Index);
		
		int knight2Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.BLACK, squares.get(knight2Index), true));
		squares.remove(knight2Index);
		int queenIndex = random.nextInt(squares.size());
		builder.setPiece(new Queen(Alliance.BLACK, squares.get(queenIndex), true));
		squares.remove(queenIndex);
		
		whiteSquares.clear();
		whiteSquares.add(56);
		whiteSquares.add(2+56);
		whiteSquares.add(4+56);
		whiteSquares.add(6+56);
		
		whiteBishopRandomIndex = random.nextInt(whiteSquares.size());
		builder.setPiece(new Bishop(Alliance.WHITE, whiteSquares.get(whiteBishopRandomIndex), true));
		whiteSquares.remove(whiteBishopRandomIndex);
		
		blackSquares.clear();
		blackSquares.add(1+56);
		blackSquares.add(3+56);
		blackSquares.add(5+56);
		blackSquares.add(7+56);
		blackBishopRandomIndex = random.nextInt(blackSquares.size());
		builder.setPiece(new Bishop(Alliance.WHITE, blackSquares.get(blackBishopRandomIndex), true));
		blackSquares.remove(blackBishopRandomIndex);
		
		squares.clear();
		squares.addAll(blackSquares);
		squares.addAll(whiteSquares);
		
		kingRandomIndex = random.nextInt(squares.size());
		builder.setPiece(new King(Alliance.WHITE, squares.get(kingRandomIndex), true));
		squares.remove(kingRandomIndex);
		
		rook1Index = random.nextInt(squares.size());
		builder.setPiece(new Rook(Alliance.WHITE, squares.get(rook1Index), true));
		squares.remove(rook1Index);
		
		rook2Index = random.nextInt(squares.size());
		builder.setPiece(new Rook(Alliance.WHITE, squares.get(rook2Index), true));
		squares.remove(rook2Index);
		
		knight1Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.WHITE, squares.get(knight1Index), true));
		squares.remove(knight1Index);
		
		knight2Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.WHITE, squares.get(knight2Index), true));
		squares.remove(knight2Index);
		queenIndex = random.nextInt(squares.size());
		builder.setPiece(new Queen(Alliance.WHITE, squares.get(queenIndex), true));
		squares.remove(queenIndex);
		
		builder.setFirstMoveMaker(Alliance.WHITE);
		return builder.build();
	}

	
	public Collection<Move> calculateLegalTransposeMoves(Collection<Piece> pieces) {
		Collection<Move> moves = new ArrayList<>();
		for(Piece piece: pieces) {
			if(piece.getPieceType() != PieceType.PAWN) {
				if(piece.getAlliance() == Alliance.WHITE) {
					int[] backWhiteRow = {56, 57, 58, 59, 60, 61, 62, 63};
					for(int pos: backWhiteRow) {
						if(pos != piece.getPiecePosition()) {
							if(this.whiteInStartingPosition(this)) {
								moves.add(new TransposeMove(this, piece, pos,  this.getTile(pos).getPiece()));
							}
						}
					}
				} else {
					int[] backBlackRow = {0, 1, 2, 3, 4, 5, 6, 7};
					for(int pos: backBlackRow) {
						if(pos != piece.getPiecePosition()) {
							if(this.blackInStartingPosition(this)) {
								moves.add(new TransposeMove(this, piece, pos,  this.getTile(pos).getPiece()));
							}
						}
					}
				}
			}
		}
		return moves;
	}
	public static class TransposeMove extends Move {
		Piece transposedPiece;
		Board board;
		Piece movedPiece;
		TransposeMove(Board board, Piece piece, int destinationCoordinate, Piece transposedPiece) {
			super(board, piece, destinationCoordinate);
			this.transposedPiece = transposedPiece;
			this.board = board;
			this.movedPiece =  piece;
		}
		
		@Override
		public Board execute(BuildHandler handler) {
			Builder builder = handler.createBuilder();
			for(final Piece piece: board.currentPlayer().getActivePieces()) {
				if(!this.movedPiece.equals(piece) && !this.transposedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			
			for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setPiece(this.transposedPiece.movePiece(new MajorMove(board, transposedPiece, movedPiece.getPiecePosition())));
			builder.setFirstMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}
	}
	
	public static class TranscendentalBuilder extends Builder {
		@Override
		public Board build() {
			return new Transcendental(this);
		}
	}
}
