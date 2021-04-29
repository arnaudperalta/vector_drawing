package fr.univ.client;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.univ.factory.HandShapeFactory;
import fr.univ.factory.ShapeFactory;
import fr.univ.factory.StandardShapeFactory;
import fr.univ.shapes.Graphics;
import fr.univ.shapes.SubPicture;

public class GraphicEditor extends JPanel {

	private static final long serialVersionUID = -3354282073194736248L;
	private final int width = 800;
	private final int height = 600;

	private JButton circleButton;
	private JButton lineButton;
	private JButton rectangleButton;
	private JButton moveShapeButton;
	private JButton deleteShapeButton;
	private JButton groupShapeButton;
	private JButton ungroupShapeButton;
	private JButton drawModeSwitch;

	private GraphicsPanel gp;

	private List<Graphics> shapes;
	private ShapeFactory hsf;

	private int x;
	private int y;

	private boolean secondInput;
	private Shape shapeType;

	private Graphics selectedShape;

	private AppMode mode;

	private DrawMode drawMode;

	public GraphicEditor() {
		createModel();
		createView();
		setUpLayout();
		createController();
	}

	private void createModel() {
		this.shapes = new ArrayList<Graphics>();
		shapeType = Shape.CIRCLE;
		mode = AppMode.ADD_SHAPE;
		drawMode = DrawMode.STANDARD;
		hsf = new StandardShapeFactory();
	}

	private void createView() {
		setVisible(true);
		setSize(width, height);
		circleButton = new JButton("Circle");
		lineButton = new JButton("Line");
		rectangleButton = new JButton("Rectangle");
		moveShapeButton = new JButton("Move Shape");
		deleteShapeButton = new JButton("Delete Shape");
		groupShapeButton = new JButton("Group Shape");
		ungroupShapeButton = new JButton("Ungroup Shape");
		drawModeSwitch = new JButton("Dessin standard");
		gp = new GraphicsPanel(this.shapes);
	}

	private void setUpLayout() {
		JMenuBar test = new JMenuBar();
		test.add(circleButton);
		test.add(lineButton);
		test.add(rectangleButton);
		test.add(moveShapeButton);
		test.add(deleteShapeButton);
		test.add(groupShapeButton);
		test.add(ungroupShapeButton);
		test.add(drawModeSwitch);
		this.setLayout(new BorderLayout());
		this.add(test, BorderLayout.PAGE_START);
		this.add(gp, BorderLayout.CENTER);
	}

