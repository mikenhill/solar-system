package com.mikenhill.stonehenge.geometric;

import com.mikenhill.stonehenge.equation.EquationCircle;
import com.mikenhill.stonehenge.equation.GeometricEquation;

public abstract class Geometric {
	
	private EquationCircle equation;

	protected abstract GeometricEquation getEquation();
	
}
