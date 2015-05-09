package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.AstroBiz.STATE;
import astroBiz.info.ENTITY_TYPE;
import astroBiz.info.FontInformation;
import astroBiz.info.ScenarioInformation.SI;
import astroBiz.lib.AI;
import astroBiz.lib.Location;
import astroBiz.lib.Location.LOCATIONTYPE;
import astroBiz.lib.Scenario;
import astroBiz.lib.SpriteSheet;
import astroBiz.lib.TextWindow;
import astroBiz.util.Confirmation;
import astroBiz.util.textUtilities;

/**
 * Contains most everything involved in selecting a Scenario for play and configuring it for use.
 * @author Matt Bangert
 *
 */
public class ScenarioView implements Manager{
	public static enum BUSICONFIGOPTIONS {
		NAME,
		COLOR,
		EXIT,
		SELECT
	}
	public static enum HQPLACEMENTVIEW {
		WORLD,
		REGION
	}
	/**
	 * Enumerator for all the available view modes.
	 * What is rendered on screen is dependent upon the
	 * selected view mode.
	 * @author Matt Bangert
	 *
	 */
	public static enum SCENARIOVIEWMODE implements VM{
		VM_BUSI_NAME			(0),
		VM_BUSI_NAME_SELECT		(3),	
		VM_BUSI_COLOR			(3),
		VM_BUSI_COLOR_SELECT	(2),
		VM_BUSI_CONFIG			(2),
		VM_SCEN_CONFIRM			(0),
		VM_SCEN_SELECT			(3),
		VM_DIFF_SELECT			(3),
		VM_PLYR_SELECT			(3),
		VM_SET_HQ				(0);
		
		private int maxopt;
		
		SCENARIOVIEWMODE(int opt){
			this.maxopt = opt;
		}

		@Override
		public int getOpt() {
			return this.maxopt;
		}
	}

	
	private astroBiz.AstroBiz ab;
	private BufferedImage employeeSprite;
	private BufferedImage region;
	private BufferedImage selectSprite;
//	private Confirmation c = new Confirmation();
	private Confirmation c = new Confirmation();
	private boolean yesNo = true;
	private boolean isActive = false;
	private Font sbfont = new Font("sans", Font.BOLD, 16);
	private Font sbconf	= new Font("sans", Font.BOLD, 32);
	private int businessSelect = 0;
	private int optionSelect = 0;
	private int previousOption = optionSelect;
	private int scenarioPlayerConfigure = 0;		// The number of the player being configured.
	private int scenarioPlayersToConfigure = 0;		// The total number of players to be configured.
	/**
	 * Buffer for editing business names.
	 */
	private String businessNameBuffer = null;
	
	private TextWindow textWin;

	private BUSICONFIGOPTIONS busiConfigOptions = BUSICONFIGOPTIONS.EXIT;			//	Active business configuration option
	private ENTITY_TYPE type = ENTITY_TYPE.VIEW_MANAGER;
	private HQPLACEMENTVIEW hqPlacementView = HQPLACEMENTVIEW.WORLD;				//	Active placement view for HQ selection
	private SCENARIOVIEWMODE scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_SELECT;	//	Active view mode for scenario selection and setup
	private Vector<Location> availableHqLocations;									//	Locations available to be selected as a HQ.

