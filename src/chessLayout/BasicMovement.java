package chessLayout;
import java.awt.Point;



public class BasicMovement extends Movement {

	public BasicMovement(int dx, int dy, boolean repeat) {
		super(dx, dy, repeat);
	}

	@Override
	public boolean canMove(Point point, Piece piece, Board board, boolean safe) {
		if (!board.inBounds(point) || safe && willCauseCheck(point, piece, board)) {
			return false;
		}
		Piece targetPiece = board.getPiece(point);
		if (targetPiece == null || targetPiece.getPlayer() != piece.getPlayer()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRepeat(Point point, Piece piece, Board board) {
		return board.getPiece(point) == null && board.inBounds(point);
	}

}
