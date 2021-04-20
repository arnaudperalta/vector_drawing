package fr.univ.client;
import java.awt.*;
import javax.swing.JPanel;
import java.util.List;
import fr.univ.shapes.Drawable;
import fr.univ.shapes.Graphics;

public class GraphicsPanel extends JPanel {

  private List<Graphics> shapes;

  public GraphicsPanel(List<Graphics> shapes) {
    super();
    this.shapes = shapes;
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		for (Drawable shape : this.shapes) {
			shape.draw((Graphics2D) g);
		}
		repaint(100);
	}

}
