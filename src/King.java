import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class King extends Piece {
	boolean isCastled = false;
	
	int[][] wKingFactor = {{-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            {20, 20, -10, -10, -10, -10, 20, 20},
            {20, 30, 10, 0, 0, 10, 30, 20}};
	
	int[][] wKingEndFactor = {{-100, -50, -50, -50, -50, -50, -50, -100},
            {-50, -30, -30, -30, -30, -30, -30, -50},
            {-50, -10, 20, 30, 30, 20, -30, -50},
            {-50, -10, 30, 40, 40, 30, -30, -50},
            {-50, -10, 30, 40, 40, 30, -30, -50},
            {-50, -10, 20, 30, 30, 20, -30, -50},
            {-30, -30, -30, -30, -30, -30, -30, -50},
            {-100, -50, -50, -50, -50, -50, -50, -100}};
	
	int[][] bKingFactor = {{20, 30, 10, 0, 0, 10, 30, 20},
			{20, 20, -10, -10, -10, -10, 20, 20},
			{-10, -20, -20, -20, -20, -20, -20, -10},
			{-20, -30, -30, -40, -40, -30, -30, -20},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30}};
	
	int[][] bKingEndFactor = {{-100, -50, -50, -50, -50, -50, -50, -100},
			{-50, -30, -30, -30, -30, -30, -30, -30},
			{-50, -30, 20, 30, 30, 20, -10, -50},
			{-50, -30, 30, 40, 40, 30, -10, -50},
			{-50, -30, 30, 40, 40, 30, -10, -50},
			{-50, -30, 20, 30, 30, 20, -10, -50},
			{-50, -30, -30, -30, -30, -30, -30, -50},
			{-100, -50, -50, -50, -50, -50, -50, -100}};
	

	King(int row, int col, boolean color) throws IOException {
		super(row, col, color);
		this.type = Type.KING;
		this.bitBoard =(color) ? ChessGame.WK : ChessGame.BK;
		if (color) {
			this.image = ImageIO.read(new File("images\\white_king.png"));
		}
		else
			this.image = ImageIO.read(new File("images\\black_king.png"));
		image = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
	}

	@Override
	public long getMoves() {
		long K = (1L << (row*8+col));
        long possibility;
        int iLocation=Long.numberOfTrailingZeros(K);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH&NOT_MY_PIECES&~GamePanel.threatMap;
        }
        else {
            possibility &=~FILE_AB&NOT_MY_PIECES&~GamePanel.threatMap;
        }
		if (color) {
			if (GamePanel.wcks && !GamePanel.isInCheck) {
				long empty = (3L << 61);
				empty &= ~OCCUPIED;
				empty &= ~GamePanel.threatMap;
				long r = (1L << 63);
				r &= ChessGame.pieceTables.get("trueROOK");
				if (empty == (3L << 61) && r == (1L << 63)) {
					long add = (1L << 62);
					possibility |= add;
				}
			}
			if (GamePanel.wcqs && !GamePanel.isInCheck) {
				long empty = (7L << 57);
				empty &= ~OCCUPIED;
				empty &= ~GamePanel.threatMap;
				long r = (1L << 56);
				r &= ChessGame.pieceTables.get("trueROOK");
				if (empty == (7L << 57) && r == (1L << 56)) {
					long add = (1L << 58);
					possibility |= add;
				}
			}
		}
		else {
			if (GamePanel.bcks && !GamePanel.isInCheck) {
				long empty = (3L << 5);
				empty &= ~OCCUPIED;
				empty &= ~GamePanel.threatMap;
				long r = (1L << 7);
				r &= ChessGame.pieceTables.get("falseROOK");
				if (empty == (3L << 5) && r == (1L << 7)) {
					long add = (1L << 6);
					possibility |= add;
				}
			}
			if (GamePanel.bcqs && !GamePanel.isInCheck) {
				long empty = (7L << 1);
				empty &= ~OCCUPIED;
				empty &= ~GamePanel.threatMap;
				long r = (1L);
				r &= ChessGame.pieceTables.get("falseROOK");
				if (empty == (7L << 1) && r == (1L)) {
					long add = (1L << 2);
					possibility |= add;
				}
			}
		}
        return possibility;
    }

	@Override
	protected int evalPiece(boolean raw, boolean endGame) {
		if (!raw) {
			if (color) {
				if (!endGame)
					return 20000 + wKingFactor[row][col];
				else
					return 20000 + wKingEndFactor[row][col];
			}
			else {
				if (!endGame)
					return 20000 +bKingFactor[row][col];
				else
					return 20000 + bKingEndFactor[row][col];
			}
		}
		else
			return 20000;
	}

	@Override
	public Piece copy() {
		try {
			return new King(this.row, this.col, this.color);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
