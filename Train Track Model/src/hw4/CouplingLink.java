/**
 * @author Owen Ternus
 */
package hw4;

import api.Crossable;
import api.Point;
import api.PositionVector;

public class CouplingLink implements Crossable{

	Point hp;
	/*
	 * Instance variable to keep track of high point.
	 */
	Point lp;
	/**
	 * Instance variable to keep track of low point.
	 */
	
	public CouplingLink(Point highpoint, Point lowpoint) {
		lp = lowpoint;
		hp = highpoint;
	}
	
	@Override
	public void shiftPoints(PositionVector positionVector) {
		if(positionVector.getPointB() == hp) {
			positionVector.setPointA(hp);
			positionVector.setPointB(lp);
		}else {
			positionVector.setPointA(lp);
			positionVector.setPointB(hp);
		}
	}

	@Override
	public Point getConnectedPoint(Point point) {
		if(point.getX() == lp.getX() && point.getY() == lp.getY()) {
			return hp;
		}else {
			return lp;
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
		if(lp != null) {
			++paths;
		}
		if(hp != null) {
			++paths;
		}
		return paths;
	}
	
	

}
