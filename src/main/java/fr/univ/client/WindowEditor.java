package fr.univ.client;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class WindowEditor extends JFrame {

    private GraphicEditor graphicEditor;
    private final int width = 1366;
    private final int height = 768;
    private String path;

    public WindowEditor() {
        setVisible(true);
        setTitle("Afficheur de dessin");
        path = null;
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
				try {
                    openSaveAs();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        });
		itemOpen.addActionListener(actionListener -> {
            try {
                openDrawing();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        itemSave.addActionListener(actionListener -> {
            try {
                save();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
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

	public void openSaveAs() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			graphicEditor.saveDrawing(chooser.getSelectedFile().getAbsolutePath());
		}
        path = chooser.getSelectedFile().getAbsolutePath();
	}

    public void save() throws IOException {
		graphicEditor.saveDrawing(path);
	}

	public void openDrawing() throws IOException {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getSelectedFile().getAbsolutePath());
			graphicEditor.openDrawing(chooser.getSelectedFile().getAbsolutePath());
		}
        path = chooser.getSelectedFile().getAbsolutePath();
	}
}
