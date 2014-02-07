package unitTests.test;
import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import chessLayout.Board;
import chessLayout.Piece;
import chessLayout.RectangularBoard;
import chessPieces.King;
import chessPieces.Pawn;
import chessPieces.Queen;
import chessPieces.Rook;


public class BoardTest {

	Board board = new RectangularBoard(8,8);
	boolean checkListenerCalled = false;
	boolean checkMateListenerCalled = false;
	
	@Test
	/**
	 * Tests if a given location is within
	 * the bounds of the given board
	 */
	public void testInBounds() {
		// Invalid Point
		Point testPoint1 = new Point(8,7);
		assertEquals("Out of bounds point; Should return false", false, board.inBounds(testPoint1));
		// Valid Point
		Point testPoint2 = new Point(0,0);
		assertEquals("In bounds point; Should return true", true, board.inBounds(testPoint2));
		// Null value
		assertEquals("Null point passed; Should return false", false, board.inBounds(null));
	}

	@Test
	/**
	 * Tests if a given piece has been properly
	 * placed on the chess board and if
	 * it can be properly retrieved
	 */
	public void testSetAndGetPiece() {
		Piece testPiece = new Pawn(0, 5, 5, board);
		Point currentLocation = new Point(5,5);
		Point emptyLocation = new Point(5,7);
		Point invalidLocation = new Point(8,8);
		board.setPiece(testPiece.getLocation(), testPiece);
		// Valid piece and its location
		assertEquals("Should return testPiece", testPiece, board.getPiece(currentLocation));
		// Empty location
		assertEquals("Empty position; Should return null", null, board.getPiece(emptyLocation));
		// Invalid location
		assertEquals("Invalid location; Should return null", null, board.getPiece(invalidLocation));
		testPiece.die();
	}
	
	@Test
	/**
	 * Tests retrieval of the set of 
	 * all active pieces for a given player
	 */
	public void testGetPlayerPieces() {
		// No pieces for player 0
		assertEquals("No pieces; Should return empty list", 0, board.getPlayerPieces(0).size());
		Piece pawn = new Pawn(0,1,1,board);
		// 1 pawn for player 0
		assertEquals("Should return a list of length 1 containing a Pawn", "pawn", board.getPlayerPieces(0).get(0).getType());
		pawn.die();
		// No active players for player 0
		assertEquals("No active pieces; Should return empty list", 0, board.getPlayerPieces(0).size());
	}

	@Test
	/**
	 * Tests if chess board is appropriately 
	 * updated when piece moves
	 */
	public void testUpdatePointPieceBoolean() {
		Piece testPiece = new Pawn(0, 5, 5, board);
		Point oldLocation = new Point(5,5);
		Point newLocation = new Point(6,6);
		testPiece.move(newLocation, board);
		// Test old location
		assertEquals("Piece moved testing oldLocation; Shout return null", null, board.getPiece(oldLocation));
		// Test new location
		assertEquals("Piece moved testing newLocation; Shout return testPiece", testPiece, board.getPiece(newLocation));
		testPiece.die();
		// Inactive piece
		assertEquals("Dead piece; Should return null", null, board.getPiece(newLocation));
	}

	@Test
	/**
	 * Tests conditions for Check
	 */
	public void testCheck() {
		// Starting in check position
		Piece offQueen = new Queen(0,4,0,board);
		Piece defKing = new King(1,4,4,board);
		assertEquals("Check position; Should return true", true, board.check(0));
		// Queen moves to a non-threatening position
		offQueen.move(new Point(5,0), board);
		assertEquals("Safe position; Should return false", false, board.check(0));
		// Queen moves back
		offQueen.move(new Point(4,0), board);
		// King moves to safe position
		defKing.move(new Point(5,4), board);
		assertEquals("Safe position; Should return false", false, board.check(0));
		// King can move anywhere other than column 4
		assertEquals("King should have only 5 possible moves", 5, defKing.getValidMoveLocations(true).size());
		offQueen.die();
		defKing.die();
	}
	
	@Test
	/**
	 * Tests conditions for CheckMate
	 */
	public void testCheckMate() {
		// Starting in check + checkmate position
		Piece offQueen = new Queen(0,1,6,board);
		Piece offRook = new Rook(0,1,7,board);
		Piece defKing = new King(1,7,7,board);
		assertEquals("King should be in check", true, board.check(0));
		assertEquals("King in checkmate; Should return true", true, board.checkMate(1));
		// Move Rook behind Queen, no longer check/checkmate
		offRook.move(new Point(0,6), board);
		assertEquals("Not check; Should return false", false, board.check(0));
		assertEquals("Not checkmate; Should return false", false, board.checkMate(1));
		// Move Queen to attacking position with Rook's support, check + checkmate
		offQueen.move(new Point(6,6), board);
		assertEquals("King should be in check again", true, board.check(0));
		assertEquals("King in checkmate again; Should return true", true, board.checkMate(1));
		offQueen.die();
		offRook.die();
		defKing.die();
	}

	@Test
	/**
	 * Tests that listeners for
	 * Check and CheckMate get called
	 */
	public void testSetCheckListener() {
		// Starts in safe position
		Piece offQueen = new Queen(0,6,0,board);
		Piece offRook = new Rook(0,1,7,board);
		Piece defKing = new King(1,7,7,board);
		Board.CheckListener m = new Board.CheckListener() {
			@Override
			public void onCheckMate() {
				System.out.println("CheckMate");
				checkMateListenerCalled = true;
			}
			
			@Override
			public void onCheck() {
				System.out.println("Check");
				checkListenerCalled = true;
			}
		};;;
		board.setCheckListener(m);
		// Queen moves into attacking position, check listener gets called
		offQueen.move(new Point(6,6), board);
		// Rook moves to protect queen, checkmate listener gets called
		offRook.move(new Point(1,6), board);
		assertEquals("Check listener should be true", true, checkListenerCalled);
		assertEquals("CheckMate listener should be true", true, checkMateListenerCalled);
		offQueen.die();
		offRook.die();
		defKing.die();
	}

}
