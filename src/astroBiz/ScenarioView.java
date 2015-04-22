package astroBiz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ScenarioView {
	public static final int SBWIDTH = 736;
	public static final int SBHEIGHT = 80;
	
	private AstroBiz astrobiz;
	
	ScenarioView(AstroBiz ab){
		this.astrobiz = ab;
	}
	
	public void tick(){
		
	}
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		// Draw the boxes.
		g2d.drawRect(32, 32, SBWIDTH, SBHEIGHT);
		g2d.drawRect(32, 144, SBWIDTH, SBHEIGHT);
		g2d.drawRect(32, 256, SBWIDTH, SBHEIGHT);
		g2d.drawRect(32, 368, SBWIDTH, SBHEIGHT);
		g2d.drawString("Scenario 1", 32, 32 + SBHEIGHT);
		g2d.drawString("Scenario 2", 32, 144 + SBHEIGHT);
		g2d.drawString("Scenario 3", 32, 256 + SBHEIGHT);
		g2d.drawString("Scenario 4", 32, 368 + SBHEIGHT);	
	}

}
