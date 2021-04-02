package fr.univ.client;
import java.awt.*;
import javax.swing.JPanel;
import java.util.List;
import fr.univ.shapes.Drawable;

public class GraphicsPanel extends JPanel {

  private List<Drawable> shapes;

  public GraphicsPanel(List<Drawable> shapes) {
    super();
    this.shapes = shapes;
  }

  @Override
  protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Drawable shape : this.shapes) {
			shape.draw((Graphics2D) g);
		}
		repaint(100);
	}

}
