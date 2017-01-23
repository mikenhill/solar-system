package com.mikenhill.stonehenge.geometric;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mikenhill.stonehenge.Field;
import com.mikenhill.stonehenge.StdDraw;
import com.mikenhill.stonehenge.equation.EquationCircle;
import com.mikenhill.stonehenge.equation.GeometricEquation;


public class Circle extends Geometric {
	private Map<String, Chord> mChords = new HashMap<String, Chord>();
	private EquationCircle equation;
	private Point center; 
	private double radius;
	private int numPoints;
	private Map<Integer, Point> points = new TreeMap<Integer, Point>();
	private EquationCircle eqCircle;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Circle (double radius, int numPoints, String name) {
		this.radius = radius;
		this.numPoints = numPoints;
		this.name = name;
		this.center = new Point (0.0d, 0.0d);
		if (numPoints > 0) {
			makeSelf();
		}
		
	}
	public Circle (double radius, Point center, String name) {
		this.radius = radius;
		this.numPoints = 0;
		this.center = center;
		this.name = name;
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
	
	public GeometricEquation getEquation() {
		return eqCircle;
	}
	
	public void annotate (Color circleColor) {
		if (getPoints() != null && getPoints().size() > 0) {
			for (Integer index : getPoints().keySet()) {
				Point p = getPoints().get(index);
				StdDraw.setPenRadius(0.009);
				StdDraw.setPenColor(circleColor);			
				//Draw a chord
				StdDraw.text(p.getX() * 1.03, p.getY() * 1.03, ""+ index);
				StdDraw.show(10);
			}
		}
	}
	
	private void makeSelf () {
		
		eqCircle = new EquationCircle(center, radius);
		
		double degIncrement = 360d/numPoints;
		double degrees = 0.0;
		for (int i = 0; i<numPoints; i++) {
			
			degrees += degIncrement;
			double radians = Math.toRadians(degrees);
			
			Point point = new Point(Math.sin(radians) * radius, Math.cos(radians) * radius);
			points.put(new Integer(i+1), point);
			
		}
	}
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public void setEquation(EquationCircle equation) {
		this.equation = equation;
	}
	public Map<String, Chord> getmChords() {
		return mChords;
	}
	public void setmChords(Map<String, Chord> mChords) {
		this.mChords = mChords;
	}
	
	public void draw(Color circleColor) {
		StdDraw.setPenRadius(0.0005);
		StdDraw.setPenColor(circleColor);
		StdDraw.circle(getCenter().getX(), getCenter().getY(), getRadius());
		StdDraw.show(10);
		
	}
	
	
	public void drawByPoints(Color circleColor) {
		for (Integer pointIndex : getPoints().keySet()) {
			Point point = getPoints().get(pointIndex);
			StdDraw.setPenRadius(0.0005);
			StdDraw.setPenColor(circleColor);
			StdDraw.point(point.getX(), point.getY());
			
			int point2Index = ((pointIndex + 2) % numPoints) + 1;
			Point point2 = getPoints().get(point2Index);
			StdDraw.line(
					point.getX(), 
					point.getY(),
					point2.getX(),
					point2.getY());
			
			StdDraw.show(10);
			

			
		}
	}
}
