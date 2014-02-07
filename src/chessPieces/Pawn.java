package chessPieces;

import java.util.ArrayList;

import chessLayout.Board;
import chessLayout.Movement;
import chessLayout.PawnMovement;
import chessLayout.Piece;


public class Pawn extends Piece {
	
	private boolean hasMoved;
	private static final String TYPE = "pawn";

	public Pawn(int player, int x, int y, Board board) {
		super(player, x, y, board);
		hasMoved = false;
	}

	@Override
	protected ArrayList<Movement> getMovements() {
		ArrayList<Movement> moves = new ArrayList<Movement>();
		moves.add(new PawnMovement(0,1,false));
		moves.add(new PawnMovement(0,2,false));
		moves.add(new PawnMovement(1,1,false));
		moves.add(new PawnMovement(-1,1,false));
		moves.add(new PawnMovement(0,-1,false));
		moves.add(new PawnMovement(0,-2,false));
		moves.add(new PawnMovement(1,-1,false));
		moves.add(new PawnMovement(-1,-1,false));
		return moves;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void firstMoveCompleted() {
		hasMoved = true;
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
