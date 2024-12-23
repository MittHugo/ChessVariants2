package com.chess;

import java.util.ArrayList;

import com.chess.engine.board.Move;
import com.chess.engine.board.variants.*;
//import com.chess.engine.board.variants.DusnanyChess.DusnanyPlayer;
//import com.chess.engine.board.variants.DusnanyChess.DusnanyPlayer;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.BlackPlayerHandler;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.player.WhitePlayerHandler;
import com.chess.gui.Table;

public class JChess {
	public static void main(String[] args) { 
		
		//TODO fix hasEscapeMoves() player - fix castling - fix piece moves
		// TODO change castling when castling fixed
		// TODO Fix problem w/ pieces when board isn't square
		// Muscateer Chess - Pre-Chess - Polgar reform chess - Transcendental Chess (fix transpose) - Way of Knight
		//TODO fix first jump pattern knight for Flacon-HunterChess
		// 2000 AD
		Table.get();
	}
}
