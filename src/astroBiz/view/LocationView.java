package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import astroBiz.Location;

public class LocationView {
	private Location location;
	
	public LocationView(Location l){
		this.location = l;
	}
	public void render(Graphics g){
		// Place holders
		Graphics2D g2d = (Graphics2D) g;
		Font fnt1 = new Font("arial", Font.BOLD, 25);
		g.setFont(fnt1);
		g2d.setColor(Color.WHITE);
		g2d.drawString(location.getLocationName(), 160, 96);
		g2d.setColor(Color.BLACK);				// Exit X Button
		g2d.fillRect(736, 32, 32, 32);			// Exit X Button
		g2d.setColor(Color.WHITE);				// Exit X Button
		g2d.drawRect(736, 32, 32, 32);			// Exit X Button
		g2d.setColor(Color.WHITE);				// Exit X Button
		g2d.drawString("X", 736 + 8, 32 + 25);	// Exit X Button
		g2d.setColor(Color.BLUE);
		g2d.fillRect(64, 32, 96, 64);			// Flag
		g2d.setColor(Color.CYAN);
		g2d.fillRect(64, 96, 288, 160); 		// City Drawing
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(352, 96, 384, 192);		// Business Ventures
		g2d.setColor(Color.GREEN);
		g2d.fillRect(64, 256, 288, 64);			// Slots : Flights Info
		g2d.setColor(Color.BLACK);
		Font slotfont = new Font("arial", Font.BOLD, 15);
		g.setFont(slotfont);
		g2d.drawString("Total Slots:", 64, 288);
		g2d.drawString(location.getLocationSlotAvailable() + " / " + location.getLocationSlotTotal(), 64, 320);
		
		// TODO: Actually render the location GUI.
	}
	public void tick(){
		// TODO: Determine if anything in the LV actually needs to tick.
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	public void setLocationView(Location l){
		this.location = l;
	}

}
