/**
 * @author Owen Ternus
 */
package hw4;

import api.Point;

public class TurnLink extends AbstractLink{
	
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
	
	public TurnLink(Point a, Point b, Point c) {
		super(a, b, c);
	}
	
}
