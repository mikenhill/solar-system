package com.mikenhill.stonehenge;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.mikenhill.stonehenge.geometric.Chord;
import com.mikenhill.stonehenge.geometric.Circle;
import com.mikenhill.stonehenge.geometric.Intersection;
import com.mikenhill.stonehenge.geometric.Line;
import com.mikenhill.stonehenge.geometric.Point;


public class StoneHenge {
	
	private static double STICK_LENGTH = 1.0d;//294.0d;
	private static double FIELD_WIDTH = 11 * STICK_LENGTH;
	private static double FIELD_HEIGHT = FIELD_WIDTH;
	private static String CIRCLE_10 = "circle10";
	private static String CIRCLE_11 = "circle11";
	private static String CIRCLE_AUBREY = "circleAubrey";
	private static String CIRCLE_VERNIER = "circleVernier";

	
	
	private Set<String> steps = new HashSet<String>();
	
	public StoneHenge (Set steps) {
		this.steps = steps;
	}
	
	public static void main(String[] args) throws Exception {
		
		Set<String> steps = new HashSet<String>();
		if (args != null && args.length > 0) {
			for (String arg : args) {
				steps.add(arg);
			}
		}
		StoneHenge sHenge = new StoneHenge(steps);
		sHenge.draw();
		
	}
	
