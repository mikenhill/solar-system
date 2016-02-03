package com.mikenhill.stonehenge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mikenhill.stonehenge.geometric.Circle;


public class Field {
	private double stickLength;
	Map<String, Circle> mCircles = new HashMap<String, Circle>();
	
	
	public Field (double stickLength) {
		this.stickLength = stickLength;
	}


	public double getStickLength() {
		return stickLength;
	}


	public void setStickLength(double stickLength) {
		this.stickLength = stickLength;
	}


	public Map<String, Circle> getmCircles() {
		return mCircles;
	}


	public void setmCircles(Map<String, Circle> mCircles) {
		this.mCircles = mCircles;
	}
	
	
}
