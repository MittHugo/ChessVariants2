package com.chess.engine.board.variants;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.*;
import com.chess.gui.Table;

public class PreChess extends Board{
	
	public PreChess(Builder builder) {
		super(builder, 8, 8, false, true, true, true);
	}
	
	public static Board createBoard(JFrame gameframe) {
		final PreChessBuilder builder = new PreChessBuilder();
		
		// Black Layout
		builder.setPiece(new Rook(Alliance.BLACK, 0, true));
		builder.setPiece(new Knight(Alliance.BLACK, 1, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 2, true));
		builder.setPiece(new Queen(Alliance.BLACK, 3, true));
		builder.setPiece(new King(Alliance.BLACK, 4, true));
		builder.setPiece(new Bishop(Alliance.BLACK, 5, true));
		builder.setPiece(new Knight(Alliance.BLACK, 6, true));
		builder.setPiece(new Rook(Alliance.BLACK, 7, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
		builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
		//White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
		builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
		builder.setPiece(new Rook(Alliance.WHITE, 56, true));
		builder.setPiece(new Knight(Alliance.WHITE, 57, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 58, true));
		builder.setPiece(new Queen(Alliance.WHITE, 59, true));
		builder.setPiece(new King(Alliance.WHITE, 60, true));
		builder.setPiece(new Bishop(Alliance.WHITE, 61, true));
		builder.setPiece(new Knight(Alliance.WHITE, 62, true));
		builder.setPiece(new Rook(Alliance.WHITE, 63, true));
		
		// white to move
		 builder.setFirstMoveMaker(Alliance.WHITE);
		 
		 return builder.build();
	}
	
	public static class PreChessBuilder extends Builder {
		@Override
		public Board build() {
			return new PreChess(this);
		}
	}
	
	class PreChessSetup extends JDialog {
		PreChessSetup(final JFrame panel, boolean modal, Board board){
			super(panel, modal);
			panel.add(new BoardPanel(board));
			
		}
	}
	private class BoardPanel extends JPanel{
		final List<TilePanel>boardTiles;
		
		BoardPanel(Board chessBoard){
			super(new GridLayout(chessBoard.getNumberRows(), chessBoard.getNumberColumns()));
			this.boardTiles =new ArrayList<>();
			
			for(int i=0; i<chessBoard.getNumberTiles(); i++) {
				final TilePanel tilePanel = new TilePanel(this, i, chessBoard);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(new Dimension(400, 350));
			validate();
			
		}

		public void drawBoard(final Board board) {
			removeAll();
			for(final TilePanel tilePanel: boardTiles) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
			
		}
		
	}
	private class TilePanel extends JPanel{
		private final int tileId;
		TilePanel(final BoardPanel boardPanel, final int tileId, Board chessBoard) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize( new Dimension(10,10));
			assignTileColor(chessBoard);
			assignTilePieceIcon(chessBoard);
			validate();
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
                   
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		
		public void drawTile(Board board) {
			assignTileColor(board);
			assignTilePieceIcon(board);
//			highlightLegals(board);
			validate();
			repaint();
		}
		
		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileId).isTileOccupied()) {
				try {
					BufferedImage img = ImageIO.read(new File(Table.defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
							.getAlliance().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".gif"));
					add(new JLabel(new ImageIcon(img)));
				} catch (IOException e) {
					try {
//						System.out.println(defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
//								.getAlliance().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".png");
						BufferedImage img = ImageIO.read(new File(Table.defaultPieceImagesPath + board.getTile(this.tileId).getPiece()
								.getAlliance().toString().substring(0,1)+board.getTile(this.tileId).getPiece().toString()+".png"));
						add(new JLabel(new ImageIcon(img)));
					}catch (IOException ex) {
						e.printStackTrace();
						ex.printStackTrace();
					}
				}
			}
		}
		
		private void assignTileColor(Board chessBoard) {
//			BoardUtils.EIGHT_RANK[this.tileId]||
//			BoardUtils.SIXTH_RANK[this.tileId]||
//			BoardUtils.FOURTH_RANK[this.tileId]||
//			BoardUtils.SECOND_RANK[this.tileId]
//			if(chessBoard.isEvenRank(this.tileId)) {
//				setBackground(this.tileId%2==0? lightTileColor: darkTileColor);
//			} else if (chessBoard.isOddRank(this.tileId)){
//				setBackground(this.tileId %2!=0 ? lightTileColor: darkTileColor);
//			}
			int rows = chessBoard.getNumberRows(); // Get the total number of rows
			int columns = chessBoard.getNumberColumns(); // Get the total number of columns

			int row = this.tileId / columns; // Determine the row based on the tile ID
			int col = this.tileId % columns; // Determine the column based on the tile ID

			// Check if the sum of row and column indices is even or odd to decide the color
			if((row + col) % 2 == 0) {
			    setBackground(Table.lightTileColor); // Even sum results in light color
			} else {
			    setBackground(Table.darkTileColor); // Odd sum results in dark color
			}

//			BoardUtils.SEVENTH_RANK[this.tileId]||
//			BoardUtils.FIFTH_RANK[this.tileId]||
//			BoardUtils.THIRD_RANK[this.tileId]||
//			BoardUtils.FIRST_RANK[this.tileId]
		}
	}
	
}