	private void draw() {
		
		//Constants of henge
		Point intersection = new Point(1,1);
		
		
		double radiusCircle10 = STICK_LENGTH * 10;//10		
		int numPoints = 56;
		
		//================================================Field==============================================
		//Set up field and pass in a canvas size of 800 x 800
		Field shField = setUpField(800, 800);
		//================================================Field==============================================
		
		//================================================Inner Circle=======================================
		//Step 3 - Inner circle		
		Circle circle10 = new Circle(radiusCircle10, numPoints, CIRCLE_10);		
		shField.addCircle(circle10);
		circle10.drawByPoints(StdDraw.RED);
		circle10.annotate(StdDraw.RED);
		
		//Step 4 - Circle 11
		Circle circle11 = new Circle(radiusCircle10 + (STICK_LENGTH * 1), numPoints, CIRCLE_11);		
		shField.addCircle(circle11);
		circle11.drawByPoints(StdDraw.BLACK);
		circle11.annotate(StdDraw.BLACK);
		
		//Step 5
		//Take any point on circle11 and obtain (not draw) a new circle of same radius as circle11
		Point ptCircleTempCenter = circle11.getPoints().get(1);
		Circle circleTemp = new Circle (circle11.getRadius(), ptCircleTempCenter, "circleTemp");
		shField.addCircle(circleTemp);
		//Get the intersection of this new circle with circle10
		List<Point> circle10CircleTempIntersect = Intersection.getCircleCircleIntersect(shField.getCircle(CIRCLE_10), shField.getCircle("circleTemp"));
		//Draw a line from the start point on circle11 to the first intersection on circle10
		StdDraw.setPenRadius(0.0005);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.line(ptCircleTempCenter.getX(), ptCircleTempCenter.getY(), 
					 circle10CircleTempIntersect.get(0).getX(), circle10CircleTempIntersect.get(0).getY());
		StdDraw.show(10);
		
		//Step 6 and 7
		//Find the shortest point from the circle11 center to this new line
		Point p1 = new Point(ptCircleTempCenter.getX(), ptCircleTempCenter.getY());
		Point p2 = new Point(circle10CircleTempIntersect.get(0).getX(), circle10CircleTempIntersect.get(0).getY());
		Point p3 = new Point(shField.getCircle(CIRCLE_11).getCenter().getX(),shField.getCircle(CIRCLE_11).getCenter().getY());
		double radiusInner = Intersection.distanceToSegment(p1, p2, p3);
		Circle circleAubrey = new Circle (radiusInner, numPoints, CIRCLE_AUBREY);
		
		shField.addCircle(circleAubrey);
		circleAubrey.drawByPoints(StdDraw.BLUE);
		circleAubrey.annotate(StdDraw.BLUE);
		
		//===========================================================================================================
		//Step 8 - Draw a chord across the circle from Aubrey point 2 to point 42 and make a mark where it crosses the axis line. 
		//As a check, repeat this for Aubrey point 54 and point 14.

		//Chord-2-42
		StdDraw.setPenRadius(0.005);
		StdDraw.line(circleAubrey.getPoints().get(2).getX(), circleAubrey.getPoints().get(2).getY(),
				circleAubrey.getPoints().get(42).getX(), circleAubrey.getPoints().get(42).getY());
		StdDraw.show(10);

		//Chord-54-14
		StdDraw.line(circleAubrey.getPoints().get(54).getX(), circleAubrey.getPoints().get(54).getY(),
				circleAubrey.getPoints().get(14).getX(), circleAubrey.getPoints().get(14).getY());
		StdDraw.show(10);
		
		//Find the point where Chord-2-42 and Chord-54-14 cross - this will be on the vertical axis  
		intersection = Intersection.lineIntersect(circleAubrey.getPoints().get(2).getX(), circleAubrey.getPoints().get(2).getY(),
				circleAubrey.getPoints().get(42).getX(),circleAubrey.getPoints().get(42).getY(),
				circleAubrey.getPoints().get(54).getX(),circleAubrey.getPoints().get(54).getY(),
				circleAubrey.getPoints().get(14).getX(),circleAubrey.getPoints().get(14).getY());
		
		
		//Draw a point at this intersection
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.point(intersection.getX(), intersection.getY());
		StdDraw.show(10);		
		//Above is realisation of step 8 and is the point at which step-8-chord-A and step-8-chord-B cross the y-axis 
		//===========================================================================================================
		
		

	
		//Step 9
		//Vernier circle
		//Step 9 - Find the distance from intersection to point 28 - this is the diameter of the vernier circle
		double x = shField.getCircle(CIRCLE_AUBREY).getPoints().get(28).getX() - intersection.getX();
		double y = shField.getCircle(CIRCLE_AUBREY).getPoints().get(28).getY() - intersection.getY();
		double vernierRadius = Math.sqrt((x*x) + (y*y))/2;		
		//Point vernierCenter = new Point(0.0d, (intersection.getY() + shField.getCircle(CIRCLE_11).getPoints().get(28).getY())/2);
		Point vernierCenter = new Point(0.0d, (intersection.getY() + shField.getCircle(CIRCLE_AUBREY).getPoints().get(28).getY())/2);
		Circle vernierCircle = new Circle(vernierRadius, vernierCenter, CIRCLE_VERNIER);
		vernierCircle.draw(StdDraw.RED);
		
//		//xxxx
//		//Step 10 - 
//		int pointIndex = 55; //start point
//		int pointJump = 16;
//		int numPointsToPlot = 15;
//		pointIndex = pointIndex % numPoints;
//		while (numPointsToPlot > 0) {
//			//Draw a line from this point to point + 16			
//			int startPointIndex = pointIndex % numPoints;
//			int endPointIndex = (pointIndex + pointJump) % numPoints;
//			String chordIdentifier = "" + startPointIndex +"_"+ endPointIndex;
//			
//			Point startPoint = shField.getCircle(CIRCLE_AUBREY).getPoints().get(startPointIndex);
//			Point endPoint = shField.getCircle(CIRCLE_AUBREY).getPoints().get(endPointIndex);
//			Chord chord = new Chord(startPoint, endPoint);
//			vernierCircle.getmChords().put(chordIdentifier, chord);
//			
//			StdDraw.setPenRadius(0.0005);
//			StdDraw.setPenColor(StdDraw.RED);
//			StdDraw.line(chord.getStartPoint().getX(),chord.getStartPoint().getY(), chord.getEndPoint().getX()
//					,chord.getEndPoint().getY());
//			StdDraw.show(10);
//			pointIndex = (pointIndex+2) % numPoints;
//			numPointsToPlot--;
//			
//			Intersection vIntersection = new Intersection();
//			List<Point> vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chord);		
//			StdDraw.setPenRadius(0.005);
//			StdDraw.setPenColor(StdDraw.RED);
//			StdDraw.point(vPoint.get(0).getX(),vPoint.get(0).getY());
//			StdDraw.show(10);
//			
//		}
		
		List<Chord> yCircleChords = drawVernierRadials(shField.getCircle(CIRCLE_AUBREY), vernierCircle, 16);
		drawVernierRadials(shField.getCircle(CIRCLE_AUBREY), vernierCircle, 16);
//		
//				
//		
		
		//Capture the
		if (yCircleChords != null) {
			Point newCenter = shField.getCircle(CIRCLE_11).getCenter();
			int index = 1;
			for (Point point : vernierCircle.getPoints().values()) {
				StdDraw.setPenRadius(0.0005);
				StdDraw.setPenColor(StdDraw.RED);
				
				Line radialLine = new Line(new Point(newCenter.getX(), newCenter.getY()), point);
				
				StdDraw.line(radialLine.getStartPoint().getX(), radialLine.getStartPoint().getY(), 
						radialLine.getEndPoint().getX(), radialLine.getEndPoint().getY());
				StdDraw.show(10);
				
				drawYCirclePoint(yCircleChords, radialLine, index++);
				
			}
		}
//		
//		List<Point> vPoints = new ArrayList();
////		
//		List<Integer> cwPoints1 = Arrays.asList(55, 1, 3, 5, 7, 9,11, 1,55,53,51,49,47,45);
//		List<Integer> cwPoints2 = Arrays.asList(15,17,19,21,23,25,27,41,39,37,35,33,31,29);
//		for (int index = 0; index < cwPoints1.size(); index++) {
//			Point startPoint = shField.getCircle(CIRCLE_AUBREY).getPoints().get(cwPoints1.get(index));
//			Point endPoint = shField.getCircle(CIRCLE_AUBREY).getPoints().get(cwPoints2.get(index));
//			
//			String chordIdentifier = "" + cwPoints1.get(index) +"_"+ cwPoints2.get(index);
//			
//			Chord chord = new Chord(startPoint, endPoint);
//			vernierCircle.getmChords().put(chordIdentifier, chord);
//			
//			StdDraw.setPenRadius(0.0005);
//			StdDraw.setPenColor(StdDraw.RED);
//			StdDraw.line(chord.getStartPoint().getX(),chord.getStartPoint().getY(), chord.getEndPoint().getX()
//					,chord.getEndPoint().getY());
//			StdDraw.show(10);
//			
//			Intersection Intersection = new Intersection();
//			List<Point> vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chord);	
//			vPoints.addAll(vPoint);
//			StdDraw.setPenRadius(0.005);
//			StdDraw.setPenColor(StdDraw.BLUE);
//			StdDraw.point(vPoint.get(0).getX(),vPoint.get(0).getY());
//			StdDraw.show(10);
//			
//		}
		
		
		
	}
	
