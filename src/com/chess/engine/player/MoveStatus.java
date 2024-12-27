package com.chess.engine.player;

public enum MoveStatus {
	DONE {
		@Override
		public boolean isDone() {
			return true;
		}
	}, 	PROHIBITION {
		@Override
		public boolean isDone() {
			return false;
		}
	},
	IllegalMove {
		@Override
		public boolean isDone() {
			return false;
		}
	}, LEAVES_PLAYER_IN_CHECK {
		@Override
		public boolean isDone() {
			return false;
		}
	}, MUST_ATTACK {
		@Override
		public boolean isDone() {
			return false;
		}
	};
	
	public abstract boolean isDone();
}
