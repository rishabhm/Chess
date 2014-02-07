package chessLayout;
import java.awt.Point;
import chessLayout.*;
import chessPieces.Pawn;


public class PawnMovement extends Movement {

	public PawnMovement(int dx, int dy, boolean repeat) {
		super(dx, dy, repeat);
	}

	@Override
	/**
	 * Variation of function described in Parent class
	 * A pawn can move diagonally only to kill
	 */
	public boolean canMove(Point point, Piece piece, Board board, boolean safe) {
		if (!board.inBounds(point) || safe && willCauseCheck(point, piece, board)) {
			return false;
		}
		// Player 0 pawns cannot move down the y-axis
		if (piece.getPlayer() == 0 && dy < 0) {
			return false;
		}
		// Player 1 pawns cannot move up the y-axis
		if (piece.getPlayer() == 1 && dy > 0) {
			return false;
		}
		// Check for special first move
		if (dy*dy == 4) {
			if (((Pawn)piece).hasMoved()) {
				return false;
			} else {
				((Pawn)piece).firstMoveCompleted();
				return true;
				
			}
		}
		// The only restriction on it moving forward is that
		// the space in front of it must be empty
		if (dx == 0) {
			return board.getPiece(point) == null;
		}
		// If it is moving diagonally, then there must be an
		// opponent's piece at that location
		Piece targetPiece = board.getPiece(point);
		return targetPiece != null && targetPiece.getPlayer() != piece.getPlayer(); 
	}

	@Override
	public boolean shouldRepeat(Point point, Piece piece, Board board) {
		return false;
	}

}
