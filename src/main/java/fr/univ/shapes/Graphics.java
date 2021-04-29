package fr.univ.shapes;

public interface Graphics extends Drawable {
	
	public void move(int dx, int dy);
	
	public double getMostSouthPoint();

	public void mirror(double symetryPoint);

	public double getArea();
}
