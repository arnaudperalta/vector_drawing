package fr.univ.builder;

import java.awt.Color;
import java.util.List;

import fr.univ.factory.ShapeFactory;
import fr.univ.factory.HandShapeFactory;
import fr.univ.shapes.Graphics;
import fr.univ.shapes.ICircle;
import fr.univ.shapes.ILine;
import fr.univ.shapes.IRectangle;

public class SketchBuilderHand implements SketchBuilder {
	
	Double x0; 
	Double y0; 
	Double x1;
	Double y1; 
	Double rad;
	Color color;
	ShapeFactory factory;
	
	public SketchBuilderHand() {
		factory = new HandShapeFactory();
	}
	
	public void addPoint(double[] point) {
		if (x0 == null) {
			x0 = point[0];
			y0 = point[1];
		}
		else if (x1 == null) {
			x1 = point[0];
			y1 = point[1];
		}
	}
	
	public void addColor(Color c) {
		color = c; 
	}
	
	public void addRadius(double rad) {
		this.rad = rad;
	}
	
	public void startCircle() {
		initialize();
	}
	
	public ICircle endCircle() {
		return factory.createICircle(x0, y0, rad, color);
	}

	@Override
	public void startLine() {
		initialize();
	}

	@Override
	public ILine endLine() {
		return factory.createILine(x0, y0, x1, y1, color);
	}

	@Override
	public void startRectangle() {
		initialize();
	}

	@Override
	public IRectangle endRectangle() {
		return factory.createIRectangle(x0, y0, x1, y1, color);
	}
	
	private void initialize() {
		x0 = null;
		y0 = null;
		x1 = null;
		y1 = null;
		rad = null;
		color = null;
	}

}
