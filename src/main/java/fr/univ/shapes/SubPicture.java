package fr.univ.shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

public class SubPicture implements Graphics {

	Collection<Graphics> subPics;
	
	public SubPicture() {
		subPics = new ArrayList<>();
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
	
}
