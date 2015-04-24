package astroBiz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ScenarioView {
	public static final int SBWIDTH = 736;
	public static final int SBHEIGHT = 80;
	
	private AstroBiz ab;
	
	private String textBuffer = "";
	
	private BufferedImage employeeSprite;
	private BufferedImage selectSprite;
	private int x = 32;
	private int y = 32;
	
	ScenarioView(AstroBiz astrobiz){
		this.ab = astrobiz;
		this.employeeSprite = ab.getEmployeeSprites().grabImage(1, 1, 128, 128);
		this.selectSprite = ab.getRegionSprites().grabImage(2, 3, 16, 16);
	}
	
	public void tick(){
		switch(AstroBiz.State){
		case SCENARIOVIEW:
			typeText("Please select a Scenario to play.");
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * Simple textType.
	 * Problem is it needs a way to deal with multiple strings without
	 * losing the contents of the buffer.
	 * @param tt	The string to be typed.
	 * @return		The currently completed section the string.
	 */
	private String typeText(String tt){
		if(textBuffer.length() < tt.length()){
			textBuffer += tt.charAt(textBuffer.length());
		}
		return textBuffer;
	}
	
	public void render(Graphics g){
		Font sbfont = new Font("sans", Font.BOLD, 16);
		switch(AstroBiz.State){
		case SCENARIOCONFIRM:
			// TODO: Come up with a way to type text out.
			break;
		case SCENARIOVIEW:
			g.setFont(sbfont);
			g.setColor(Color.white);
			g.drawRect(32, 32, 736, 272);
			
			g.drawString("Scenario 1:  " + ScenarioInformation.scenarioInfoName[0], 64, 64);
			g.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[0] + " - " + ScenarioInformation.scenarioInfoEndingYear[0] + ")", 96, 64+16);
			
			g.drawString("Scenario 2:  " + ScenarioInformation.scenarioInfoName[1], 64, 128);
			g.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[1] + " - " + ScenarioInformation.scenarioInfoEndingYear[1] + ")", 96, 128+16);
			
			g.drawString("Scenario 3:  " + ScenarioInformation.scenarioInfoName[2], 64, 192);
			g.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[2] + " - " + ScenarioInformation.scenarioInfoEndingYear[2] + ")", 96, 192+16);
			
			g.drawString("Scenario 4:  " + ScenarioInformation.scenarioInfoName[3], 64, 256);
			g.drawString("(" + ScenarioInformation.scenarioInfoStartingYear[3] + " - " + ScenarioInformation.scenarioInfoEndingYear[3] + ")", 96, 256+16);
			
			g.drawImage(this.selectSprite, x, y, null);
			g.drawImage(this.employeeSprite, 32, 320, null);
			g.drawString(textBuffer, 160, 384);

			break;
		default:
			break;
		}
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int n){
		this.x = n;
	}
	
	public void setY(int n){
		this.y = n;
	}
}
