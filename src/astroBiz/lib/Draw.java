package astroBiz.lib;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import astroBiz.AstroBiz;
import astroBiz.info.FontInformation;
import astroBiz.info.HALIGN;
import astroBiz.info.VALIGN;
import astroBiz.util.textUtilities;

/**
 * Anything drawn that requires multiple steps or needs to be accessible from any class goes here.
 * @author Matt Bangert
 *
 */
public class Draw {
	public static void drawFareSelect(Graphics g, Route route, int setting, int x, int y){
		Graphics2D g2d = (Graphics2D)g;
		Color prevColor = g2d.getColor();
		
		RoundRectangle2D.Float rect = new RoundRectangle2D.Float();
		rect.setRoundRect(x, y, 184, 32, 16, 16);
		g2d.setColor(Color.white);
//		g2d.draw(rect);
		x+=9;
		y+=4;
		for(int i = 0; i <= setting; i++){
			rect.setRoundRect(x, y, 6, 24, 0, 0);
			g2d.setColor(Color.blue);
			if(i > 7) g2d.setColor(Color.green);
			if(i > 13) g2d.setColor(Color.yellow);
			if(i > 16) g2d.setColor(Color.red);
			g2d.fill(rect);
			g2d.setColor(Color.white);
//			g2d.draw(rect);
			x+=8;
		}
		g2d.setColor(prevColor);
	}

	public static void drawMinimap(Graphics g, Business b){
		int sx = 32;
		int sy = 320;
		int height = 128;
		int width = 160;
		
		int px = 48;
		int py = 320;
		int pheight = 128;
		int pwidth = 128;
		
		g.setColor(Color.black);
		g.fillRect(sx, sy, width, height);
		
		//	Draw Planet Orbit Lines
		g.setColor(Color.DARK_GRAY);
		//	Region Neptune
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Uranus
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Saturn
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Jupiter
		g.drawOval(px, py, pwidth, pheight);
		px+=10; py+=10; pwidth-=20; pheight-=20;
		//	Region Mars
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Earth
		if(b.regionContainsHub(2))g.setColor(b.getColor());
		else g.setColor(Color.darkGray);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Venus
		if(b.regionContainsHub(1))g.setColor(b.getColor());
		else g.setColor(Color.darkGray);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Mercury
		if(b.regionContainsHub(0))g.setColor(b.getColor());
		else g.setColor(Color.darkGray);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		
		// Draw Planets
		//	Neptune
		if(b.regionContainsHub(8))
			drawPlanetMini(g,40,368,17,Color.white,b.getColor());
		else
			drawPlanetMini(g,40,368,17,Color.darkGray,Color.black);
		
		//	Uranus
		if(b.regionContainsHub(7))
			drawPlanetMini(g,73,424,17,Color.white,b.getColor());
		else
			drawPlanetMini(g,73,424,17,Color.darkGray,Color.black);
		
		//	Saturn
		if(b.regionContainsHub(6))
			drawPlanetMini(g,148,400,17,Color.white,b.getColor());
		else
			drawPlanetMini(g,148,400,17,Color.darkGray,Color.black);
		
		//	Jupiter
		if(b.regionContainsHub(5))
			drawPlanetMini(g,117,331,22,Color.white,b.getColor());
		else
			drawPlanetMini(g,117,331,22,Color.darkGray,Color.black);
		
		//	Mars
		if(b.regionContainsHub(4))
			drawPlanetMini(g,75,395,8,Color.white,b.getColor());
		else
			drawPlanetMini(g,75,395,8,Color.darkGray,Color.black);
		
		//	Luna
		// ...
		
		//	Earth
		if(b.regionContainsHub(2))
			drawPlanetMini(g,135,367,8,Color.white,b.getColor());
		else
			drawPlanetMini(g,135,367,8,Color.darkGray,Color.black);
	}
	
	public static void drawSpaceCraftModelRoute(Graphics g, SpaceCraft model, Business busi){

		
	}
	
	public static void drawSpaceCraftModelStats(Graphics g, SpaceCraft model, Business busi){
		int x = 160 - 8;
		int y = 96 - 8;
		int w = 480 + 16;
		int h = 128 + 16;
		Rectangle stat = new Rectangle();
		Rectangle window = new Rectangle(x, y, w, h);
		
		//	This is nice, and will probably be reused, move it to its own function.
		for(int i = 0; i <= 3; i++){
			if(i < 3)
				drawWindow(g, window, Color.black, busi.getColor());
			x+=2;
			y+=2;
			w-=4;
			h-=4;
			window.setBounds(x, y, w, h);
			}
		g.setColor(Color.white);
//		g.drawString(x + ", " + y + ", " + w + ", " + h, 0, 32);
		
//		Selected Model Picture Box
		stat.setBounds(160, 96, 192, 128);
		drawWindow(g, stat, Color.blue, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.LEFT, VALIGN.BOTTOM, model.getName());
		
//		Range Box
		stat.setBounds(384, 96, 96, 32);
		drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "Range:");
		
		//	Range Value Box
		stat.setBounds(480, 96, 160, 32);
		drawWindow(g, stat, Color.black, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.CENTER, VALIGN.MIDDLE, model.getRange() + "AU");

