package com.mikenhill.stonehenge.equation;

import java.math.BigDecimal;

import com.mikenhill.stonehenge.geometric.Point;
//Circle is all the points (x,y) that are "r" away from the center (a,b).
//So if we know x,y coordinate, then the equation is
// (x-a)^2 + (y-b)^2 = r^2
public class EquationCircle extends GeometricEquation {
	private Point centerOfCircle;
	private double radius;
	private double x;
	private double y;
	
	
	
	public EquationCircle (final Point center, double radius) {
		this.centerOfCircle = center;
		this.radius = radius;
	}
	public Point getCenterOfCircle() {
		return centerOfCircle;
	}
	public void setCenterOfCircle(Point centerOfCircle) {
		this.centerOfCircle = centerOfCircle;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public boolean isPointOnCircle (final Point point) {
		
		BigDecimal xCoord = new BigDecimal (point.getxCoord()).setScale(6, BigDecimal.ROUND_DOWN);
		BigDecimal yCoord = new BigDecimal (point.getyCoord()).setScale(6, BigDecimal.ROUND_DOWN);
		
		BigDecimal eqLHS = new BigDecimal(Math.pow((point.getxCoord() - centerOfCircle.getxCoord()),2) 
				   + Math.pow((point.getyCoord() - centerOfCircle.getyCoord()),2)).setScale(6, BigDecimal.ROUND_DOWN);
		
		BigDecimal eqRHS = new BigDecimal(Math.pow(radius,2)).setScale(6, BigDecimal.ROUND_DOWN);
		
		if (eqLHS.equals(eqRHS)) {
			return true;
		} else {
			return false;
		}
	}
	
}
