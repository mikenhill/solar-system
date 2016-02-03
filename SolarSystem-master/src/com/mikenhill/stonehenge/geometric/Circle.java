package com.mikenhill.stonehenge.geometric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mikenhill.stonehenge.equation.EquationCircle;


public class Circle extends Geometric {
	private EquationCircle equation;
	
	private double radius;
	private int numPoints;
	private Map<Integer, Point> points = new HashMap<Integer, Point>();
	public Circle (double radius, int numPoints) {
		this.radius = radius;
		this.numPoints = numPoints;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public int getNumPoints() {
		return numPoints;
	}
	public void setNumPoints(int numPoints) {
		this.numPoints = numPoints;
	}
	public Map<Integer, Point> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Point> coordinates) {
		this.points = coordinates;
	}
	
	public void makeSelf () {
		double degIncrement = 360d/numPoints;
		double degrees = 0.0;
		for (int i = 0; i<numPoints; i++) {
			
			degrees += degIncrement;
			double radians = Math.toRadians(degrees);
			
			Point point = new Point(Math.sin(radians) * radius, Math.cos(radians) * radius);
			points.put(new Integer(i+1), point);
			
		}
	}
	
}
