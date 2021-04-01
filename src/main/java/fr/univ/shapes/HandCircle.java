package fr.univ.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import fr.univ.noise.Noise;

public class HandCircle implements ICircle {

	private double cx, cy, rad;
	private Color c;
	
	public HandCircle(double cx, double cy, double rad, Color c) {
		this.cx = cx;
		this.cy = cy;
		this.rad = rad;
		this.c = c;
	}
	
	
	@Override
	public void draw(Graphics2D screen) {
		screen.setColor(c);
		screen.draw(new Ellipse2D.Double(
				cx - rad, 
				cy - rad, 
				rad * 2 + Noise.getNoise(rad), 
				rad * 2 + Noise.getNoise(rad)
		));
	}

	@Override
	public double getRadius() {
		return rad;
	}

	@Override
	public void move(int dx, int dy) {
		this.cx += dx;
		this.cy += dy;
	}
	
}
