/*
 * @author Owen Ternus
 */
package hw4;

import api.Crossable;
import api.Point;
import api.PointPair;
import api.PositionVector;

public class MultiSwitchLink implements Crossable{

	PointPair[] connections;
	/**
	 * Instance variable to keep track of pairs of points.
	 */
	boolean tInLink;
	/**
	 * t/f to keep track of pairs of points.
	 */
	
	public MultiSwitchLink(PointPair[] connections) {
		this.connections = connections;
		tInLink = false;
	}
	
	public void switchConnection(PointPair pointPair, int index) {
		if(tInLink == false) {
			connections[index] = pointPair;
		}
	}
	
	@Override
	public void shiftPoints(PositionVector positionVector) {
		boolean found = false;
		for(int i = 0; i < connections.length && found == false; ++i) {
			if(positionVector.getPointB() == connections[i].getPointA()) {
				positionVector.setPointA(connections[i].getPointA());
				positionVector.setPointB(connections[i].getPointB());
				found = true;
			}
			if(positionVector.getPointB() == connections[i].getPointB()) {
				positionVector.setPointA(connections[i].getPointB());
				positionVector.setPointB(connections[i].getPointA());
				found = true;
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
		tInLink = true;
		
	}

	@Override
	public void trainExitedCrossing() {
		tInLink = false;
		
	}

	@Override
	public int getNumPaths() {
		return connections.length;
	}

}