	private Field setUpField(int width, int height) {
		Field shField = new Field(STICK_LENGTH);
		StdDraw.setCanvasSize(width, 	height);
		StdDraw.setXscale(-FIELD_WIDTH ,FIELD_WIDTH);
		StdDraw.setYscale(-FIELD_HEIGHT, FIELD_HEIGHT);
		StdDraw.picture(0, 0, "",FIELD_WIDTH+100,FIELD_WIDTH+100);		
		
		
		//Step 1, 2 - Draw X and Y axis
		StdDraw.setPenRadius(0.0009);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.line(-FIELD_WIDTH, 0, +FIELD_WIDTH, 0);
		StdDraw.line(0, -FIELD_HEIGHT, 0, +FIELD_HEIGHT);				
		//Draw grid
		drawGrid(FIELD_WIDTH, FIELD_HEIGHT);
		return shField;
	}
	
	public void drawYCirclePoint(List<Chord> yCircleChords, Line radialLine, int index) {
		
		System.out.println("Radial - " + radialLine.toString());
		
		double minDistance = 0.0d;
		Point closestPoint = null;
		
		for (Chord thisChord : yCircleChords) {
			//Check to see if this chord intersects with the radial
			Point intersectPoint = Intersection.lineIntersect(thisChord.getStartPoint().getX(), 
					thisChord.getStartPoint().getY(), 
					thisChord.getEndPoint().getX(), 
					thisChord.getEndPoint().getY(), 
					radialLine.getStartPoint().getX(), 
					radialLine.getStartPoint().getY(), 
					radialLine.getEndPoint().getX(), 
					radialLine.getEndPoint().getY());
			
			
			
			
			if (intersectPoint != null) {
				//Check the hypontenuse for this point
				double x = intersectPoint.getX() - radialLine.getStartPoint().getX();
				double y = intersectPoint.getY() - radialLine.getStartPoint().getY();

				double r = Math.sqrt((x * x) + (y * y));
				
				System.out.println("Chord - Start index = " + thisChord.getStartIndex() + " End index = " + thisChord.getEndIndex());
				
				if (minDistance == 0.0d || r < minDistance) {
					System.out.println("Shortest with distance = " + r);
					closestPoint = intersectPoint;
					minDistance = r;
				}
				
			}
		}
		
		//Plot the point
		StdDraw.setPenRadius(0.009);
		StdDraw.setPenColor(StdDraw.BLACK);			
		//Draw a chord
		StdDraw.point(closestPoint.getX(), closestPoint.getY());
		StdDraw.text(closestPoint.getX() * 1.05, closestPoint.getY() * 1.05, ""+ index);
		StdDraw.show(10);
		
		//Now work out the Cosine of this point
		double radius = minDistance;
		double yComponent = closestPoint.getY();
		double cosValue = yComponent / radius;
		double degrees = Math.cos(cosValue);
		System.out.println("Cosine = " + degrees);
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

//	public void drawVernierRadials(int numPoints, int startIndex, int endIndex, int pointJump1, int pointJump2, Circle aubreyCircle,
//			Circle vernierCircle) {
//
//		for (int pointIndex = startIndex; pointIndex <= endIndex; pointIndex++) {
//			
//			int endIndex1 = (pointIndex + pointJump1) % numPoints;			
//			//int endIndex2 = (pointIndex + pointJump2) % numPoints;
//			//if (endIndex2 == 0) { endIndex2 = numPoints;}
//			if (endIndex1 == 0) { endIndex1 = numPoints;}
//
//			System.out.println("pointIndex = " + pointIndex + " endIndex1 = " + endIndex1 );
//			Point startPoint = aubreyCircle.getPoints().get(pointIndex);
//			Point endPoint1 = aubreyCircle.getPoints().get( endIndex1 );
//			//Point endPoint2 = aubreyCircle.getPoints().get( endIndex2 );
//			StdDraw.setPenRadius(0.0005);
//			StdDraw.setPenColor(StdDraw.GREEN);
//			
//			//Draw a chord
//			Chord chord1 = new Chord(startPoint, endPoint1);
//			StdDraw.line(startPoint.getX(), startPoint.getY(), endPoint1.getX(), endPoint1.getY());
//			
//			//We only draw capture the points from the odd pointIndexes
//			if (pointIndex % 2 == 1) {
//				List<Point> vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chord1);
//				vernierCircle.getPoints().put(pointIndex, vPoint.get(0));
//			}
//			
////			Chord chord2 = new Chord(startPoint, endPoint2);
////			StdDraw.line(startPoint.getX(),startPoint.getY(),endPoint2.getX(),endPoint2.getY());
////			vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chord2);
////			vernierCircle.getPoints().put(pointIndex, vPoint.get(0));
//			StdDraw.show(10);
//		}		
//	}
	
	public void drawVernierRadials(Circle aubreyCircle, Circle vernierCircle) {
		int start = 55;
		int count = 1;
		int jump = 16;
		do {
			int startIndex = start % 56;
			int endIndex = (startIndex+jump) % 56;

			Point startPoint = aubreyCircle.getPoints().get(startIndex);
			Point endPoint = aubreyCircle.getPoints().get( endIndex );
			StdDraw.setPenRadius(0.0009);
			StdDraw.setPenColor(StdDraw.GREEN);			
			//Draw a chord
			Chord chordOdd = new Chord(startPoint, endPoint);
			StdDraw.line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
			
			List<Point> vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chordOdd);
			vernierCircle.getPoints().put(vernierCircle.getPoints().size()+1, vPoint.get(0));			
			vernierCircle.getPoints().put(vernierCircle.getPoints().size()+1, vPoint.get(1));
			System.out.println("startIndex = " + startIndex + ", endIndex = " + endIndex);
			
			StdDraw.show(10);
			
			int startIndex2 = (start-26) % 56;
			int endIndex2 = (startIndex2+jump) % 56;
			
			startPoint = aubreyCircle.getPoints().get(startIndex2);
			endPoint = aubreyCircle.getPoints().get(endIndex2);
			StdDraw.setPenRadius(0.0009);
			StdDraw.setPenColor(StdDraw.GREEN);			
			//Draw a chord
			chordOdd = new Chord(startPoint, endPoint);
			StdDraw.line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
			
			vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chordOdd);
			vernierCircle.getPoints().put(vernierCircle.getPoints().size()+1, vPoint.get(0));			
			vernierCircle.getPoints().put(vernierCircle.getPoints().size()+1, vPoint.get(1));
			System.out.println("startIndex2 = " + startIndex2 + ", endIndex2 = " + endIndex2);
			
			
			StdDraw.show(10);			

			start +=2;
			count++;
		} while (count <= 7);
	}	

	public List<Chord> drawVernierRadials(Circle aubreyCircle, Circle vernierCircle, int step) {
		if (aubreyCircle.getPoints() != null && aubreyCircle.getPoints().size() > 0) {
			int numPoints = aubreyCircle.getPoints().size();
			for (Integer index : aubreyCircle.getPoints().keySet()) {
				Point startPoint = aubreyCircle.getPoints().get(index);
				int endIndex = index.intValue() + step;
				if (endIndex > numPoints) {
					endIndex = endIndex - numPoints;
				}
				Point endPoint = aubreyCircle.getPoints().get(endIndex);
				StdDraw.setPenRadius(0.0009);
				StdDraw.setPenColor(StdDraw.GREEN);			
				//Draw a chord
				Chord chord = new Chord(startPoint, endPoint);
				StdDraw.line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
				StdDraw.show(10);
				
				if (index.intValue() % 2 != 0) {
					//Odd point so draw a radial at the 2 crossing points
				}
				
			}
		}
		return null;
	}
	
