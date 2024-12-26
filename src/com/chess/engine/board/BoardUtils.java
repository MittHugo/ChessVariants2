package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table;
import com.chess.gui.Table.PlayerCount;

public class BoardUtils {
	public static <T extends Piece> void buildBoard(Class<? extends Piece>[] classes, Builder builder,
			int numTilesPerColumn, int numTilesPerRow, int blackPawnLine, Class<? extends Piece> pawnClass) {
		for (int i = 0; i < classes.length; i++) {
//			System.out.println(classes[i]);
			if (classes[i] != EmptyPiece.class) {
				buildPiece(classes[i], builder, Alliance.BLACK, i);
			}
		}
		if(blackPawnLine < 100) {
			createPawnLine(builder, Alliance.BLACK, blackPawnLine, numTilesPerColumn, pawnClass);
			createPawnLine(builder, Alliance.WHITE, numTilesPerRow - (blackPawnLine - 1), numTilesPerColumn, pawnClass);
		}
		int count = 0;
		for (int i = classes.length; i > 0; i--) {
			count ++;
			int position = 0;
			if (classes.length > numTilesPerColumn) {
				position = (numTilesPerRow * numTilesPerColumn) - count;
				if (classes[classes.length - i] != EmptyPiece.class) {
					buildPiece(classes[classes.length - i], builder, Alliance.WHITE, position);
				}
			} else {
				position = (numTilesPerRow * numTilesPerColumn) - (classes.length - i + 1);
				if (classes[i - 1] != EmptyPiece.class) {
					buildPiece(classes[i - 1], builder, Alliance.WHITE, position);
				}

			}
		}
	}
	public static <T extends Piece> void buildBoard(Class<? extends Piece>[] classes, Builder builder,
			int numTilesPerColumn, int numTilesPerRow, int blackPawnLine, Class<? extends Piece> pawnClass,
			int numberOfLines) {
		for (int i = 0; i < classes.length; i++) {
			if (classes[i] != EmptyPiece.class) {
				if(Table.get() != null) {
					if(Table.get().getPlayerCount() == PlayerCount.Regular) {
						buildPiece(classes[i], builder, Alliance.BLACK, i);
					} else {
						int desiredRow = 0;
						if(i>(numTilesPerColumn)-2*(numberOfLines+1)) {
							desiredRow = (int) i/((numTilesPerColumn)-2*(numberOfLines+1));
							buildPiece(classes[i], builder, Alliance.BLACK, (desiredRow)*numTilesPerColumn + 
									desiredRow*(numberOfLines+1)
									+ (i-1)%(numTilesPerColumn)-2*(numberOfLines+1)-1);
//							System.out.println((i)%(numTilesPerColumn)-2*(numberOfLines+1)-1);
						} else {
							buildPiece(classes[i], builder, Alliance.BLACK, i+numberOfLines+1);
						}
					}
				} else {
					buildPiece(classes[i], builder, Alliance.BLACK, i);
				}
			}
		}
		if(blackPawnLine < 100) {
			createPawnLine(builder, Alliance.BLACK, blackPawnLine, numTilesPerColumn, pawnClass);
			createPawnLine(builder, Alliance.WHITE, numTilesPerRow - (blackPawnLine - 1), numTilesPerColumn, pawnClass);
		}
		int count;
		int reCounter= 1;
		if(Table.get() != null) {
			if(Table.get().getPlayerCount() == PlayerCount.Regular) {
				count = 0;
			} else {
				count = numberOfLines+1;
			}
		} else {
			count = 0;
		}
		for (int i = classes.length; i > 0; i--) {
			count ++;
			if(Table.get() != null) {
				if(Table.get().getPlayerCount() != PlayerCount.Regular) {
					int desiredRow = 0;
					if((classes.length-i)>(numTilesPerColumn)-2*(numberOfLines+1)) {
						reCounter ++;
//						System.out.println((count-(numTilesPerColumn)-2*(numberOfLines+1)));
						desiredRow = (int) (classes.length-i)/((numTilesPerColumn)-2*(numberOfLines+1));
						count = numTilesPerColumn*desiredRow +desiredRow*(numberOfLines+1)
								+ (reCounter);
					}
				}
			}
			int position = 0;
			position = (numTilesPerColumn * numTilesPerColumn) - count;
			if (classes[classes.length - i] != EmptyPiece.class) {
				buildPiece(classes[classes.length - i], builder, Alliance.WHITE, position);
			}
		}
		if(Table.get() != null) {
			if(Table.get().getPlayerCount() != PlayerCount.Regular) {
				for(int i = 0; i < classes.length; i ++) {
					int desiredColumn =0;
					int position = i*numTilesPerColumn +numTilesPerColumn*(numberOfLines+1);
					if(i > (numTilesPerColumn)-2*(numberOfLines+1)) {
						desiredColumn = (int) i/((numTilesPerColumn)-2*(numberOfLines+1));
						position = (desiredColumn+numberOfLines)*numTilesPerColumn+ 
								numTilesPerColumn*(i%((numTilesPerColumn)-2*(numberOfLines+1)))+desiredColumn;
					}
					buildPiece(classes[i], builder, Alliance.RED, position);
					if(i > (numTilesPerColumn)-2*(numberOfLines+1)) {
						position +=numTilesPerColumn-2*desiredColumn-1;
					} else {
						position = (i+1)*numTilesPerColumn +numTilesPerColumn*(numberOfLines+1)-1;
					}
					buildPiece(classes[i], builder, Alliance.BLUE, position);
				}
				createPawnVerticalLine(builder, Alliance.RED, numberOfLines,numTilesPerColumn,pawnClass);
				createPawnVerticalLine(builder, Alliance.BLUE,numTilesPerColumn-numberOfLines+1,numTilesPerColumn,pawnClass);
			}
		}
	}

