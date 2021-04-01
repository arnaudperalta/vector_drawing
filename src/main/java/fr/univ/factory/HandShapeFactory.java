package fr.univ.factory;

import java.awt.Color;

import fr.univ.shapes.*;

public class HandShapeFactory implements ShapeFactory {

	@Override
	public ICircle createICircle(double cx, double cy, double rad, Color c) {
		return new HandCircle(cx, cy, rad, c);
	}

	@Override
	public IRectangle createIRectangle(double x0, double y0, double x1, double y1, Color c) {
		return new HandRectangle(x0, y0, x1, y1, c);
	}

	@Override
	public ILine createILine(double x0, double y0, double x1, double y1, Color c) {
		return new HandLine(x0, y0, x1, y1, c);
	}

}