//	public List<Chord> drawVernierRadials(Circle aubreyCircle, Circle vernierCircle, int step) {
//
//		List<Chord> yCircleChords = new ArrayList<Chord>();
//		
//		int numPoints = aubreyCircle.getPoints().size();
//		for (int i = 0; i < numPoints; i++) {
//			int start = (i % numPoints) + 1;
//			int end = ((i + step) % numPoints) + 1;
//			
//			Point startPoint = aubreyCircle.getPoints().get(start);
//			Point endPoint = aubreyCircle.getPoints().get( end );
//			StdDraw.setPenRadius(0.0009);
//			StdDraw.setPenColor(StdDraw.GREEN);			
//			//Draw a chord
//			Chord chord = new Chord(startPoint, endPoint);
//			
//			//Add the indexes to the chord - these are the artificial numbers around the edge of the circle.
//			chord.setStartIndex(start);
//			chord.setEndIndex(end);
//			
//			yCircleChords.add(chord);
//			StdDraw.line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
//			
//			
//			
//			//We use the odd points for the radials
//			if (start % 2 != 0) {
//				System.out.println("From " + start + " to " + end );
//				List<Point> vPoint = Intersection.getCircleLineIntersectionPoint(vernierCircle, chord);
//				vernierCircle.getPoints().put(start, vPoint.get(0));							
//				vernierCircle.getPoints().put(end, vPoint.get(1));
//			}
//			StdDraw.show(10);
//			
//			
//			
//		}
//		return yCircleChords;
//	}	
	
	
	
}
