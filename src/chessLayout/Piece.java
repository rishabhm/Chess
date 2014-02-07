package chessLayout;
import java.awt.Point;
import java.util.ArrayList;


public abstract class Piece {

	private int player;
	private Point location;
	private Point prevLocation;
	private boolean alive;
	private Board board;
	private ArrayList<Movement> movements; 
	
	/**
	 * Constructor
	 * @param player : The player this piece belongs to
	 * @param x : Its x coordinate
	 * @param y : Its y coordinate
	 * @param board : The board it belongs to
	 */
	public Piece(int player, int x, int y, Board board) {
		location = new Point(x,y);
		this.player = player;
		this.board = board;
		// Makes call to abstract method to retrieve all movements of the current piece
		movements = getMovements();
		alive = true;
		board.setPiece(location, this);
	}

	public int getX() {
		return location.x;
	}
	
	public int getY() {
		return location.y;
	}
	
	public int getPlayer() {
		return player;
	}
	
	/**
	 * Returns the other player
	 * A player can be either 0 or 1
	 * 0 XOR 1 = 1
	 * 1 XOR 1 = 0
	 */
	public int getOtherPlayer() {
		return player ^ 1;
	}

	/**
	 * Returns all the positions on the board this piece can move to
	 * @param safe : If set to false, function will return positions that 
	 * 				 allow for check to be placed on the King.
	 * @return ArrayList<Point>
	 */
	public ArrayList<Point> getValidMoveLocations(boolean safe) {
		ArrayList<Point> validLocations = new ArrayList<Point>();
		for(Movement m:movements) {
			validLocations.addAll(m.getPossibleMoves(this, board, safe));
		}
		return validLocations;
	}
	
	/**
	 * Abstract method
	 * Returns all the movements that this piece can do
	 * @return ArrayList<Movement>
	 */
	protected abstract ArrayList<Movement> getMovements();
	
	/**
	 * Abstract method
	 * Returns the Type of the piece
	 * @return String
	 */
	public abstract String getType();
	
	/**
	 * Moves the piece
	 * If destination is occupied, kills the piece occupying it
	 * and moves to that location
	 * NOTE : It is not necessary to check if destination is occupied
	 * 		  by the same player's piece. That logic is handled by the movements
	 * @param point
	 * @param board
	 */
	public void move(Point point, Board board) {
		Piece targetPiece = board.getPiece(point);
		if (targetPiece != null) {
			targetPiece.die();
		}
		moveToLocation(point, true);
	}
	
	/**
	 * Kills the piece and renders it inacive
	 * Updates the corresponding location on the board
	 */
	public void die() {
		alive = false;
		board.clear(location);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public Point getLocation() {
		return location;
	}
	
	/**
	 * Saves the piece's current position
	 * Updates the chess board and the piece
	 * @param point
	 * @param board
	 */
	public void moveToLocation(Point point, boolean safe) {
		prevLocation = new Point(location);
		board.update(prevLocation, point, this, safe);	
	}
	
	/**
	 * Moves piece back to previous location, if valid
	 * Updates the chess board and piece
	 * Sets previous location to null
	 * @param board
	 */
	public void undoMove(boolean safe) {
		if (prevLocation == null) {
			return;
		}
		Point currentLocation = new Point(location);
		board.update(currentLocation, prevLocation, this, safe);
		prevLocation = null;
	}
}

