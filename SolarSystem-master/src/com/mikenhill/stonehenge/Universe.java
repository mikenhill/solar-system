package com.mikenhill.stonehenge;

public class Universe {
	private double radiusOfUniverse;
	// Gravitational Constant
	private double gravitationalConstant = 6.674e-11;
	
	public Universe (double radius) {
		setRadiusOfUniverse(radius);
	}
	
	public double getRadiusOfUniverse() {
		return radiusOfUniverse;
	}

	public void setRadiusOfUniverse(double radiusOfUniverse) {
		this.radiusOfUniverse = radiusOfUniverse;
	}

	public double getGravitationalConstant() {
		return gravitationalConstant;
	}

	public void setGravitationalConstant(double gravitationalConstant) {
		this.gravitationalConstant = gravitationalConstant;
	}
	
	
	
}
