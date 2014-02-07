package chessPieces;

import java.util.ArrayList;

import chessLayout.BasicMovement;
import chessLayout.Board;
import chessLayout.Movement;
import chessLayout.Piece;


public class King extends Piece {
	
	private static final String TYPE = "king";

	public King(int player, int x, int y, Board board) {
		super(player, x, y, board);
	}

	@Override
	protected ArrayList<Movement> getMovements() {
		ArrayList<Movement> moves = new ArrayList<Movement>();
		moves.add(new BasicMovement(1,0,false));
		moves.add(new BasicMovement(-1,0,false));
		moves.add(new BasicMovement(0,1,false));
		moves.add(new BasicMovement(0,-1,false));
		moves.add(new BasicMovement(1,1,false));
		moves.add(new BasicMovement(1,-1,false));
		moves.add(new BasicMovement(-1,1,false));
		moves.add(new BasicMovement(-1,-1,false));
		return moves;
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
