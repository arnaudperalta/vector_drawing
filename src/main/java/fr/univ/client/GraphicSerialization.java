package fr.univ.client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.awt.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import fr.univ.shapes.Circle;
import fr.univ.shapes.Rectangle;
import fr.univ.shapes.SubPicture;
import fr.univ.shapes.Graphics;
import fr.univ.shapes.HandCircle;
import fr.univ.shapes.HandLine;
import fr.univ.shapes.HandRectangle;
import fr.univ.shapes.Line;

public class GraphicSerialization {

	private GraphicSerialization() {}

	public static String serialize(List<Graphics> shapes) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<!DOCTYPE drawing SYSTEM \"drawing.dtd\">"
			+ "<drawing xmlns=\"http://www.univ-rouen.fr/drawing\">";
		for (Graphics g : shapes) {
			xml += g.serialize();
		}
		xml += "</drawing>";
		return xml;
	}

	public static List<Graphics> deserialize(String filePath) throws IOException {
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
			return handler.getResult();
		} catch (ParserConfigurationException | SAXException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	private static class HandlerImpl extends DefaultHandler {

		private List<Graphics> graphicsList = new ArrayList<>();
		private boolean isRadius, isHandMade;
		private Double x0;
		private Double y0;
		private Double x1;
		private Double y1;
		private Double radius;
		private Color color;
		private Deque<SubPicture> subPicStack = new LinkedList<>();

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
				if (atts.getValue("color").equals("black"))
					color = Color.BLACK;
				if (atts.getValue("type").equals("hand"))
					isHandMade = true;
				if (atts.getValue("type").equals("std"))
					isHandMade = false;
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
			if (qName.equals("subpicture"))
				subPicStack.push(new SubPicture());
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (qName.equals("circle")) {
				Graphics g;
				if (isHandMade)
					g = new HandCircle(x0, y0, radius, color);
				else
					g = new Circle(x0, y0, radius, color);
				if (!subPicStack.isEmpty())
					subPicStack.getLast().addGraphic(g);
				else 
					graphicsList.add(g);
				resetPoints();
			}
			if (qName.equals("rectangle")) {
				Graphics g;
				if (isHandMade)
					g = new HandRectangle(x0, y0, x1, y1, color);
				else
					g = new Rectangle(x0, y0, x1, y1, color);
				if (!subPicStack.isEmpty())
					subPicStack.getLast().addGraphic(g);
				else 
					graphicsList.add(g);
				resetPoints();
			}
			if (qName.equals("line")) {
				Graphics g;
				if (isHandMade)
					g = new HandLine(x0, y0, x1, y1, color);
				else
					g = new Line(x0, y0, x1, y1, color);
				if (!subPicStack.isEmpty())
					subPicStack.getLast().addGraphic(g);
				else 
					graphicsList.add(g);
				resetPoints();
			}
			if (qName.equals("radius"))
				isRadius = false;
			if (qName.equals("subpicture"))
				graphicsList.add(subPicStack.pop());
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			if (isRadius) {
				radius = Double.parseDouble(new String(ch, start, length));
			}
		}

		public void resetPoints() {
			x0 = null;
			y0 = null;
			x1 = null;
			y1 = null;
		}

		public List<Graphics> getResult() {
			return this.graphicsList;
		}

	}
}