	public ScenarioView(AstroBiz astrobiz){
		this.ab = astrobiz;
		this.employeeSprite = AstroBiz.employeeSprites.grabImage(1, 1, 128, 128);
		this.selectSprite = AstroBiz.regionSprites.grabImage(2, 3, 16, 16);
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
/**
 * Draws everything related to ScenarioView on screen depending upon the active {@link #scenarioViewMode}.
 * @param g (Graphics) The active graphics buffer
 */
	public void render(Graphics g){
		int x = 0;
		int y = 0;
		g.setColor(Color.white);
		textUtilities.drawStringMultiLine(g, FontInformation.debug, x, y, 800, scenarioViewMode.toString());
		switch(this.scenarioViewMode){
		case VM_BUSI_COLOR:
			scenarioBusiConfig(g);
			break;
			
		case VM_BUSI_COLOR_SELECT:
			scenarioBusiConfig(g);
			break;
			
		case VM_BUSI_CONFIG:
			scenarioBusiConfig(g);
			break;
			
		case VM_BUSI_NAME:
			scenarioBusiConfig(g);
			break;
			
		case VM_BUSI_NAME_SELECT:
			scenarioBusiConfig(g);
			break;

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
		if(c.getIsActive()){
			c.render(g);
		}
	}
	/**
	 * Method for drawing all things related to business configuration based upon the selected view mode.
	 * @param g	The graphics buffer to be drawn to.
	 */
	private void scenarioBusiConfig(Graphics g){


		int x = 800 / 2 - 160;
		int y = 64;
		int width = 320;
		int height = 32;
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR){
			g.setColor(Color.darkGray);
			g.fillRoundRect(x-8, y-8, width+16, height * 4 + 3 * 5 + 16, 16, 16);
			g.setColor(Color.lightGray);
			g.fillRoundRect(x-4, y-4, width+8, height * 4 + 3 * 5 + 8, 8, 8);
			if(optionSelect == 0) g.drawImage(selectSprite, x-24, 72, null);
			else if(optionSelect == 1) g.drawImage(selectSprite, x-24, 110, null);
			else if(optionSelect == 2) g.drawImage(selectSprite, x-24, 148, null);
			else if(optionSelect == 3) g.drawImage(selectSprite, x-24, 186, null);
			for(int i = 0; i < ab.getScenario().getBusinesses().size(); i++){
				g.setColor(ab.getScenario().getBusinesses().elementAt(i).getColor());
				g.fillRoundRect(x, y, width, height, 8, 8);
				g.setColor(Color.black);
				g.drawRoundRect(x+1, y+1, width-1, height-1, 8, 8);
				g.setColor(Color.white);
				textUtilities.drawStringCenterV(g, FontInformation.modelheader, x + 8, y, 32, ab.getScenario().getBusinesses().elementAt(i).getName());
		//		textUtilities.drawString(g, x, y+8, ab.getScenario().getBusinesses().elementAt(i).getName(), Color.white);
				y += height + 5;
			}
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Change color for which company?", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}	
		}
		else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT){
			g.setColor(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor());
			g.fillRoundRect(x, y, width, height, 8, 8);
			g.setColor(Color.white);
			FontMetrics fm = g.getFontMetrics(FontInformation.chitchat);
			if(optionSelect == 0)g.setColor(Color.red);
			int strlen = fm.stringWidth("R: 255");
			textUtilities.drawStringMultiLine(g, FontInformation.chitchat, 400 - (strlen/2), 96, 400, "R: " + ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed());
			g.setColor(Color.white);
			if(optionSelect == 1)g.setColor(Color.green);
			textUtilities.drawStringMultiLine(g, FontInformation.chitchat, 400 - (strlen/2), 96+32, 400, "G: " + ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen());
			g.setColor(Color.white);
			if(optionSelect == 2)g.setColor(Color.blue);
			textUtilities.drawStringMultiLine(g, FontInformation.chitchat, 400 - (strlen/2), 96+64, 400, "B: " + ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue());
			g.setColor(Color.white);
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Choose a color for this company", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}	
			
		}
		else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_CONFIG){

			g.setColor(Color.darkGray);
			g.fillRoundRect(x-8, y-8, width+16, height * 4 + 3 * 5 + 16, 16, 16);
			g.setColor(Color.lightGray);
			g.fillRoundRect(x-4, y-4, width+8, height * 4 + 3 * 5 + 8, 8, 8);
			for(int i = 0; i < ab.getScenario().getBusinesses().size(); i++){
				g.setColor(ab.getScenario().getBusinesses().elementAt(i).getColor());
				g.fillRoundRect(x, y, width, height, 8, 8);
				g.setColor(Color.black);
				g.drawRoundRect(x+1, y+1, width-1, height-1, 8, 8);
				g.setColor(Color.white);
				textUtilities.drawStringCenterV(g, FontInformation.modelheader, x + 8, y, 32, ab.getScenario().getBusinesses().elementAt(i).getName());
				y += height + 5;
			}
			if(busiConfigOptions == BUSICONFIGOPTIONS.COLOR){
				FontMetrics fm = g.getFontMetrics(FontInformation.modelheader);
				int strlen = fm.stringWidth("COLOR");
				g.setColor(Color.green);
				textUtilities.drawStringMultiLine(g, FontInformation.modelheader, 400 - (strlen / 2), 300, strlen, "COLOR");
				
			}
			if(busiConfigOptions == BUSICONFIGOPTIONS.EXIT){
				FontMetrics fm = g.getFontMetrics(FontInformation.modelheader);
				int strlen = fm.stringWidth("EXIT");
				g.setColor(Color.green);
				textUtilities.drawStringMultiLine(g, FontInformation.modelheader, 400 - (strlen / 2), 300, strlen, "EXIT");
				
			}
			if(busiConfigOptions == BUSICONFIGOPTIONS.NAME){
				FontMetrics fm = g.getFontMetrics(FontInformation.modelheader);
				int strlen = fm.stringWidth("NAME");
				g.setColor(Color.green);
				textUtilities.drawStringMultiLine(g, FontInformation.modelheader, 400 - (strlen / 2), 300, strlen, "NAME");
				
			}
			g.setColor(Color.white);
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Customize each company's name and color?", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}	
		}
		else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME){
			g.setColor(Color.darkGray);
			g.fillRoundRect(x-8, y-8, width+16, height * 4 + 3 * 5 + 16, 16, 16);
			g.setColor(Color.lightGray);
			g.fillRoundRect(x-4, y-4, width+8, height * 4 + 3 * 5 + 8, 8, 8);
			if(businessSelect == 0) g.drawImage(selectSprite, x-24, 72, null);
			else if(businessSelect == 1) g.drawImage(selectSprite, x-24, 110, null);
			else if(businessSelect == 2) g.drawImage(selectSprite, x-24, 148, null);
			else if(businessSelect == 3) g.drawImage(selectSprite, x-24, 186, null);
			for(int i = 0; i < ab.getScenario().getBusinesses().size(); i++){
				g.setColor(ab.getScenario().getBusinesses().elementAt(i).getColor());
				g.fillRoundRect(x, y, width, height, 8, 8);
				g.setColor(Color.black);
				g.drawRoundRect(x+1, y+1, width-1, height-1, 8, 8);
				g.setColor(Color.white);
				textUtilities.drawStringCenterV(g, FontInformation.modelheader, x + 8, y, 32, ab.getScenario().getBusinesses().elementAt(i).getName());
				y += height + 5;
			}
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Change the name of which company?", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}	
		}
		else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT){
			FontMetrics fm = g.getFontMetrics(FontInformation.modelheader);
			int strlen = fm.stringWidth(businessNameBuffer);
			textUtilities.drawStringMultiLine(g, FontInformation.modelheader, 400 - (strlen/2), 128, 480, businessNameBuffer);
//			textUtilities.drawString(g, 400 - 10 * 16, 128, businessNameBuffer);
			g.setColor(Color.white);
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Enter a new name for this company.", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}		
		}
	}
	/**
	 * Method for drawing all things related to difficulty selection.
	 * @param g	The graphics buffer to be drawn to.
	 */
	private void scenarioDifficulty(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		
		g.drawString("Difficulty Level 1:", 64, 64);
		g.drawString("Difficulty Level 2:", 64, 128);
		g.drawString("Difficulty Level 3:", 64, 192);
		g.drawString("Difficulty Level 4:", 64, 256);
		
		switch(this.optionSelect){
		case 0:
			g.drawImage(this.selectSprite, 48, 64-16, null);
			break;
		case 1:
			g.drawImage(this.selectSprite, 48, 128-16, null);
			break;
		case 2:
			g.drawImage(this.selectSprite, 48, 192-16, null);
			break;
		case 3:
			g.drawImage(this.selectSprite, 48, 256-16, null);
			break;
		}
		
		g.drawImage(this.employeeSprite, 32, 320, null);
		g.drawString("Select a difficulty level.", 160, 384);
	}
	/**
	 * A method for drawing a player query for confirmation.
	 * @param g The graphics buffer to be drawn to.
	 */
	private void scenarioConfirm(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		textUtilities.drawStringMultiLine(g, FontInformation.briefing, 32, 32, 736, 5, ab.getScenario().getScenarioDescription());
		if(!c.getIsActive()){
			c.setConfirmVM(this, SCENARIOVIEWMODE.VM_SCEN_SELECT, SCENARIOVIEWMODE.VM_PLYR_SELECT, employeeSprite, "Is this scenario OK?");
			c.setActive(true);
			AstroBiz.getController().addEntity(c);
		}
		g.setFont(sbconf);
	}
	/**
	 * Method for drawing all things related to player count selection.
	 * @param g	The graphics buffer to be drawn to.
	 */
	private void scenarioPlayers(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		
		g.drawString("1 Player", 64, 64);
		g.drawString("2 Player", 64, 128);
		g.drawString("3 Player", 64, 192);
		g.drawString("4 Player", 64, 256);
		
		switch(this.optionSelect){
		case 0:
			g.drawImage(this.selectSprite, 48, 64-16, null);
			break;
		case 1:
			g.drawImage(this.selectSprite, 48, 128-16, null);
			break;
		case 2:
			g.drawImage(this.selectSprite, 48, 192-16, null);
			break;
		case 3:
			g.drawImage(this.selectSprite, 48, 256-16, null);
			break;
		}
		
		if(!AstroBiz.getController().containsEntity(textWin)){
			textWin = new TextWindow("Select the number of players.", this.employeeSprite);
			AstroBiz.getController().addEntity(textWin);
		}
	}
	/**
	 * Method for drawing all things related to player headquarters location selection.
	 * @param g The graphics buffer to be drawn to.
	 */
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
		
		g.setColor(Color.white);
		switch(this.hqPlacementView){
		case REGION:
			g.drawImage(region, 32, 32, null);
			for(int i = 0; i < this.availableHqLocations.size(); i++){
				g.drawImage(this.availableHqLocations.elementAt(i).getSprite(ab.getScenario()), 
							this.availableHqLocations.elementAt(i).getLocationX(), 
							this.availableHqLocations.elementAt(i).getLocationY(), 
							null);
			}
			
			g.setColor(Color.green);
			
			if(this.availableHqLocations.size() > 0){
				g.drawString(this.availableHqLocations.elementAt(optionSelect).getLocationName(),
							this.availableHqLocations.elementAt(optionSelect).getLocationX() + 16,
							this.availableHqLocations.elementAt(optionSelect).getLocationY() + 16);
				
				g.drawOval(this.availableHqLocations.elementAt(optionSelect).getLocationX(),
							this.availableHqLocations.elementAt(optionSelect).getLocationY(),
							16, 16);
			}
			
			g.setFont(sbfont);
			g.setColor(Color.white);
			
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Select headquarters location for " + s + ".", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}	
			
			break;	//	End	REGION
			
		case WORLD:
			if(this.optionSelect == 0){
				g.setColor(Color.green);
				g.drawString("Mercury", 96+15, 177+15);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(96, 177, 15, 15);		// Mercury
			
			if(this.optionSelect == 1){
				g.setColor(Color.green);
				g.drawString("Venus", 160+32, 160+32);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(160, 160, 32, 32);		// Venus
			
			if(this.optionSelect == 2){
				g.setColor(Color.green);
				g.drawString("Earth", 224+32, 64+32);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(224, 64, 32, 32);		// Earth
			
			if(this.optionSelect == 3){
				g.setColor(Color.green);
				g.drawString("Luna", 256+10, 54+10);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(256, 54, 10, 10);		// Luna
			
			if(this.optionSelect == 4){
				g.setColor(Color.green);
				g.drawString("Mars", 288+24, 160+24);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(288, 160, 24, 24);		// Mars
			
			if(this.optionSelect == 5){
				g.setColor(Color.green);
				g.drawString("Jupiter", 352+96, 192+96);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(352, 192, 96, 96);		// Jupiter
			
			if(this.optionSelect == 6){
				g.setColor(Color.green);
				g.drawString("Saturn", 480+64, 128+64);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(480, 128, 64, 64);		// Saturn
			
			if(this.optionSelect == 7){
				g.setColor(Color.green);
				g.drawString("Uranus", 576+64, 192+64);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(576, 192, 64, 64);		// Uranus
			
			if(this.optionSelect == 8){
				g.setColor(Color.green);
				g.drawString("Neptune", 676+64, 96+64);
			}
			else{
				g.setColor(Color.white);
			}
			g.fillOval(676, 96, 64, 64);		// Neptune
			
			g.setFont(sbfont);
			g.setColor(Color.white);
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("Select headquarters region for " + s + ".", this.employeeSprite);
				AstroBiz.getController().addEntity(textWin);
			}	
			break;	//	End	WORLD
		}
	}
	/**
	 * Method for drawing all things related to Scenario selection.
	 * @param g The graphics buffer to be drawn to.
	 */
	private void scenarioView(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		
		int nameX = 64; int nameY = 48;
		int yearX = 96; int yearY = 64;
		for(SI si : SI.values()){
			textUtilities.drawStringCenterV(g, FontInformation.modelheader, nameX, nameY, 16, si.getName());
			textUtilities.drawStringCenterV(g, FontInformation.modelstat, yearX, yearY, 16, "("+si.getYearStart() + " - " + si.getYearEnd() +")");
			nameY += 64; yearY += 64;
		}
		
		switch(this.optionSelect){
		case 0:
			g.drawImage(this.selectSprite, 48, 64-16, null);
			break;
		case 1:
			g.drawImage(this.selectSprite, 48, 128-16, null);
			break;
		case 2:
			g.drawImage(this.selectSprite, 48, 192-16, null);
			break;
		case 3:
			g.drawImage(this.selectSprite, 48, 256-16, null);
			break;
		}
		if(!AstroBiz.getController().containsEntity(textWin)){
			textWin = new TextWindow("Please select a scenario to play.", this.employeeSprite);
			AstroBiz.getController().addEntity(textWin);
		}
	}

@Deprecated	
	private void cycleBcoNext(){
		if(busiConfigOptions == BUSICONFIGOPTIONS.NAME)
			busiConfigOptions = BUSICONFIGOPTIONS.EXIT;
		else if(busiConfigOptions == BUSICONFIGOPTIONS.COLOR)
			busiConfigOptions = BUSICONFIGOPTIONS.NAME;
		else if(busiConfigOptions == BUSICONFIGOPTIONS.EXIT)
			busiConfigOptions = BUSICONFIGOPTIONS.COLOR;
	}
@Deprecated	
	private void cycleBcoPrev(){
		if(busiConfigOptions == BUSICONFIGOPTIONS.NAME)
			busiConfigOptions = BUSICONFIGOPTIONS.COLOR;
		else if(busiConfigOptions == BUSICONFIGOPTIONS.COLOR)
			busiConfigOptions = BUSICONFIGOPTIONS.EXIT;
		else if(busiConfigOptions == BUSICONFIGOPTIONS.EXIT)
			busiConfigOptions = BUSICONFIGOPTIONS.NAME;
	}
	
	private void cycleOptionNext(){
		int maxOption = scenarioViewMode.getOpt();
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ){
			if(hqPlacementView == HQPLACEMENTVIEW.WORLD) maxOption = 8;
			if(hqPlacementView == HQPLACEMENTVIEW.REGION) maxOption = this.availableHqLocations.size() - 1;
		}
		if(maxOption < 0) return;
		if(optionSelect < maxOption)optionSelect++;
	}
	
	private void cycleOptionPrev(){
		if(optionSelect < 0) optionSelect = 0;
		if(optionSelect > 0) optionSelect--;
	}
		
	private Location getHqSelectedLocation(){
		return this.availableHqLocations.elementAt(this.optionSelect);
	}
	
	private void increaseColor(){
		int r = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed();
		int g = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen();
		int b = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue();
		if(optionSelect == 0){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed() < 255){
				Color temp = new Color(r+1, g, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(optionSelect == 1){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen() < 255){
				Color temp = new Color(r, g+1, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(optionSelect == 2){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue() < 255){
				Color temp = new Color(r, g, b+1);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
	}
	
	private void decreaseColor(){
		int r = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed();
		int g = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen();
		int b = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue();
		if(optionSelect == 0){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed() > 0){
				Color temp = new Color(r-1, g, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(optionSelect == 2){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen() > 0){
				Color temp = new Color(r, g-1, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(optionSelect == 3){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue() > 0){
				Color temp = new Color(r, g, b-1);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
	}
	
	public void keyAction(KeyEvent e){
		if(c.getIsActive()){
			c.keyAction(e);
			return;
		}
		switch(e.getKeyCode()){
		case KeyEvent.VK_A:			
		case KeyEvent.VK_B:
		case KeyEvent.VK_C:
		case KeyEvent.VK_D:
		case KeyEvent.VK_E:
		case KeyEvent.VK_F:
		case KeyEvent.VK_G:
		case KeyEvent.VK_H:
		case KeyEvent.VK_I:
		case KeyEvent.VK_J:
		case KeyEvent.VK_K:
		case KeyEvent.VK_L:
		case KeyEvent.VK_M:
		case KeyEvent.VK_N:
		case KeyEvent.VK_O:
		case KeyEvent.VK_P:
		case KeyEvent.VK_Q:
		case KeyEvent.VK_R:
		case KeyEvent.VK_S:
		case KeyEvent.VK_T:
		case KeyEvent.VK_U:
		case KeyEvent.VK_V:
		case KeyEvent.VK_W:
		case KeyEvent.VK_X:
		case KeyEvent.VK_Y:
		case KeyEvent.VK_Z:
		case KeyEvent.VK_1:
		case KeyEvent.VK_2:
		case KeyEvent.VK_3:
		case KeyEvent.VK_4:
		case KeyEvent.VK_5:
		case KeyEvent.VK_6:
		case KeyEvent.VK_7:
		case KeyEvent.VK_8:
		case KeyEvent.VK_9:
		case KeyEvent.VK_0:
		case KeyEvent.VK_SPACE:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT){
				if(businessNameBuffer.length() == 20) break;
				else businessNameBuffer = textUtilities.addEndChar(businessNameBuffer, e.getKeyChar());
				break;
			}
			break;
			
		case KeyEvent.VK_BACK_SPACE:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT){
				if(businessNameBuffer.length() == 0) break;
				else businessNameBuffer = textUtilities.deleteEndChar(businessNameBuffer);
			}
			break;
			
		case KeyEvent.VK_ESCAPE:
			AstroBiz.getController().purge(ENTITY_TYPE.TEXT_WINDOW);
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR) scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_CONFIG;
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME) scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_CONFIG;
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ){
				if(hqPlacementView == HQPLACEMENTVIEW.WORLD){
					scenarioViewMode = SCENARIOVIEWMODE.VM_PLYR_SELECT;
					optionSelect = 0;
				}
				else if(hqPlacementView == HQPLACEMENTVIEW.REGION){
					optionSelect = previousOption;
					hqPlacementView = HQPLACEMENTVIEW.WORLD;
				}
			}
			break;
			
		case KeyEvent.VK_DOWN:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR){
				cycleOptionNext();
//				cycleBusinessNext();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT){
				cycleOptionNext();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME) cycleOptionNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_DIFF_SELECT) cycleOptionNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_PLYR_SELECT) cycleOptionNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_SELECT) cycleOptionNext();
			break;
		case KeyEvent.VK_ENTER:
			AstroBiz.getController().purge(ENTITY_TYPE.TEXT_WINDOW);
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR){
				businessSelect = optionSelect;
				System.out.println(businessSelect);
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT){
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_COLOR;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_CONFIG){
				if(busiConfigOptions == BUSICONFIGOPTIONS.COLOR) {
					scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_COLOR;
				}
				else if(busiConfigOptions == BUSICONFIGOPTIONS.NAME){
					scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_NAME;
				}
				else if(busiConfigOptions == BUSICONFIGOPTIONS.EXIT){
					this.setActive(false);
					AstroBiz.State = STATE.REGIONVIEW;
				}
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME){
				businessNameBuffer = ab.getScenario().getBusinesses().elementAt(businessSelect).getName();
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT){
				ab.getScenario().getBusinesses().elementAt(businessSelect).setName(businessNameBuffer);
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_NAME;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_DIFF_SELECT){
				setDifficulty();
				scenarioViewMode = SCENARIOVIEWMODE.VM_PLYR_SELECT;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_PLYR_SELECT){
				setPlayers(ab.getScenario());
				scenarioViewMode = SCENARIOVIEWMODE.VM_SET_HQ;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_CONFIRM){
				if(yesNo){
					ab.setScenario(new Scenario());
					setScenario();
					scenarioViewMode = SCENARIOVIEWMODE.VM_DIFF_SELECT;
				}
				else{
					yesNo = true;
					scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_SELECT;
				}
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_SELECT){
				setScenario();
				scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_CONFIRM;
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ){
				if(hqPlacementView == HQPLACEMENTVIEW.WORLD){
					loadRegionMap(AstroBiz.worldMap);
					//loadLocationVector(ab.getRegion().getLocationVector());
					loadLocationVector(ab.getScenario().getLocations());
					previousOption = optionSelect;
					hqPlacementView = HQPLACEMENTVIEW.REGION;
				}
				
				else if(hqPlacementView == HQPLACEMENTVIEW.REGION){
					if(this.availableHqLocations.size() == 0)
						break;
					else{
						ab.getScenario().getBusinesses().elementAt(scenarioPlayerConfigure - 1).setHQ(getHqSelectedLocation());
					}
					if(scenarioPlayerConfigure <= scenarioPlayersToConfigure){
						scenarioPlayerConfigure++;
						if(scenarioPlayerConfigure > scenarioPlayersToConfigure){
							AI ai = ab.getScenario().getAI();
							for(int i = scenarioPlayerConfigure; i <= 4; i++){
								ai.aiPlaceHq(ab.getScenario(), ab.getScenario().getBusinesses().elementAt(i - 1), ab.getScenario().getLocations());
							}
							scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_CONFIG;
							// Move to business customization
						}
						else{
							// Allow further HQ placement.
							hqPlacementView = HQPLACEMENTVIEW.WORLD;
						}		 
					}
					else{
						//??
					}
				}
			}
			resetOptionSelect();
			break;
		case KeyEvent.VK_LEFT:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_CONFIG) cycleBcoPrev();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT) decreaseColor();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_CONFIRM) yesNo = true;
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ) cycleOptionPrev();
			break;
		case KeyEvent.VK_RIGHT:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_CONFIG) cycleBcoNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT) increaseColor();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_CONFIRM) yesNo = false;
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ) cycleOptionNext();
			break;
		case KeyEvent.VK_UP:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR){
				cycleOptionPrev();
				//cycleBusinessPrev();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT){
				cycleOptionPrev();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME) cycleOptionPrev();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_DIFF_SELECT) cycleOptionPrev();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_PLYR_SELECT) cycleOptionPrev();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_SELECT) cycleOptionPrev();
			break;
		default:
			break;
		}
	}
	
	private void loadLocationVector(Vector<Location> v){
		if(this.availableHqLocations == null){
			this.availableHqLocations = new Vector<Location>();
		}
		else{
			this.availableHqLocations.clear();
		}
		for(int i = 0; i < v.size(); i++){
			if(v.elementAt(i).getLocationRegion() == optionSelect &&
				!v.elementAt(i).getLocationIsHub() &&
				v.elementAt(i).getLocationType() == LOCATIONTYPE.LT_CITY){
				this.availableHqLocations.addElement(v.elementAt(i));
			}
		}
	}

	private void loadRegionMap(SpriteSheet map){
		switch(this.optionSelect){
		case 0:
			region = map.grabImage(1, 1, 736, 288);
			break;
		case 1:
			region = map.grabImage(2, 1, 736, 288);
			break;
		case 2:
			region = map.grabImage(3, 1, 736, 288);
			break;
		case 3:
			region = map.grabImage(1, 2, 736, 288);
			break;
		case 4:
			region = map.grabImage(2, 2, 736, 288);
			break;
		case 5:
			region = map.grabImage(3, 2, 736, 288);
			break;
		case 6:
			region = map.grabImage(1, 3, 736, 288);
			break;
		case 7:
			region = map.grabImage(2, 3, 736, 288);
			break;
		case 8:
			region = map.grabImage(3, 3, 736, 288);
			break;		
		}
	}
	
	private void resetOptionSelect(){
		this.optionSelect = 0;
	}
	
	private void setDifficulty(){
		switch(this.optionSelect){
		case 0:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		case 1:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		case 2:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		case 3:
			ab.getScenario().setScenarioDifficulty(1);
			break;
		}
	}
	
	private void setPlayers(Scenario s){
		switch(this.optionSelect){
		case 0:
			s.setScenarioPlayers(1);
			scenarioPlayersToConfigure = 1;
			break;
		case 1:
			s.setScenarioPlayers(2);
			scenarioPlayersToConfigure = 2;
			break;
		case 2:
			s.setScenarioPlayers(3);
			this.scenarioPlayersToConfigure = 3;
			break;
		case 3:
			s.setScenarioPlayers(4);
			this.scenarioPlayersToConfigure = 4;
			break;
		}
		scenarioPlayerConfigure = 1;
	}
	
	private void setScenario(){
		switch(this.optionSelect){
		case 0:
			ab.getScenario().loadScenario(0);
			break;
		case 1:
			ab.getScenario().loadScenario(1);
			break;
		case 2:
			ab.getScenario().loadScenario(2);
			break;
		case 3:
			ab.getScenario().loadScenario(3);
			break;
		}
	}

	@Override
	public void setVM(VM vm) {
		this.scenarioViewMode = (SCENARIOVIEWMODE)vm;	
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public void setActive(boolean b) {
		isActive = b;
		
	}

	@Override
	public ENTITY_TYPE getType() {
		return type;
		// TODO Auto-generated method stub
	}

}
