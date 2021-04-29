package fr.univ.shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

public class SubPicture implements Graphics {

	Collection<Graphics> subPics;

	public SubPicture() {
		subPics = new ArrayList<>();
	}

	public Collection<Graphics> getShapes() {
		return subPics;
	}

	public void addGraphic(Graphics g) {
		subPics.add(g);
	}

	@Override
	public void draw(Graphics2D screen) {
		for (Graphics g : subPics)
			g.draw(screen);
	}

	@Override
	public void move(int dx, int dy) {
		for (Graphics g : subPics)
			g.move(dx, dy);
	}

	public double[] getMiddle() {
		double x = 0.0;
		double y = 0.0;
		for (Graphics g : subPics) {
			x += g.getMiddle()[0];
			y += g.getMiddle()[1];
		}
		double[] res = {x/subPics.size(), y/subPics.size()};
		return res;
	}

	@Override
	public String serialize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getMostSouthPoint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mirror(double symetryPoint) {
		throw new UnsupportedOperationException();
	}

}
