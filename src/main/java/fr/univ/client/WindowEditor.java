package fr.univ.client;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowEditor extends JFrame {

    private GraphicEditor graphicEditor;
    private final int width = 1366;
    private final int height = 768;

    public WindowEditor() {
        setVisible(true);
        setTitle("Afficheur de dessin");

        this.graphicEditor = new GraphicEditor();
        JMenuBar menu = new JMenuBar();
        JButton itemNew = new JButton( "New" );
        menu.add(itemNew);
        JButton itemOpen = new JButton( "Open" );
        menu.add(itemOpen);
        JButton itemSave = new JButton( "Save" );
        menu.add(itemSave);
        JButton itemSaveAs = new JButton( "Save as" );
        menu.add(itemSaveAs);
        this.setJMenuBar(menu);
        getContentPane().add(this.graphicEditor);
        itemNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                graphicEditor.newCanvas();
            }

        });
        itemSaveAs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                graphicEditor.saveDrawing();
            }

        });
        this.pack();
        setSize(width,height);
        setResizable(false);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
