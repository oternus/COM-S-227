/**
 * @author Owen Ternus
 */
package hw4;

import api.Crossable;
import api.Point;
import api.PositionVector;

public class DeadEndLink implements Crossable {
	public DeadEndLink() {
		
	}

	@Override
	public void shiftPoints(PositionVector positionVector) {
		
	}

	@Override
	public Point getConnectedPoint(Point point) {
		return null;
	}

	@Override
	public void trainEnteredCrossing() {
		
	}

	@Override
	public void trainExitedCrossing() {
		
	}

	@Override
	public int getNumPaths() {
		return 0;
	}
	
}
