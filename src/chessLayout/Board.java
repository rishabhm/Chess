package chessLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class Board {

	// Maintains state of the chessboard
	private HashMap<Point, Piece> grid = new HashMap<Point, Piece>();
	// Listens for Check and CheckMate
	private CheckListener mCheckListener;
	
	/**
	 * Abstract method
	 * Checks if given point is within bounds
	 * @param point
	 * @return boolean
	 */
	public abstract boolean inBounds(Point point);
	
	/**
	 * Place given piece at the given location
	 * @param location
	 * @param piece
	 */
	public void setPiece(Point location, Piece piece) {
		if (piece == null) {
			return;
		}
		grid.put(location, piece);
	}
	
	/**
	 * Polymorphic function
	 * Adds boolean parameter and calls update(Point, Point, Piece, boolean)
	 * @param oldLocation
	 * @param newLocation
	 * @param piece
	 */
	public void update(Point oldLocation, Point newLocation, Piece piece) {
		update(oldLocation, newLocation, piece, true);
	}
	
	/**
	 * Updates the chess board and the location of the piece
	 * Can also test for Check and CheckMate conditions
	 * @param oldLocation : Current location of the piece
	 * @param newLocation : Location to which the piece should be moved
	 * @param piece : The piece
	 * @param notifyListener : If false, function will not test for Check (or CheckMate)
	 * 						   Consequently, their listeners will not be notified
	 */
	public void update(Point oldLocation, Point newLocation, Piece piece, boolean notifyListener) {
		if (piece == null) {
			return;
		}
		if (oldLocation != null) {
			// Clears old position
			grid.remove(oldLocation);	
		}
		// Updates the piece's location
		piece.getLocation().setLocation(newLocation);
		// Reflects the change in the chess board
		grid.put(newLocation, piece);

		if (notifyListener && mCheckListener != null) {
			if (check(piece.getPlayer())) {
				// if check, tests for checkmate
				// notifies appropriate listener
				if (checkMate(piece.getOtherPlayer())) {
					mCheckListener.onCheckMate();
				} else {
					mCheckListener.onCheck();
				}
			}
		}
	}
	
	/**
	 * Tests for Check condition
	 * @param offensivePlayer
	 * @return boolean
	 */
	public boolean check(int offensivePlayer) {
		// Iterate through all the offending player's pieces
		for (Piece piece:getPlayerPieces(offensivePlayer)) {
			// Iterate through all the locations they can move
			// "false" indicates that these positions are allowed to endanger their own King
			// because if one of these positions can kill the opposition's King, the game would conclude
			for (Point point:piece.getValidMoveLocations(false)) {
				// Get the piece at that location
				Piece currentPiece = grid.get(point);
				// If the piece at that location is a King, it implies a Check state
				// NOTE: We do not need to check if its their own King
				// 		 That logic is done by movements from inside getValidMoveLocations()
				if (currentPiece != null && currentPiece.getType().equals("king")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Tests for CheckMate condition
	 * @param defensivePlayer
	 * @return boolean
	 */
	public boolean checkMate(int defensivePlayer) {
		// Should only continue if a Check is in place
		if (!check(defensivePlayer ^ 1)) {
			return false;
		}
		// Iterate through all the defending player's pieces
		for (Piece defensivePiece:getPlayerPieces(defensivePlayer)) {
			// Iterate through each piece's possible moves
			for (Movement move:defensivePiece.getMovements()) {
				// If even a single possible move exists, it implies no Check Mate
				// NOTE: A possible move exists only if it does not result in a Check state
				// 		 Thus, existence of a possible move indicates a way to escape from Check
				if (!move.getPossibleMoves(defensivePiece, this, true).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Return all the pieces of the given player
	 * @param player
	 * @return ArrayList<Piece>
	 */
	public ArrayList<Piece> getPlayerPieces(int player) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		// Iterate through all the keys in the grid
		for (Point p:grid.keySet()) {
			// Get piece at each key
			Piece currentPiece = grid.get(p);
			// If the piece is active and belongs to the current player, accept it
			if (currentPiece.isAlive() && currentPiece.getPlayer() == player) {
				pieces.add(currentPiece);
			}
		}
		return pieces;
	}
	
	/**
	 * Returns piece at a given location
	 * @param point
	 * @return
	 */
	public Piece getPiece(Point point) {
		return grid.get(point);
	}
	
	/**
	 * Sets a listener for Check and CheckMate
	 * @param chk
	 */
	public void setCheckListener(CheckListener chk) {
		mCheckListener = chk;
	}
	
	/**
	 * Clears a piece from a given location
	 * Deletes the corresponding key
	 * @param point
	 */
	public void clear(Point point) {
		grid.remove(point);
	}
	
	/**
	 * Interface for listeners for Check and CheckMate
	 * @author rishabhmarya
	 *
	 */
	public interface CheckListener {
		public void onCheck();
		public void onCheckMate();
	}
	
}
