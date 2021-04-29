package fr.univ.client;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import fr.univ.factory.ShapeFactory;
import fr.univ.factory.StandardShapeFactory;
import fr.univ.shapes.Circle;
import fr.univ.shapes.Graphics;
import fr.univ.shapes.Line;
import fr.univ.shapes.Rectangle;
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

	private GraphicsPanel gp;

	private List<Graphics> shapes;
	private ShapeFactory hsf;

	private int x;
	private int y;

	private boolean secondInput;
	private Shape shapeType;

	private Graphics selectedShape;

	private AppMode mode;

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
	}

	public void saveDrawing(String filePath) throws IOException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<drawing xmlns=\"http://www.univ-rouen.fr/drawing\">";
		for (Graphics g : shapes) {
			xml += g.serialize();
		}
		xml += "</drawing>";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(xml);
		}
	}

	public void openDrawing(String filePath) throws IOException {
		InputSource is = new InputSource(new BufferedInputStream(new FileInputStream(filePath)));
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		XMLReader xr;
		try {
			sp = spf.newSAXParser();
			xr = sp.getXMLReader();
			HandlerImpl handler = new HandlerImpl();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			xr.parse(is);
		} catch (ParserConfigurationException | SAXException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	private class HandlerImpl extends DefaultHandler {

		private List<Graphics> graphicsList = new ArrayList<>();
		private boolean isRadius;
		private Double x0;
		private Double y0;
		private Double x1;
		private Double y1;
		private Double radius;
		private Color color;

		
		@Override
		public void startElement(String uri, String localname, String qName, Attributes atts) {
			if (qName.equals("circle")
					|| qName.equals("rectangle")
					|| qName.equals("line")) {
				if (atts.getValue("color").equals("blue"))
					color = Color.BLUE;
				if (atts.getValue("color").equals("red"))
					color = Color.RED;
				if (atts.getValue("color").equals("green"))
					color = Color.GREEN;
			}
			if (qName.equals("point")) {
				// Si le premier point est null
				if (x0 == null) {
					this.x0 = Double.parseDouble(atts.getValue("x"));
					this.y0 = Double.parseDouble(atts.getValue("y"));
				} else {
					this.x1 = Double.parseDouble(atts.getValue("x"));
					this.y1 = Double.parseDouble(atts.getValue("y"));
				}
			}
			if (qName.equals("radius"))
				isRadius = true;
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) {
			if (qName.equals("circle")) {
				Graphics g = new Circle(x0, y0, radius, color);
				graphicsList.add(g);
				resetPoints();
			}
			if (qName.equals("rectangle")) {
				Graphics g = new Rectangle(x0, y0, x1, y1, color);
				graphicsList.add(g);
				resetPoints();
			}
			if (qName.equals("line")) {
				Graphics g = new Line(x0, y0, x1, y1, color);
				graphicsList.add(g);
				resetPoints();
			}
			if (qName.equals("radius"))
				isRadius = false;
		}
		
		@Override
		public void characters(char[] ch, int start, int length) {
			if (isRadius) {
				radius = Double.parseDouble(new String(ch, start, length));
			}
		}

		@Override
		public void endDocument() {
			for (Graphics g : graphicsList) {
				System.out.println(g.serialize());
			}
		}

		public void resetPoints() {
			x0 = null;
			y0 = null;
			x1 = null;
			y1 = null;
		}

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

}
