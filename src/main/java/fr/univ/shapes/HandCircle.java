package fr.univ.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import fr.univ.noise.Noise;

public class HandCircle implements ICircle {

	private double cx, cy, rad, xrad, yrad;
	private Color c;

	public HandCircle(double cx, double cy, double rad, Color c) {
		this.cx = cx;
		this.cy = cy;
		this.rad = rad;
		this.c = c;
		this.xrad = Noise.getNoise(rad);
		this.yrad = Noise.getNoise(rad);
	}


	@Override
	public void draw(Graphics2D screen) {
		screen.setColor(c);
		screen.draw(new Ellipse2D.Double(
				cx - rad,
				cy - rad,
				rad * 2 + xrad,
				rad * 2 + yrad
		));
	}

	@Override
	public double getRadius() {
		return rad;
	}

	public double[] getMiddle() {
		double[] res = {cx, cy};
		return res;
	}

	@Override
	public void move(int dx, int dy) {
		this.cx += dx;
		this.cy += dy;
	}

	@Override
	public String serialize() {
		return
			"<circle color=\"" + "\">"
				+ "<point x=\"" + this.cx
						+ "\" y=\"" + this.cy + "\" /> + "
				+ "<radius>" + this.rad + "</radius>"
			+ "</circle>";
	}

}
