package fr.univ.client;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.Color;
import java.util.*;

import fr.univ.factory.HandShapeFactory;
import fr.univ.factory.ShapeFactory;
import fr.univ.factory.StandardShapeFactory;
import fr.univ.shapes.*;
import fr.univ.viewer.GraphicViewer;

public class Test {
	
	public static List<Drawable> getDemo() {
		ShapeFactory hsf = new StandardShapeFactory();
		java.util.List<Drawable> ls=new ArrayList<Drawable>();
		ls.add(hsf.createILine(0, 500, 800, 500, Color.GREEN));
		ls.add(hsf.createILine(300, 0, 0, 300, Color.YELLOW));
		
		ls.add(hsf.createILine(30, 300, 180, 200, Color.BLUE));
		ls.add(hsf.createILine(330, 300, 180, 200, Color.BLUE));
		ls.add(hsf.createIRectangle(30, 300,330, 500, Color.RED));

		double sunX = 600;
		double sunY = 120;
		double sunRad = 60; 
		ls.add(hsf.createICircle(sunX, sunY, sunRad, Color.BLACK));
		int sunRay = 20;
		for (int i=0; i<sunRay; ++i) {
			double tau=i*2*Math.PI/sunRay;
			ls.add(hsf.createILine(sunX+(sunRad+5)*Math.cos(tau),
				sunY-(sunRad+5)*Math.sin(tau),  
				sunX+(1.5*sunRad+5)*Math.cos(tau),
				sunY-(1.5*sunRad+5)*Math.sin(tau),
				Color.BLACK));
		}

		double manX=600;
		double manY=450;
		ls.add(hsf.createILine(manX, manY-70, manX-40, manY-110, Color.RED));
		ls.add(hsf.createILine(manX, manY-70, manX+40, manY-110, Color.RED));
		ls.add(hsf.createICircle(manX, manY-120, 20, Color.GRAY));
		ls.add(hsf.createILine(manX, manY, manX, manY-100, Color.BLUE));
		ls.add(hsf.createILine(manX, manY, manX-20, manY+50, Color.BLACK));
		ls.add(hsf.createILine(manX, manY, manX+20, manY+50, Color.BLACK));
		
		return ls;
	}
	
	public static List<Drawable> getDemoGroups() {
		ShapeFactory hsf = new StandardShapeFactory();
		java.util.List<Drawable> ls=new ArrayList<Drawable>();
		
		SubPicture home = new SubPicture();
		home.addGraphic(hsf.createILine(0, 500, 800, 500, Color.GREEN));
		home.addGraphic(hsf.createILine(300, 0, 0, 300, Color.YELLOW));
		home.addGraphic(hsf.createILine(30, 300, 180, 200, Color.BLUE));
		home.addGraphic(hsf.createILine(330, 300, 180, 200, Color.BLUE));
		home.addGraphic(hsf.createIRectangle(30, 300,330, 500, Color.RED));
		
		ls.add(home);

		double sunX = 600;
		double sunY = 120;
		double sunRad = 60; 
		
		SubPicture sun = new SubPicture();
		sun.addGraphic(hsf.createICircle(sunX, sunY, sunRad, Color.BLACK));
		int sunRay = 20;
		for (int i=0; i<sunRay; ++i) {
			double tau=i * 2 * Math.PI/sunRay;
			sun.addGraphic(hsf.createILine(sunX+(sunRad+5)*Math.cos(tau),
				sunY-(sunRad+5)*Math.sin(tau),  
				sunX+(1.5*sunRad+5)*Math.cos(tau),
				sunY-(1.5*sunRad+5)*Math.sin(tau),
				Color.BLACK));
		}
		ls.add(sun);

		double manX=600;
		double manY=450;
		SubPicture homme = new SubPicture();
		homme.addGraphic(hsf.createILine(manX, manY-70, manX-40, manY-110, Color.RED));
		homme.addGraphic(hsf.createILine(manX, manY-70, manX+40, manY-110, Color.RED));
		homme.addGraphic(hsf.createICircle(manX, manY-120, 20, Color.GRAY));
		homme.addGraphic(hsf.createILine(manX, manY, manX, manY-100, Color.BLUE));
		homme.addGraphic(hsf.createILine(manX, manY, manX-20, manY+50, Color.BLACK));
		homme.addGraphic(hsf.createILine(manX, manY, manX+20, manY+50, Color.BLACK));

		ls.add(homme);
		
		homme.move(-400, 0);
		
		return ls;
	}
}
