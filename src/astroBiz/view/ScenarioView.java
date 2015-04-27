package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import astroBiz.AstroBiz;
import astroBiz.info.ScenarioInformation;
import astroBiz.util.textUtilities;

public class ScenarioView {
	public static final int SBWIDTH = 672;
	public static final int SBHEIGHT = 40;

	private final int MAINTEXTWIDTH = 672;
	private final int MAINTEXTINDENT = 64;
	
	private astroBiz.AstroBiz ab;
	
	private BufferedImage employeeSprite;
	private BufferedImage selectSprite;
	
	private boolean yesNo = true;
	private int s = 0;			// Scenario Selected
	private int x = 48;			// Cursor X position
	private int y = 48;			// Curson Y position
	
	private Font sbfont = new Font("sans", Font.BOLD, 16);
	private Font sbconf	= new Font("sans", Font.BOLD, 32);
	
	public static enum DIFFICULTYSELECT {
		DIFF1,
		DIFF2,
		DIFF3,
		DIFF4
	}
	
	private DIFFICULTYSELECT scenarioDifficulty = DIFFICULTYSELECT.DIFF1;
	
	public ScenarioView(AstroBiz astrobiz){
		this.ab = astrobiz;
		this.employeeSprite = ab.getEmployeeSprites().grabImage(1, 1, 128, 128);
		this.selectSprite = ab.getRegionSprites().grabImage(2, 3, 16, 16);
	}
	
	public void tick(){
		switch(astroBiz.AstroBiz.State){
		
		case SCENARIOCONFIRM:
			break;
		case SCENARIOVIEW:
			break;
			
		default:
			break;
		}
	}
	
	public void render(Graphics g){
		switch(astroBiz.AstroBiz.State){

		case SCENARIOCONFIRM:
			scenarioConfirm(g);
			break;		// End SCENARIOCONFIRM
			
		case SCENARIOVIEW:
			scenarioView(g);
			break;		// End SCENARIOVIEW
			
		case SETDIFFICULTY:
			scenarioDifficulty(g);
			break;
			
		default:
			break;
		}
	}
	
	private void scenarioDifficulty(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		
		g.drawString("Difficulty Level 1:", 64, 64);
		g.drawString("Difficulty Level 2:", 64, 128);
		g.drawString("Difficulty Level 3:", 64, 192);
		g.drawString("Difficulty Level 4:", 64, 256);
		
		switch(this.scenarioDifficulty){
		case DIFF1:
			g.drawImage(this.selectSprite, 48, 64-16, null);
			break;
		case DIFF2:
			g.drawImage(this.selectSprite, 48, 128-16, null);
			break;
		case DIFF3:
			g.drawImage(this.selectSprite, 48, 192-16, null);
			break;
		case DIFF4:
			g.drawImage(this.selectSprite, 48, 256-16, null);
			break;
		}
		
	//	g.drawImage(this.selectSprite, x, y, null);
		g.drawImage(this.employeeSprite, 32, 320, null);
		g.drawString("Select a difficulty level.", 160, 384);
	}
	
	private void scenarioConfirm(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		textUtilities.drawStringMultiLine(g, sbfont, MAINTEXTINDENT, 64, MAINTEXTWIDTH, ScenarioInformation.scenarioInfoDescription[this.s]);
		g.drawImage(this.employeeSprite, 32, 320, null);
		g.drawString("Is this scenario OK?", 160, 384);
		g.setFont(sbconf);
		if(yesNo){
			g.setColor(Color.red);
			g.fillRect(192, 416, 64, 32);
			g.setColor(Color.white);
			g.drawString("YES", 194, 444);
			g.setColor(Color.blue);
			g.fillRect(192+64, 416, 64, 32);
			g.setColor(Color.gray);
			g.drawString("NO", 192+73, 444);
		}
		else{
			g.setColor(Color.blue);
			g.fillRect(192, 416, 64, 32);
			g.setColor(Color.gray);
			g.drawString("YES", 194, 444);
			g.setColor(Color.red);
			g.fillRect(192+64, 416, 64, 32);
			g.setColor(Color.white);
			g.drawString("NO", 192+73, 444);		
		}
	}
	
	private void scenarioView(Graphics g){
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
		g.drawString("Please select a scenario to play.", 160, 384);
	}
	
	public void cycleDifficultyNext(){
		if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF1)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF2;
		else if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF2)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF3;
		else if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF3)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF4;
		else if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF4)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF1;
	}
	
	public void cycleDifficultyPrev(){
		if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF1)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF4;
		else if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF2)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF1;
		else if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF3)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF2;
		else if(this.scenarioDifficulty == DIFFICULTYSELECT.DIFF4)
			this.scenarioDifficulty = DIFFICULTYSELECT.DIFF3;
	}
	
	public DIFFICULTYSELECT getDifficulty(){
		return this.scenarioDifficulty;
	}
	
	public int getS(){
		return this.s;
	}
	
	public int getX(){
		return this.x;
	}
		
	public int getY(){
		return this.y;
	}
	
	public boolean getYesNo(){
		return this.yesNo;
	}

	public void setDifficulty(){
		switch(this.scenarioDifficulty){
		case DIFF1:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		case DIFF2:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		case DIFF3:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		case DIFF4:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		}
	}
	
	public void setS(int n){
		this.s = n;
	}
	
	public void setX(int n){
		this.x = n;
	}
		
	public void setY(int n){
		this.y = n;
	}
	
	public void setYesNo(boolean b){
		this.yesNo = b;
	}

}
