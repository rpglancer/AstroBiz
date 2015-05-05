package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.AstroBiz.STATE;
import astroBiz.Location;
import astroBiz.Location.LOCATIONTYPE;
import astroBiz.Scenario;
import astroBiz.SpriteSheet;
import astroBiz.info.ScenarioInformation.SI;
import astroBiz.util.textUtilities;

/**
 * Contains most everything involved in selecting a Scenario for play and configuring it for use.
 * @author Matt Bangert
 *
 */
public class ScenarioView {
	public static final int SBWIDTH = 672;
	public static final int SBHEIGHT = 40;
	private final int MAINTEXTWIDTH = 704;
	private final int MAINTEXTINDENT = 48;
	
	public static enum BUSICONFIGOPTIONS {
		NAME,
		COLOR,
		EXIT,
		SELECT
	}
	public static enum HQPLACEMENTVIEW{
		WORLD,
		REGION
	}
	/**
	 * Enumerator for all the available view modes.
	 * <br>What is rendered on screen is dependent upon the
	 * <br>selected view mode.
	 * @author Matt Bangert
	 *
	 */
	public static enum SCENARIOVIEWMODE{
		VM_BUSI_NAME,
		VM_BUSI_NAME_SELECT,
		VM_BUSI_COLOR,
		VM_BUSI_COLOR_SELECT,
		VM_BUSI_CONFIG,
		VM_SCEN_CONFIRM,
		VM_SCEN_SELECT,
		VM_DIFF_SELECT,
		VM_PLYR_SELECT,
		VM_SET_HQ
	}

	
	private astroBiz.AstroBiz ab;
	private BufferedImage employeeSprite;
	private BufferedImage region;
	private BufferedImage selectSprite;
	private boolean yesNo = true;
	private Font sbfont = new Font("sans", Font.BOLD, 16);
	private Font sbconf	= new Font("sans", Font.BOLD, 32);
	private int businessSelect = 0;
	private int colorSelect = 0;
	private int optionSelect = 0;
	private int previousOption = optionSelect;
	private int scenarioPlayerConfigure = 0;		// The number of the player being configured.
	private int scenarioPlayersToConfigure = 0;		// The total number of players to be configured.
	/**
	 * Buffer for editing business names.
	 */
	private String businessNameBuffer = null;

	private BUSICONFIGOPTIONS busiConfigOptions = BUSICONFIGOPTIONS.EXIT;			//	Active business configuration option
	private HQPLACEMENTVIEW hqPlacementView = HQPLACEMENTVIEW.WORLD;				//	Active placement view for HQ selection
	private SCENARIOVIEWMODE scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_SELECT;	//	Active view mode for scenario selection and setup
	private Vector<Location> availableHqLocations;									//	Locations available to be selected as a HQ.

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
/**
 * Draws everything related to ScenarioView on screen depending upon the active {@link #scenarioViewMode}.
 * @param g (Graphics) The active graphics buffer
 */
	public void render(Graphics g){
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
		g.dispose();
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
				textUtilities.drawString(g, x, y+8, ab.getScenario().getBusinesses().elementAt(i).getName(), Color.white);
				y += height + 5;
			}
			g.drawImage(this.employeeSprite, 32, 320, null);
			textUtilities.drawString(g, 160, 384, "Change color for which company?");
		}
		else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT){
			g.setColor(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor());
			g.fillRoundRect(x, y, width, height, 8, 8);
			textUtilities.drawString(g, 400 - 3 * 16, 96, "R: " + ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed());
			textUtilities.drawString(g, 400 - 3 * 16, 96+32, "G: " + ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen());
			textUtilities.drawString(g, 400 - 3 * 16, 96+64, "B: " + ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue());
			g.drawImage(this.employeeSprite, 32, 320, null);
			textUtilities.drawString(g, 160, 384, "Select your desired color.");
			
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
				textUtilities.drawString(g, x, y+8, ab.getScenario().getBusinesses().elementAt(i).getName(), Color.white);
				y += height + 5;
			}
			if(busiConfigOptions == BUSICONFIGOPTIONS.COLOR) textUtilities.drawString(g, 400 - ("COLOR".length() / 2) * 16, 300, "COLOR", Color.green);
			if(busiConfigOptions == BUSICONFIGOPTIONS.EXIT) textUtilities.drawString(g, 400 - ("EXIT".length() / 2) * 16, 300, "EXIT", Color.green);
			if(busiConfigOptions == BUSICONFIGOPTIONS.NAME) textUtilities.drawString(g, 400 - ("NAME".length() / 2) * 16, 300, "NAME", Color.green);
			g.setColor(Color.white);
			g.drawImage(this.employeeSprite, 32, 320, null);
			textUtilities.drawString(g, 160, 384, "Customize each company's name and color?");
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
				textUtilities.drawString(g, x, y+8, ab.getScenario().getBusinesses().elementAt(i).getName(), Color.white);
				y += height + 5;
			}
			g.drawImage(this.employeeSprite, 32, 320, null);
			textUtilities.drawString(g, 160, 384, "Change name of which company?");
		}
		else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT){
			textUtilities.drawString(g, 400 - 10 * 16, 128, businessNameBuffer);
			g.drawImage(this.employeeSprite, 32, 320, null);
			textUtilities.drawString(g, 160, 384, "Enter the name of your company.");		
		}
		g.dispose();
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
		g.dispose();
	}
	/**
	 * A method for drawing a player query for confirmation.
	 * @param g The graphics buffer to be drawn to.
	 */
	private void scenarioConfirm(Graphics g){
		g.setFont(sbfont);
		g.setColor(Color.white);
		g.drawRect(32, 32, 736, 272);
		textUtilities.drawStringMultiLine(g, 40, 40, MAINTEXTWIDTH + 16, ab.getScenario().getScenarioDescription());
		g.drawImage(this.employeeSprite, 32, 320, null);
		textUtilities.drawString(g, 160, 380, "Is this scenario OK?");
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
		g.dispose();
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
		
		g.drawImage(this.employeeSprite, 32, 320, null);
		g.drawString("Choose the number of players.", 160, 384);
		g.dispose();
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
			g.drawImage(this.employeeSprite, 32, 320, null);
			g.drawString("Select headquarters location for " + s, 160, 384);		
			
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
			g.drawImage(this.employeeSprite, 32, 320, null);
			g.drawString("Select headquarters region for " + s, 160, 384);		
			break;	//	End	WORLD
		}
		g.dispose();
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
			textUtilities.drawString(g, nameX, nameY, si.getName());
			textUtilities.drawString(g, yearX, yearY, "("+si.getYearStart() + " - " + si.getYearEnd() +")");
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
		g.drawImage(this.employeeSprite, 32, 320, null);
		textUtilities.drawString(g, 160, 380, "Please select a scenario to play.");
		g.dispose();
	}
