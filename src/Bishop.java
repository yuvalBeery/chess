import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Bishop extends Piece {
	
	static int[][] wBishopFactor = {{-20, -10, -10, -10, -10, -10, -10, -20},
            {-10, 0, 0, 0, 0, 0, 0, -10},
            {-10, 0, 5, 10, 10, 5, 0, -10},
            {-10, 5, 5, 10, 10, 5, 5, -10},
            {-10, 0, 10, 10, 10, 10, 0, -10},
            {-10, 10, 10, 10, 10, 10, 10, -10},
            {-10, 5, 0, 0, 0, 0, 5, -10},
            {-20, -10, -10, -10, -10, -10, -10, -20}};
	
	int[][] bBishopFactor = {{-20, -10, -10, -10, -10, -10, -10, -20},
			{-10, 5, 0, 0, 0, 0, 5, -10},
			{-10, 10, 10, 10, 10, 10, 10, -10},
			{-10, 0, 10, 10, 10, 10, 0, -10},
			{-10, 5, 5, 10, 10, 5, 5, -10},
			{-10, 0, 5, 10, 10, 5, 0, -10},
			{-10, 0, 0, 0, 0, 0, 0, -10},
			{-20, -10, -10, -10, -10, -10, -10, -20, }};

	Bishop(int row, int col, boolean color) throws IOException {
		super(row, col, color);
		this.type = Type.BISHOP;
		this.bitBoard =(color) ? ChessGame.WB : ChessGame.BB;
		if (color)
			this.image = ImageIO.read(new File("D:\\pictures\\chess\\white_bishop.png"));
		else
			this.image = ImageIO.read(new File("D:\\pictures\\chess\\black_bishop.png"));
		image = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		smallImage = image.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
	}

	@Override
	public long getMoves() { //
		List<Integer[]> moves = new ArrayList<>();
		long B = (1L << (row*8+col));
        long i=B& -B;
		int iLocation=Long.numberOfTrailingZeros(i);
		return DAndAntiDMoves(iLocation)&NOT_MY_PIECES;
    }

	@Override
	protected int evalPiece(boolean raw, boolean endGame) {
		if (!raw) {
			if (color)
				return 330 + wBishopFactor[row][col];
			else
				return 330 + bBishopFactor[row][col];
		}
		else
			return 330;
	}

	@Override
	public Piece copy() {
		try {
			return new Bishop(this.row, this.col, this.color);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
