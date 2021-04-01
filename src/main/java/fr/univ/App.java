package fr.univ;


import fr.univ.shapes.Drawable;
import fr.univ.viewer.GraphicViewer;

import static fr.univ.client.Test.*;

public class App
{
    public static void main(String[] args) {
        GraphicViewer gv = new GraphicViewer();
        java.util.List<Drawable> demo = getDemoGroups();
        gv.draw(demo);
    }
}
