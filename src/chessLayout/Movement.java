package chessLayout;
import java.awt.Point;
import java.util.ArrayList;


public abstract class Movement {
	public int dx, dy;
	public boolean repeat;
	
	/**
	 * Constructor
	 * @param dx : Distance moved along the positive/negative x axis
	 * @param dy : Distance moved along the positive/negative y axis
	 * @param repeat : Indicates if the above displaces can be repeated within the same move
	 */
	public Movement(int dx, int dy, boolean repeat) {
		this.dx = dx;
		this.dy = dy;
		this.repeat = repeat;
	}
	
	/**
	 * Abstract method
	 * Checks if a given piece can move to a given location on a given board
	 * Checks if the location is within bounds
	 * Checks if the location is occupied by an allied piece
	 * May check for special conditions depending on particular movement
	 * @param point : Location the piece needs to be moved to
	 * @param piece : The piece to be moved
	 * @param board : The boards on which the piece exists
	 * @param safe : Indicates if the function should check if moving to the given
	 * 				 location will put the piece's King in Check
	 * @return
	 */
	public abstract boolean canMove(Point point, Piece piece, Board board, boolean safe);
	
	/**
	 * Abstract method
	 * Checks if the movement should be repeated 
	 * @param point : The location of the piece after initial (and possibly consequent) movements
	 * @param piece : The piece that is being moved
	 * @param board : The boards on which the piece exists
	 * @return
	 */
	public abstract boolean shouldRepeat(Point point, Piece piece, Board board);
	
	/**
	 * Returns a list of locations where the given piece can be moved
	 * @param piece : The piece to be moved
	 * @param board : The boards on which the piece exists
	 * @param safe : Indicates if the function should check if moving to the given
	 * 				 location will put the piece's King in Check
	 * @return ArrayList<Point>
	 */
	public ArrayList<Point> getPossibleMoves(Piece piece, Board board, boolean safe) {
		Point currLocation = new Point(piece.getLocation());
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		do {
			// move once along the required displacements
			currLocation.translate(dx, dy);
			// if the move is allowed accept it
			if (canMove(currLocation, piece, board, safe)) {
				possibleMoves.add(new Point(currLocation));
			}
			// repeat as long as allowed
		} while (repeat && shouldRepeat(currLocation, piece, board));
		return possibleMoves;
	}
	
	/**
	 * Tests if moving a piece to a given location will cause a Check against its King
	 * @param point : The location of the piece after initial (and possibly consequent) movements
	 * @param piece : The piece that is being moved
	 * @param board : The boards on which the piece exists
	 * @return boolean
	 */
	protected boolean willCauseCheck(Point point, Piece piece, Board board) {
		// Store piece at target location, may be null
		Piece targetPiece = board.getPiece(point);
		// Move piece to target location
		// NOTE: This nullifies the existence of targetPiece as the chess 
		// 	     board now thinks a different piece is at that location
		piece.moveToLocation(point, false);
		// Get the opposition player
		int offensivePlayer = piece.getOtherPlayer();
		// Test if the opposition player can put you under Check
		boolean causesCheck = board.check(offensivePlayer);
		// Return piece to its current location
		piece.undoMove(false);
		// If there was a piece at the target location, put it back there
		// Else, reset that location on the chess baord
		if (targetPiece != null) {
			targetPiece.moveToLocation(point, false);
		} else {
			board.clear(point);
		}
		return causesCheck;
	}
}
