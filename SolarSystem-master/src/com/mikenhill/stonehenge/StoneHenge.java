package com.mikenhill.stonehenge;
import java.util.ArrayList;
import java.util.List;

import com.mikenhill.stonehenge.equation.EquationLine;
import com.mikenhill.stonehenge.geometric.Circle;
import com.mikenhill.stonehenge.geometric.Point;


public class StoneHenge {
	public static void main(String[] args) throws Exception {
		
		StoneHenge sHenge = new StoneHenge();
		sHenge.calc();
		
	}
	
	private void calc() {
		List<Point> lCoordinate = new ArrayList<Point>();
		Point intersection = new Point(1,1);
		double stickLength = 1.0d;//294.0d;
		double fieldWidth = 10 * stickLength;
		double fieldHeight = fieldWidth;
		double radiusInner = stickLength * 10;//10		
		int numPoints = 56;
		
		Field shField = new Field(stickLength);
		StdDraw.setXscale(-fieldWidth ,fieldWidth);
		StdDraw.setYscale(-fieldHeight, fieldHeight);
		StdDraw.picture(0, 0, "",fieldWidth,fieldWidth);		
		
		//Draw X and Y axis
		StdDraw.setPenRadius(0.0009);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.line(-fieldWidth, 0, +fieldWidth, 0);
		StdDraw.line(0, -fieldHeight, 0, +fieldHeight);
				
		//Draw grid
		drawGrid(fieldWidth, fieldHeight);
		
		//Inner circle
		Circle innerCircle = new Circle(radiusInner, numPoints);
		innerCircle.makeSelf();
		shField.getmCircles().put("aubrey", innerCircle);
		
//		//Middle
//		Circle middleCircle = new Circle(radiusInner + (stickLength * 1), numPoints);
//		middleCircle.makeSelf();
//		shField.getmCircles().put("middle", middleCircle);
//
//		//Outer
//		Circle outerCircle = new Circle(radiusInner + (stickLength * 2), numPoints);
//		outerCircle.makeSelf();
//		shField.getmCircles().put("outer", outerCircle);
		
		
		
		for (Circle thisCircle : shField.getmCircles().values()) {
			//We must draw in point order
			for (Integer pointIndex : thisCircle.getPoints().keySet()) {
				Point point = thisCircle.getPoints().get(pointIndex);
				StdDraw.setPenRadius(0.0005);
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.point(point.getxCoord(), point.getyCoord());
				
				int point2Index = ((pointIndex + 2) % numPoints) + 1;
				Point point2 = thisCircle.getPoints().get(point2Index);
				StdDraw.line(
						point.getxCoord(), 
						point.getyCoord(),
						point2.getxCoord(),
						point2.getyCoord());
				
				StdDraw.show(10);
			}
		
			//Step 8 - Draw a chord across the circle from Aubrey point 2 to point 42 and make a mark where it crosses the axis line. 
			//As a check, repeat this for Aubrey point 54 and point 14.
			//Chord-2-42
			StdDraw.line(thisCircle.getPoints().get(2).getxCoord(),thisCircle.getPoints().get(2).getyCoord(),
						 thisCircle.getPoints().get(42).getxCoord(),thisCircle.getPoints().get(42).getyCoord());
			//Chord-54-14
			StdDraw.line(thisCircle.getPoints().get(54).getxCoord(),thisCircle.getPoints().get(54).getyCoord(),
					 thisCircle.getPoints().get(14).getxCoord(),thisCircle.getPoints().get(14).getyCoord());
			
			//Step 9 - Find the point where Chord-2-42 and Chord-54-14 cross - this will be on the vertical axis  
			intersection = lineIntersect(thisCircle.getPoints().get(2).getxCoord(),thisCircle.getPoints().get(2).getyCoord(),
						 thisCircle.getPoints().get(42).getxCoord(),thisCircle.getPoints().get(42).getyCoord(),
						 thisCircle.getPoints().get(54).getxCoord(),thisCircle.getPoints().get(54).getyCoord(),
						 thisCircle.getPoints().get(14).getxCoord(),thisCircle.getPoints().get(14).getyCoord());
			
			//Draw a point at this intersection
			StdDraw.setPenRadius(0.005);
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.point(intersection.getxCoord(), intersection.getyCoord());
			StdDraw.show(10);
		}
	
		//Vernier circle
		//Step 9 - Find the distance from intersection to point 28 - this is the diameter of the vernier circle
		double x = shField.getmCircles().get("aubrey").getPoints().get(28).getxCoord() - intersection.getxCoord();
		double y = shField.getmCircles().get("aubrey").getPoints().get(28).getyCoord() - intersection.getyCoord();
		double vernierRadius = Math.sqrt((x*x) + (y*y))/2;
		
		StdDraw.setPenRadius(0.0009);
		StdDraw.setPenColor(StdDraw.BLACK);
		Point vernierCenter = new Point(0.0d, (intersection.getyCoord() + shField.getmCircles().get("aubrey").getPoints().get(28).getyCoord())/2);
		
		StdDraw.circle(vernierCenter.getxCoord(), vernierCenter.getyCoord(), vernierRadius);
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.point(vernierCenter.getxCoord(), vernierCenter.getyCoord());
		StdDraw.show(10);
		
		//Step 10 - 
		int pointIndex = 55; //start point
		int pointJump = 16;
		int numPointsToPlot = 15;
		pointIndex = pointIndex % numPoints;
		while (numPointsToPlot > 0) {
			//Draw a line from this point to point + 16			
			Point startPoint = shField.getmCircles().get("aubrey").getPoints().get(pointIndex % numPoints);
			Point endPoint = shField.getmCircles().get("aubrey").getPoints().get( (pointIndex + pointJump) % numPoints);
			StdDraw.setPenRadius(0.0005);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(startPoint.getxCoord(),startPoint.getyCoord(),endPoint.getxCoord(),endPoint.getyCoord());
			StdDraw.show(10);
			pointIndex = (pointIndex+2) % numPoints;
			numPointsToPlot--;
		}
		
		//The equation of the vernier circle is: (x-h)^2 + (y-k)^2 = r^2 
		//Radius = vernierRadius
		//x = vernierCenter.getxCoord()
		//y = vernierCenter.getyCoord()
		//The equation of the Chord-55-15 = y = mx + b
		//m = findSlopeOfLine
		//b = findBOfLine
		EquationLine eqLine55_15 = new EquationLine(
				shField.getmCircles().get("aubrey").getPoints().get(55),
				shField.getmCircles().get("aubrey").getPoints().get(15)); 
		
		//So y = (slopeOfLine * x) + bOfLine
		//So y^2 = [(slopeOfLine * x) + bOfLine]^2
		//y^2 = slopeOfLine*x^2 + (2*bOfLine)*x + (2*bOfLine) 
		//Substituting for the equation of a circle:
		//x^2 + slopeOfLine*x^2 + (2*bOfLine)*x + (2*bOfLine) = radius^2
		//(slopeOfLine+1)*x^2 + (2*bOfLine)*x + (2*bOfLine-radius^2) = 0
		double aComponent = eqLine55_15.getSlope() + 1.0d;
		double bComponent = eqLine55_15.getOffset() * 2.0d;
		double cComponent = (eqLine55_15.getOffset() * 2.0d) - (vernierRadius);
		double root1 = quadraticEquationRoot1 (aComponent, bComponent, cComponent);
		double root2 = quadraticEquationRoot2 (aComponent, bComponent, cComponent);
		StdDraw.setPenRadius(0.05);
		StdDraw.setPenColor(StdDraw.PINK);
		StdDraw.point(root1, root2);
		StdDraw.show(10);
	}
	
