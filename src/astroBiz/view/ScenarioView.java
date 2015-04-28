package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.Location;
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
	
	private Font sbfont = new Font("sans", Font.BOLD, 16);
	private Font sbconf	= new Font("sans", Font.BOLD, 32);
	
	public static enum REGIONSELECT{
		R1,
		R2,
		R3,
		R4,
		R5,
		R6,
		R7,
		R8,
		R9
	}
	
	public static enum SCENARIOSELECT{
		S1,
		S2,
		S3,
		S4
	}
	
	public static enum DIFFICULTYSELECT {
		DIFF1,
		DIFF2,
		DIFF3,
		DIFF4
	}
	
	public static enum PLAYERSELECT{
		P1,
		P2,
		P3,
		P4
	}

	public static enum HQPLACEMENTVIEW{
		WORLD,
		REGION
	}
	
	public static enum SCENARIOVIEWMODE{
		VM_SCEN_CONFIRM,
		VM_SCEN_SELECT,
		VM_DIFF_SELECT,
		VM_PLYR_SELECT,
		VM_SET_HQ
	}
	
	private BufferedImage region;
	
	private DIFFICULTYSELECT scenarioDifficulty = DIFFICULTYSELECT.DIFF1;
	private HQPLACEMENTVIEW hqPlacementView = HQPLACEMENTVIEW.WORLD;
	private PLAYERSELECT scenarioPlayers = PLAYERSELECT.P1;
	private REGIONSELECT hqPlacementRegion = REGIONSELECT.R1;
	private SCENARIOSELECT scenarioSelect = SCENARIOSELECT.S1;
	private SCENARIOVIEWMODE scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_SELECT;
	
	private Vector<Location> availableHqLocations;
	
	private int availableHqLocationNumber = -1;		// Contains the Vector index number for the currently selected Location or -1 for an empty Vector.
	
	private int scenarioPlayerConfigure = 0;		// Contains the number of the player currently being configured.
	private int scenarioPlayersToConfigure = 0;		// Contains the total number of players needing to be configured.
	
	public ScenarioView(AstroBiz astrobiz){
		this.ab = astrobiz;
		this.employeeSprite = ab.getEmployeeSprites().grabImage(1, 1, 128, 128);
		this.selectSprite = ab.getRegionSprites().grabImage(2, 3, 16, 16);
	}
	
	public void tick(){
		switch(this.scenarioViewMode){
		case VM_DIFF_SELECT:
			break;
		case VM_PLYR_SELECT:
			break;
		case VM_SCEN_CONFIRM:
			break;
		case VM_SCEN_SELECT:
			break;
		default:
			break;
		}
	}
	
	public void render(Graphics g){
		switch(this.scenarioViewMode){

		case VM_SCEN_CONFIRM:
			scenarioConfirm(g);
			break;

		case VM_SCEN_SELECT:
			scenarioView(g);
			break;
			
		case VM_DIFF_SELECT:
			scenarioDifficulty(g);
			break;
			
		case VM_PLYR_SELECT:
			scenarioPlayers(g);
			break;
			
		case VM_SET_HQ:
			scenarioSetHQ(g);
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
		
		g.drawImage(this.employeeSprite, 32, 320, null);
		g.drawString("Select a difficulty level.", 160, 384);
	}
	
	private void scenarioConfirm(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		textUtilities.drawStringMultiLine(g, sbfont, MAINTEXTINDENT, 64, MAINTEXTWIDTH, ab.getScenario().getScenarioDescription());
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
	
	private void scenarioPlayers(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		
		g.drawString("1 Player", 64, 64);
		g.drawString("2 Player", 64, 128);
		g.drawString("3 Player", 64, 192);
		g.drawString("4 Player", 64, 256);
		
		switch(this.scenarioPlayers){
		case P1:
			g.drawImage(this.selectSprite, 48, 64-16, null);
			break;
		case P2:
			g.drawImage(this.selectSprite, 48, 128-16, null);
			break;
		case P3:
			g.drawImage(this.selectSprite, 48, 192-16, null);
			break;
		case P4:
			g.drawImage(this.selectSprite, 48, 256-16, null);
			break;
		}
		
		g.drawImage(this.employeeSprite, 32, 320, null);
		g.drawString("Choose the number of players.", 160, 384);
	}
	
	private void scenarioSetHQ(Graphics g){

		String s = "";
		if(this.scenarioPlayerConfigure == 1)
			s = "Player 1";
		if(this.scenarioPlayerConfigure == 2)
			s = "Player 2";
		if(this.scenarioPlayerConfigure == 3)
			s = "Player 3";
		if(this.scenarioPlayerConfigure == 4)
			s = "Player 4";
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		switch(this.hqPlacementView){
		case REGION:
			g2d.drawImage(region, 32, 32, null);

			for(int i = 0; i < this.availableHqLocations.size(); i++){
				g2d.drawImage(this.availableHqLocations.elementAt(i).getSprite(), 
							this.availableHqLocations.elementAt(i).getLocationX(), 
							this.availableHqLocations.elementAt(i).getLocationY(), 
							null);
			}
			
			g2d.setColor(Color.green);
			
			if(this.availableHqLocationNumber > -1){
				g2d.drawString(this.availableHqLocations.elementAt(this.availableHqLocationNumber).getLocationName(), 
						this.availableHqLocations.elementAt(this.availableHqLocationNumber).getLocationX() + 16, 
						this.availableHqLocations.elementAt(this.availableHqLocationNumber).getLocationY() + 16);
		
				g2d.drawOval(this.availableHqLocations.elementAt(this.availableHqLocationNumber).getLocationX(),
						this.availableHqLocations.elementAt(this.availableHqLocationNumber).getLocationY(),
						16,
						16);
			}
			
			g2d.setFont(sbfont);
			g2d.setColor(Color.white);
			g2d.drawImage(this.employeeSprite, 32, 320, null);
			g2d.drawString("Select headquarters location for " + s, 160, 384);		
			
			break;	//	End	REGION
			
		case WORLD:
			if(this.hqPlacementRegion == REGIONSELECT.R1){
				g2d.setColor(Color.green);
				g2d.drawString("Mercury", 96+15, 177+15);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(96, 177, 15, 15);		// Mercury
			
			if(this.hqPlacementRegion == REGIONSELECT.R2){
				g2d.setColor(Color.green);
				g2d.drawString("Venus", 160+32, 160+32);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(160, 160, 32, 32);		// Venus
			
			if(this.hqPlacementRegion == REGIONSELECT.R3){
				g2d.setColor(Color.green);
				g2d.drawString("Earth", 224+32, 64+32);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(224, 64, 32, 32);		// Earth
			
			if(this.hqPlacementRegion == REGIONSELECT.R4){
				g2d.setColor(Color.green);
				g2d.drawString("Luna", 256+10, 54+10);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(256, 54, 10, 10);		// Luna
			
			if(this.hqPlacementRegion == REGIONSELECT.R5){
				g2d.setColor(Color.green);
				g2d.drawString("Mars", 288+24, 160+24);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(288, 160, 24, 24);		// Mars
			
			if(this.hqPlacementRegion == REGIONSELECT.R6){
				g2d.setColor(Color.green);
				g2d.drawString("Jupiter", 352+96, 192+96);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(352, 192, 96, 96);		// Jupiter
			
			if(this.hqPlacementRegion == REGIONSELECT.R7){
				g2d.setColor(Color.green);
				g2d.drawString("Saturn", 480+64, 128+64);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(480, 128, 64, 64);		// Saturn
			
			if(this.hqPlacementRegion == REGIONSELECT.R8){
				g2d.setColor(Color.green);
				g2d.drawString("Uranus", 576+64, 192+64);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(576, 192, 64, 64);		// Uranus
			
			if(this.hqPlacementRegion == REGIONSELECT.R9){
				g2d.setColor(Color.green);
				g2d.drawString("Neptune", 676+64, 96+64);
			}
			else{
				g2d.setColor(Color.white);
			}
			g2d.fillOval(676, 96, 64, 64);		// Neptune
			
			g2d.setFont(sbfont);
			g2d.setColor(Color.white);
			g2d.drawImage(this.employeeSprite, 32, 320, null);
			g2d.drawString("Select headquarters region for " + s, 160, 384);		
			break;	//	End	WORLD
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
		
		switch(this.scenarioSelect){
		case S1:
			g.drawImage(this.selectSprite, 48, 64-16, null);
			break;
		case S2:
			g.drawImage(this.selectSprite, 48, 128-16, null);
			break;
		case S3:
			g.drawImage(this.selectSprite, 48, 192-16, null);
			break;
		case S4:
			g.drawImage(this.selectSprite, 48, 256-16, null);
			break;
		}
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

	public void cycleLocationNext(){
		if(this.availableHqLocationNumber == -1)
			return;
		if(this.availableHqLocationNumber < this.availableHqLocations.size() - 1)
			this.availableHqLocationNumber++;
		else
			this.availableHqLocationNumber = 1;
	}
	
	public void cycleLocationPrev(){
		if(this.availableHqLocationNumber == -1)
			return;
		if(this.availableHqLocationNumber > 0)
			this.availableHqLocationNumber--;
		else
			this.availableHqLocationNumber = this.availableHqLocations.size() - 1;
	}
	
	public void cyclePlayerNext(){
		if(this.scenarioPlayers == PLAYERSELECT.P1)
			this.scenarioPlayers = PLAYERSELECT.P2;
		else if(this.scenarioPlayers == PLAYERSELECT.P2)
			this.scenarioPlayers = PLAYERSELECT.P3;
		else if(this.scenarioPlayers == PLAYERSELECT.P3)
			this.scenarioPlayers = PLAYERSELECT.P4;
		else if(this.scenarioPlayers == PLAYERSELECT.P4)
			this.scenarioPlayers = PLAYERSELECT.P1;
	}
	
	public void cyclePlayerPrev(){
		if(this.scenarioPlayers == PLAYERSELECT.P1)
			this.scenarioPlayers = PLAYERSELECT.P4;
		else if(this.scenarioPlayers == PLAYERSELECT.P2)
			this.scenarioPlayers = PLAYERSELECT.P1;
		else if(this.scenarioPlayers == PLAYERSELECT.P3)
			this.scenarioPlayers = PLAYERSELECT.P2;
		else if(this.scenarioPlayers == PLAYERSELECT.P4)
			this.scenarioPlayers = PLAYERSELECT.P3;
	}
	
	public void cycleRegionNext(){
		if(this.hqPlacementRegion == REGIONSELECT.R1)
			this.hqPlacementRegion = REGIONSELECT.R2;
		else if(this.hqPlacementRegion == REGIONSELECT.R2)
			this.hqPlacementRegion = REGIONSELECT.R3;
		else if(this.hqPlacementRegion == REGIONSELECT.R3)
			this.hqPlacementRegion = REGIONSELECT.R4;
		else if(this.hqPlacementRegion == REGIONSELECT.R4)
			this.hqPlacementRegion = REGIONSELECT.R5;
		else if(this.hqPlacementRegion == REGIONSELECT.R5)
			this.hqPlacementRegion = REGIONSELECT.R6;
		else if(this.hqPlacementRegion == REGIONSELECT.R6)
			this.hqPlacementRegion = REGIONSELECT.R7;
		else if(this.hqPlacementRegion == REGIONSELECT.R7)
			this.hqPlacementRegion = REGIONSELECT.R8;
		else if(this.hqPlacementRegion == REGIONSELECT.R8)
			this.hqPlacementRegion = REGIONSELECT.R9;
		else if(this.hqPlacementRegion == REGIONSELECT.R9)
			this.hqPlacementRegion = REGIONSELECT.R1;
	}
	
	public void cycleRegionPrev(){
		if(this.hqPlacementRegion == REGIONSELECT.R1)
			this.hqPlacementRegion = REGIONSELECT.R9;
		else if(this.hqPlacementRegion == REGIONSELECT.R2)
			this.hqPlacementRegion = REGIONSELECT.R1;
		else if(this.hqPlacementRegion == REGIONSELECT.R3)
			this.hqPlacementRegion = REGIONSELECT.R2;
		else if(this.hqPlacementRegion == REGIONSELECT.R4)
			this.hqPlacementRegion = REGIONSELECT.R3;
		else if(this.hqPlacementRegion == REGIONSELECT.R5)
			this.hqPlacementRegion = REGIONSELECT.R4;
		else if(this.hqPlacementRegion == REGIONSELECT.R6)
			this.hqPlacementRegion = REGIONSELECT.R5;
		else if(this.hqPlacementRegion == REGIONSELECT.R7)
			this.hqPlacementRegion = REGIONSELECT.R6;
		else if(this.hqPlacementRegion == REGIONSELECT.R8)
			this.hqPlacementRegion = REGIONSELECT.R7;
		else if(this.hqPlacementRegion == REGIONSELECT.R9)
			this.hqPlacementRegion = REGIONSELECT.R8;
	}
	
	public void cycleScenarioNext(){
		if(this.scenarioSelect == SCENARIOSELECT.S1)
			this.scenarioSelect = SCENARIOSELECT.S2;
		else if(this.scenarioSelect == SCENARIOSELECT.S2)
			this.scenarioSelect = SCENARIOSELECT.S3;
		else if(this.scenarioSelect == SCENARIOSELECT.S3)
			this.scenarioSelect = SCENARIOSELECT.S4;
		else if(this.scenarioSelect == SCENARIOSELECT.S4)
			this.scenarioSelect = SCENARIOSELECT.S1;
	}
	
	public void cycleScenarioPrev(){
		if(this.scenarioSelect == SCENARIOSELECT.S1)
			this.scenarioSelect = SCENARIOSELECT.S4;
		else if(this.scenarioSelect == SCENARIOSELECT.S2)
			this.scenarioSelect = SCENARIOSELECT.S1;
		else if(this.scenarioSelect == SCENARIOSELECT.S3)
			this.scenarioSelect = SCENARIOSELECT.S2;
		else if(this.scenarioSelect == SCENARIOSELECT.S4)
			this.scenarioSelect = SCENARIOSELECT.S3;
	}
	
	public DIFFICULTYSELECT getDifficulty(){
		return this.scenarioDifficulty;
	}

	public int getHqLocationCount(){
		return this.availableHqLocationNumber;
	}
	
	public Location getHqSelectedLocation(){
		return this.availableHqLocations.elementAt(this.availableHqLocationNumber);
	}
	
	public HQPLACEMENTVIEW getHqPlacementView(){
		return this.hqPlacementView;
	}
	
	/**
	 * Method for returning the total number of players that need to be configured by a human being.
	 * @return	The total number of players that need to be configured by a human being.
	 */
	public int getPlayersToConfigure(){
		return this.scenarioPlayersToConfigure;
	}
	
	public boolean getYesNo(){
		return this.yesNo;
	}
	
	/**
	 * Method for returning the numeric identifier of the current player being configured.
	 * @return The number of the player being actively configured.
	 */
	public int getScenarioPlayerConfigure(){
		return this.scenarioPlayerConfigure;
	}
	
	public SCENARIOVIEWMODE getViewMode(){
		return this.scenarioViewMode;
	}
	
	public void loadLocationVector(){
		this.availableHqLocationNumber = -1;
		if(this.availableHqLocations != null)
			this.availableHqLocations = null;
		this.availableHqLocations = new Vector<Location>();
		for(int i = 0; i < ab.getRegion().getLocationVector().size(); i++){
			switch(this.hqPlacementRegion){
			case R1:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 0 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R2:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 1 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R3:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 2 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R4:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 3 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R5:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 4 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R6:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 5 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R7:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 6 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R8:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 7 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			case R9:
				if(ab.getRegion().getLocationVector().elementAt(i).getLocationRegion() == 8 && !ab.getRegion().getLocationVector().elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(ab.getRegion().getLocationVector().elementAt(i));
				break;
			}
		}
		if(this.availableHqLocations.size() > 0)
			this.availableHqLocationNumber = 0;
		else
			this.availableHqLocationNumber = -1;
	}

	public void loadRegionMap(){
		switch(this.hqPlacementRegion){
		case R1:
			region = ab.getWorldMap().grabImage(1, 1, 736, 288);
			break;
		case R2:
			region = ab.getWorldMap().grabImage(2, 1, 736, 288);
			break;
		case R3:
			region = ab.getWorldMap().grabImage(3, 1, 736, 288);
			break;
		case R4:
			region = ab.getWorldMap().grabImage(1, 2, 736, 288);
			break;
		case R5:
			region = ab.getWorldMap().grabImage(2, 2, 736, 288);
			break;
		case R6:
			region = ab.getWorldMap().grabImage(3, 2, 736, 288);
			break;
		case R7:
			region = ab.getWorldMap().grabImage(1, 3, 736, 288);
			break;
		case R8:
			region = ab.getWorldMap().grabImage(2, 3, 736, 288);
			break;
		case R9:
			region = ab.getWorldMap().grabImage(3, 3, 736, 288);
			break;		
		}
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
	
	public void setHqPlacementView(HQPLACEMENTVIEW vm){
		this.hqPlacementView = vm;
	}
	
	public void setPlayers(){
		switch(this.scenarioPlayers){
		case P1:
			ab.getScenario().setScenarioPlayers(1);
			this.scenarioPlayersToConfigure = 1;
			this.scenarioPlayerConfigure = 1;
			break;
		case P2:
			ab.getScenario().setScenarioPlayers(2);
			this.scenarioPlayersToConfigure = 2;
			this.scenarioPlayerConfigure = 1;
			break;
		case P3:
			ab.getScenario().setScenarioPlayers(3);
			this.scenarioPlayersToConfigure = 3;
			this.scenarioPlayerConfigure = 1;
			break;
		case P4:
			ab.getScenario().setScenarioPlayers(4);
			this.scenarioPlayersToConfigure = 4;
			this.scenarioPlayerConfigure = 1;
			break;
		}
	}
	
	public void setScenario(){
		switch(this.scenarioSelect){
		case S1:
			ab.getScenario().loadScenario(0);
			break;
		case S2:
			ab.getScenario().loadScenario(1);
			break;
		case S3:
			ab.getScenario().loadScenario(2);
			break;
		case S4:
			ab.getScenario().loadScenario(3);
			break;
		}
	}

	public void setScenarioPlayerConfigure(int p){
		this.scenarioPlayerConfigure = p;
	}
	
	public void setViewMode(SCENARIOVIEWMODE vm){
		this.scenarioViewMode = vm;
	}
	
	public void setYesNo(boolean b){
		this.yesNo = b;
	}

}
