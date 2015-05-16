package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.info.ENTITY_TYPE;
import astroBiz.info.FontInformation;
import astroBiz.info.HALIGN;
import astroBiz.info.VALIGN;
import astroBiz.lib.Business;
import astroBiz.lib.Draw;
import astroBiz.lib.Location;
import astroBiz.lib.Manufacturer;
import astroBiz.lib.Route;
import astroBiz.lib.Scenario;
import astroBiz.lib.SpaceCraft;
import astroBiz.lib.TextWindow;
import astroBiz.util.Confirmation;
import astroBiz.util.ImageUtilities;
import astroBiz.util.textUtilities;

public class RegionView implements Manager, Serializable{
	private static final int BUTTONHEIGHT = 64;
	private static final int BUTTONWIDTH = 96;
	
	private static final long serialVersionUID = -5748594383657019059L;
	
	private static enum REGIONVM implements VM{
		VM_BRIEFING,
		VM_BUY,
		VM_BUY_SELECT_MODEL,
		VM_BUY_SELECT_MFG,
		VM_BUY_SELECT_QTY,
//		VM_OPEN_ROUTE_CRAFT,
//		VM_OPEN_ROUTE_QTY,
//		VM_OPEN_ROUTE_DEST,
//		VM_OPEN_ROUTE_LOC,
//		VM_OPEN_ROUTE,
		VM_ORDER_CONFIRM,
		VM_REGION,
		VM_REGIONSWAP,;

		@Override
		public int getOpt() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	private boolean isActive = false;
	private byte activeRegion = 2;
	
	private int selectedLocation = -1;
	private int selectedOption = 0;
	private int previousOption = selectedOption;
	
	private ENTITY_TYPE type = ENTITY_TYPE.VIEW_MANAGER;
	private REGIONVM regionVm = REGIONVM.VM_REGION;

	private AstroBiz ab;
	private Business busi;
	private BufferedImage[] buttons;	// Contains the buttons displayed on the regional map.
	private Confirmation c = new Confirmation();
	private Font sectorfont = new Font("arial", Font.BOLD, 15);
	private Location hub;
	private Location loc;
	private Manufacturer selectedManufacturer;
	private Route route;
	private RouteConfig rc = new RouteConfig(this);
	private Scenario scenario;
	private SpaceCraft selectedSpaceCraft;
	private TextWindow textWin = AstroBiz.textWin;
	
	private Vector<Location> locationsAvailable = new Vector<Location>();
	private Vector<Manufacturer> manufacturersAvailable = new Vector<Manufacturer>();
	
	public RegionView(AstroBiz astrobiz, Scenario scenario){
		this.ab = astrobiz;
		this.scenario = scenario;
		textWin.setActive(false);
		buttons = new BufferedImage[12];
		int i = 0;
		for(int y = 1; y <= 4; y++){
			for(int x = 1; x <= 3; x++){
				buttons[i] = AstroBiz.regionButtons.grabImage(x, y, BUTTONWIDTH, BUTTONHEIGHT);
				i++;
			}
		}	
	}
	