	public static Point lineIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		  double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		  if (denom == 0.0) { // Lines are parallel.
		     return null;
		  }
		  double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))/denom;
		  double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))/denom;
		    if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
		        // Get the intersection point.
		        return new Point( (x1 + ua*(x2 - x1)),  (y1 + ua*(y2 - y1)));
		    }

		  return null;
	}	
	
	
	
	public static  double quadraticEquationRoot1(double a, double b, double c) {    
	    double root1, root2; //This is now a double, too.
	    root1 = (-b + Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*a);
	    root2 = (-b - Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*a);
	    return Math.max(root1, root2);  
	}

	public static double quadraticEquationRoot2(double a, double b, double c) {    
	    //Basically the same as the other method, but use Math.min() instead!
		double root1, root2; //This is now a double, too.
	    root1 = (-b + Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*a);
	    root2 = (-b - Math.sqrt(Math.pow(b, 2) - 4*a*c)) / (2*a);
	    return Math.min(root1, root2);  
	}	
	
	public void drawGrid(double xLength, double yLength) {
		//Draw x axis		
		StdDraw.setPenRadius(0.0005);
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.line(0,xLength,0,-xLength);
					
		//draw y axis
		StdDraw.setPenRadius(0.0005);
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.line(0,yLength,0,-yLength);
		
		for (double yPos = -yLength; yPos <= yLength; yPos++) {
			StdDraw.line(-xLength, yPos, xLength, yPos);
			StdDraw.show(10);
		}
		for (double xPos = -xLength; xPos <= xLength; xPos++) {
			StdDraw.line(xPos, -yLength, xPos, yLength);
			StdDraw.show(10);
		}
	}
	
}
