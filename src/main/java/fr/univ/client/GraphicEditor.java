package fr.univ.client;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import fr.univ.shapes.Drawable;

public class GraphicEditor extends JPanel {
	
	private static final long serialVersionUID = -3354282073194736248L;
	private final int width = 800;
	private final int height = 600;
	private Graphics2D onscreen;

	
	public GraphicEditor() {
		setVisible(true);
		setSize(width, height);

		BufferedImage onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		ImageIcon icon = new ImageIcon(onscreenImage);
		JLabel draw = new JLabel(icon);
		onscreen  = onscreenImage.createGraphics();
	}
	
	
	public void draw(java.util.List<Drawable> shapes) {
		for (Drawable shape : shapes) {
			shape.draw(onscreen);
		}
		repaint(100);
	}

}
