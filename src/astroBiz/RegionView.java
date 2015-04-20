package astroBiz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class RegionView {
	private static final int MAPWIDTH = 2208;
	private static final int MAPHEIGHT = 864;
	private static final int REGIONWIDTH = 736;
	private static final int REGIONHEIGHT = 288;
	private static final int SPRITEHEIGHT = 16;
	private static final int SPRITEWIDTH = 16;

	private String[] regionName = {"Mercury", "Venus", "Earth", "Luna", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
	private Vector<Location> mapLocations = new Vector<Location>();
	private byte regionX = 3;	// Default Sector
	private byte regionY = 1;	// Default Sector
	
	private BufferedImage map;			// Contains the entirety of the map
	private BufferedImage region;		// Contains the displayed region of the map

	
	public RegionView(AstroBiz astrobiz){
		SpriteSheet wm = new SpriteSheet(astrobiz.getWorldMap());
		SpriteSheet ss = new SpriteSheet(astrobiz.getSpriteSheet());	// Will also need access to the SpriteSheet ;_;
		map = wm.grabImage(1, 1, MAPWIDTH, MAPHEIGHT);
		region = wm.grabImage((int)regionX, (int)regionY, REGIONWIDTH, REGIONHEIGHT);
		region = map.getSubimage(regionX * REGIONWIDTH - REGIONWIDTH, regionY * REGIONHEIGHT - REGIONHEIGHT, REGIONWIDTH, REGIONHEIGHT);getClass();

// TODO: Find a way of populating the regions with locations that doesn't involve manual creation of every one.
		Location testCity = new Location(ss.grabImage(1, 1, SPRITEWIDTH, SPRITEHEIGHT));
		Location testCity2 = new Location(ss.grabImage(1, 1, SPRITEWIDTH, SPRITEHEIGHT));
		testCity.generateLocation(100, 100, 2);
		testCity2.generateLocation(62, 531, 3);
		mapLocations.addElement(testCity);
		mapLocations.addElement(testCity2);
	}
	
	public void tick(){
		region = map.getSubimage(regionX * REGIONWIDTH - REGIONWIDTH, regionY * REGIONHEIGHT - REGIONHEIGHT, REGIONWIDTH, REGIONHEIGHT);
	}
	
	public void render(Graphics g){
		g.drawImage(region, 32, 32, null);
		Font sectorfont = new Font("arial", Font.BOLD, 15);
		g.setFont(sectorfont);
		g.setColor(Color.WHITE);
		g.drawString("Region: " + regionName[getRegionID(regionX, regionY)], 32, 25);
		g.drawString("ID: " + getRegionID(regionX, regionY), 400, 25);
		
		// Prototype Render Locations
		for(int i = 0; i < mapLocations.size(); i++){
			if(mapLocations.elementAt(i).getLocationRegion() == getRegionID(regionX, regionY)){
				g.drawImage(mapLocations.elementAt(i).getSprite() , mapLocations.elementAt(i).getLocationX(), mapLocations.elementAt(i).getLocationY(), null);
			}
		}
		// TODO: Render Routes.
	}
	public byte getRegionID(int x, int y){
		int id = 0;
		if(x < 0 || x > 64)
			return (byte)id;
		if(y < 0 || y > 64)
			return (byte)id;
		
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
