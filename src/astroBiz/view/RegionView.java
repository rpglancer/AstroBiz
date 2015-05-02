package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.Location;

public class RegionView {
	private static final int MAPWIDTH = 2208;
	private static final int MAPHEIGHT = 864;
	private static final int REGIONWIDTH = 736;
	private static final int REGIONHEIGHT = 288;
	private static final int SPRITEHEIGHT = 16;
	private static final int SPRITEWIDTH = 16;
	private static final int BUTTONHEIGHT = 64;
	private static final int BUTTONWIDTH = 96;
	
	private static enum REGIONVM{
		
	}
	private static enum REGIONBUTTON{
		RB_ROUTEOPEN,
		RB_ROUTEADJUST,
		RB_SLOTBID,
		RB_BUY,
		RB_BUDGET,
		RB_VENTURE,
		RB_HUBOPEN,
		RB_AD,
		RB_MEETING,
		RB_STATUS,
		RB_SETTING,
		RB_TURN;
	}
	private REGIONBUTTON regionButton = REGIONBUTTON.RB_ROUTEOPEN;
	private String[] regionName = {"Mercury", "Venus", "Earth", "Luna", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
	private Vector<Location> mapLocations = new Vector<Location>();
	private byte regionX = 3;	// Default Sector
	private byte regionY = 1;	// Default Sector

	private AstroBiz ab;
	private BufferedImage map;			// Contains the entirety of the map
	private BufferedImage region;		// Contains the displayed region of the map
	private BufferedImage[] buttons;	// Contains the buttons displayed on the regional map.
	
	private Font sectorfont = new Font("arial", Font.BOLD, 15);

	public RegionView(AstroBiz astrobiz){
		this.ab = astrobiz;
		map = ab.getWorldMap().grabImage(1, 1, MAPWIDTH, MAPHEIGHT);
		region = map.getSubimage(regionX * REGIONWIDTH - REGIONWIDTH, regionY * REGIONHEIGHT - REGIONHEIGHT, REGIONWIDTH, REGIONHEIGHT);
		buttons = new BufferedImage[12];
		
		int i = 0;
		for(int y = 1; y <= 4; y++){
			for(int x = 1; x <= 3; x++){
				buttons[i] = ab.getRegionButtons().grabImage(x, y, BUTTONWIDTH, BUTTONHEIGHT);
				i++;
			}
		}
// TODO: Find a way of populating the regions with locations that doesn't involve manual creation of every one.
		for(int l = 0; l < 8; l++){
			Location location = new Location();
			location.generateEarthLocation(l);
			mapLocations.addElement(location);
		}
		for(int l = 0; l < 8; l++){
			Location location = new Location();
			location.generateLunaLocation(l);
			mapLocations.addElement(location);
		}
	}
	
	public void tick(){
		region = map.getSubimage(regionX * REGIONWIDTH - REGIONWIDTH, regionY * REGIONHEIGHT - REGIONHEIGHT, REGIONWIDTH, REGIONHEIGHT);
	}
	
	public void render(Graphics g){
		g.drawImage(region, 32, 32, null);
		g.setFont(sectorfont);
		g.setColor(Color.WHITE);
		g.drawString("Region: " + regionName[getRegionID(regionX, regionY)], 32, 25);		// temp drawings
		g.drawString("Active: " + ab.getScenario().getActiveBusiness(), 400, 25);
//		g.drawString("ID: " + getRegionID(regionX, regionY), 400, 25);						// temp drawings
		g.drawString(Integer.toString(ab.getScenario().getCurrentYear()), 600, 25);			// temp drawings
		
		// Prototype Render Locations
		for(int i = 0; i < mapLocations.size(); i++){
			if(mapLocations.elementAt(i).getLocationRegion() == getRegionID(regionX, regionY)){
				g.drawImage(mapLocations.elementAt(i).getSprite(ab.getScenario()) , mapLocations.elementAt(i).getLocationX(), mapLocations.elementAt(i).getLocationY(), null);
			}
		}
		
		// TODO: Render Routes.
		
		// Prototype Render Buttons
		int x = 192;
		int y = 320;
		for(int i = 0; i < buttons.length; i++){
			g.drawImage(buttons[i], x, y, null);
			x += BUTTONWIDTH;
			if(x > 672){
				x = 192;
				y += BUTTONHEIGHT;
			}
		}
	}
	
	private void cycleButtonDown(){
		if(regionButton == REGIONBUTTON.RB_ROUTEOPEN) regionButton = REGIONBUTTON.RB_HUBOPEN;
		else if(regionButton == REGIONBUTTON.RB_ROUTEADJUST) regionButton = REGIONBUTTON.RB_AD;
		else if(regionButton == REGIONBUTTON.RB_SLOTBID) regionButton = REGIONBUTTON.RB_MEETING;
		else if(regionButton == REGIONBUTTON.RB_BUY) regionButton = REGIONBUTTON.RB_STATUS;
		else if(regionButton == REGIONBUTTON.RB_BUDGET) regionButton = REGIONBUTTON.RB_SETTING;
		else if(regionButton == REGIONBUTTON.RB_VENTURE) regionButton = REGIONBUTTON.RB_TURN;
	}
	
	private void cycleButtonLeft(){	
	}
	
	private void cycleButtonRight(){
	}
	
	private void cycleButtonUp(){
		if(regionButton == REGIONBUTTON.RB_HUBOPEN) regionButton = REGIONBUTTON.RB_ROUTEOPEN;
		else if(regionButton == REGIONBUTTON.RB_AD) regionButton = REGIONBUTTON.RB_ROUTEADJUST;
		else if(regionButton == REGIONBUTTON.RB_MEETING) regionButton = REGIONBUTTON.RB_SLOTBID;
		else if(regionButton == REGIONBUTTON.RB_STATUS) regionButton = REGIONBUTTON.RB_BUY;
		else if(regionButton == REGIONBUTTON.RB_SETTING) regionButton = REGIONBUTTON.RB_BUDGET;
		else if(regionButton == REGIONBUTTON.RB_TURN) regionButton = REGIONBUTTON.RB_VENTURE;
	}
	
	public void keyAction(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			setRegionY(getRegionY() - 1);
			break;
		case KeyEvent.VK_DOWN:
			setRegionY(getRegionY() + 1);
			break;
		case KeyEvent.VK_RIGHT:
			setRegionX(getRegionX() + 1);
			break;
		case KeyEvent.VK_LEFT:
			setRegionX(getRegionX() - 1);
			break;
		default:
			break;
		}
	}
	
	public byte getRegionID(int x, int y){
		int id = 0;
		if(x < 0 || x > 64){
			System.out.println("Region X coordinate (" + x + ") is invalid, returning " + id + ".");
			return (byte)id;
		}
		if(y < 0 || y > 64){
			System.out.println("Region Y coordinate (" + y + ") is invalid, returning " + id + ".");
			return (byte)id;
		}
		id = (y - 1) * 3 + (x - 1);
		return (byte)id;
	}
	public byte getRegionX(){
		return this.regionX;
	}
	public byte getRegionY(){
		return this.regionY;
	}
	public Vector<Location> getLocationVector(){
		return this.mapLocations;
	}
	public void setRegionX(int x){
		if(x < 1) 
			x = 3;
		if(x > 3)
			x = 1;
		this.regionX = (byte)x;
	}
	public void setRegionY(int y){
		if(y < 1) 
			y = 3;
		if(y > 3)
			y = 1;
		this.regionY = (byte)y;
	}

}
