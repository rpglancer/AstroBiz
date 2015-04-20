package astroBiz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RegionView {
	private static final int MAPWIDTH = 2208;
	private static final int MAPHEIGHT = 864;
	private static final int REGIONWIDTH = 736;
	private static final int REGIONHEIGHT = 288;

	private String[] regionName = {"Mercury", "Venus", "Earth", "Luna", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
	private byte regionX = 1;
	private byte regionY = 1;
	
	private BufferedImage map;
	private BufferedImage region;
	
	public RegionView(AstroBiz astrobiz){
		SpriteSheet wm = new SpriteSheet(astrobiz.getWorldMap());
		SpriteSheet ss = new SpriteSheet(astrobiz.getSpriteSheet());	// Will also need access to the SpriteSheet ;_;
		map = wm.grabImage(1, 1, MAPWIDTH, MAPHEIGHT);
		region = wm.grabImage((int)regionX, (int)regionY, REGIONWIDTH, REGIONHEIGHT);
		region = map.getSubimage(regionX * REGIONWIDTH - REGIONWIDTH, regionY * REGIONHEIGHT - REGIONHEIGHT, REGIONWIDTH, REGIONHEIGHT);
	}
	
	public void tick(){
		region = map.getSubimage(regionX * REGIONWIDTH - REGIONWIDTH, regionY * REGIONHEIGHT - REGIONHEIGHT, REGIONWIDTH, REGIONHEIGHT);
	}
	
	public void render(Graphics g){
		g.drawImage(region, 32, 32, null);
		Font sectorfont = new Font("arial", Font.BOLD, 15);
		g.setFont(sectorfont);
		g.setColor(Color.WHITE);
		g.drawString("Region: " + regionName[(regionY - 1) * 3 + (regionX - 1)], 32, 25);
		// TODO: Render destinations
		// TODO: Render active routes
	}
	
	public byte getRegionX(){
		return this.regionX;
	}
	public byte getRegionY(){
		return this.regionY;
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