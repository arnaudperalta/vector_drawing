package fr.univ.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public class HandRectangle implements IRectangle {

	private double x0, y0, x1, y1;
	private ILine l1, l2, l3, l4;
	private Color c;

	public HandRectangle(double x0, double y0, double x1, double y1, Color c) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.c = c;
		l1 = new HandLine(this.x0, this.y0, this.x0, this.y1, c);
		l2 = new HandLine(this.x0, this.y0, this.x1, this.y0, c);
		l3 = new HandLine(this.x1, this.y0, this.x1, this.y1, c);
		l4 = new HandLine(this.x0, this.y1, this.x1, this.y1, c);
	}

	@Override
	public void draw(Graphics2D screen) {
		screen.setColor(c);
		l1.draw(screen);
		l2.draw(screen);
		l3.draw(screen);
		l4.draw(screen);
	}

	@Override
	public double getWidth() {
		return Math.abs(x1 - x0);
	}

	@Override
	public double getHeight() {
		return Math.abs(y1 - y0);
	}

	public double[] getMiddle() {
		double[] res = { (x0 + x1)/2 , (y0 + y1) / 2 };
		return res;
	}

	@Override
	public void move(int dx, int dy) {
		this.x0 += dx;
		this.x1 += dx;
		this.y0 += dy;
		this.y1 += dy;
	}

	@Override
	public String serialize() {
		String colorStr = "";
		if (c.equals(Color.RED))
			colorStr = "red";
		if (c.equals(Color.GREEN))
			colorStr = "green";
		if (c.equals(Color.BLUE))
			colorStr = "blue";
		if (c.equals(Color.BLACK))
			colorStr = "black";
		return 
			"<rectangle color=\"" + colorStr + "\">"
				+ "<point x=\"" + this.x0 
						+ "\" y=\"" + this.y0 + "\" />"
				+ "<point x=\"" + this.x1 
						+ "\" y=\"" + this.y1 + "\" />"
			+ "</rextangle>";
	}
}