		//	Capacity Box
		stat.setBounds(384, 128, 96, 32);
		drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "Seats:");
		
		// Capacity Value Box
		stat.setBounds(480, 128, 160, 32);
		drawWindow(g, stat, Color.black, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.CENTER, VALIGN.MIDDLE, model.getCapacity() + "s");
		
		//	Fuel Efficiency
		stat.setBounds(384, 160, 64, 32);
		drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "FuelE:");
		
		//	Fuel Efficiency Value Box
		stat.setBounds(448, 160, 64, 32);
		drawWindow(g, stat, Color.black, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.CENTER, VALIGN.MIDDLE, model.getFuelE() + "");
		
		//	Reliability
		stat.setBounds(512, 160, 64, 32);
		drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "Rely:");
		
		//	Reliability Value Box
		stat.setBounds(576, 160, 64, 32);
		drawWindow(g, stat, Color.black, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.CENTER, VALIGN.MIDDLE, model.getMaintR() + "");
		
		//	# in Use
		stat.setBounds(384, 192, 64, 32);
		drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "#USE:");
		
		//	# in Use Value Box
		stat.setBounds(448, 192, 64, 32);
		drawWindow(g, stat, Color.black, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.CENTER, VALIGN.MIDDLE, busi.getCraftInService(model) + "");
		
		//	# Hangar
		stat.setBounds(512, 192, 64, 32);
		drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "#HGR:");
		
		//	# in Hangar Value Box
		stat.setBounds(576, 192, 64, 32);
		drawWindow(g, stat, Color.black, Color.white);
		if(busi.getCraftInHangar(model) == 0)
			g.setColor(Color.red);
		else
			g.setColor(Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.CENTER, VALIGN.MIDDLE, busi.getCraftInHangar(model) + "");
	}
	
	public static void drawRegion(Graphics g, byte region){
		int height = 288;
		int width = 736;
		if(region == 0) g.drawImage(AstroBiz.worldMap.grabImage(1, 1, width, height), 32, 32, null);
		else if(region == 1) g.drawImage(AstroBiz.worldMap.grabImage(2, 1, width, height), 32, 32, null);
		else if(region == 2) g.drawImage(AstroBiz.worldMap.grabImage(3, 1, width, height), 32, 32, null);
		else if(region == 3) g.drawImage(AstroBiz.worldMap.grabImage(1, 2, width, height), 32, 32, null);
		else if(region == 4) g.drawImage(AstroBiz.worldMap.grabImage(2, 2, width, height), 32, 32, null);
		else if(region == 5) g.drawImage(AstroBiz.worldMap.grabImage(3, 2, width, height), 32, 32, null);
		else if(region == 6) g.drawImage(AstroBiz.worldMap.grabImage(1, 3, width, height), 32, 32, null);
		else if(region == 7) g.drawImage(AstroBiz.worldMap.grabImage(2, 3, width, height), 32, 32, null);
		else g.drawImage(AstroBiz.worldMap.grabImage(3, 3, width, height), 32, 32, null);
	}
	
	public static void drawRegionLocations(Graphics g, Scenario s, byte region){
		for(int i = 0; i < s.getLocations().size(); i++){
			if(s.getLocations().elementAt(i).getRegion() == region){
				Location l = s.getLocations().elementAt(i);
				g.drawImage(l.getSprite(s), l.getX(), l.getY(), null);
//				if(l.getSlotAllocatedFor(s.getActiveBusiness()) > 0){
				if(l.getSlotAvailableFor(s.getActiveBusiness()) > 0){
					g.setFont(FontInformation.modelstat);
					g.setColor(Color.white);
					g.drawString(l.getSlotAvailableFor(s.getActiveBusiness()) + "", l.getX() + 16, l.getY() + 16);
				}
			}
		}
	}

	public static void drawSolarSystem(Graphics g, int selection){
		g.setFont(FontInformation.regionselect);
		if(selection == 0){
			g.setColor(Color.green);
			g.drawString("Mercury", 96+15, 177+15);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(96, 177, 15, 15);		// Mercury
		
		if(selection == 1){
			g.setColor(Color.green);
			g.drawString("Venus", 160+32, 160+32);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(160, 160, 32, 32);		// Venus
		
		if(selection == 2){
			g.setColor(Color.green);
			g.drawString("Earth", 224+32, 64+32);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(224, 64, 32, 32);		// Earth
		
		if(selection == 3){
			g.setColor(Color.green);
			g.drawString("Luna", 256+10, 54+10);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(256, 54, 10, 10);		// Luna
		
		if(selection == 4){
			g.setColor(Color.green);
			g.drawString("Mars", 288+24, 160+24);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(288, 160, 24, 24);		// Mars
		
		if(selection == 5){
			g.setColor(Color.green);
			g.drawString("Jupiter", 352+96, 192+96);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(352, 192, 96, 96);		// Jupiter
		
		if(selection == 6){
			g.setColor(Color.green);
			g.drawString("Saturn", 480+64, 128+64);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(480, 128, 64, 64);		// Saturn
		
		if(selection == 7){
			g.setColor(Color.green);
			g.drawString("Uranus", 576+64, 192+64);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(576, 192, 64, 64);		// Uranus
		
		if(selection == 8){
			g.setColor(Color.green);
			g.drawString("Neptune", 676+64, 96+64);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(676, 96, 64, 64);		// Neptune
	}
	
	public static void drawWindow(Graphics g, Rectangle r, Color bg, Color border){
		Graphics2D g2d = (Graphics2D)g;
		Color prev = g2d.getColor();
		g2d.setColor(bg);
		g2d.fill(r);
		g2d.setColor(border);
		g2d.draw(r);
		g2d.setColor(prev);
	}

	private static void drawPlanetMini(Graphics g, int x, int y, int size, Color border, Color fill){
		Color old = g.getColor();
		g.setColor(border);
		g.fillOval(x, y, size, size);
		g.setColor(fill);
		g.fillOval(x+1, y+1, size-2, size-2);
		g.setColor(old);
	}
}