	public static <T extends Piece> void buildBoard(Class<? extends Piece>[] classes, Builder builder,
			int numTilesPerColumn, int numTilesPerRow, int blackPawnLine, Class<? extends Piece> pawnClass,
			Alliance alliance) {
		if (alliance == Alliance.BLACK) {
			for (int i = 0; i < classes.length; i++) {
//				System.out.println(classes[i]);
				if (classes[i] != EmptyPiece.class) {
					buildPiece(classes[i], builder, Alliance.BLACK, i);
				}
			}
			createPawnLine(builder, Alliance.BLACK, blackPawnLine, numTilesPerColumn, pawnClass);
		} else if (alliance == Alliance.WHITE) {
			createPawnLine(builder, Alliance.WHITE, numTilesPerRow - (blackPawnLine - 1), numTilesPerColumn, pawnClass);
			for (int i = classes.length; i > 0; i--) {
				if (classes[i - 1] != EmptyPiece.class) {
					int position;
					if (classes.length > numTilesPerColumn) {
						if (i > numTilesPerColumn) {
							position = (numTilesPerRow * numTilesPerColumn) - (i - numTilesPerColumn);
						} else {
							position = (numTilesPerRow * numTilesPerColumn) - (i + numTilesPerColumn);
						}
					} else {
						position = (numTilesPerRow * numTilesPerColumn) - (classes.length - i + 1);
					}
					buildPiece(classes[i - 1], builder, Alliance.WHITE, position);
				}
			}
		}
	}
	
	public static <T extends Piece> void betterBuildBoard(Class<? extends Piece>[] classes, Builder builder,
			int numTilesPerColumn, int numTilesPerRow, int blackPawnLine, Class<? extends Piece> pawnClass,
			int numberOfLines) {
		if(Table.get() != null) {
			if(Table.get().getPlayerCount() != PlayerCount.Regular) {
				BoardUtils.buildBoard(BoardUtils.convertClassToDouble(classes, numTilesPerColumn), builder, 
						BoardUtils.getDimensionFor4Player(BoardUtils.convertWidthToDouble(numTilesPerColumn), numberOfLines), 
						BoardUtils.getDimensionFor4Player(BoardUtils.convertWidthToDouble(numTilesPerColumn), numberOfLines),
						blackPawnLine, Pawn.class, numberOfLines);
			}else {
				BoardUtils.buildBoard(BoardUtils.convertClassToDouble(classes, numTilesPerColumn), builder,
					BoardUtils.convertWidthToDouble(numTilesPerColumn), numTilesPerRow, blackPawnLine, Pawn.class);
			}
		} else {
			BoardUtils.buildBoard(classes, builder, numTilesPerColumn, numTilesPerRow, blackPawnLine, Pawn.class);
		}
	}
	public static Class<? extends Piece>[] convertClassToDouble(Class<? extends Piece>[] classes, int numTilesPerColumn) {
		Class<? extends Piece>[] doubleClasses = new Class[classes.length*2];
		for (int i = 0; i < classes.length; i++) {
			if (i < numTilesPerColumn) {
				// First set of rows
				doubleClasses[i] = classes[i];
				doubleClasses[numTilesPerColumn + i] = classes[i];
			} else {
				// Rows beyond the first set
				int desiredRow = i / numTilesPerColumn;
				int adjustedIndex = i % numTilesPerColumn + (desiredRow * numTilesPerColumn * 2);
				doubleClasses[adjustedIndex] = classes[i];
				doubleClasses[adjustedIndex+numTilesPerColumn] = classes[i];
			}
		}
		if(Table.get() != null) {
			if(Table.get().isDoubleChess()){
				return doubleClasses;
			}
		}
		return classes;
	}

