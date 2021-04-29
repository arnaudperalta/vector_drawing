package fr.univ.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import fr.univ.shapes.Graphics;

public class MirrorOperation {
	
	private MirrorOperation() {}

	public static void mirrorDrawingFromFile(String srcPath, String resPath) throws IOException {
		List<Graphics> shapes = GraphicSerialization.deserialize(srcPath);
		mirrorDrawing(shapes);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(resPath))) {
			writer.write(GraphicSerialization.serialize(shapes));
		}
	}

	public static void mirrorDrawing(List<Graphics> shapes) {
		double maxY = 0;
		for (Graphics g : shapes) {
			maxY = Math.max(maxY, g.getMostSouthPoint());
		}
		for (Graphics g : shapes) {
			g.mirror(maxY / 2);
		}
	}

}
