package chessPieces;

import java.util.ArrayList;

import chessLayout.BasicMovement;
import chessLayout.Board;
import chessLayout.Movement;
import chessLayout.Piece;


public class Knight extends Piece {
	
	private static final String TYPE = "knight";

	public Knight(int player, int x, int y, Board board) {
		super(player, x, y, board);
	}

	@Override
	protected ArrayList<Movement> getMovements() {
		ArrayList<Movement> moves = new ArrayList<Movement>();
		moves.add(new BasicMovement(2,1,false));
		moves.add(new BasicMovement(2,-1,false));
		moves.add(new BasicMovement(-2,1,false));
		moves.add(new BasicMovement(-2,-1,false));
		moves.add(new BasicMovement(1,2,false));
		moves.add(new BasicMovement(1,-2,false));
		moves.add(new BasicMovement(-1,2,false));
		moves.add(new BasicMovement(-1,-2,false));
		return moves;
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
