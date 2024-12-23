package com.chess.engine;

import java.awt.Color;

import com.chess.engine.board.Board;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.BluePlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.RedPlayer;
import com.chess.engine.player.WhitePlayer;
import com.chess.gui.Table;
import com.chess.gui.Table.PlayerCount;

public enum Alliance {
	WHITE{
		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public boolean isWhite() {
			return true;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer,RedPlayer redPlayer, BluePlayer bluePlayer) {
			return whitePlayer;
		}

		@Override
		public int getOppositeDirection() {
			return 1;
		}

		@Override
		public boolean isPawnPromotionSquare(int position, Board board) {
			return board.FIRST_ROW[position];
		}

		@Override
		public Color getColor() {
			return new Color(255,255,255);
		}

//		@Override
//		public Alliance getOppositeAlliance() {
//			return Alliance.BLACK;
//		}
		@Override
		public boolean canAttack(Alliance alliance) {
			if(Table.get() !=null) {
				if(Table.get().getPlayerCount() != null) {
					if(Table.get().getPlayerCount() == PlayerCount.FourTeams) {
						if(alliance == Alliance.BLACK || alliance == Alliance.WHITE) {
							return false;
						} else {
							return true;
						}
					}
				}
			}
			if(alliance == Alliance.WHITE) {
				return false;
			} else {
				return true;
			}
		}
		@Override 
		public boolean isRed() {
			return false;
		}
		@Override 
		public boolean isBlue() {
			return false;
		}
	},
	BLACK {
		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer,RedPlayer redPlayer, BluePlayer bluePlayer) {
			return blackPlayer;
		}

		@Override
		public int getOppositeDirection() {
			return -1;
		}

		@Override
		public boolean isPawnPromotionSquare(int position, Board board) {
			return board.LAST_ROW[position];
		}
		@Override
		public Color getColor() {
			return new Color(0,0,0);
		}

		@Override
		public boolean canAttack(Alliance alliance) {
			if(Table.get() !=null) {
				if(Table.get().getPlayerCount() != null) {
					if(Table.get().getPlayerCount() == PlayerCount.FourTeams) {
						if(alliance == Alliance.BLACK || alliance == Alliance.WHITE) {
							return false;
						} else {
							return true;
						}
					}
				}
			}
			if(alliance == Alliance.BLACK) {
				return false;
			} else {
				return true;
			}
		}
//		@Override
//		public Alliance getOppositeAlliance() {
//			return Alliance.WHITE;
//		}
		@Override 
		public boolean isRed() {
			return false;
		}
		@Override 
		public boolean isBlue() {
			return false;
		}
	},RED{
		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer, RedPlayer redPlayer, BluePlayer bluePlayer) {
			return redPlayer;
		}

		@Override
		public int getOppositeDirection() {
			return -1;
		}

		@Override
		public boolean isPawnPromotionSquare(int position, Board board) {
			return board.LAST_COLUMN[position];
		}
		@Override
		public Color getColor() {
			return new Color(255,0,0);
		}
		@Override
		public boolean canAttack(Alliance alliance) {
			if(Table.get() !=null) {
				if(Table.get().getPlayerCount() != null) {
					if(Table.get().getPlayerCount() == PlayerCount.FourTeams) {
						if(alliance == Alliance.RED || alliance == Alliance.BLUE) {
							return false;
						} else {
							return true;
						}
					}
				}
			}
			if(alliance == Alliance.RED) {
				return false;
			} else {
				return true;
			}
		}
		@Override 
		public boolean isRed() {
			return true;
		}
		@Override 
		public boolean isBlue() {
			return false;
		}
//		@Override
//		public Alliance getOppositeAlliance() {
//			return Alliance.BLACK;
//		}
	},BLUE {
		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer, RedPlayer redPlayer, BluePlayer bluePlayer) {
			return bluePlayer;
		}

		@Override
		public int getOppositeDirection() {
			return 1;
		}

		@Override
		public boolean isPawnPromotionSquare(int position, Board board) {
			return board.FIRST_COLUMN[position];
		}
		@Override
		public Color getColor() {
			return new Color(0,0,255);
		}

		@Override
		public boolean canAttack(Alliance alliance) {
			if(Table.get() !=null) {
				if(Table.get().getPlayerCount() != null) {
//					System.out.println("here");
//					System.out.println(Table.get().getPlayerCount());
					if(Table.get().getPlayerCount() == PlayerCount.FourTeams) {
						if(alliance == Alliance.RED || alliance == Alliance.BLUE) {
							return false;
						} else {
							return true;
						}
					}
				}
			}
			if(alliance == Alliance.BLUE) {
				return false;
			} else {
				return true;
			}
		}
		@Override 
		public boolean isRed() {
			return false;
		}
		@Override 
		public boolean isBlue() {
			return true;
		}
//		@Override
//		public Alliance getOppositeAlliance() {
//			return Alliance.WHITE;
//		}
	};
	public abstract int getDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer,RedPlayer redPlayer, BluePlayer bluePlayer);
	public abstract int getOppositeDirection();
	public abstract boolean isPawnPromotionSquare(int position, Board board);
	public abstract Color getColor();
	public abstract boolean canAttack(Alliance alliance);
	public abstract boolean isRed();
	public abstract boolean isBlue();
}
