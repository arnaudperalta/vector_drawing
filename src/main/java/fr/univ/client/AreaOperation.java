package fr.univ.client;

import java.io.IOException;
import java.util.List;

import fr.univ.shapes.Graphics;

public class AreaOperation {

	private AreaOperation() {}

	public static void getAreasOfDrawingFromFile(String srcPath) throws IOException {
		List<Graphics> shapes = GraphicSerialization.deserialize(srcPath);
	}

	public static double getAreasOfDrawing(List<Graphics> shapes) {
		double totalArea = 0;
		for (Graphics g : shapes) {
			totalArea += g.getArea();
		}
		return totalArea;
	}
}
