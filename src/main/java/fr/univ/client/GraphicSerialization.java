package fr.univ.client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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

import fr.univ.builder.SketchBuilder;
import fr.univ.builder.SketchBuilderHand;
import fr.univ.builder.SketchBuilderStd;
import fr.univ.shapes.SubPicture;
import fr.univ.shapes.Graphics;

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

		private SketchBuilderHand sbh = new SketchBuilderHand();
		private SketchBuilderStd sbs = new SketchBuilderStd();
		private SketchBuilder actualBuilder;
		private List<Graphics> graphicsList = new ArrayList<>();
		private Deque<SubPicture> subPicStack = new ArrayDeque<>();
		private boolean isRadius;

		@Override
		public void startElement(String uri, String localname, String qName, Attributes atts) {
			if (atts.getValue("type") != null && atts.getValue("type").equals("std"))
				actualBuilder = sbs;
			if (atts.getValue("type") != null && atts.getValue("type").equals("hand"))
				actualBuilder = sbh;
			if (qName.equals("circle"))
				actualBuilder.startCircle();
			if (qName.equals("rectangle"))
				actualBuilder.startRectangle();
			if (qName.equals("line"))
				actualBuilder.startLine();
			if (atts.getValue("color") != null && atts.getValue("color").equals("blue"))
				actualBuilder.addColor(Color.BLUE);
			if (atts.getValue("color") != null && atts.getValue("color").equals("red"))
				actualBuilder.addColor(Color.RED);
			if (atts.getValue("color") != null && atts.getValue("color").equals("green"))
				actualBuilder.addColor(Color.GREEN);
			if (atts.getValue("color") != null && atts.getValue("color").equals("black"))
				actualBuilder.addColor(Color.BLACK);
			if (qName.equals("point")) {
				double[] point = { Double.parseDouble(atts.getValue("x")), Double.parseDouble(atts.getValue("y")) };
				actualBuilder.addPoint(point);
			}
			if (qName.equals("radius"))
				isRadius = true;
			if (qName.equals("subpicture"))
				subPicStack.push(new SubPicture());
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (qName.equals("circle")) {
				Graphics g = actualBuilder.endCircle();
				if (!subPicStack.isEmpty())
					subPicStack.getFirst().addGraphic(g);
				else 
					graphicsList.add(g);
			}
			if (qName.equals("rectangle")) {
				Graphics g = actualBuilder.endRectangle();
				if (!subPicStack.isEmpty())
					subPicStack.getFirst().addGraphic(g);
				else 
					graphicsList.add(g);
			}
			if (qName.equals("line")) {
				Graphics g = actualBuilder.endLine();
				if (!subPicStack.isEmpty())
					subPicStack.getFirst().addGraphic(g);
				else 
					graphicsList.add(g);
			}
			if (qName.equals("radius"))
				isRadius = false;
			if (qName.equals("subpicture")) {
				SubPicture lastSub = subPicStack.pop();
				if (!subPicStack.isEmpty())
					subPicStack.getFirst().addGraphic(lastSub);
				else
					graphicsList.add(lastSub);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			if (isRadius)
				actualBuilder.addRadius(Double.parseDouble(new String(ch, start, length)));
		}

		public List<Graphics> getResult() {
			return this.graphicsList;
		}

	}
}
