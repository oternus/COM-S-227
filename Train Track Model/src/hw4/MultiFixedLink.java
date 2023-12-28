/**
 * @author Owen Ternus
 */
package hw4;

import api.Crossable;
import api.Point;
import api.PointPair;
import api.PositionVector;

public class MultiFixedLink implements Crossable{

	
	PointPair[] connections;
	/**
	 * Instance variable to keep track of pairs of points.
	 */
	
	public MultiFixedLink(PointPair[] connections) {
		this.connections = connections;
	}
	
	@Override
	public void shiftPoints(PositionVector positionVector) {
		boolean shifted = false;
		for(int i = 0; i < connections.length && shifted == false; ++i) {
			if(positionVector.getPointB() == connections[i].getPointA()) {
				positionVector.setPointA(connections[i].getPointA());
				positionVector.setPointB(connections[i].getPointB());
				shifted = true;
			}else if(positionVector.getPointB() == connections[i].getPointB()) {
				positionVector.setPointA(connections[i].getPointB());
				positionVector.setPointB(connections[i].getPointA());
				shifted = true;
			}
		}
	}

	@Override
	public Point getConnectedPoint(Point point) {
		for(int i = 0; i < connections.length; ++i) {
			if(connections[i].getPointA() == point) {
				return connections[i].getPointB();
			}else if(connections[i].getPointB() == point) {
				return connections[i].getPointA();
			}
		}
		return null;
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
		return connections.length;
	}

}
