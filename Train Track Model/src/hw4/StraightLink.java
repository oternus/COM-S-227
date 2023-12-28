/**
 * @author Owen Ternus
 */
package hw4;

import api.Crossable;
import api.Point;
import api.PositionVector;

public class StraightLink implements Crossable{
	
	Point a;
	/*
	 * Instance variable to keep track of point a
	 */
	Point b;
	/*
	 * Instance variable to keep track of point b
	 */
	Point c;
	/*
	 * Instance variable to keep track of point c
	 */
	
	public StraightLink(Point inA, Point inB, Point inC) {
		a = inA;
		b = inB;
		c = inC;
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
			positionVector.setPointB(b);
		}
	}

	@Override
	public Point getConnectedPoint(Point point) {
		if(point == c || point == b) {
			return a;
		}else {
			return b;
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
		}if(b != null) {
			++paths;
		}if(c != null) {
			++paths;
		}
		return paths;
	}

}
