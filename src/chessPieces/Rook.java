package chessPieces;

import java.util.ArrayList;

import chessLayout.BasicMovement;
import chessLayout.Board;
import chessLayout.Movement;
import chessLayout.Piece;


public class Rook extends Piece {
	
	private static final String TYPE = "rook";

	public Rook(int player, int x, int y, Board board) {
		super(player, x, y, board);
	}

	@Override
	protected ArrayList<Movement> getMovements() {
		ArrayList<Movement> moves = new ArrayList<Movement>();
		moves.add(new BasicMovement(1,0,true));
		moves.add(new BasicMovement(-1,0,true));
		moves.add(new BasicMovement(0,1,true));
		moves.add(new BasicMovement(0,-1,true));
		return moves;
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
