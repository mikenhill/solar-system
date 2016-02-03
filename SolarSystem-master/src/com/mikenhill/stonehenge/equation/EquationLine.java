package com.mikenhill.stonehenge.equation;

import com.mikenhill.stonehenge.geometric.Point;

public class EquationLine extends GeometricEquation {
	private double slope;
	private double offset;
	private Point startPoint;
	private Point endPoint;
	private double x;
	private double y;
	
	public double getX(double y) {
		if (slope == 0.0d) {
			return 0.0d;
		} else {
			return (y - offset) / slope ;
		}
	}

	public double getY(double x) {
		return (slope * x) + offset;
	}

	public double getSlope() {
		return slope;
	}

	public double getOffset() {
		return offset;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public EquationLine (Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;	
		this.computeSelf();
	}
	
	public void computeSelf () {
		setSlope(findSlope(startPoint, endPoint));
		setOffset(findOffset(startPoint, getSlope()));
	}
	public double findSlope (final Point startPoint, final Point endPoint) {
		return ((startPoint.getyCoord() - endPoint.getyCoord())) / 
				((startPoint.getxCoord() - endPoint.getxCoord()));
	}
	//y = mx + b, so b = y - mx
	public double findOffset (final Point aPoint, final double slopeOfLine) {
		if (slopeOfLine == Double.POSITIVE_INFINITY || slopeOfLine == Double.NEGATIVE_INFINITY) {
			return 0.0d;
		}
		double diviser = (slopeOfLine * aPoint.getxCoord());
		if (diviser == 0.0d) {
			return 0.0d;
		} else {
			return aPoint.getyCoord() / diviser;
		}
		
	}	
}
