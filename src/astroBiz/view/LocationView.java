package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import astroBiz.AstroBiz;
import astroBiz.info.FontInformation;
import astroBiz.lib.Location;
import astroBiz.util.textUtilities;

public class LocationView {
	private Location location;
	
	public LocationView(Location l){
		this.location = l;
	}
	public void render(Graphics g, AstroBiz ab){
		Font fnt1 = new Font("arial", Font.BOLD, 25);
		g.setFont(fnt1);
		g.setColor(Color.WHITE);
		textUtilities.drawStringMultiLine(g, FontInformation.modelheader, 160, 80, 500, location.getLocationName());
		g.setColor(Color.BLACK);				// Exit X Button
		g.fillRect(736, 32, 32, 32);			// Exit X Button
		g.setColor(Color.WHITE);				// Exit X Button
		g.drawRect(736, 32, 32, 32);			// Exit X Button
		g.setColor(Color.WHITE);				// Exit X Button
		g.drawString("X", 736 + 8, 32 + 25);	// Exit X Button
		g.setColor(Color.BLUE);
		g.fillRect(64, 32, 96, 64);				// Flag
		g.setColor(Color.CYAN);
		g.fillRect(64, 96, 288, 160); 			// City Drawing
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(352, 96, 384, 192);			// Business Ventures
		g.setColor(Color.GREEN);
		g.fillRect(64, 256, 288, 64);			// Slots : Flights Info
		g.setColor(ab.getScenario().getBusinesses().elementAt(0).getColor());
		g.fillRect(352, 288, 96, 32);
		g.setColor(ab.getScenario().getBusinesses().elementAt(1).getColor());
		g.fillRect(448, 288, 96, 32);
		g.setColor(ab.getScenario().getBusinesses().elementAt(2).getColor());
		g.fillRect(544, 288, 96, 32);
		g.setColor(ab.getScenario().getBusinesses().elementAt(3).getColor());
		g.fillRect(640, 288, 96, 32);
		g.setColor(Color.BLACK);
		Font slotfont = new Font("arial", Font.BOLD, 15);
		g.setFont(slotfont);
		g.drawString("Total Slots:", 64, 288);
		g.drawString(location.getLocationSlotAvailable() + " / " + location.getLocationSlotTotal(), 64, 320);
		g.dispose();
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
