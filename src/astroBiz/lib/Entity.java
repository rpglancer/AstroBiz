package astroBiz.lib;

import java.awt.Graphics;

public interface Entity {
	
	public void tick();
	public void render(Graphics g);
	
	public void getX();
	public void getY();

}
