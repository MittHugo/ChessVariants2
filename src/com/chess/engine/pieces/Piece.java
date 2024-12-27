package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Move;
import com.chess.gui.Table;
import com.chess.gui.Table.ChessType;
import com.chess.engine.board.Board;

public abstract class Piece {
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final PieceType pieceType;
	private final int cachedHashCode;
	
	public Piece(final PieceType pieceType, final Alliance pieceAlliance,final int piecePosition,
			final boolean isFirstMove){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = isFirstMove;
		this.pieceType = pieceType;
		this.cachedHashCode = computeHashCode();
	}
	
	public Piece getPromotionPiece() {
		return null;
	}
	@Override
	public String toString() {
		return pieceType.toString();
	}
	private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceAlliance.hashCode();
        result = 31 * result + this.piecePosition;
        result = 31 * result + (this.isFirstMove ? 1 : 0);
        return result;
	}
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
//        System.out.println(piecePosition == otherPiece.piecePosition);
        return this.piecePosition == otherPiece.piecePosition && this.pieceType == otherPiece.pieceType &&
               this.pieceAlliance == otherPiece.pieceAlliance && this.isFirstMove == otherPiece.isFirstMove;
    }
	
	@Override
	public int hashCode() {
		return cachedHashCode;
	}

	public PieceType getPieceType() {
		return pieceType;
	}
	
	public int getPiecePosition() {
		return piecePosition;
	}
	
	public abstract Piece movePiece(Move move);
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	public Alliance getAlliance() {
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove() {
		return isFirstMove;
	}
	
	public enum PieceType {
		PAWN(100, "P") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},	AUGMENTED_KNIGHT(350, "AugKnight") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		PRINCE(150, "PRINCE") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},MACHINE(150, "MACHINE") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},SHIP(350, "SHIP") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},BUFFALO(350, "BUFFALO") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},STAR(450, "STAR") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},BULL(250, "BULL") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},VAO(350, "VAO") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},WIZARD(300, "WIZARD") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},ANTELOPE(300, "ANTELOPE") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},	CHAMPION(250, "CHAMPION") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},JESTER(200, "J") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},SAGE(100, "UK") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},LION(350, "LI") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},CANNON(350, "CANNON") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},GIRAFFE(350, "GI") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},ROC(500, "ROC") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},UNICORN(350, "UN") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		SERGEANT(150, "UP") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},STRATOMIC(500, "S") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		ELEPHANT_FROM_WOLF_CHESS(1000, "M") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},	ELEPHANT(200, "ELEPHANT") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},NIGHTRIDER(400, "NI") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},WILDEBEAST(300, "WI") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},CAMEL(300, "CAM") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},EMPERESS(computeKingValue(1000), "E") {
			@Override
			public void updatePieceValue() {
				this.score =computeKingValue(1000);
			}
			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		ATTENDANT(300, "AT") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},GORGON(300, "G") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},BEAR(200, "Be") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, LIEUTENANT(300, "L") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},CAPTIAN(300, "CA") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},WARLORD(500, "W") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},GENERAL(500, "GE") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, SPARTAN_KING(computeKingValue(200), "SK") {
			
			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public void updatePieceValue() {
				this.score =computeKingValue(200);
			}
		}, 
		TORPEDO_PAWN(100, "P") {
			
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public void updatePieceValue() {}
		}, MAHARAJAH(computeKingValue(10000), "M") {
			@Override
			public void updatePieceValue() {
				this.score = computeKingValue(10000);
			}
			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		DUSNANY_PAWN(100, "P") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, 
		KNIGHT(299,"N") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		SUPERX_KNIGHT(300,"N") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		BISHOP(300, "B") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		SUPERX_BISHOP(300, "B") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		ROOK(500,"R") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return true;
			}
		},
		SUPERX_ROOK(500,"R") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return true;
			}
		},
		QUEEN(900, "Q") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		BEROLINA_PAWN(100, "A"){
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		KING(computeKingValue(200), "K") {
		    @Override
		    public boolean isKing() {
		        return true;
		    }

		    @Override
		    public boolean isRook() {
		        return false;
		    }

			@Override
			public void updatePieceValue() {
				this.score = computeKingValue(200);
				
			}
		},FALCON(500,"UB"){
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
			
		}, HUNTER(500,"UR") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
			
		}, CHANCELLOR(900,"C") {
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, ARCHBISHOP(900,"AB") {
			@Override
			public boolean isKing() {
				return false;
			}
			@Override
			public boolean isRook() {
				return false;
			}
			@Override
			public void updatePieceValue() {}
			
		}, GRASSHOPPER(200,"G"){
			@Override
			public void updatePieceValue() {}
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}};
		
		private String pieceName;
		protected int score;
		
		PieceType(int score, final String pieceName){
			this.score = score;
			this.pieceName = pieceName;
		}
		
		static int computeKingValue(int value) {
			if(Table.get() != null) {
				if(Table.get().getChessType() == ChessType.ConquerAll) {
					return value;
				} else {
					return 10000;
				}
			} else {
				return 10000;
			}
		}

		@Override
		public String toString() {
			return this.pieceName;
		}
		public int getPieceValue() {
			return score;
		}
		public abstract boolean isKing();
		public abstract boolean isRook();
		public abstract void updatePieceValue();
	}
}
