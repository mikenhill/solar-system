package com.mikenhill.stonehenge;
import java.util.HashMap;
import java.util.Map;


public class Planet {
	
	public static String X_INITIAL = "x_initial";
	public static String Y_INITIAL = "y_initial";
	public static String X_VEL_INITIAL = "x_vel_initial";
	public static String Y_VEL_INITIAL = "y_vel_initial";
	public static String MASS = "mass";
	public static String IMAGE = "image";
	
	private String planetGraphic;
	private Map<String, Double> planetInfo = new HashMap<String, Double>();
	
	private Map<Planet, Force> forceEncountered = new HashMap<Planet, Force>();
	
	public Map<Planet, Force> getForceEncountered() {
		return forceEncountered;
	}

	public void setForceEncountered(Map<Planet, Force> forceEncountered) {
		this.forceEncountered = forceEncountered;
	}



	public String getPlanetGraphic() {
		return planetGraphic;
	}

	public void setPlanetGraphic(String planetGraphic) {
		this.planetGraphic = planetGraphic;
	}

	public Double getPlanetInfo(final String name) {
		return planetInfo.get(name);
	}

	public Map<String, Double> getPlanetInfo() {
		return planetInfo;
	}	
	
	public void setPlanetInfo(Map<String, Double> planetInfo) {
		this.planetInfo = planetInfo;
	}

	
}
