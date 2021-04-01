package fr.univ.factory;

import java.awt.Color;

import fr.univ.shapes.*;

public interface ShapeFactory {

	ICircle createICircle(double cx, double cy, double rad, Color c);
	
	IRectangle createIRectangle(double x0, double y0, double x1, double y1, Color c);
	
	ILine createILine(double x0, double y0, double x1, double y1, Color c);
	
}
