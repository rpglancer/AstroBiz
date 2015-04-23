package astroBiz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ScenarioView {
	public static final int SBWIDTH = 736;
	public static final int SBHEIGHT = 80;
	
	private BufferedImage employeeSprite;
	
	private AstroBiz astrobiz;
	
	ScenarioView(AstroBiz ab){
		this.astrobiz = ab;
		this.employeeSprite = astrobiz.getEmployeeSprites().grabImage(1, 1, 128, 128);
	}
	
	public void tick(){		
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		Font sbfont = new Font("sans", Font.BOLD, 16);
		switch(AstroBiz.State){
		case SCENARIOCONFIRM:
			// TODO: Come up with a way to type text out.
			break;
		case SCENARIOVIEW:
			g2d.setFont(sbfont);
			g2d.setColor(Color.white);
			g2d.drawRect(32, 32, 736, 272);
			g2d.drawString("Scenario 1:  " + ScenarioInformation.scenarioInfoName[0], 64, 64);
			g2d.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[0] + " - " + ScenarioInformation.scenarioInfoEndingYear[0] + ")", 96, 64+16);
			
			g2d.drawString("Scenario 2:  " + ScenarioInformation.scenarioInfoName[1], 64, 128);
			g2d.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[1] + " - " + ScenarioInformation.scenarioInfoEndingYear[1] + ")", 96, 128+16);
			
			g2d.drawString("Scenario 3:  " + ScenarioInformation.scenarioInfoName[2], 64, 192);
			g2d.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[2] + " - " + ScenarioInformation.scenarioInfoEndingYear[2] + ")", 96, 192+16);
			
			g2d.drawString("Scenario 4:  " + ScenarioInformation.scenarioInfoName[3], 64, 256);
			g2d.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[3] + " - " + ScenarioInformation.scenarioInfoEndingYear[3] + ")", 96, 256+16);
			
			g2d.drawImage(this.employeeSprite, 32, 320, null);
			g2d.drawString("Please select a Scenario to play.", 160, 384);
			break;
		default:
			break;
		}
	}
}
