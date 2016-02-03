package com.mikenhill.stonehenge;
import java.io.FileInputStream;


public class NbodyAtype {
	public static void main(String[] args) throws Exception{

        //plays music for opening
        //StdAudio.play("2001.mid");
		
		// read in planet information
		System.setIn(new FileInputStream("planets.txt"));
	// number of planets
		int N = StdIn.readInt();
		// radius of universe
		double R = StdIn.readDouble();
		// Gravitational Constant
		double G = 6.67e-11;
		// time step
		double dt = 25000; //time delta
		
		//graphic info
		StdDraw.setXscale(-R, R);
        StdDraw.setYscale(-R, R);
        StdDraw.picture(0, 0, "starfield.jpg",2*R,2*R);

		//planet graphic array
		String[] planetGraphics = new String[N];
		
		//planet force array
		double[][][] forceArray = new double[N][N][2];
		
		//planet info array (2d)
		double[][] planetInfo = new double[N][5];
		//fills in array with planet info from txt file
		for (int p = 0;p<N;p++){
			for(int i = 0;i<5;i++){
				planetInfo[p][i]=StdIn.readDouble();//gets numerical info for double array
			}
			planetGraphics[p]=StdIn.readString();//gets graphic name for string array
		}
		
		//draw images in universe
		for (int im = 0;im<N;im++){
			StdDraw.picture(planetInfo[im][0], planetInfo[im][1], planetGraphics[im]);
		}
		
		
		//loop for all of the calculating and animating
		int t = 0;
		while(true){
			
//OPTIONAL prints edited arrays
			
			for (int p = 0;p<N;p++){
				for(int i = 0;i<5;i++){
					System.out.format("%6.3e   ",planetInfo[p][i]);
				}
				System.out.format("%s  \n",planetGraphics[p]);
			}
			System.out.println();			
			
			//CALCULATE THE FORCES AND STORE THEM
			for(int i=0;i<N;i++){//loop for main planet
				double forceX=0;
				double forceY=0;
				double mPlanet = planetInfo[i][4];
			for(int j=0;j<N;j++){//loop for planet comparison
				if(i==j){continue;}
				double moPlanet = planetInfo[j][4];
				double x = planetInfo[j][0]-planetInfo[i][0];
				double y = planetInfo[j][1]-planetInfo[i][1];
				
				//Distance between the 2 objects
				double r = Math.sqrt((x*x) + (y*y));
				
				//strength of the gravitational force between two particles is given by the product of their masses 
				//divided by the square of the distance between them, 
				//scaled by the gravitational constant G (6.67 × 10-11 N m2 / kg2)
				double gravForce = (G * mPlanet * moPlanet) / (r * r);
				
				//The x component of the force is the gravitational force multiplied by (the x part of the distance
				//between them divided by the direct distance between them 
				forceX = gravForce*(x/r);
				
				//The y component of the force is the gravitational force multiplied by (the y part of the distance
				//between them divided by the direct distance between them				
				forceY = gravForce*(y/r);
				
				//Store the x and y components of the force 
				forceArray[i][j][0]=forceX;
				forceArray[i][j][1]=forceY;
				
				//resets force x and y
				forceX=0;
				forceY=0;
				}
			}
			
			//CALCULATE THE VELOCITY AND GET NEW POSITION
			for(int i = 0; i<N; i++){
				double netForceX=0;
				double netForceY=0;
				
				//sums up forces saved in array so you get net forces for one planet
				for(int c=0; c<N; c++){
					netForceX+=forceArray[i][c][0];
					netForceY+=forceArray[i][c][1];
				}
				
				//Get x and y velocities of the planet
				double vx = planetInfo[i][2];
				double vy = planetInfo[i][3];
				
				//Mass of planet
				double mPlanet = planetInfo[i][4];
				
				//calculates acceleration
				//Acceleration. Newton's second law of motion postulates that the accelerations 
				//in the x- and y-directions are given by: ax = Fx / m, ay = Fy / m.
				double accelX=netForceX/mPlanet;
				double accelY=netForceY/mPlanet;
			
				//calculates and replaces velocity in x and y direction
				// the new velocity is (vx + Δt ax, vy + Δt ay).
				vx=vx+(dt*accelX);
				vy=vy+(dt*accelY);
				
				planetInfo[i][2]=vx;
				planetInfo[i][3]=vy;
			
				//calculates and replaces position in x and y
				double posX=planetInfo[i][0];
				double posY=planetInfo[i][1];
				planetInfo[i][0]=posX+(vx*dt);
				planetInfo[i][1]=posY+(vy*dt);
				//posX=planetInfo[i][0];
				//posY=planetInfo[i][1];
			}
			
			
			//replaces images
			StdDraw.picture(0, 0, "starfield.jpg");
			for (int im = 0;im<N;im++){
				StdDraw.picture(planetInfo[im][0], planetInfo[im][1], planetGraphics[im]);
			}
			StdDraw.show(10);
			
			
			
			t+=dt;
		}
				
	}

}