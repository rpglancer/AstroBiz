package astroBiz.lib;

import java.awt.Color;
import java.awt.Graphics;

import astroBiz.AstroBiz;
import astroBiz.info.FontInformation;

public class Draw {
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
				if(l.getSlotAllocatedFor(s.getActiveBusiness()) > 0){
					g.setFont(FontInformation.modelstat);
					g.setColor(Color.white);
					g.drawString(l.getSlotAllocatedFor(s.getActiveBusiness()) + "", l.getX() + 16, l.getY() + 16);
				}
			}
		}
	}

}