	public void keyAction(KeyEvent e){
		//	Forward key input to Confirmation if it is active.
		if(c.getIsActive()){
			c.keyAction(e);
			return;
		}
		switch(e.getKeyCode()){
		case KeyEvent.VK_R:
			textWin.setActive(false);
			if(regionVm == REGIONVM.VM_REGION){
				regionVm = REGIONVM.VM_REGIONSWAP;
				previousOption = selectedOption;
				selectedOption = this.activeRegion;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionDown();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionDown();
			break;
			
		case KeyEvent.VK_ENTER:
			keyEnter();
			break;
			
		case KeyEvent.VK_ESCAPE:
			textWin.setActive(false);
			if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
				regionVm = REGIONVM.VM_BUY_SELECT_MFG;
				selectedOption = previousOption;
			}
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
				regionVm = REGIONVM.VM_REGION;
				resetSelectedOpt();
			}
			else if(regionVm == REGIONVM.VM_REGIONSWAP){
				selectedOption = previousOption;
				regionVm = REGIONVM.VM_REGION;
			}
			break;
			
		case KeyEvent.VK_LEFT:
			cycleOptionLeft();
			break;
			
		case KeyEvent.VK_UP:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionUp();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionUp();
			break;
			
		case KeyEvent.VK_RIGHT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGIONSWAP)cycleOptionRight();
			else cycleOptionRight();
			break;
		default:
			break;
		}
	}
	
	public void render(Graphics g){
		if(!AstroBiz.getController().containsEntity(textWin))
			AstroBiz.getController().addEntity(textWin);
		int x = 0;
		int y = 470;
		g.setColor(Color.white);
		textUtilities.drawStringMultiLine(g, FontInformation.debug, x, y, 800, regionVm.toString());
		
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) drawBuySelectModel(g);
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) drawBuySelectMfg(g);
		else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY) drawBuySelectModel(g);
		else if(regionVm == REGIONVM.VM_ORDER_CONFIRM) drawOrderConfirm(g);
		else if(regionVm == REGIONVM.VM_REGION) drawRegion(g);
		else if(regionVm == REGIONVM.VM_REGIONSWAP) drawRegionSwap(g);
		
		if(c.getIsActive()) c.render(g);
	}
	
	public void tick(){}
	
	private void buttonHilight(Graphics g){
		int x = 192;
		int y = 320;
		int width = 96;
		int height = 64;
		for(int i = 0; i != selectedOption; i++){
			if(i == 5){
				x = 192;
				y += height;
			}
			else{
				x+=width;
			}
		}
		g.setColor(Color.red);
		BufferedImage temp = buttons[selectedOption];
		temp = ImageUtilities.colorizeImage(temp, new Color(buttons[selectedOption].getRGB(48, 32)), Color.red);
		g.drawImage(temp, x, y, null);
	}

	private void cycleOptionDown(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			for(int i = 0; i < manufacturersAvailable.size(); i++){
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(scenario).size() > 0) maxOpt++;
			}
			if(selectedOption > 0) selectedOption--;
			else selectedOption = maxOpt - 1;
		}
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 6) selectedOption += 6;
		}
	}
	
	private void cycleOptionLeft(){
		if(selectedOption > 0) selectedOption--;
	}
	
	private void cycleOptionRight(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			for(int i = 0; i < manufacturersAvailable.size(); i++){
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(scenario).size() > 0) maxOpt++;
			}
			if(selectedOption < maxOpt - 1) selectedOption++;
			else selectedOption = 0;
		}
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
			maxOpt = selectedManufacturer.getModeslAvailable(scenario).size() - 1;
			if(selectedOption < maxOpt) selectedOption++;
		}
		
		if(regionVm == REGIONVM.VM_BUY_SELECT_QTY){
			maxOpt = 10;
			if(selectedOption < maxOpt) selectedOption++;
		}
		
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 12){
				if(selectedOption == 5) return;
				else if(selectedOption == 11) return;
				else selectedOption++;
			}
		}
		if(regionVm == REGIONVM.VM_REGIONSWAP){
			maxOpt = 8;
			if(selectedOption < maxOpt)selectedOption++;
		}
	}
	
	private void cycleOptionUp(){
		int maxOpt = 0;
		if(this.regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			for(int i = 0; i < manufacturersAvailable.size(); i++){
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(scenario).size() > 0) maxOpt++;
			}
			if(selectedOption < maxOpt - 1) selectedOption++;
			else selectedOption = 0;
		}
		if(this.regionVm == REGIONVM.VM_REGION){
			if (selectedOption > 5) selectedOption -= 6;
		}
	}
	
	private void drawBuySelectModel(Graphics g){
		SpaceCraft temp;
		if(c.getIsActive()) temp = selectedSpaceCraft;
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) temp = selectedManufacturer.getModeslAvailable(scenario).elementAt(selectedOption);
		else temp = selectedSpaceCraft;
		Business busi = scenario.getBusinesses().elementAt(scenario.getActiveBusiness());
		Rectangle rect = new Rectangle();
		
		//	Background
		g.setColor(Color.darkGray);
		g.fillRect(32, 32, 736, 288);
		
		// Toggle Model Arrows
		if(selectedManufacturer.getModeslAvailable(scenario).size() > 1){
			if(selectedOption > 0)
				g.drawImage(AstroBiz.regionSprites.grabImage(1, 4, 16, 16), 128, 168, null);
			if(selectedOption < selectedManufacturer.getModeslAvailable(scenario).size() - 1)
				g.drawImage(AstroBiz.regionSprites.grabImage(2, 4, 16, 16), 672 - 16, 168, null);
		}
		Draw.drawSpaceCraftModelStats(g, temp, busi);
		
		//	Manufacturer Name Box
		rect.setBounds(160, 60, 480, 32);
		g.setColor(Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, rect, HALIGN.RIGHT, VALIGN.BOTTOM, selectedManufacturer.getName());
		
		//	Footer Box
		rect.setBounds(160, 228, 480, 32);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, rect, HALIGN.LEFT, VALIGN.MIDDLE, "Introduced: " + temp.getYearIntroduced());
		textUtilities.drawStringToBox(g, FontInformation.modelheader, rect, HALIGN.RIGHT, VALIGN.MIDDLE, "Cost: " + temp.getCost() + "K");
		
		//	Corporate Box
		rect.setBounds(354, 260, 288, 32);
		Draw.drawWindow(g, rect, busi.getColor(), busi.getColor());
		textUtilities.drawStringToBox(g, FontInformation.modelheader, rect, HALIGN.RIGHT, VALIGN.MIDDLE, "Balance: " + busi.getAccountBalance() + "K");
		
		if(!c.isActive()){
			if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
				if(!textWin.isActive()){
					textWin.updateText("Nice to meet you. Which model are you interested in?");
					textWin.setActive(true);
				}
			}
		}
		
		if(regionVm == REGIONVM.VM_BUY_SELECT_QTY){
			int offset = 32;
			int startY = 256;
			int startX = 160;
			g.setColor(Color.gray);
			for(int y = 0; y < 2; y++){
				for(int x = 0; x < 5; x++){
					g.drawRect(startX, startY, offset, offset);
					startX += offset;
				}
				startX = 160;
				startY += offset;
			}
			drawBuySelectQty(g);
			if(!textWin.isActive()){
				textWin.updateText("You can purchase a maximum of " + scenario.getMaxOrderQty(selectedSpaceCraft.getCost()) + " vehicles. How many would you like to purchase?");
				textWin.setActive(true);
			}
		}
		
	}

	private void drawBuySelectQty(Graphics g){
		BufferedImage craftsprite = ImageUtilities.colorizeImage(AstroBiz.regionSprites.grabImage(2, 3, 16, 16), 
				new Color(AstroBiz.regionSprites.grabImage(2, 3, 16, 16).getRGB(5, 5)),
				scenario.getBusinesses().elementAt(scenario.getActiveBusiness()).getColor());
		int x = 168;
		int y = 264;
		for(int i = 0; i < selectedOption; i++){
			g.drawImage(craftsprite, x, y, null);
			x += 32;
			if(i == 4){
				x = 168;
				y += 32;
			}
		}
	}
	
	private void drawBuySelectMfg(Graphics g){
		g.setColor(Color.white);
		for(int i = 0; i < this.manufacturersAvailable.size(); i++){
			if(this.manufacturersAvailable.elementAt(i).getModeslAvailable(scenario).size() > 0){
				Manufacturer temp = manufacturersAvailable.elementAt(i);
				if(i == selectedOption){
					g.setColor(Color.green);
					textUtilities.drawStringCenterV(g, FontInformation.modelstat, temp.getX(), temp.getY(), 32, temp.getSymbol());
					g.drawString(temp.getName(),
							800 - textUtilities.getTextWidth(g, FontInformation.modelstat, temp.getName()),
							0 + textUtilities.getTextHeight(g, FontInformation.modelstat, temp.getName()));
				}
				else{
					g.setColor(Color.white);
					textUtilities.drawStringCenterV(g, FontInformation.modelstat, temp.getX(), temp.getY(), 32, temp.getSymbol());
				}
			}
		}
	}
	
	private void drawModelRoute(Graphics g){
		int x = 160 - 8;
		int y = 96 - 8;
		int w = 480 + 16;
		int h = 128 + 16;
		Rectangle stat = new Rectangle();
		Rectangle window = new Rectangle(x, y, w, h);
		
		//	This is nice, and will probably be reused, move it to its own function.
		for(int i = 0; i <= 3; i++){
			if(i < 3)
				Draw.drawWindow(g, window, Color.black, busi.getColor());
			x+=2;
			y+=2;
			w-=4;
			h-=4;
			window.setBounds(x, y, w, h);
			}
		
//		Selected Model Picture Box
		stat.setBounds(160, 96, 192, 128);
		Draw.drawWindow(g, stat, Color.blue, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.LEFT, VALIGN.BOTTOM, selectedSpaceCraft.getName());
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.RIGHT, VALIGN.BOTTOM, "x" + route.getCraftCount());

