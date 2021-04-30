package fr.univ.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.univ.shapes.Circle;
import fr.univ.shapes.Graphics;
import fr.univ.shapes.HandCircle;
import fr.univ.shapes.HandLine;
import fr.univ.shapes.HandRectangle;
import fr.univ.shapes.Line;
import fr.univ.shapes.Rectangle;

public class StandardizationOperation {
	
	private StandardizationOperation() {}

	public static void standardizeDrawingFromFile(String srcPath, String resPath) throws IOException {
		List<Graphics> shapes = GraphicSerialization.deserialize(srcPath);
		shapes = standardizeDrawing(shapes);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(resPath))) {
			writer.write(GraphicSerialization.serialize(shapes));
		}
	}

	public static List<Graphics> standardizeDrawing(List<Graphics> shapes) {
		List<Graphics> stdShapes = new ArrayList<>();
		for (Graphics g : shapes) {
			if (g instanceof HandCircle) {
				HandCircle hc = (HandCircle) g;
				Circle c = new Circle(
					hc.getMiddle()[0],
					hc.getMiddle()[1],
					hc.getRadius(),
					hc.getColor()
				);
				g = c;
			}
			if (g instanceof HandLine) {
				HandLine hl = (HandLine) g;
				Line l = new Line(
					hl.getPoints()[0],
					hl.getPoints()[1],
					hl.getPoints()[2],
					hl.getPoints()[3],
					hl.getColor()
				);
				g = l;
			}
			if (g instanceof HandRectangle) {
				HandRectangle hr = (HandRectangle) g;
				Rectangle r = new Rectangle(
					hr.getPoints()[0],
					hr.getPoints()[1],
					hr.getPoints()[2],
					hr.getPoints()[3],
					hr.getColor()
				);
				g = r;
			}
			stdShapes.add(g);
		}
		return stdShapes;
	}
}
