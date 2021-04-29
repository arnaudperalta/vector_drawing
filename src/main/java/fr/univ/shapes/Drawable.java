package fr.univ.shapes;
import java.awt.Graphics2D;

public interface Drawable {

	public void draw(Graphics2D screen);

	public String serialize();

	public double[] getMiddle();

}

