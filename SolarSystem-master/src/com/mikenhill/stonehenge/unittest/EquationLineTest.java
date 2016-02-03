package com.mikenhill.stonehenge.unittest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.number.IsCloseTo.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mikenhill.stonehenge.equation.EquationLine;
import com.mikenhill.stonehenge.geometric.Point;


@RunWith(PowerMockRunner.class)

@PrepareForTest(
        {        	
        	EquationLine.class
        }
    )

public class EquationLineTest {
	
	private static final double DELTA = 1e-15;

	@Test
	public void test_equationLine() throws Exception {
	
		//====================================================================
		//Create a new EquationLine as y = x
		Point startPoint = new Point (0.0d, 0.0d);
		Point endPoint = new Point (1.0d, 1.0d);
		EquationLine eqLine = new EquationLine(startPoint, endPoint);
		
		assertThat(eqLine.getOffset(), closeTo(0.0d, DELTA));
		assertThat(eqLine.getSlope(), closeTo(1.0d, DELTA));
		assertThat(eqLine.getY(1.0d), closeTo(1.0d, DELTA));
		assertThat(eqLine.getX(1.0d), closeTo(1.0d, DELTA));
		//When x is -2, y is -2
		assertThat(eqLine.getY(-2.0d), closeTo(-2.0d, DELTA));
		
		//====================================================================
		//Create a new EquationLine as y = 1/3x + 0
		startPoint = new Point (0.0d, 0.0d);
		endPoint = new Point (3.0d, 1.0d);
		eqLine = new EquationLine(startPoint, endPoint);
		
		//offset is zero as we are starting at 0,0
		assertThat(eqLine.getOffset(), closeTo(0.0d, DELTA));
		//Slope is 1/3
		assertThat(eqLine.getSlope() , closeTo(1.0d/3.0d, DELTA));
		//When x is 3 y is 1/3 of x = 1
		assertThat(eqLine.getY(3.0d), closeTo(1.0d, DELTA));
		//When y is 1, x is 3
		assertThat(eqLine.getX(1.0d) , closeTo(3.0d, DELTA));
		
		//====================================================================
		//Create a new EquationLine as y = -x
		startPoint = new Point (0.0d, 0.0d);
		endPoint = new Point (-1.0d, 1.0d);
		eqLine = new EquationLine(startPoint, endPoint);
		
		//offset is zero as we are starting at 0,0
		assertThat(eqLine.getOffset(), closeTo(0.0d, DELTA));
		//Slope is -1
		assertThat(eqLine.getSlope() , closeTo(-1.0d, DELTA));
		//When x is -1 y is 1
		assertThat(eqLine.getY(3.0d), closeTo(-3.0d, DELTA));
		//When y is 1, x is -1
		assertThat(eqLine.getX(1.0d) , closeTo(-1.0d, DELTA));
		//When x is 1 y is -1
		assertThat(eqLine.getY(1.0d), closeTo(-1.0d, DELTA));
		
		//====================================================================
		//Create a new EquationLine as y = -x
		startPoint = new Point (0.0d, 3.0d);
		endPoint = new Point (0.0d, -3.0d);
		eqLine = new EquationLine(startPoint, endPoint);
		
		//offset is zero as we are starting at 0,0
		assertThat(eqLine.getOffset(), closeTo(0.0d, DELTA));
		//Slope is -1
		assertTrue(eqLine.getSlope() == Double.POSITIVE_INFINITY);
		//When x is -1 y is 1
		assertTrue(eqLine.getY(3.0d) == Double.POSITIVE_INFINITY);
		
		
		
	}
}
