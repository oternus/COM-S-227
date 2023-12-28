/**
 * @author Owen Ternus
 */
package hw4;

import api.Point;
import api.PositionVector;

public class SwitchLink extends AbstractLink{
	
	private Point a;
	/*
	 * Instance variable to keep track of point a
	 */
	private Point b;
	/*
	 * Instance variable to keep track of point b
	 */
	private Point c;
	/*
	 * Instance variable to keep track of point c
	 */
	private boolean tInCrossing, turn;
	/*
	 * t/f to keep track of if train is crossing/turning.
	 */
	public SwitchLink(Point a, Point b, Point c) {
		super(a, b, c);
		tInCrossing = turn = false;
	}
	
	@Override
	public void shiftPoints(PositionVector positionVector) {
		if(positionVector.getPointB() == b) {
			positionVector.setPointA(b);
			positionVector.setPointB(a);
		}else if(positionVector.getPointB() == c) {
			positionVector.setPointA(c);
			positionVector.setPointB(a);
		}else if(positionVector.getPointB() == a && turn == true) {
			positionVector.setPointA(a);
			positionVector.setPointB(c);
		}else {
			positionVector.setPointA(a);
			positionVector.setPointB(b);
		}
		
	}

	@Override
	public Point getConnectedPoint(Point point) {
		if(point == b || point == c) {
			return a;
		}else if(point == a && turn == true) {
			return c;
		}else {
			return b;
		}
	}

	@Override
	public void trainEnteredCrossing() {
		tInCrossing = true;
	}

	@Override
	public void trainExitedCrossing() {
		tInCrossing = false;
	}

	public void setTurn(boolean turn) {
		if(tInCrossing == false) {
			this.turn = turn;
		}
	}

}
