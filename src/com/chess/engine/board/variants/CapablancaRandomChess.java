package com.chess.engine.board.variants;

import java.util.ArrayList;
import java.util.Random;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Archbishop;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.Chancellor;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;

public class CapablancaRandomChess extends Board {

	public CapablancaRandomChess(Builder builder) {
		// does castle - need to fix
		super(builder, 8, 10, false, true, true, true);
	}
	
	public static Board createBoard() {
		final CapablancaRandomBuilder builder = new CapablancaRandomBuilder();
		
		createPawnLine(builder, Alliance.BLACK, 2,10);
		createPawnLine(builder, Alliance.WHITE, 7,10);
		
		ArrayList<Integer> whiteSquares = new ArrayList<>();
		whiteSquares.add(0);
		whiteSquares.add(2);
		whiteSquares.add(4);
		whiteSquares.add(6);
		whiteSquares.add(8);
		
		Random random = new Random();
		int whiteBishopRandomIndex = random.nextInt(whiteSquares.size());
		builder.setPiece(new Bishop(Alliance.BLACK, whiteSquares.get(whiteBishopRandomIndex), true));
		builder.setPiece(new Bishop(Alliance.WHITE, whiteSquares.get(whiteBishopRandomIndex)+70, true));
		whiteSquares.remove(whiteBishopRandomIndex);
		
		ArrayList<Integer> blackSquares = new ArrayList<>();
		blackSquares.add(1);
		blackSquares.add(3);
		blackSquares.add(5);
		blackSquares.add(7);
		blackSquares.add(9);
		int blackBishopRandomIndex = random.nextInt(blackSquares.size());
		builder.setPiece(new Bishop(Alliance.BLACK, blackSquares.get(blackBishopRandomIndex), true));
		builder.setPiece(new Bishop(Alliance.WHITE, blackSquares.get(blackBishopRandomIndex)+70, true));
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
			for(int i = squares.get(kingRandomIndex); i < 9; i ++) {
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
		builder.setPiece(new King(Alliance.WHITE, squares.get(kingRandomIndex)+70, true));
		squares.remove(kingRandomIndex);
		
		int leftRookRandomIndex = random.nextInt(leftRookPose.size());
		builder.setPiece(new Rook(Alliance.BLACK, leftRookPose.get(leftRookRandomIndex), true));
		builder.setPiece(new Rook(Alliance.WHITE, leftRookPose.get(leftRookRandomIndex)+70, true));
		
		int rightRookRandomIndex = random.nextInt(rightRookPose.size());
		builder.setPiece(new Rook(Alliance.BLACK, rightRookPose.get(rightRookRandomIndex), true));
		builder.setPiece(new Rook(Alliance.WHITE, rightRookPose.get(rightRookRandomIndex)+70, true));
		
		squares.remove(squares.indexOf(leftRookPose.get(leftRookRandomIndex)));
		squares.remove(squares.indexOf(rightRookPose.get(rightRookRandomIndex)));
		
		int knight1Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.BLACK, squares.get(knight1Index), true));
		builder.setPiece(new Knight(Alliance.WHITE, squares.get(knight1Index)+70, true));
		squares.remove(knight1Index);
		
		int knight2Index = random.nextInt(squares.size());
		builder.setPiece(new Knight(Alliance.BLACK, squares.get(knight2Index), true));
		builder.setPiece(new Knight(Alliance.WHITE, squares.get(knight2Index)+70, true));
		squares.remove(knight2Index);
		int queenIndex = random.nextInt(squares.size());
		builder.setPiece(new Queen(Alliance.BLACK, squares.get(queenIndex), true));
		builder.setPiece(new Queen(Alliance.WHITE, squares.get(queenIndex)+70, true));
		squares.remove(queenIndex);
		int chancelorIndex = random.nextInt(squares.size());
		builder.setPiece(new Chancellor(Alliance.BLACK, squares.get(chancelorIndex), true));
		builder.setPiece(new Chancellor(Alliance.WHITE, squares.get(chancelorIndex)+70, true));
		squares.remove(chancelorIndex);
		int archbishopIndex = random.nextInt(squares.size());
		builder.setPiece(new Archbishop(Alliance.BLACK, squares.get(archbishopIndex), true));
		builder.setPiece(new Archbishop(Alliance.WHITE, squares.get(archbishopIndex)+70, true));
		squares.remove(archbishopIndex);
		
		
		builder.setMoveMaker(Alliance.WHITE);
		return builder.build();
	}

	public static class CapablancaRandomBuilder extends Builder {
		@Override
		public Board build() {
			return new CapablancaRandomChess(this);
		}
	}
}
