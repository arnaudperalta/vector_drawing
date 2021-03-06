package fr.univ.shapes;
import java.awt.*;
import java.awt.geom.*;

public class Circle implements ICircle {

	private double cx, cy, rad;
	private Color color;

	public Circle(double cx, double cy, double rad, Color color) {
		this.cx = cx;
		this.cy = cy;
		this.rad = rad;
		this.color = color;
	}

	public double getRadius() { return rad; }

	public double[] getMiddle() {
		double[] res = {cx, cy};
		return res;
	}

	public void draw(Graphics2D screen) {
		screen.setColor(color);
		screen.draw(
			new Ellipse2D.Double(
				cx-rad,
				cy-rad,
				rad*2,
				rad*2
			)
		);
	}

	@Override
	public void move(int dx, int dy) {
		this.cx += dx;
		this.cy += dy;
	}

	@Override
	public String serialize() {
		String colorStr = "";
		if (color.equals(Color.RED))
			colorStr = "red";
		if (color.equals(Color.GREEN))
			colorStr = "green";
		if (color.equals(Color.BLUE))
			colorStr = "blue";
		if (color.equals(Color.BLACK))
			colorStr = "black";
		return 
			"<circle color=\"" + colorStr + "\" type=\"std\">"
				+ "<point x=\"" + this.cx 
						+ "\" y=\"" + this.cy + "\" />"
				+ "<radius>" + this.rad + "</radius>"
			+ "</circle>";
	}

	@Override
	public double getMostSouthPoint() {
		return cy + rad;
	}

	@Override
	public void mirror(double symetryPoint) {
		if (cy <= symetryPoint)
			cy += symetryPoint;
		else
			cy -= symetryPoint;
	}

	@Override
	public double getArea() {
		return Math.PI * rad * rad;
	}
}
