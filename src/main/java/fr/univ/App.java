package fr.univ;

import fr.univ.client.AreaOperation;
import fr.univ.client.GraphicEditor;
import fr.univ.client.MirrorOperation;
import fr.univ.client.WindowEditor;

public class App
{
    public static void main(String[] args) throws Exception {
		if (args.length == 0)
        	new WindowEditor();
		else if (args[0].equals("mirroir")) {
			if (args.length < 3)
				throw new Exception("Missing arguments");
			MirrorOperation.mirrorDrawingFromFile(args[1], args[2]);
		}
		else if (args[0].equals("standard")) {
			System.out.println("standard");
		}
		else if (args[0].equals("surface")) {
			if (args.length < 2)
				throw new Exception("Missing arguments");
			AreaOperation.getAreasOfDrawingFromFile(args[1]);
		}
		else if (args[0].equals("debug")) {
			new GraphicEditor().openDrawing("drawing.xml");
		}
	}
}
