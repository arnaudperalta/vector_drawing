package fr.univ.builder;

import java.awt.*;

import fr.univ.shapes.ICircle;
import fr.univ.shapes.ILine;
import fr.univ.shapes.IRectangle;

public interface SketchBuilder {
	
	public void addPoint(double[] point);
	public void addRadius(double rad);
	
	public void addColor(Color c);
	
	public void startCircle();
	public ICircle endCircle();
	
	public void startLine();
	public ILine endLine();
	
	public void startRectangle();
	public IRectangle endRectangle();

}
