import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Queen extends Piece {
	
	int[][] wQueenFactor = {{-20, -10, -10, -5, -5, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 5, 5, 5, 0, -10},
            {-5, 0, 5, 5, 5, 5, 0, -5},
            {0, 0, 5, 5, 5, 5, 0, -5},
            {-10, 5, 5, 5, 5, 5, 0, -10},
            {-10, 0, 5, 0, 0, 0, 0, -10},
            {-20, -10, -10, -5, -5, -10, -10, -20}};
	
	int[][] bQueenFactor = {{-20, -10, -10, -5, -5, -10, -10, -20},
			{-10, 0, 0, 0, 0, 5, 0, -10},
			{-10, 0, 5, 5, 5, 5, 5, -10},
			{-5, 0, 5, 5, 5, 5, 0, 0},
			{-5, 0, 5, 5, 5, 5, 0, -5},
			{-10, 0, 5, 5, 5, 5, 0, -10},
			{-10, 0, 0, 0, 0, 0, 0, -10},
			{-20, -10, -10, -5, -5, -10, -10, -20}};

	Queen(int row, int col, boolean color) throws IOException {
		super(row, col, color);
		this.type = Type.QUEEN;
		this.bitBoard =(color) ? ChessGame.WQ : ChessGame.BQ;
		if (color)
			this.image = ImageIO.read(new File("D:\\pictures\\chess\\white_queen.png"));
		else
			this.image = ImageIO.read(new File("D:\\pictures\\chess\\black_queen.png"));
		image = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		smallImage = image.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
	}

	@Override
	 public long getMoves() {
		List<Integer[]> moves = new ArrayList<>();
		long Q = (1L << (row*8+col));
        long i=Q&-Q;
		int iLocation=Long.numberOfTrailingZeros(i);
		return (HAndVMoves(iLocation)|DAndAntiDMoves(iLocation))&NOT_MY_PIECES;
	    }

	@Override
	protected int evalPiece(boolean raw, boolean endGame) {
		if (!raw) {
			if (color)
				return 900 + wQueenFactor[row][col];
			else
				return 900 + bQueenFactor[row][col];
		}
		else
			return 900;
	}

	@Override
	public Piece copy() {
		try {
			return new Queen(this.row, this.col, this.color);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