@Deprecated	
	private void cycleBusinessNext(){
		if(businessSelect == 3) businessSelect = 0;
		else businessSelect++;
	}
@Deprecated	
	private void cycleBusinessPrev(){
		if(businessSelect == 0) businessSelect = 3;
		else businessSelect--;
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
@Deprecated	
	private void cycleColorNext(){
		if(colorSelect == 2) colorSelect = 0;
		else colorSelect++;
	}
@Deprecated	
	private void cycleColorPrev(){
		if(colorSelect == 0) colorSelect = 2;
		else colorSelect--;
	}
		
	private void cycleOptionNext(){
		int maxOption = 0;
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR);
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT);
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_CONFIG) maxOption = 3;
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME);
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT);
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_DIFF_SELECT) maxOption = 3;
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_PLYR_SELECT) maxOption = 3;
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_CONFIRM);
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_SELECT) maxOption = 3;
		if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ){
			if(hqPlacementView == HQPLACEMENTVIEW.WORLD) maxOption = 8;
			if(hqPlacementView == HQPLACEMENTVIEW.REGION) maxOption = this.availableHqLocations.size() - 1;
		}

		if(maxOption < 0) return;

		if(optionSelect < maxOption)optionSelect++;
		else optionSelect = 0;
	}
	
	private void cycleOptionPrev(){
		switch(scenarioViewMode){
		case VM_BUSI_COLOR:
			break;
		case VM_BUSI_COLOR_SELECT:
			break;
		case VM_BUSI_CONFIG:
			break;
		case VM_BUSI_NAME:
			break;
		case VM_BUSI_NAME_SELECT:
			break;
		case VM_DIFF_SELECT:
			if(optionSelect > 0) optionSelect--;
			else optionSelect = 3;
			break;
		case VM_PLYR_SELECT:
			if(optionSelect > 0) optionSelect--;
			else optionSelect = 3;
			break;
		case VM_SCEN_CONFIRM:
			break;
		case VM_SCEN_SELECT:
			if(optionSelect > 0) optionSelect--;
			else optionSelect = 3;
			break;
		case VM_SET_HQ:
			if(hqPlacementView == HQPLACEMENTVIEW.WORLD){
				if(this.optionSelect > 0) optionSelect--;
				else optionSelect = 8;
			}
			else if(hqPlacementView == HQPLACEMENTVIEW.REGION){
				if(this.availableHqLocations.size() == 0) return;
				if(this.optionSelect > 0) optionSelect--;
				else optionSelect = this.availableHqLocations.size() - 1;
			}
			break;
		default:
			break;
		
		}
		
	}
		
	private Location getHqSelectedLocation(){
		return this.availableHqLocations.elementAt(this.optionSelect);
	}
	
	private void increaseColor(){
		int r = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed();
		int g = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen();
		int b = ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue();
		if(colorSelect == 0){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed() < 255){
				Color temp = new Color(r+1, g, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(colorSelect == 1){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen() < 255){
				Color temp = new Color(r, g+1, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(colorSelect == 2){
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
		if(colorSelect == 0){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getRed() > 0){
				Color temp = new Color(r-1, g, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(colorSelect == 1){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getGreen() > 0){
				Color temp = new Color(r, g-1, b);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
		else if(colorSelect == 2){
			if(ab.getScenario().getBusinesses().elementAt(businessSelect).getColor().getBlue() > 0){
				Color temp = new Color(r, g, b-1);
				ab.getScenario().getBusinesses().elementAt(businessSelect).setColor(temp);
			}
		}
	}
	
	public void keyAction(KeyEvent e){
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
				//else if(e.isShiftDown()) businessNameBuffer = textUtilities.addEndChar(businessNameBuffer, 'A');
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
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR) cycleBusinessNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT) cycleColorNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME) cycleBusinessNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_DIFF_SELECT) cycleOptionNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_PLYR_SELECT) cycleOptionNext();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_SELECT) cycleOptionNext();
			break;
		case KeyEvent.VK_ENTER:
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR){
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT){
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_COLOR;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_CONFIG){
				if(busiConfigOptions == BUSICONFIGOPTIONS.COLOR) {
					scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_COLOR;
					System.gc();
				}
				else if(busiConfigOptions == BUSICONFIGOPTIONS.NAME){
					scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_NAME;
					System.gc();
				}
				else if(busiConfigOptions == BUSICONFIGOPTIONS.EXIT){
					AstroBiz.State = STATE.REGIONVIEW;
					System.gc();
				}
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME){
				businessNameBuffer = ab.getScenario().getBusinesses().elementAt(businessSelect).getName();
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME_SELECT){
				ab.getScenario().getBusinesses().elementAt(businessSelect).setName(businessNameBuffer);
				scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_NAME;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_DIFF_SELECT){
				setDifficulty();
				scenarioViewMode = SCENARIOVIEWMODE.VM_PLYR_SELECT;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_PLYR_SELECT){
				setPlayers(ab.getScenario());
				scenarioViewMode = SCENARIOVIEWMODE.VM_SET_HQ;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_CONFIRM){
				if(yesNo){
					ab.setScenario(new Scenario());
					setScenario();
					scenarioViewMode = SCENARIOVIEWMODE.VM_DIFF_SELECT;
					System.gc();
				}
				else{
					yesNo = true;
					scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_SELECT;
					System.gc();
				}
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SCEN_SELECT){
				setScenario();
				scenarioViewMode = SCENARIOVIEWMODE.VM_SCEN_CONFIRM;
				System.gc();
			}
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_SET_HQ){
				if(hqPlacementView == HQPLACEMENTVIEW.WORLD){
					loadRegionMap(AstroBiz.worldMap);
					loadLocationVector(ab.getRegion().getLocationVector());
					previousOption = optionSelect;
					hqPlacementView = HQPLACEMENTVIEW.REGION;
				}
				else if(hqPlacementView == HQPLACEMENTVIEW.REGION){
					if(this.availableHqLocations.size() == 0)
						break;
					else{
						getHqSelectedLocation().setLocationIsHub(true, scenarioPlayerConfigure - 1);
						ab.getScenario().getBusinesses().elementAt(scenarioPlayerConfigure - 1).setHQ(getHqSelectedLocation());
					}
					if(scenarioPlayerConfigure <= scenarioPlayersToConfigure){
						scenarioPlayerConfigure++;
						if(scenarioPlayerConfigure > scenarioPlayersToConfigure){
							scenarioViewMode = SCENARIOVIEWMODE.VM_BUSI_CONFIG;
							// Move to business customization
						}
						else{
							// Allow further HQ placement.
							hqPlacementView = HQPLACEMENTVIEW.WORLD;
						}		 
					}
					else{
						// Switch to AI HQ placement
					}
				}
				System.gc();
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
			if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR) cycleBusinessPrev();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_COLOR_SELECT) cycleColorPrev();
			else if(scenarioViewMode == SCENARIOVIEWMODE.VM_BUSI_NAME) cycleBusinessPrev();
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
		
	/*	
		for(int i = 0; i < v.size(); i++){
			switch(this.optionSelect){
			case 0:
				if(v.elementAt(i).getLocationRegion() == 0 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 1:
				if(v.elementAt(i).getLocationRegion() == 1 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 2:
				if(v.elementAt(i).getLocationRegion() == 2 && !v.elementAt(i).getLocationIsHub() && v.elementAt(i).getLocationType() == LOCATIONTYPE.LT_CITY){
					this.availableHqLocations.addElement(v.elementAt(i));
					System.out.println("Adding element " + v.elementAt(i) + " to availableHqLocations!");
					System.out.println("availableHqLocations Vector size now " + this.availableHqLocations.size());
				}
				break;
			case 3:
				if(v.elementAt(i).getLocationRegion() == 3 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 4:
				if(v.elementAt(i).getLocationRegion() == 4 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 5:
				if(v.elementAt(i).getLocationRegion() == 5 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 6:
				if(v.elementAt(i).getLocationRegion() == 6 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 7:
				if(v.elementAt(i).getLocationRegion() == 7 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			case 8:
				if(v.elementAt(i).getLocationRegion() == 8 && !v.elementAt(i).getLocationIsHub())
					this.availableHqLocations.addElement(v.elementAt(i));
				break;
			}
		}
	*/
		
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

}
