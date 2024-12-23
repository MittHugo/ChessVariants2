package com.chess.engine.player.ai;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BuildHandler;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MonteCarloTreeSearch implements MoveStrategy {

	@Override
	public Move execute(Board board, Integer depth, BuildHandler handler) {
		return null;
	}

//	public class Node {
//		State state;
//		Node parent;
//		List<Node> childArray;
//		
//		// setters and getters
//		public State getState() {
//			return state;
//		}
//	}
//
//	public class Tree {
//		Node root;
//		Tree() {
//			
//		}
//		public Node getRoot() {
//			return root;
//		}
//	}
//
//	public class State {
//		Board board;
//		Alliance playerNo;
//		int visitCount;
//		double winScore;
//
//		// copy constructor, getters, and setters
//
//		public List<State> getAllPossibleStates() {
//			// constructs a list of all possible states from current state
//			return null;
//		}
//
//		public void randomPlay() {
//			/*
//			 * get a list of all possible positions on the board and play a random move
//			 */
//		}
//
//		public void setBoard(Board board) {
//			this.board = board;
//		}
//
//		public void setPlayer(Alliance opponent) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public Object getBoard() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	}
//
//	static final int WIN_SCORE = 10;
//	int level;
//	Alliance opponent;
//
//	public Board findNextMove(Board board) {
//		// define an end time which will act as a terminating condition
//
//		opponent = board.currentPlayer().getNextPlayer().getAlliance();
//		Tree tree = new Tree();
//		Node rootNode = tree.getRoot();
//		rootNode.getState().setBoard(board);
//		rootNode.getState().setPlayer(opponent);
//
//		while (System.currentTimeMillis() < end) {
//			Node promisingNode = selectPromisingNode(rootNode);
//			if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS) {
//				expandNode(promisingNode);
//			}
//			Node nodeToExplore = promisingNode;
//			if (promisingNode.getChildArray().size() > 0) {
//				nodeToExplore = promisingNode.getRandomChildNode();
//			}
//			int playoutResult = simulateRandomPlayout(nodeToExplore);
//			backPropogation(nodeToExplore, playoutResult);
//		}
//
//		Node winnerNode = rootNode.getChildWithMaxScore();
//		tree.setRoot(winnerNode);
//		return winnerNode.getState().getBoard();
//	}
//	private Node selectPromisingNode(Node rootNode) {
//	    Node node = rootNode;
//	    while (node.getChildArray().size() != 0) {
//	        node = UCT.findBestNodeWithUCT(node);
//	    }
//	    return node;
//	}
//	public class UCT {
//	    public static double uctValue(
//	      int totalVisit, double nodeWinScore, int nodeVisit) {
//	        if (nodeVisit == 0) {
//	            return Integer.MAX_VALUE;
//	        }
//	        return ((double) nodeWinScore / (double) nodeVisit) 
//	          + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
//	    }
//
//	    public static Node findBestNodeWithUCT(Node node) {
//	        int parentVisit = node.getState().getVisitCount();
//	        return Collections.max(
//	          node.getChildArray(),
//	          Comparator.comparing(c -> uctValue(parentVisit, 
//	            c.getState().getWinScore(), c.getState().getVisitCount())));
//	    }
//	}
//	private void expandNode(Node node) {
//	    List<State> possibleStates = node.getState().getAllPossibleStates();
//	    possibleStates.forEach(state -> {
//	        Node newNode = new Node(state);
//	        newNode.setParent(node);
//	        newNode.getState().setPlayerNo(node.getState().getOpponent());
//	        node.getChildArray().add(newNode);
//	    });
//	}
//	private void backPropogation(Node nodeToExplore, int playerNo) {
//	    Node tempNode = nodeToExplore;
//	    while (tempNode != null) {
//	        tempNode.getState().incrementVisit();
//	        if (tempNode.getState().getPlayerNo() == playerNo) {
//	            tempNode.getState().addScore(WIN_SCORE);
//	        }
//	        tempNode = tempNode.getParent();
//	    }
//	}
//	private int simulateRandomPlayout(Node node) {
//	    Node tempNode = new Node(node);
//	    State tempState = tempNode.getState();
//	    int boardStatus = tempState.getBoard().checkStatus();
//	    if (boardStatus == opponent) {
//	        tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
//	        return boardStatus;
//	    }
//	    while (boardStatus == Board.IN_PROGRESS) {
//	        tempState.togglePlayer();
//	        tempState.randomPlay();
//	        boardStatus = tempState.getBoard().checkStatus();
//	    }
//	    return boardStatus;
//	}
}
