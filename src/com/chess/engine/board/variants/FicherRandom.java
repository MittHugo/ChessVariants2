package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Random;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class FicherRandom extends Board {

	public FicherRandom(Builder builder) {
		// does castle - need to fix
		super(builder, 8, 8, false, true, true, true);
	}
	
	public static Board createBoard() {
		final FicherRandomBuilder builder = new FicherRandomBuilder();
		
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
		builder.setPiece(new Bishop(Alliance.WHITE, whiteSquares.get(whiteBishopRandomIndex)+56, true));
		whiteSquares.remove(whiteBishopRandomIndex);
		
		ArrayList<Integer> blackSquares = new ArrayList<>();
		blackSquares.add(1);
		blackSquares.add(3);
		blackSquares.add(5);
		blackSquares.add(7);
		int blackBishopRandomIndex = random.nextInt(blackSquares.size());
		builder.setPiece(new Bishop(Alliance.BLACK, blackSquares.get(blackBishopRandomIndex), true));
		builder.setPiece(new Bishop(Alliance.WHITE, blackSquares.get(blackBishopRandomIndex)+56, true));
		blackSquares.remove(blackBishopRandomIndex);
		
		ArrayList<Integer> squares = new ArrayList<>();
		squares.addAll(blackSquares);
		squares.addAll(whiteSquares);
		
		int kingRandomIndex = random.nextInt(squares.size());
		boolean hasOpenLower = false;
		boolean hasOpenHigher = false;
		ArrayList<Integer> leftRookPose = new ArrayList<>();
		ArrayList<Integer> rightRookPose = new ArrayList<>();
		do {
			for(int i = squares.get(kingRandomIndex); i > 0; i --) {
				if(squares.indexOf(i) !=-1 && i != squares.get(kingRandomIndex)) {
					hasOpenLower = true;
					leftRookPose.add(i);
				}
			}
			for(int i = squares.get(kingRandomIndex); i < 7; i ++) {
				if(squares.indexOf(i) !=-1 && i != squares.get(kingRandomIndex)) {
					hasOpenHigher = true;
					rightRookPose.add(i);
				}
			}
			if(!hasOpenLower || !hasOpenHigher) {
				 kingRandomIndex = random.nextInt(squares.size());
				 hasOpenLower=false;
				 hasOpenHigher = false;
				 leftRookPose.clear();
				 rightRookPose.clear();
			}
		}while(!hasOpenLower && !hasOpenHigher);
		builder.setPiece(new King(Alliance.BLACK, squares.get(kingRandomIndex), true));
		builder.setPiece(new King(Alliance.WHITE, squares.get(kingRandomIndex)+56, true));
		squares.remove(kingRandomIndex);
		
		int leftRookRandomIndex = random.nextInt(leftRookPose.size());
		builder.setPiece(new Rook(Alliance.BLACK, leftRookPose.get(leftRookRandomIndex), true));
		builder.setPiece(new Rook(Alliance.WHITE, leftRookPose.get(leftRookRandomIndex)+56, true));
		
		int rightRookRandomIndex = random.nextInt(rightRookPose.size());
		builder.setPiece(new Rook(Alliance.BLACK, rightRookPose.get(rightRookRandomIndex), true));
		builder.setPiece(new Rook(Alliance.WHITE, rightRookPose.get(rightRookRandomIndex)+56, true));
		
		squares.remove(squares.indexOf(leftRookPose.get(leftRookRandomIndex)));
		squares.remove(squares.indexOf(rightRookPose.get(rightRookRandomIndex)));
		
		int knight1Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.BLACK, squares.get(knight1Index), true));
		builder.setPiece(new Knight(Alliance.WHITE, squares.get(knight1Index)+56, true));
		squares.remove(knight1Index);
		
		int knight2Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.BLACK, squares.get(knight2Index), true));
		builder.setPiece(new Knight(Alliance.WHITE, squares.get(knight2Index)+56, true));
		squares.remove(knight2Index);
		int queenIndex = random.nextInt(squares.size());
		builder.setPiece(new Queen(Alliance.BLACK, squares.get(queenIndex), true));
		builder.setPiece(new Queen(Alliance.WHITE, squares.get(queenIndex)+56, true));
		squares.remove(queenIndex);
		
		builder.setMoveMaker(Alliance.WHITE);
		return builder.build();
	}

	public static class FicherRandomBuilder extends Builder {
		@Override
		public Board build() {
			return new FicherRandom(this);
		}
	}
}