	public static int convertWidthToDouble(int numTilesPerColumn) {
		if(Table.get() != null) {
			if(Table.get().isDoubleChess()){
				return numTilesPerColumn*2;
			}
		}
		return numTilesPerColumn;
	}

	// private static void buildDoubleBoard(Class<? extends Piece>[] classes, Builder builder,
	// 		int numTilesPerColumn, int numTilesPerRow, int blackPawnLine, Class<? extends Piece> pawnClass) {
	// 			Class<? extends Piece>[] doubleClasses = new Class[1000];
	// 			for (int i = 0; i < classes.length; i++) {
	// 				if (i < numTilesPerColumn) {
	// 					// First set of rows
	// 					doubleClasses[i] = classes[i];
	// 					doubleClasses[numTilesPerColumn + i] = classes[i];
	// 				} else {
	// 					// Rows beyond the first set
	// 					int desiredRow = i / numTilesPerColumn;
	// 					int adjustedIndex = i % numTilesPerColumn + (desiredRow * numTilesPerColumn * 2);
	// 					doubleClasses[adjustedIndex] = classes[i];
	// 					doubleClasses[adjustedIndex+numTilesPerColumn] = classes[i];
	// 				}
	// 			}
	// 			BoardUtils.buildBoard(doubleClasses, builder, numTilesPerColumn*2, numTilesPerRow, blackPawnLine, Pawn.class);
	// }
	private static <T extends Piece> void buildPiece(Class<T> pieceClass, Builder builder, Alliance alliance,
			int position) {
		try {
			builder.setPiece(pieceClass.getDeclaredConstructor(Alliance.class, int.class, boolean.class)
					.newInstance(alliance, position, true));
		} catch (Exception e) {
//			System.out.println("Huston we have a problem");
		}
	}

//	<T extends Piece>
	public static <T extends Piece> void createPawnLine(Builder builder, Alliance alliance, int startRow, int numRow,
			Class<T> pawnClass) {
		int startingTile = (startRow - 1) * numRow;
		for (int i = startingTile; i < (startingTile + numRow); i++) {
			buildPiece(pawnClass, builder, alliance, i);
//			builder.setPiece(new Pawn(alliance, i, true));
		}
	}
	public static <T extends Piece> void createPawnVerticalLine(Builder builder, Alliance alliance, int startColumn, int numColumns, Class<T> pawnClass) {
	    for (int i = 0; i < numColumns; i++) { // Assuming an 8x8 board
	        int tile = (i * numColumns) + (startColumn - 1);
	        buildPiece(pawnClass, builder, alliance, tile);
	    }
	}



	public static int  getDimensionFor4Player(int regularDimensions, int numberOfRowsUsed) {
		return (numberOfRowsUsed+1)*2+regularDimensions;
	}

	public static class EmptyPiece extends Piece {

		public EmptyPiece(Alliance pieceAlliance, int piecePosition, boolean isFirstMove) {
			super(null, pieceAlliance, piecePosition, isFirstMove);
		}

		@Override
		public Piece movePiece(Move move) {
			return null;
		}

		@Override
		public Collection<Move> calculateLegalMoves(Board board) {
			return new ArrayList<>();
		}

	}
}
