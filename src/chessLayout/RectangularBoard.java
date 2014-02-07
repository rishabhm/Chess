package chessLayout;
import java.awt.Point;



public class RectangularBoard extends Board {

	int width, height;
	
	public RectangularBoard(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean inBounds(Point point) {
		if (point == null) {
			return false;
		}
		boolean checkX = point.x >= 0 && point.x < width,
				checkY = point.y >= 0 && point.y < height;
		return checkX && checkY;
	}

}
