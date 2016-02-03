package com.mikenhill.stonehenge.unittest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.number.IsCloseTo.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mikenhill.stonehenge.equation.EquationCircle;
import com.mikenhill.stonehenge.equation.EquationLine;
import com.mikenhill.stonehenge.geometric.Circle;
import com.mikenhill.stonehenge.geometric.Point;


@RunWith(PowerMockRunner.class)

@PrepareForTest(
        {        	
        	EquationLine.class
        }
    )

public class EquationCircleTest {
	
	private static final double DELTA = 1e-15;

	@Test
	public void test_equationCircle() throws Exception {
		
		double radius = 5.0d;
		
		//Center
		Point center = new Point(4,2);
		Point onCircle1 = new Point(9,2);
		Point onCircle2 = new Point(4,7);
		Point onCircle3 = new Point(-1,2);
		Point onCircle4 = new Point(4,-3);
		
		Point notOnCircle1 = new Point(-9,2);
		Point notOnCircle2 = new Point(-4,7);
		Point notOnCircle3 = new Point(1,2);
		Point notOnCircle4 = new Point(-4,-3);		
		
		//Create circle at 4,2
		EquationCircle eqCircle = new EquationCircle(center, radius);
		
		assertTrue(eqCircle.isPointOnCircle(onCircle1));
		assertTrue(eqCircle.isPointOnCircle(onCircle2));
		assertTrue(eqCircle.isPointOnCircle(onCircle3));
		assertTrue(eqCircle.isPointOnCircle(onCircle4));
		
		assertFalse(eqCircle.isPointOnCircle(notOnCircle1));
		assertFalse(eqCircle.isPointOnCircle(notOnCircle2));
		assertFalse(eqCircle.isPointOnCircle(notOnCircle3));
		assertFalse(eqCircle.isPointOnCircle(notOnCircle4));
		
		//Now it gets a bit tricky - let's do one at 45 degrees.
		double radians = Math.toRadians(45.0d);
		
		Point point = new Point( 
				((Math.sin(radians) * radius) + 4), 
				((Math.cos(radians) * radius) + 2));
		assertTrue(eqCircle.isPointOnCircle(point));
		
		//one at 135 degrees.
		radians = Math.toRadians(135.0d);
		
		point = new Point( 
				((Math.sin(radians) * radius) + 4), 
				((Math.cos(radians) * radius) + 2));
		assertTrue(eqCircle.isPointOnCircle(point));		
		
		//one at 225 degrees.
		radians = Math.toRadians(225.0d);
		
		point = new Point( 
				((Math.sin(radians) * radius) + 4), 
				((Math.cos(radians) * radius) + 2));
		assertTrue(eqCircle.isPointOnCircle(point));				
		
		//Now one that is not
		point = new Point( 
				((Math.sin(radians) * radius) + 44), 
				((Math.cos(radians) * radius) + 22));
		assertTrue(eqCircle.isPointOnCircle(point) == false);						
		
	}
}