	private void createController() {
		circleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.ADD_SHAPE;
				shapeType = Shape.CIRCLE;
				secondInput = false;
			}

		});
		lineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.ADD_SHAPE;
				shapeType = Shape.LINE;
				secondInput = false;
			}

		});
		rectangleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.ADD_SHAPE;
				shapeType = Shape.RECTANGLE;
				secondInput = false;
			}

		});

		moveShapeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.MOVE_SHAPE;
				secondInput = false;
			}

		});

		deleteShapeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.DELETE_SHAPE;
				secondInput = false;
			}

		});
		groupShapeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.GROUP_SHAPE;
				secondInput = false;
			}

		});

		ungroupShapeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.UNGROUP_SHAPE;
				secondInput = false;
			}

		});
		gp.addMouseListener(new MouseInputAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				switch(mode) {
					case ADD_SHAPE:
					if(secondInput) {
						switch(shapeType) {
							case CIRCLE:
								double dis=Math.sqrt((e.getX()-x)*(e.getX()-x) + (e.getY()-y)*(e.getY()-y));
								shapes.add(hsf.createICircle(x, y, dis, Color.RED));
								break;
							case LINE:
								shapes.add(hsf.createILine(x, y, e.getX(), e.getY(), Color.RED));
								break;
							case RECTANGLE:
								shapes.add(hsf.createIRectangle(x, y, e.getX(), e.getY(), Color.RED));
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
					break;
					case MOVE_SHAPE:
					if(secondInput) {
					  int dx = e.getX() - (int) selectedShape.getMiddle()[0];
						int dy = e.getY() - (int) selectedShape.getMiddle()[1];
						selectedShape.move(dx,dy);
						secondInput = false;
					}
					else {
						double minDis = Double.POSITIVE_INFINITY;
						Graphics resShape = null;
						for( Graphics shape : shapes) {
							double[] mid = shape.getMiddle();
							double dis =Math.sqrt((e.getX()-mid[0])*(e.getX()-mid[0]) + (e.getY()-mid[1])*(e.getY()-mid[1]));
							if(dis < minDis) {
								resShape = shape;
								minDis = dis;
							}
						}
						selectedShape = resShape;
						secondInput = true;
					}
					break;
					case DELETE_SHAPE:
					double minDis = Double.POSITIVE_INFINITY;
					Graphics resShape = null;
					for( Graphics shape : shapes) {
						double[] mid = shape.getMiddle();
						double dis =Math.sqrt((e.getX()-mid[0])*(e.getX()-mid[0]) + (e.getY()-mid[1])*(e.getY()-mid[1]));
						if(dis < minDis) {
							resShape = shape;
							minDis = dis;
						}
					}
					shapes.remove(resShape);
					break;
					case GROUP_SHAPE:
					if(secondInput) {
						SubPicture sp = new SubPicture();
						for( Graphics shape : shapes) {
							double sx = shape.getMiddle()[0];
							double sy = shape.getMiddle()[1];
							if(((sx > x && sx < e.getX()) || (sx < x && sx > e.getX())) &&
							((sy > y && sy < e.getY()) || (sy < y && sy > e.getY()))) {
								sp.addGraphic(shape);
							}
						}
						shapes.add(sp);
						for(Graphics shape : sp.getShapes()) {
							shapes.remove(shape);
						}
						secondInput = false;
					}
					else {
						x = e.getX();
						y = e.getY();
						secondInput = true;
					}
					break;
					case UNGROUP_SHAPE:
					double minSpDis = Double.POSITIVE_INFINITY;
					Graphics resSp = null;
					for( Graphics shape : shapes) {
						double[] mid = shape.getMiddle();
						double dis =Math.sqrt((e.getX()-mid[0])*(e.getX()-mid[0]) + (e.getY()-mid[1])*(e.getY()-mid[1]));
						if(dis < minSpDis && shape instanceof SubPicture) {
							resSp = shape;
							minSpDis = dis;
						}
					}
					if(resSp !=null) {
						SubPicture sp = (SubPicture) resSp;
						for( Graphics g: sp.getShapes()) {
							shapes.add(g);
						}
						shapes.remove(sp);
					}
					break;
					case MIRROR_SHAPE:
					minDis = Double.POSITIVE_INFINITY;
					resShape = null;
					for( Graphics shape : shapes) {
						double[] mid = shape.getMiddle();
						double dis =Math.sqrt((e.getX()-mid[0])*(e.getX()-mid[0]) + (e.getY()-mid[1])*(e.getY()-mid[1]));
						if(dis < minDis ) {
							resSp = shape;
							minDis = dis;
						}
					}
					break;
				}
			}
		});

		drawModeSwitch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (drawMode) {
					case STANDARD:
						hsf = new HandShapeFactory();
						drawMode = DrawMode.HAND;
						drawModeSwitch.setText("Dessin a la main");
						break;
					case HAND:
						hsf = new StandardShapeFactory();
						drawMode = DrawMode.STANDARD;
						drawModeSwitch.setText("Dessin standard");
						break;
				}

			}

		});
	}

	public void saveDrawing(String filePath) throws IOException {
		String xml = GraphicSerialization.serialize(shapes);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(xml);
		}
	}

	public void openDrawing(String filePath) throws IOException {
		System.out.println(GraphicSerialization.deserialize(filePath));
	}

	public void newCanvas() {
		String choice = JOptionPane.showInputDialog(
				this,
				"Taille du canvas",
				"Nouveau fichier",
				JOptionPane.QUESTION_MESSAGE);
		shapes.clear();
		String[] size = choice.split("x");
		gp.setSize(new Dimension(Integer.parseInt(size[0]),Integer.parseInt(size[1])));
	}


	private enum AppMode {
		ADD_SHAPE,
		MOVE_SHAPE,
		DELETE_SHAPE,
		GROUP_SHAPE,
		UNGROUP_SHAPE,
		MIRROR_SHAPE
	}

	private enum Shape {
		CIRCLE,
		LINE,
		RECTANGLE
	}

	private enum DrawMode {
		STANDARD,
		HAND
	}

}
