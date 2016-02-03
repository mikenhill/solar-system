package com.mikenhill.stonehenge.geometric;

public class Point extends Geometric {
	private double xCoord;
	private double yCoord;
	
	public Point (double x, double y) {
		this.xCoord = x;
		this.yCoord = y;
	}
	
	public double getxCoord() {
		return xCoord;
	}
	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}
	public double getyCoord() {
		return yCoord;
	}
	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}
	
}
