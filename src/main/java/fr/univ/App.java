package fr.univ;

import fr.univ.client.WindowEditor;

public class App
{
    public static void main(String[] args) {
		if (args.length == 0)
        	new WindowEditor();
		else if (args[0].equals("mirroir"))
			System.out.println("mirroir");
		else if (args[0].equals("standard"))
			System.out.println("standard");
		else if (args[0].equals("surface"))
			System.out.println("surface");
    }
}
