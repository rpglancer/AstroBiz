package astroBiz;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RegionView {
	private static final int REGIONWIDTH = 736;
	private static final int REGIONHEIGHT = 288;
	
	private BufferedImage map;
	private BufferedImage region;
	
	public RegionView(AstroBiz astrobiz){
		SpriteSheet wm = new SpriteSheet(astrobiz.getWorldMap());
		region = wm.grabImage(2, 2, 736, 288);
	}
	
	public void render(Graphics g){
		g.drawImage(region, 32, 32, null);
	}

}
