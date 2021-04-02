package fr.univ.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

import fr.univ.noise.Noise;

public class HandLine implements ILine {
	
	private double x0, y0, x1, y1, ctrlx, ctrly;
	private Color c;

	public HandLine(double x0, double y0, double x1, double y1, Color c) {
		this.x0 = x0 + Noise.getNoise();
		this.y0 = y0 + Noise.getNoise();
		this.x1 = x1 + Noise.getNoise();
		this.y1 = y1 + Noise.getNoise();
		this.ctrlx = (x0 + x1) / 2 + Noise.getNoise(this.getLength());
		this.ctrly = (y0 + y1) / 2 + Noise.getNoise(this.getLength());
		this.c = c;
	}
	
	@Override
	public void draw(Graphics2D screen) {
		screen.setColor(c);
		screen.draw(new QuadCurve2D.Double(x0, y0, ctrlx, ctrly, x1, y1));
	}

	@Override
	public double getLength() {
		double dx = Math.abs(x1 - x0);
		double dy = Math.abs(y1 - y0);
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public void move(int dx, int dy) {
		this.x0 += dx;
		this.x1 += dx;
		this.y0 += dy;
		this.y1 += dy;
	}
	
}