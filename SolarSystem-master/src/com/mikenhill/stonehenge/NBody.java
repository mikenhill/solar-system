package com.mikenhill.stonehenge;
/*******************************
 * NBody Simulation - visually represent solar system using Newtonian PhysicsCar 


 * @author Carrie Lindeman
 * 2/25/14
 * 
 */

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class NBody {

	public static void main(String[] args) throws Exception {

		// plays music for opening
		// StdAudio.play("2001.mid");

		// read in planet information
		System.setIn(new FileInputStream("planets.txt"));
		// number of planets
		int numberOfPlanets = StdIn.readInt();
		// radius of universe
		Universe universe = new Universe(StdIn.readDouble());

		// time step
		double dt = 25000; // time delta

		// graphic info
		StdDraw.setXscale(-universe.getRadiusOfUniverse(),
				universe.getRadiusOfUniverse());
		StdDraw.setYscale(-universe.getRadiusOfUniverse(),
				universe.getRadiusOfUniverse());
		StdDraw.picture(0, 0, "starfield.jpg",
				2 * universe.getRadiusOfUniverse(),
				2 * universe.getRadiusOfUniverse());

		List<Planet> thePlanets = new ArrayList<Planet>(numberOfPlanets);
		for (int i = 0; i < numberOfPlanets; i++) {
			thePlanets.add(new Planet());
		}

		// planet force array
		//double[][][] forceArray = new double[numberOfPlanets][numberOfPlanets][2];

		// planet info array (2d)
//		double[][] planetInfo = new double[numberOfPlanets][5];
	
		// fills in array with planet info from txt file
		for (Planet aPlanet : thePlanets) {
			aPlanet.getPlanetInfo().put(Planet.X_INITIAL, new Double(StdIn.readDouble()));
			aPlanet.getPlanetInfo().put(Planet.Y_INITIAL, new Double(StdIn.readDouble()));
			aPlanet.getPlanetInfo().put(Planet.X_VEL_INITIAL, new Double(StdIn.readDouble()));
			aPlanet.getPlanetInfo().put(Planet.Y_VEL_INITIAL, new Double(StdIn.readDouble()));
			aPlanet.getPlanetInfo().put(Planet.MASS, new Double(StdIn.readDouble()));
			aPlanet.setPlanetGraphic(StdIn.readString());// gets graphic name for
		}

		// draw images in universe
		for (Planet aPlanet : thePlanets) {
			StdDraw.picture(aPlanet.getPlanetInfo(Planet.X_INITIAL), aPlanet.getPlanetInfo(Planet.Y_INITIAL),
					aPlanet.getPlanetGraphic());
		}

		// loop for all of the calculating and animating
		int t = 0;
		while (true) {
			
			// OPTIONAL prints edited arrays
			
			  for (Planet thePlanet : thePlanets){ 
				  System.out.format("%6.3e   ", thePlanet.getPlanetInfo().get(Planet.X_INITIAL)); 
				  System.out.format("%6.3e   ", thePlanet.getPlanetInfo().get(Planet.Y_INITIAL));
				  System.out.format("%6.3e   ", thePlanet.getPlanetInfo().get(Planet.X_VEL_INITIAL));
				  System.out.format("%6.3e   ", thePlanet.getPlanetInfo().get(Planet.Y_VEL_INITIAL));
				  System.out.format("%6.3e   ", thePlanet.getPlanetInfo().get(Planet.MASS));
				  System.out.format("%s  \n",thePlanet.getPlanetGraphic()); 
			  }
			  System.out.println();

			// CALCULATE THE FORCES AND STORE THEM
			int iCount = 0;
			for (Planet thisPlanet : thePlanets) {// loop for main planet
				iCount++;
				
				
				//double mPlanet = aPlanet.getPlanetInfo()[4];
				int yCount = 0;
				
				for (Planet theOtherPlanet : thePlanets) {// loop for planet
					yCount++;										// comparison
					if (thisPlanet == theOtherPlanet) {
						continue;
					}
					
					//double moPlanet = bPlanet.getPlanetInfo()[4];
					double x = theOtherPlanet.getPlanetInfo(Planet.X_INITIAL) - thisPlanet.getPlanetInfo(Planet.X_INITIAL);
					double y = theOtherPlanet.getPlanetInfo(Planet.Y_INITIAL) - thisPlanet.getPlanetInfo(Planet.Y_INITIAL);

					double r = Math.sqrt((x * x) + (y * y));
					double gravForce = (universe.getGravitationalConstant()
							* thisPlanet.getPlanetInfo(Planet.MASS) * theOtherPlanet.getPlanetInfo(Planet.MASS))
							/ (r * r);
					Force theForce = new Force();
					theForce.setxForce(gravForce * (x / r));
					theForce.setyForce(gravForce * (y / r));
					
					// calculates net force
					thisPlanet.getForceEncountered().put(theOtherPlanet, theForce);
//					System.out.println(thisPlanet.getPlanetGraphic());
//					System.out.println(theOtherPlanet.getPlanetGraphic());
//					System.out.format("%6.3e   ",thisPlanet.getForceEncountered().get(theOtherPlanet).getxForce());
//					System.out.format("%6.3e   ",thisPlanet.getForceEncountered().get(theOtherPlanet).getyForce());
				}
			}

			// CALCULATE THE VELOCITY AND GET NEW POSITION
			
			for (Planet aPlanet : thePlanets) {
				
				double netForceX = 0;
				double netForceY = 0;
				// sums up forces saved in array so you get net forces for one
				// planet
				
				for (Planet bPlanet : thePlanets) {
					if (bPlanet != aPlanet) {
						netForceX += aPlanet.getForceEncountered().get(bPlanet).getxForce();
						netForceY += aPlanet.getForceEncountered().get(bPlanet).getyForce();
					}
				}
				double vx = aPlanet.getPlanetInfo(Planet.X_VEL_INITIAL);
				double vy = aPlanet.getPlanetInfo(Planet.Y_VEL_INITIAL);
				
				// calculates acceleration
				double accelX = netForceX / aPlanet.getPlanetInfo(Planet.MASS);
				double accelY = netForceY / aPlanet.getPlanetInfo(Planet.MASS);
				// calculates and replaces velocity in x and y direction
				vx = vx + (dt * accelX);
				vy = vy + (dt * accelY);
				aPlanet.getPlanetInfo().put(Planet.X_VEL_INITIAL, new Double(vx));
				aPlanet.getPlanetInfo().put(Planet.Y_VEL_INITIAL, new Double(vy));
				// calculates and replaces position in x and y
				double posX = aPlanet.getPlanetInfo(Planet.X_INITIAL);
				double posY = aPlanet.getPlanetInfo(Planet.Y_INITIAL);
				
				aPlanet.getPlanetInfo().put(Planet.X_INITIAL, new Double(posX + (vx * dt)));
				aPlanet.getPlanetInfo().put(Planet.Y_INITIAL, new Double(posY + (vy * dt)));
			}

			// replaces images
			StdDraw.picture(0, 0, "starfield.jpg");
			for (Planet aPlanet : thePlanets) {
				StdDraw.picture(aPlanet.getPlanetInfo(Planet.X_INITIAL), aPlanet.getPlanetInfo(Planet.Y_INITIAL),
						aPlanet.getPlanetGraphic());
			}
			StdDraw.show(10);

			
			 
			t += dt;
		}

	}

}
