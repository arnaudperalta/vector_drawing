package fr.univ;

import java.io.IOException;

import fr.univ.client.GraphicEditor;
import fr.univ.client.WindowEditor;

public class App
{
    public static void main(String[] args) throws IOException {
		if (args.length == 0)
        	new WindowEditor();
		else if (args[0].equals("mirroir"))
			System.out.println("mirroir");
		else if (args[0].equals("standard"))
			System.out.println("standard");
		else if (args[0].equals("surface"))
			System.out.println("surface");
		else if (args[0].equals("debug"))
			new GraphicEditor().openDrawing("drawing.xml");
	}
}
