package unitTests.test;
import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import chessLayout.Board;
import chessLayout.Piece;
import chessLayout.RectangularBoard;
import chessPieces.Bishop;
import chessPieces.King;
import chessPieces.Knight;
import chessPieces.Pawn;
import chessPieces.Queen;
import chessPieces.Rook;


public class PieceTest {
	
	Board squareBoard = new RectangularBoard(3,3);
	Board bigSquareBoard = new RectangularBoard(5,5);
	Board rectBoard = new RectangularBoard(2,3);
	
	@Test
	/**
	 * Tests that pawn's change y-axis appropriately
	 */
	public void testPawnDirection() {
		Piece pawn0 = new Pawn(0, 1, 1, squareBoard);
		ArrayList<Point> possibleLocations0 = pawn0.getValidMoveLocations(false);
		assertEquals(1, possibleLocations0.size());
		assertEquals(new Point(1,2), possibleLocations0.get(0));
		pawn0.die();
		Piece pawn1 = new Pawn(1,1,1,squareBoard);
		ArrayList<Point> possibleLocations1 = pawn1.getValidMoveLocations(false);
		assertEquals(1, possibleLocations1.size());
		assertEquals(new Point(1,0), possibleLocations1.get(0));
		pawn1.die();
	}

	@Test
	/**
	 * Tests pawn's special first move
	 */
	public void testPawnFirstMove() {
		Piece pawn = new Pawn(0,1,0,squareBoard);
		ArrayList<Point> possibleLocations = pawn.getValidMoveLocations(false);
		assertEquals(true, possibleLocations.contains(new Point(1,2)));
		pawn.die();
	}
	
	@Test
	public void functionName() {
		
	}
	
	@Test
	/**
	 * Tests pawn's kill move
	 */
	public void testPawnKill() {
		Piece pawn = new Pawn(0,1,0,squareBoard);
		Piece rook = new Rook(1,0,1, squareBoard);
		Point pawnLocation = new Point(1,0);
		Point rookLocation = new Point(0,1);
		ArrayList<Point> possibleLocations = pawn.getValidMoveLocations(false);
		assertEquals(true, possibleLocations.contains(rookLocation));
		assertEquals("rook", squareBoard.getPiece(rookLocation).getType());
		pawn.move(rookLocation, squareBoard);
		assertEquals("pawn", squareBoard.getPiece(rookLocation).getType());
		assertEquals(false, rook.isAlive());
		pawn.die();
	}
	

	@Test
	/**
	 * Tests king's move
	 */
	public void testKing() {
		Piece king = new King(0,1,1,squareBoard);
		Point behind = new Point(1,0);
		Point ahead = new Point(1,2);
		Point left = new Point(0,1);
		Point right = new Point(2,1);
		Point fl = new Point(0,2);
		Point fr = new Point(2,2);
		Point bl = new Point(0,0);
		Point br = new Point(2,0);
		ArrayList<Point> possibleLocations = king.getValidMoveLocations(false);
		assertEquals(true, possibleLocations.contains(behind));
		assertEquals(true, possibleLocations.contains(ahead));
		assertEquals(true, possibleLocations.contains(left));
		assertEquals(true, possibleLocations.contains(right));
		assertEquals(true, possibleLocations.contains(fl));
		assertEquals(true, possibleLocations.contains(fr));
		assertEquals(true, possibleLocations.contains(bl));
		assertEquals(true, possibleLocations.contains(br));
		king.die();
		
	}	
	
	@Test
	/**
	 * Tests rook's moves
	 */
	public void testRook() {
		Piece rook = new Rook(0,2,2,bigSquareBoard);
		Point behind = new Point(2,0);
		Point ahead = new Point(2,4);
		Point left = new Point(0,2);
		Point right = new Point(4,2);
		ArrayList<Point> possibleLocations = rook.getValidMoveLocations(false);
		assertEquals(true, possibleLocations.contains(behind));
		assertEquals(true, possibleLocations.contains(ahead));
		assertEquals(true, possibleLocations.contains(left));
		assertEquals(true, possibleLocations.contains(right));
		rook.die();
	}
	
	@Test
	/**
	 * Tests bishop's moves
	 */
	public void testBishop() {
		Piece bishop = new Bishop(0,2,2,bigSquareBoard);
		Point fl = new Point(0,4);
		Point fr = new Point(4,4);
		Point bl = new Point(0,0);
		Point br = new Point(4,0);
		ArrayList<Point> possibleLocations = bishop.getValidMoveLocations(false);
		assertEquals(true, possibleLocations.contains(fl));
		assertEquals(true, possibleLocations.contains(fr));
		assertEquals(true, possibleLocations.contains(bl));
		assertEquals(true, possibleLocations.contains(br));
		bishop.die();
	}
	
	@Test
	/**
	 * Test queen's moves
	 */
	public void testQueen() {
		Piece queen = new Queen(0,2,2,bigSquareBoard);
		Point behind = new Point(2,0);
		Point ahead = new Point(2,4);
		Point left = new Point(0,2);
		Point right = new Point(4,2);
		Point fl = new Point(0,4);
		Point fr = new Point(4,4);
		Point bl = new Point(0,0);
		Point br = new Point(4,0);
		ArrayList<Point> possibleLocations = queen.getValidMoveLocations(false);
		assertEquals(true, possibleLocations.contains(behind));
		assertEquals(true, possibleLocations.contains(ahead));
		assertEquals(true, possibleLocations.contains(left));
		assertEquals(true, possibleLocations.contains(right));
		assertEquals(true, possibleLocations.contains(fl));
		assertEquals(true, possibleLocations.contains(fr));
		assertEquals(true, possibleLocations.contains(bl));
		assertEquals(true, possibleLocations.contains(br));
		queen.die();
	}
	
	@Test
	/**
	 * Tests knight's moves
	 */
	public void testKnight() {
		Piece knight = new Knight(0,0,0,rectBoard);
		ArrayList<Point> possibleLocations = knight.getValidMoveLocations(false);
		Point tr = new Point(1,2);
		assertEquals(1, possibleLocations.size());
		assertEquals(true, possibleLocations.contains(tr));
		knight.die();
	}
	
	@Test
	/**
	 * Tests if a piece can kill another piece of the same player
	 */
	public void testFriendlyFire() {
		Piece pawn = new Pawn(0,0,0,squareBoard);
		Piece rook = new Rook(0,1,1,squareBoard);
		ArrayList<Point> possibleLocations = pawn.getValidMoveLocations(false);
		Point rookLocation = new Point(1,1);
		assertEquals(false, possibleLocations.contains(rookLocation));
	}
}
