package fr.univ.client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowEditor extends JFrame {

    private GraphicEditor graphicEditor;
    private final int width = 800;
    private final int height = 600;

    public WindowEditor() {
        setVisible(true);
        setSize(width, height);
        setTitle("Afficheur de dessin");

        this.graphicEditor = new GraphicEditor();
        this.setJMenuBar(new MenuBar());
        getContentPane().add(this.graphicEditor);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
