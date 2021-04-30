package fr.univ.client;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.image.BufferedImage;

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
	private JButton simpleMirror;
	private JButton globalMirror;


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

	private JComboBox<AppColor> color;

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
		simpleMirror = new JButton("Simple Mirror");
		globalMirror = new JButton("Global Mirror");
		gp = new GraphicsPanel(this.shapes);
		color = new JComboBox<AppColor>(AppColor.values());
		color.setRenderer(new ColorAppRenderer());
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
		test.add(simpleMirror);
		test.add(globalMirror);
		test.add(color);
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
		simpleMirror.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = AppMode.MIRROR_SHAPE;
				secondInput = false;
			}

		});
		gp.addMouseListener(new MouseInputAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				switch(mode) {
					case ADD_SHAPE:
					if(secondInput) {
						AppColor ac = (AppColor) color.getSelectedItem();
						Color selectedColor = ac.getColor();
						switch(shapeType) {
							case CIRCLE:
								double dis=Math.sqrt((e.getX()-x)*(e.getX()-x) + (e.getY()-y)*(e.getY()-y));
								shapes.add(hsf.createICircle(x, y, dis, selectedColor));
								break;
							case LINE:
								shapes.add(hsf.createILine(x, y, e.getX(), e.getY(), selectedColor));
								break;
							case RECTANGLE:
								shapes.add(hsf.createIRectangle(x, y, e.getX(), e.getY(), selectedColor));
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
							resShape = shape;
							minDis = dis;
						}
					}
					resShape.mirror(resShape.getMostSouthPoint());
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

		globalMirror.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MirrorOperation.mirrorDrawing(shapes);
			}
		});

	}

	public void saveDrawing(String filePath) throws IOException {
		String xml = GraphicSerialization.serialize(shapes);
		System.out.println(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(xml);
		}
	}

	public void openDrawing(String filePath) throws IOException {
		shapes.clear();
		shapes.addAll(GraphicSerialization.deserialize(filePath));
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

	private enum AppColor {
		RED("Red", Color.RED),
		GREEN("Green", Color.GREEN),
		BLUE("Blue", Color.BLUE),
		BLACK("Black", Color.BLACK);

		private String name;
		private Color col;
		private BufferedImage icon;

		private AppColor(String s,Color c) {
			name = s;
			col = c;
			icon = new BufferedImage(15 , 10, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphics = icon.createGraphics();
			graphics.setPaint(c);
			graphics.fillRect( 0, 0, icon.getWidth(), icon.getHeight() );
			graphics.setPaint(Color.BLACK);
			graphics.drawRect( 0, 0, icon.getWidth(), icon.getHeight() );
		}

		public String getName() {
			return name;
		}

		public Color getColor() {
			return col;
		}

		public BufferedImage getIcon() {
			return icon;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
	private class ColorAppRenderer implements ListCellRenderer {

		private DefaultListCellRenderer delegate;

		public ColorAppRenderer() {
			delegate = new DefaultListCellRenderer();
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			AppColor ac = (AppColor) value;
			delegate.setIcon(new ImageIcon(ac.getIcon()));
			delegate.setText(ac.getName());
			return delegate;
		}

	}

}
