import java.awt.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Pawn extends Piece {
	
	int[][] wPawnFactor = {{500, 500, 500, 500, 500, 500, 500, 500},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5, 5, 10, 25, 25, 10, 5, 5},
            {0, 0, 0, 20, 20, 0, 0, 0},
            {5, -5, -10, 0, 0, -10, -5, 5},
            {5, 10, 10, -20, -20, 10, 10, 5},
            {0, 0, 0, 0, 0, 0, 0, 0}};
	
	int[][] wPawnEndFactor = {{500, 500, 500, 500, 500, 500, 500, 500},
            {250, 250, 250, 250, 250, 250, 250, 250},
            {120, 120, 130, 150, 150, 130, 120, 120},
            {100, 100, 110, 120, 120, 110, 100, 100},
            {80, 80, 90, 100, 100, 90, 80, 80},
            {70, 70, 70, 80, 80, 70, 70, 70},
            {-5, -10, -10, -20, -20, -10, -10, -5},
            {0, 0, 0, 0, 0, 0, 0, 0}};
	
	int[][] bPawnFactor = {{0, 0, 0, 0, 0, 0, 0, 0},
			{5, 10, 10, -20, -20, 10, 10, 5},
			{5, -5, -10, 0, 0, -10, -5, 5},
			{0, 0, 0, 20, 20, 0, 0, 0},
			{5, 5, 10, 25, 25, 10, 5, 5},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{500, 500, 500, 500, 500, 500, 500, 500}};
	
	int[][] bPawnEndFactor = {{0, 0, 0, 0, 0, 0, 0, 0},
			{-5, -10, -10, -20, -20, -10, -10, -5},
			{70, 70, 70, 80, 80, 70, 70, 70},
			{80, 80, 90, 100, 100, 90, 80, 80},
			{100, 100, 110, 120, 120, 110, 100, 100},
			{120, 120, 130, 150, 150, 130, 120, 120},
			{250, 250, 250, 250, 250, 250, 250, 250},
			{500, 500, 500, 500, 500, 500, 500, 500}};
	
	public boolean enPassant = false;

	Pawn(int row, int col, boolean color) throws IOException {
		super(row, col, color);
		this.type = Type.PAWN;
		if (color)
			this.image = ImageIO.read(new File("D:\\pictures\\chess\\white_pawn.png"));
		else
			this.image = ImageIO.read(new File("D:\\pictures\\chess\\black_pawn.png"));
		image = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		smallImage = image.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
	}

	@Override
	public long getMoves() {
		long BP = (1L << (row*8+col));
		long WP = (1L << (row*8+col));
		long result = 0L;
        if (color) {
	        //x1,y1,x2,y2
	        long PAWN_MOVES=(WP>>7)&NOT_MY_PIECES&OCCUPIED&~FILE_A;//capture right
	        long possibility=PAWN_MOVES&-PAWN_MOVES;
 			result |= possibility;

	        long enPassantBoard = (1L << (GamePanel.enPassant[0]*8+GamePanel.enPassant[1]));
	        PAWN_MOVES=(WP>>7)&NOT_MY_PIECES&~OCCUPIED&enPassantBoard&~FILE_A&RankMasks8[2];//enPassant capture right
	        possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

	        PAWN_MOVES=(WP>>9)&NOT_MY_PIECES&OCCUPIED&~FILE_H;//capture left
	        possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

	        enPassantBoard = (1L << (GamePanel.enPassant[0]*8+GamePanel.enPassant[1]));
	        PAWN_MOVES=(WP>>9)&NOT_MY_PIECES&~OCCUPIED&enPassantBoard&~FILE_H&RankMasks8[2];//enPassnat capture left
	        possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

	        PAWN_MOVES=(WP>>8)&EMPTY;//move 1 forward
	        possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

	        PAWN_MOVES=(WP>>16)&EMPTY&(EMPTY>>8)&RANK_4;//move 2 forward
	        possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;
        }
        else {
        	//x1,y1,x2,y2
            long PAWN_MOVES=(BP<<7)&NOT_MY_PIECES&OCCUPIED&~FILE_H;//capture right
            long possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

            long enPassantBoard = (1L << (GamePanel.enPassant[0]*8+GamePanel.enPassant[1]));
            PAWN_MOVES=(BP<<7)&NOT_MY_PIECES&enPassantBoard&~OCCUPIED&~FILE_H&RankMasks8[5];//enPassant capture right
            possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

            PAWN_MOVES=(BP<<9)&NOT_MY_PIECES&OCCUPIED&~FILE_A;//capture left
            possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

            enPassantBoard = (1L << (GamePanel.enPassant[0]*8+GamePanel.enPassant[1]));
            PAWN_MOVES=(BP<<9)&NOT_MY_PIECES&enPassantBoard&~OCCUPIED&~FILE_A&RankMasks8[5];//enPasssnt capture left
            possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

            PAWN_MOVES=(BP<<8)&EMPTY;//move 1 forward
            possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

            PAWN_MOVES=(BP<<16)&EMPTY&(EMPTY<<8)&RANK_5;//move 2 forward
            possibility=PAWN_MOVES&-PAWN_MOVES;
			result |= possibility;

        }
       return result;
    }
	
	public void move(Spot[][] grid, int row, int col, boolean animate) {
		super.move(grid, row, col, animate);
			if (this.getColor()) {
				if (this.getRow() == 0) {
					try {
						Queen q = new Queen(this.getRow(), this.getCol(), true);
						grid[this.getRow()][this.getCol()].assignPiece(q);
						GamePanel.whitePieces.remove(this);
						GamePanel.whitePieces.add(q);
						long table = ChessGame.pieceTables.get(""+color+type);
						table ^= (1L << (this.row*8+this.col));
						ChessGame.pieceTables.put(""+color+type, table);
						table = ChessGame.pieceTables.get(""+color+"QUEEN");
						table ^= (1L << (this.row*8+this.col));
						ChessGame.pieceTables.put(""+color+"QUEEN", table);
						if (animate)
							ChessGame.updateBoards(!color);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			else {
				if (this.getRow() == 7) {
					try {
						Queen q = new Queen(this.getRow(), this.getCol(), false);
						grid[this.getRow()][this.getCol()].assignPiece(q);
						GamePanel.blackPieces.remove(this);
						GamePanel.blackPieces.add(q);
						long table = ChessGame.pieceTables.get(""+color+type);
						table ^= (1L << (this.row*8+this.col));
						ChessGame.pieceTables.put(""+color+type, table);
						table = ChessGame.pieceTables.get(""+color+"QUEEN");
						table ^= (1L << (this.row*8+this.col));
						ChessGame.pieceTables.put(""+color+"QUEEN", table);
						if (animate)
							ChessGame.updateBoards(!color);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
	}

	@Override
	protected int evalPiece(boolean raw, boolean endGame) {
		int score = 100;
		if (!raw) {
			if (color) {
				if (!endGame)
					return score + wPawnFactor[row][col];
				else
					return score + wPawnEndFactor[row][col];
			}
			else {
				if (!endGame)
					return score + bPawnFactor[row][col];
				else
					return score + bPawnEndFactor[row][col];
			}
		}
		else
			return score;
	}

	@Override
	public Piece copy() {
		try {
			return new Pawn(this.row, this.col, this.color);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
