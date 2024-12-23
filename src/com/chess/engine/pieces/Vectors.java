package com.chess.engine.pieces;


public class Vectors {
	public final int vector;
	public final MotionType motionType;
	public final AttackType attackType;
	public final boolean firstMoveOnlyVector;
	int[] behindVector;
	int[] expansionVectors;
	int preTravel;
	int postTravel;
	boolean hopAgain;
	int numberOfJumps;
	public Vectors(int vector, MotionType motionType, AttackType attackType, boolean firstMoveOnlyVector) {
		this.vector = vector;
		this.motionType = motionType;
		this.attackType = attackType;
		this.firstMoveOnlyVector = firstMoveOnlyVector;
		if(motionType == MotionType.JumpToItterative || motionType == MotionType.MotionIfClear || motionType== MotionType.CheckerLikeJump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	public Vectors(int vector, MotionType motionType, AttackType attackType) {
		this.vector = vector;
		this.motionType = motionType;
		this.attackType = attackType;
		if(motionType == MotionType.JumpToItterative || motionType == MotionType.MotionIfClear || motionType== MotionType.CheckerLikeJump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
		firstMoveOnlyVector = false;
	}
	
	public Vectors(int vector, MotionType motionType, boolean firstMoveOnlyVector) {
		this.vector = vector;
		this.motionType = motionType;
		this.attackType = AttackType.Both;
		this.firstMoveOnlyVector = firstMoveOnlyVector;
		if(motionType == MotionType.JumpToItterative || motionType == MotionType.MotionIfClear || motionType== MotionType.CheckerLikeJump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	public Vectors(int vector, MotionType motionType) {
		this.vector = vector;
		this.motionType = motionType;
		this.attackType = AttackType.Both;
		this.firstMoveOnlyVector = false;
		if(motionType == MotionType.JumpToItterative || motionType == MotionType.MotionIfClear || motionType== MotionType.CheckerLikeJump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	
	public Vectors(int vector, MotionType motionType, int[] vectors) {
		this.vector = vector;
		this.attackType = AttackType.Both;
		this.motionType = motionType;
		if(motionType == MotionType.JumpToItterative) {
			this.expansionVectors = vectors;
		} else if(motionType == MotionType.MotionIfClear) {
			this.behindVector = vectors;
		}
		this.firstMoveOnlyVector = false;
		if(motionType == MotionType.CheckerLikeJump || motionType == MotionType.Itterative || motionType== MotionType.Jump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	
	public Vectors(int vector, MotionType motionType, int[] vectors, boolean firstMoveOnlyVector) {
		this.vector = vector;
		this.attackType = AttackType.Both;
		this.motionType = motionType;
		if(motionType == MotionType.JumpToItterative) {
			this.expansionVectors = vectors;
		} else if(motionType == MotionType.MotionIfClear) {
			this.behindVector = vectors;
		}
		this.firstMoveOnlyVector = firstMoveOnlyVector;
		if(motionType == MotionType.CheckerLikeJump || motionType == MotionType.Itterative || motionType== MotionType.Jump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	public Vectors(int vector ,MotionType motionType, int[] vectors, AttackType attackType) {
		this.vector = vector;
		this.attackType = attackType;
		this.motionType = motionType;
		if(motionType == MotionType.JumpToItterative) {
			this.expansionVectors = vectors;
		} else if(motionType == MotionType.MotionIfClear) {
			this.behindVector = vectors;
		}
		this.firstMoveOnlyVector = false;
		if(motionType == MotionType.CheckerLikeJump || motionType == MotionType.Itterative || motionType== MotionType.Jump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	public Vectors(int vector, MotionType motionType, int[] vectors, AttackType attackType, boolean firstMoveOnlyVector) {
		this.vector = vector;
		this.attackType = attackType;
		this.motionType = motionType;
		if(motionType == MotionType.JumpToItterative) {
			this.expansionVectors = vectors;
		} else if(motionType == MotionType.MotionIfClear) {
			this.behindVector = vectors;
		}
		this.firstMoveOnlyVector = firstMoveOnlyVector;
		if(motionType == MotionType.CheckerLikeJump || motionType == MotionType.Itterative || motionType== MotionType.Jump) {
			throw new RuntimeException("Wrong Motion Type for this instantiation");
		}
	}
	
//	public Vectors(int vector, int[] expansionVectors, boolean firstMoveOnlyVector) {
//		this.vector = vector;
//		this.motionType = MotionType.JumpToItterative;
//		this.attackType = AttackType.Both;
//		this.expansionVectors = expansionVectors;
//		this.firstMoveOnlyVector = firstMoveOnlyVector;
//	}
//	public Vectors(int vector, int[] expansionVectors) {
//		this.vector = vector;
//		this.motionType = MotionType.JumpToItterative;
//		this.attackType = AttackType.Both;
//		this.expansionVectors = expansionVectors;
//		this.firstMoveOnlyVector = false;
//	}
//	public Vectors(int vector, int[] expansionVectors, AttackType attackType, boolean firstMoveOnlyVector) {
//		this.vector = vector;
//		this.motionType = MotionType.JumpToItterative;
//		this.attackType = attackType;
//		this.expansionVectors = expansionVectors;
//		this.firstMoveOnlyVector = firstMoveOnlyVector;
//	}
//	public Vectors(int vector, int[] expansionVectors, AttackType attackType) {
//		this.vector = vector;
//		this.motionType = MotionType.JumpToItterative;
//		this.attackType = attackType;
//		this.expansionVectors = expansionVectors;
//		this.firstMoveOnlyVector = false;
//	}

	public Vectors(int vector, int preTravel, int postTravel, boolean hopAgain, int numberOfJumps) {
		this.vector = vector;
		this.motionType = MotionType.CheckerLikeJump;
		this.attackType = AttackType.Both;
		this.preTravel = preTravel;
		this.postTravel = postTravel;
		this.hopAgain = hopAgain;
		this.numberOfJumps = numberOfJumps;
		this.firstMoveOnlyVector = false;
	}
	public Vectors(int vector, AttackType attackType, int preTravel, int postTravel, boolean hopAgain, int numberOfJumps) {
		this.vector = vector;
		this.motionType = MotionType.CheckerLikeJump;
		this.attackType = attackType;
		this.preTravel = preTravel;
		this.postTravel = postTravel;
		this.hopAgain = hopAgain;
		this.numberOfJumps = numberOfJumps;
		this.firstMoveOnlyVector = false;
	}
	
	public Vectors(int vector, int preTravel, int postTravel, boolean hopAgain, int numberOfJumps, boolean firstMoveOnlyVector) {
		this.vector = vector;
		this.motionType = MotionType.CheckerLikeJump;
		this.attackType = AttackType.Both;
		this.preTravel = preTravel;
		this.postTravel = postTravel;
		this.hopAgain = hopAgain;
		this.numberOfJumps = numberOfJumps;
		this.firstMoveOnlyVector = firstMoveOnlyVector;
	}
	public Vectors(int vector, AttackType attackType, int preTravel, int postTravel, boolean hopAgain, int numberOfJumps, boolean firstMoveOnlyVector) {
		this.vector = vector;
		this.motionType = MotionType.CheckerLikeJump;
		this.attackType = attackType;
		this.preTravel = preTravel;
		this.postTravel = postTravel;
		this.hopAgain = hopAgain;
		this.numberOfJumps = numberOfJumps;
		this.firstMoveOnlyVector = firstMoveOnlyVector;
	}
	
	public enum MotionType {
		Itterative, 
		Jump, 
		MotionIfClear, // Like Pawn fist jump
		JumpToItterative, 
		CheckerLikeJump,
	}
	
	public enum AttackType {
		AttackOnly,
		MotionOnly,
		Both
	}
}