//		Hangar Counts
		stat.setBounds(160, 236, 96, 64);
		Draw.drawWindow(g, stat, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.RIGHT, VALIGN.BOTTOM, busi.getCraftAvailableByType(selectedSpaceCraft).size()+ "");
		
		//	# FLIGHTS
		stat.setBounds(384, 192, 64, 32);
		Draw.drawWindow(g, stat, Color.gray, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "Flight");
		stat.setBounds(448, 192, 192, 32);
		Draw.drawWindow(g, stat, Color.black, Color.white);
		x = 488;
		y = 192;
		g.drawString(route.calcDistance(hub, loc) + "", 32, 96);
		g.drawString(route.getCraftModel(), 32, 64);
		g.drawString(route.calcWeeklyFlights() + "", 32, 32);
		g.drawString(selectedSpaceCraft.getRange() + " ", 32, 128);
		for(int i = 0; i < route.calcWeeklyFlights(); i++){
			g.drawImage(AstroBiz.regionSprites.grabImage(3, 4, 16, 16), x, y, null);
			x+=16;
			if(i == 6){
				y+=16;
				x = 488;
			}
		}
	}
	
	private void drawOpenDest(Graphics g){
		if(locationsAvailable.size() == 0){
			Draw.drawRegion(g, activeRegion);
			Draw.drawRegionLocations(g, scenario, activeRegion);
			if(!textWin.isActive()){
				textWin.updateText("We currently have no slots available in this region.");
				textWin.setActive(true);
			}		
		}
		
		else{
			loc = locationsAvailable.elementAt(selectedOption);
			route.setRouteHome(hub);
			route.setRouteDestination(loc);
			Draw.drawRegion(g, activeRegion);
			Draw.drawRegionLocations(g, scenario, activeRegion);
			g.setColor(Color.GREEN);
			g.drawOval(loc.getX(), loc.getY(), 16, 16);
			g.setColor(Color.darkGray);
			Rectangle r = new Rectangle(64,320,672,128);
			g.setColor(Color.gray);
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.LEFT, VALIGN.TOP, "["+hub.getID()+"] " + hub.getName());
			double distance = 0;
			String s = "";
			if(route.calcDistance(hub, loc) < 0.001){
				distance = (int)(route.calcDistance(hub, loc) * 150000000);
				s = "<-- " + distance + "KM" + " -->";
				g.setColor(Color.white);
				g.drawString(route.calcDistance(hub, loc) + "au", 32, 32);
			}
			else{
				distance = route.calcDistance(hub, loc);
				DecimalFormat df = new DecimalFormat("#.####");
				String trunc = df.format(distance);
				s = "<-- " + trunc + "AU" + " -->";
			}		
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.CENTER, VALIGN.TOP, s);
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.RIGHT, VALIGN.TOP, "["+loc.getID()+"] " + loc.getName());
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.CENTER, VALIGN.MIDDLE, "Cost: " + route.calcCostToOpen(hub, loc, busi) + "K");
		}
	}

	private void drawOpenRoute(Graphics g){
		String s = "";
		Business busi = scenario.getBusinesses().elementAt(scenario.getActiveBusiness());
		Draw.drawRegion(g, activeRegion);
		Draw.drawRegionLocations(g, scenario, activeRegion);
		
		if(busi.regionContainsHub(activeRegion)){
			s = "Route will depart from " + busi.getHub(activeRegion).getName() + ". Select a destination for this route.";
		}
		else{
			s = "We currently have no hubs in this region.";
		}
		
		if(!textWin.isActive()){
			textWin.updateText(s);
			textWin.setActive(true);
		}	
	}
	
	private void drawOrderConfirm(Graphics g){
		String s = "Thank you for your order of " + selectedOption + " " + selectedSpaceCraft.getName() + "s. Your order will be delivered in 3 months!";
		if(!textWin.isActive()){
			textWin.updateText(s);
			textWin.setActive(true);
		}
	}
	
	private void drawRegion(Graphics g){
		Rectangle rect = new Rectangle(32,0,736,32);
		busi = scenario.getBusinesses().elementAt(scenario.getActiveBusiness());
		g.setColor(busi.getColor());
		g.fillRect(32, 0, textUtilities.getTextWidth(g, FontInformation.modelstat, busi.getName()), 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 32, 0, 32, busi.getName());

		textUtilities.drawStringToBox(g, FontInformation.modelstat, rect, HALIGN.RIGHT, VALIGN.MIDDLE, "Balance: " + busi.getAccountBalance() + "K");
		textUtilities.drawStringToBox(g, FontInformation.modelstat, rect, HALIGN.CENTER, VALIGN.MIDDLE, "Q" + scenario.getQuarter() + " - " +scenario.getCurrentYear());
		
		Draw.drawRegion(g, activeRegion);
		Draw.drawRegionRoutes(g, scenario, activeRegion);
		Draw.drawRegionLocations(g, scenario, activeRegion);
		
		
		g.setFont(sectorfont);
		g.setColor(Color.WHITE);
		
		// TODO: Render Routes.
		
		Draw.drawMinimap(g, busi);
		//drawMinimap(g);
		
		// Prototype Render Buttons
		int x = 192;
		int y = 320;
		for(int i = 0; i < buttons.length; i++){
			g.drawImage(buttons[i], x, y, null);
			x += BUTTONWIDTH;
			if(x > 672){
				x = 192;
				y += BUTTONHEIGHT;
			}
		}
		buttonHilight(g);
	}
	
	private void drawRegionSwap(Graphics g){
		if(this.selectedOption == 0){
			g.setColor(Color.green);
			g.drawString("Mercury", 96+15, 177+15);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(96, 177, 15, 15);		// Mercury
		
		if(this.selectedOption == 1){
			g.setColor(Color.green);
			g.drawString("Venus", 160+32, 160+32);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(160, 160, 32, 32);		// Venus
		
		if(this.selectedOption == 2){
			g.setColor(Color.green);
			g.drawString("Earth", 224+32, 64+32);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(224, 64, 32, 32);		// Earth
		
		if(this.selectedOption == 3){
			g.setColor(Color.green);
			g.drawString("Luna", 256+10, 54+10);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(256, 54, 10, 10);		// Luna
		
		if(this.selectedOption == 4){
			g.setColor(Color.green);
			g.drawString("Mars", 288+24, 160+24);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(288, 160, 24, 24);		// Mars
		
		if(this.selectedOption == 5){
			g.setColor(Color.green);
			g.drawString("Jupiter", 352+96, 192+96);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(352, 192, 96, 96);		// Jupiter
		
		if(this.selectedOption == 6){
			g.setColor(Color.green);
			g.drawString("Saturn", 480+64, 128+64);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(480, 128, 64, 64);		// Saturn
		
		if(this.selectedOption == 7){
			g.setColor(Color.green);
			g.drawString("Uranus", 576+64, 192+64);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(576, 192, 64, 64);		// Uranus
		
		if(this.selectedOption == 8){
			g.setColor(Color.green);
			g.drawString("Neptune", 676+64, 96+64);
		}
		else{
			g.setColor(Color.white);
		}
		g.fillOval(676, 96, 64, 64);		// Neptune
	}

	/**
	 * This is slot negotiation, not open route... hurrr.
	 * @param g
	 */
	private void drawSlotLoc(Graphics g){
		Location l = locationsAvailable.elementAt(selectedLocation);

		g.drawImage(l.getFaction().getFlag(), 64, 32, null);		//	Flag
		g.setColor(Color.CYAN);
		g.fillRect(64, 96, 288, 160); 			// City Drawing
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(352, 96, 384, 192);			// Business Ventures
		

		g.setColor(Color.GREEN);
		g.fillRect(64, 256, 288, 64);			// Slots : Flights Info
		//	Total Slots
		Rectangle totslot = new Rectangle(64,256, 144, 40);
		Rectangle slotcnt = new Rectangle(64,296, 144, 24);
		Draw.drawWindow(g, totslot, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, totslot, HALIGN.CENTER, VALIGN.MIDDLE, "Total Slots");
		Draw.drawWindow(g, slotcnt, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, slotcnt, HALIGN.CENTER, VALIGN.MIDDLE, l.getSlotAvailable() + " / " + l.getSlotTotal());
		
		g.setColor(scenario.getBusinesses().elementAt(0).getColor());
		g.fillRect(352, 288, 96, 32);
		g.setColor(scenario.getBusinesses().elementAt(1).getColor());
		g.fillRect(448, 288, 96, 32);
		g.setColor(scenario.getBusinesses().elementAt(2).getColor());
		g.fillRect(544, 288, 96, 32);
		g.setColor(scenario.getBusinesses().elementAt(3).getColor());
		g.fillRect(640, 288, 96, 32);
		g.setColor(Color.BLACK);
		Font slotfont = new Font("arial", Font.BOLD, 15);

		Rectangle pop = new Rectangle(160, 64, 64, 32);
		Rectangle popamt = new Rectangle(224, 64, 64, 32);
		Rectangle busi = new Rectangle(352,32,64,32);
		Rectangle busiRating = new Rectangle(416,32,64,32);
		Rectangle indRating = new Rectangle(352,64,64,32);
		
		g.setColor(Color.gray);
		Draw.drawWindow(g, pop, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.chitchat, pop, HALIGN.CENTER, VALIGN.MIDDLE, "POP");
		textUtilities.drawStringToBox(g, FontInformation.modelstat, popamt, HALIGN.CENTER, VALIGN.MIDDLE, l.getPopulation() + "M");


		Draw.drawWindow(g, busi, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.chitchat, busi, HALIGN.CENTER, VALIGN.MIDDLE, "BUSI");
		textUtilities.drawStringToBox(g, FontInformation.modelstat, busiRating, HALIGN.CENTER, VALIGN.MIDDLE, l.getDemandBusi()+"");
		
		// Draw Name On Top
		g.setColor(Color.WHITE);
		g.setFont(FontInformation.modelstat);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(l.getName(), 160, 32+fm.getAscent());
		g.drawString(l.getFaction().getName(), 160, 64-fm.getDescent());
	}
	
	public byte getActiveRegion(){
		return activeRegion;
	}
	
	private void keyEnter(){
		textWin.setActive(false);
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			previousOption = selectedOption;
			selectMfg(selectedOption);
			regionVm = REGIONVM.VM_BUY_SELECT_MODEL;
			resetSelectedOpt();
		}
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
			selectedSpaceCraft = selectedManufacturer.getModeslAvailable(scenario).elementAt(selectedOption);
			c.setConfirmVM(this, REGIONVM.VM_BUY_SELECT_MODEL, REGIONVM.VM_BUY_SELECT_QTY, AstroBiz.employeeSprites.grabImage(1, 1, 128, 128), selectedSpaceCraft.getDesc());
			c.setActive(true);
			AstroBiz.getController().addEntity(c);
			resetSelectedOpt();
		}
		else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY){
			scenario.placeOrder(scenario.getBusinesses().elementAt(scenario.getActiveBusiness()), selectedManufacturer, selectedSpaceCraft, selectedOption);
			regionVm = REGIONVM.VM_ORDER_CONFIRM;
		}
		else if(regionVm == REGIONVM.VM_ORDER_CONFIRM){
			resetSelectedOpt();
			regionVm = REGIONVM.VM_BUY_SELECT_MODEL;
		}
		else if(regionVm == REGIONVM.VM_REGION) processButton();
		else if(regionVm == REGIONVM.VM_REGIONSWAP){
				this.activeRegion = (byte)selectedOption;
//				this.getActiveRegionMap();
				selectedOption = previousOption;
				regionVm = REGIONVM.VM_REGION;
		}	
	}

	private void processButton(){
		previousOption = selectedOption;
		// Open Route
		if(selectedOption == 0){
			resetSelectedOpt();
			this.setActive(false);
			rc.setActiveBusi(busi);
			rc.setScenario(scenario);
			rc.setActive(true);
		}
		//	Adjust Routes
		else if(selectedOption == 1){

		}
		// Adjust Route
		else if(selectedOption == 2);
		// Buy Vehicles
		else if(selectedOption == 3){
			this.manufacturersAvailable.clear();
			this.manufacturersAvailable = scenario.getManufacturersAvailable();
			regionVm = REGIONVM.VM_BUY_SELECT_MFG;
			resetSelectedOpt();
		}
		else if(selectedOption == 4);
		else if(selectedOption == 5);
		else if(selectedOption == 6);
		else if(selectedOption == 7);
		else if(selectedOption == 8);
		else if(selectedOption == 9);
		else if(selectedOption == 10);
		// End Turn
		else if(selectedOption == 11){
			scenario.endTurn();
			resetSelectedOpt();
		}
	}
	
	private void resetSelectedOpt(){
		this.selectedOption = 0;
	}
	
	private void selectMfg(int index){
		this.selectedManufacturer = this.manufacturersAvailable.elementAt(index);
	}

	private void setLocationsAvailable(){
		locationsAvailable = scenario.getLocationsAvailable(activeRegion);
	}
	
	@Override
	public void setVM(VM vm) {
		this.regionVm = (REGIONVM)vm;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public void setActive(boolean b) {
		this.isActive = b;
	}

	@Override
	public ENTITY_TYPE getType() {
		return type;
	}

	@Override
	public VM getVM() {
		return regionVm;
	}

	@Override
	public void setVMDefault() {
		this.regionVm = REGIONVM.VM_REGION;
	}

}
