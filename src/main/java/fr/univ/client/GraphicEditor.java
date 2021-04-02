package fr.univ.client;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.util.ArrayList;
import java.util.List;

import fr.univ.shapes.Circle;
import fr.univ.shapes.Drawable;
import fr.univ.shapes.Line;
import fr.univ.shapes.Rectangle;

public class GraphicEditor extends JPanel {

	private static final long serialVersionUID = -3354282073194736248L;
	private final int width = 800;
	private final int height = 600;

	private JButton circleButton;
	private JButton lineButton;
	private JButton rectangleButton;

	private List<Drawable> shapes;

	private double x;
	private double y;

	private boolean secondInput;
	private int shapeSelected;

	public GraphicEditor() {
		setVisible(true);
		setSize(width, height);
		this.shapes = new ArrayList<Drawable>();
		BufferedImage onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		ImageIcon icon = new ImageIcon(onscreenImage);
		JLabel draw = new JLabel(icon);
		JMenuBar test = new JMenuBar();
		circleButton = new JButton("Circle");
		lineButton = new JButton("Line");
		rectangleButton = new JButton("Rectangle");
		test.add(circleButton);
		test.add(lineButton);
		test.add(rectangleButton);
		this.setLayout(new BorderLayout());
		this.add(test, BorderLayout.PAGE_START);
		GraphicsPanel gp = new GraphicsPanel(this.shapes);
		this.add(gp, BorderLayout.CENTER);
		shapeSelected = 0;
		circleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				shapeSelected = 0;
			}

		});
		lineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				shapeSelected = 1;
			}

		});
		rectangleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				shapeSelected = 2;
			}

		});
		gp.addMouseListener(new MouseInputAdapter(){

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(secondInput) {
					switch(shapeSelected) {
						case 0:
							double dis=Math.sqrt((e.getX()-x)*(e.getX()-x) + (e.getY()-y)*(e.getY()-y));
							shapes.add(new Circle(x, y, dis, Color.RED));
							break;
						case 1:
							shapes.add(new Line(x, y, e.getX(), e.getY(), Color.RED));
							break;
						case 2:
							shapes.add(new Rectangle(x, y, e.getX(), e.getY(), Color.RED));
							break;
						default:
						 break;
					}
					secondInput = false;
				}
				else  {
					x = e.getX();
					y = e.getY();
					secondInput = true;
				}
			}


		});
	}

}
