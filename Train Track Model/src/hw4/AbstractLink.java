/**
 * @author Owen Ternus
 */

/* SPECIAL DOCUMENTATION DESCRIPTION
 * For this portion of the assignment, I decided to organize my class hierarcy with crossable at the top. In addition to this, I had AbstractLink just below crossable. Then, all the other link classes below that for organizational/ease of use.
 */

package hw4;

import api.Crossable;
import api.Point;
import api.PositionVector;

public class AbstractLink implements Crossable{
	
	Point a;
	/**
	 * instance variable to keep track of point a
	 */
	Point b;
	/**
	 * instance variable to keep track of point b
	 */
	Point c;
	/**
	 * instance variable to keep track of point c
	 */
	
	public AbstractLink(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Override
	public void shiftPoints(PositionVector positionVector) {
		if(positionVector.getPointB() == c) {
			positionVector.setPointA(c);
			positionVector.setPointB(a);
		}else if(positionVector.getPointB() == b) {
			positionVector.setPointA(b);
			positionVector.setPointB(a);
		}else {
			positionVector.setPointA(a);
			positionVector.setPointB(c);
		}
		
	}

	@Override
	public Point getConnectedPoint(Point point) {
		if(point == b || point == c) {
			return a;
		}else {
			return c;
		}
	}

	@Override
	public void trainEnteredCrossing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trainExitedCrossing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumPaths() {
		int paths = 0;
		if(a != null) {
			++paths;
		}
		if(b != null) {
			++paths;
		}
		if(c != null) {
			++paths;
		}
		return 1;
	}
}
