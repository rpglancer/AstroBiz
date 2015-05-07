package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.info.FontInformation;
import astroBiz.info.LocationInformation.LI;
import astroBiz.lib.Business;
import astroBiz.lib.Location;
import astroBiz.lib.Manufacturer;
import astroBiz.lib.SpaceCraft;
import astroBiz.util.Confirmation;
import astroBiz.util.ImageUtilities;
import astroBiz.util.textUtilities;

public class RegionView implements Manager {
	private static final int REGIONWIDTH = 736;
	private static final int REGIONHEIGHT = 288;
	private static final int BUTTONHEIGHT = 64;
	private static final int BUTTONWIDTH = 96;
	
	private static enum REGIONVM implements VM{
		VM_BRIEFING,
		VM_BUY,
		VM_BUY_SELECT_MODEL,
		VM_BUY_SELECT_MFG,
		VM_BUY_SELECT_QTY,
		VM_REGION,
		VM_REGIONSWAP,
	}

	private REGIONVM regionVm = REGIONVM.VM_REGION;
	private Vector<Location> mapLocations = new Vector<Location>();
	private Vector<Manufacturer> manufacturersAvailable = new Vector<Manufacturer>();
	private Manufacturer selectedManufacturer;
	private SpaceCraft selectedSpaceCraft;
	private byte activeRegion = 2;
	private int selectedOption = 0;
	private int previousOption = selectedOption;
	private Confirmation c = new Confirmation();

	private AstroBiz ab;
	private BufferedImage[] buttons;	// Contains the buttons displayed on the regional map.
	
	private Font sectorfont = new Font("arial", Font.BOLD, 15);

	public RegionView(AstroBiz astrobiz){
		this.ab = astrobiz;
		buttons = new BufferedImage[12];
		int i = 0;
		for(int y = 1; y <= 4; y++){
			for(int x = 1; x <= 3; x++){
				buttons[i] = AstroBiz.regionButtons.grabImage(x, y, BUTTONWIDTH, BUTTONHEIGHT);
				i++;
			}
		}
		
		for(LI li : LI.values()){
			mapLocations.addElement(new Location(li));
		}
	}
	
	public void tick(){
	}
	
	public void render(Graphics g){	
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) drawBuySelectModel(g);
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) drawBuySelectMfg(g);
		else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY) drawBuySelectQty(g);
		else if(regionVm == REGIONVM.VM_REGION) drawRegion(g);
		else if(regionVm == REGIONVM.VM_REGIONSWAP) drawRegionSwap(g);
		
		if(c.getIsActive()) c.render(g);
	}
	
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
	
	private void drawMinimap(Graphics g){
		int sx = 32;
		int sy = 320;
		int height = 128;
		int width = 160;
		
		int px = 48;
		int py = 320;
		int pheight = 128;
		int pwidth = 128;
		
		g.setColor(Color.black);
		g.fillRect(sx, sy, width, height);
		
		//	Draw Planet Orbit Lines
		g.setColor(Color.DARK_GRAY);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		g.drawOval(px, py, pwidth, pheight);
		px+=10; py+=10; pwidth-=20; pheight-=20;
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		
		// Draw Planets
		g.setColor(Color.darkGray);
		g.fillOval(40, 368, 17, 17);
		g.setColor(Color.BLACK);
		g.fillOval(41, 369, 15, 15);
		
	}
	
	private void drawBuySelectModel(Graphics g){
		SpaceCraft temp = selectedManufacturer.getModeslAvailable(ab.getScenario()).elementAt(selectedOption);
		Business busi = ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness());
		FontMetrics m = g.getFontMetrics(FontInformation.modelheader);
		int strlen = 0;
		
		//	Background
		g.setColor(Color.darkGray);
		g.fillRect(32, 32, 736, 288);
		
		// Toggle Model Arrows
		if(selectedManufacturer.getModeslAvailable(ab.getScenario()).size() > 1){
			if(selectedOption > 0)
				g.drawImage(AstroBiz.regionSprites.grabImage(1, 4, 16, 16), 128, 168, null);
			if(selectedOption < selectedManufacturer.getModeslAvailable(ab.getScenario()).size() - 1)
				g.drawImage(AstroBiz.regionSprites.grabImage(2, 4, 16, 16), 672 - 16, 168, null);
		}
		
		//	Manufacturer Name Box
		g.setColor(Color.gray);
		g.fillRect(160, 64, 480, 32);
		strlen = m.stringWidth(selectedManufacturer.getName());
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 640 - strlen, 64, 32, selectedManufacturer.getName());
		
		//	Selected Model Picture Box
		g.setColor(Color.blue);
		g.fillRect(160, 96, 192, 128);
		g.setColor(Color.white);
		g.drawString(temp.getName(), 160, 224);
		
		//	Range Box
		g.setColor(Color.gray);
		g.fillRect(384, 96, 96, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 384, 96, 32, "Range:");
		
		//	Range Value Box
		g.setColor(Color.black);
		g.fillRect(480, 96, 160, 32);
		g.setColor(Color.white);
		g.getFontMetrics(FontInformation.modelstat);
		strlen = m.stringWidth(temp.getRange() + "AU");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 640 - strlen, 96, 32, temp.getRange() + "AU");

		//	Capacity Box
		g.setColor(Color.gray);
		g.fillRect(384, 128, 96, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 384, 128, 32, "Seats:");
		
		// Capacity Value Box
		g.setColor(Color.black);
		g.fillRect(480, 128, 160, 32);
		g.setColor(Color.white);
		g.setFont(FontInformation.modelstat);
		strlen = m.stringWidth(temp.getCraftCapacity() + "s");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 640 - strlen, 128, 32, temp.getCraftCapacity() + "s");
		
		//	Fuel Efficiency
		g.setColor(Color.gray);
		g.fillRect(384, 160, 64, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 384, 160, 32, "FuelE:");
//		textUtilities.drawString(g, 384, 160 + 8, "F/E:");
		
		//	Fuel Efficiency Value Box
		g.setColor(Color.black);
		g.fillRect(448, 160, 64, 32);
		g.setColor(Color.white);
		g.setFont(FontInformation.modelstat);
		strlen = m.stringWidth(temp.getFuelE() + "");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 512 - strlen, 160, 32, temp.getFuelE() + "");
		
		//	Reliability
		g.setColor(Color.gray);
		g.fillRect(512, 160, 64, 32);
		textUtilities.drawString(g, 512, 160 + 8, "M/R:");
		
		//	Reliability Value Box
		g.setColor(Color.black);
		g.fillRect(576, 160, 64, 32);
		strlen = (temp.getMaintR() + "").length() * 16;
		textUtilities.drawString(g, 640 - strlen, 160 + 8, temp.getMaintR() + "");
		
		//	# in Use
		g.setColor(Color.gray);
		g.fillRect(384, 192, 64, 32);
		textUtilities.drawString(g, 384, 192 + 8, "#USE");
		
		//	# in Use Value Box
		g.setColor(Color.black);
		g.fillRect(448, 192, 64, 32);
		strlen = (busi.getCraftInService(temp) + "").length() * 16;
		textUtilities.drawString(g, 512 - strlen, 192 + 8, busi.getCraftInService(temp) + "");
		
		//	# Hangar
		g.setColor(Color.gray);
		g.fillRect(512, 192, 64, 32);
		textUtilities.drawString(g, 512, 192 + 8, "#HGR");
		
		//	# in Hangar Value Box
		g.setColor(Color.black);
		g.fillRect(576, 192, 64, 32);
		strlen = (busi.getCraftInHangar(temp) + "").length() * 16;
		textUtilities.drawString(g, 640 - strlen, 192 + 8, busi.getCraftInHangar(temp) + "");
		
		//	Footer Box
		g.setColor(Color.gray);
		g.fillRect(160, 224, 480, 32);
		textUtilities.drawString(g, 160, 224 + 8, "Introduced: " + temp.getYearIntroduced());
		strlen = ("Cost: " + temp.getCost() + "K").length() * 16;
		textUtilities.drawString(g, 640 - strlen, 224 + 8, "Cost: " + temp.getCost() + "K");
		
		//	Corporate Box
		g.setColor(busi.getColor());
		g.fillRect(352, 256, 288, 32);
		strlen = ("Balance: " + busi.getAccountBalance() + "K").length() * 16;
		textUtilities.drawString(g, 640 - strlen, 256 + 8, "Balance: " + busi.getAccountBalance() + "K");
		
		//	Manufacturer Representative
		g.setColor(Color.DARK_GRAY);
		g.fillRect(96, 352, 672, 96);
		g.drawImage(AstroBiz.employeeSprites.grabImage(1, 1, 128, 128), 32, 320, null);
		textUtilities.drawStringMultiLine(g, 160, 352, 608, "Nice to meet you. Which model are you interested in?");
	}

	private void drawBuySelectQty(Graphics g){
		
	}
	
	private void drawBuySelectMfg(Graphics g){
		g.setColor(Color.white);
		for(int i = 0; i < this.manufacturersAvailable.size(); i++){
			if(this.manufacturersAvailable.elementAt(i).getModeslAvailable(ab.getScenario()).size() > 0){
				if(i == selectedOption){
					textUtilities.boxText(g, this.manufacturersAvailable.elementAt(i).getX(), this.manufacturersAvailable.elementAt(i).getY(), 4, Color.darkGray, Color.green, this.manufacturersAvailable.elementAt(i).getSymbol());
					textUtilities.drawString(g, AstroBiz.WIDTH - (this.manufacturersAvailable.elementAt(i).getName().length() * 16), 0, this.manufacturersAvailable.elementAt(i).getName());
				}
				else textUtilities.boxText(g, this.manufacturersAvailable.elementAt(i).getX(), this.manufacturersAvailable.elementAt(i).getY(), 4, Color.darkGray, Color.black, this.manufacturersAvailable.elementAt(i).getSymbol());
			}
		}
	}
	
	private void drawRegion(Graphics g){
		g.setColor(ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()).getColor());
		g.fillRect(32, 0, ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()).getName().length() * 16, 32);
		textUtilities.drawString(g, 32, 8, ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()).getName());
		g.setColor(Color.darkGray);
		g.fillRect(544, 0, 224, 32);
		textUtilities.drawString(g,
				768 - (ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()).getAccountBalance().toString().length() + 2) * 16,
				8,
				"$" + ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()).getAccountBalance().toString() + "K");
		g.drawImage(getActiveRegionMap(), 32, 32, null);
		g.setFont(sectorfont);
		g.setColor(Color.WHITE);
		// Prototype Render Locations
		for(int i = 0; i < mapLocations.size(); i++){
			if(mapLocations.elementAt(i).getLocationRegion() == activeRegion){
				g.drawImage(mapLocations.elementAt(i).getSprite(ab.getScenario()) , mapLocations.elementAt(i).getLocationX(), mapLocations.elementAt(i).getLocationY(), null);
			}
		}
		
		// TODO: Render Routes.
		
		drawMinimap(g);
		
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
	
	private void cycleOptionDown(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			for(int i = 0; i < manufacturersAvailable.size(); i++){
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(ab.getScenario()).size() > 0) maxOpt++;
			}
			if(selectedOption > 0) selectedOption--;
			else selectedOption = maxOpt - 1;
		}
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 6) selectedOption += 6;
		}
	}
	
	private void cycleOptionLeft(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			for(int i = 0; i < manufacturersAvailable.size(); i++){
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(ab.getScenario()).size() > 0) maxOpt++;
			}
			if(selectedOption > 0) selectedOption--;
			else selectedOption = maxOpt - 1;
		}
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
			maxOpt = selectedManufacturer.getModeslAvailable(ab.getScenario()).size() - 1;
			if(selectedOption > 0) selectedOption--;
		}
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 12){
				if(selectedOption == 6) return;
				else if(selectedOption == 0) return;
				else selectedOption--;
			}
		}
		if(regionVm == REGIONVM.VM_REGIONSWAP){
			maxOpt = 8;
			if(selectedOption > 0) selectedOption--;
		}
	}
	
	private void cycleOptionRight(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			for(int i = 0; i < manufacturersAvailable.size(); i++){
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(ab.getScenario()).size() > 0) maxOpt++;
			}
			if(selectedOption < maxOpt - 1) selectedOption++;
			else selectedOption = 0;
		}
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
			maxOpt = selectedManufacturer.getModeslAvailable(ab.getScenario()).size() - 1;
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
				if(manufacturersAvailable.elementAt(i).getModeslAvailable(ab.getScenario()).size() > 0) maxOpt++;
			}
			if(selectedOption < maxOpt - 1) selectedOption++;
			else selectedOption = 0;
		}
		if(this.regionVm == REGIONVM.VM_REGION){
			if (selectedOption > 5) selectedOption -= 6;
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
			if(regionVm == REGIONVM.VM_REGION) regionVm = REGIONVM.VM_REGIONSWAP;
			selectedOption = this.activeRegion;
			break;
		case KeyEvent.VK_DOWN:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionDown();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionDown();
			break;
			
		case KeyEvent.VK_ENTER:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
				previousOption = selectedOption;
//				regionVmPrevious = regionVm;
				selectMfg(selectedOption);
				regionVm = REGIONVM.VM_BUY_SELECT_MODEL;
				resetSelectedOpt();
			}
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
				//c.setConfirmVM(this, REGIONVM.VM_BUY_SELECT_MODEL, REGIONVM.VM_BUY_SELECT_QTY, 400, 400);
				c.setConfirmVM(this, REGIONVM.VM_BUY_SELECT_MODEL, REGIONVM.VM_BUY_SELECT_QTY, AstroBiz.employeeSprites.grabImage(1, 1, 128, 128), "Your run of the mill description would go here!");
				resetSelectedOpt();
			}
			else if(regionVm == REGIONVM.VM_REGION) processButton();
			else if(regionVm == REGIONVM.VM_REGIONSWAP){
				this.activeRegion = (byte)selectedOption;
				this.getActiveRegionMap();
				regionVm = REGIONVM.VM_REGION;
			}
			
			break;
		case KeyEvent.VK_ESCAPE:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
				regionVm = REGIONVM.VM_BUY_SELECT_MFG;
				selectedOption = previousOption;
			}
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
				regionVm = REGIONVM.VM_REGION;
				resetSelectedOpt();
			}
			else if(regionVm == REGIONVM.VM_REGIONSWAP){
				regionVm = REGIONVM.VM_REGION;
				resetSelectedOpt();
			}
			break;
			
		case KeyEvent.VK_LEFT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionLeft();
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) cycleOptionLeft();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionLeft();
			else if(regionVm == REGIONVM.VM_REGIONSWAP)cycleOptionLeft();
			break;
			
		case KeyEvent.VK_UP:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionUp();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionUp();
			break;
			
		case KeyEvent.VK_RIGHT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGIONSWAP)cycleOptionRight();
			break;
		default:
			break;
		}
	}

	private BufferedImage getActiveRegionMap(){
		if(this.activeRegion == 0) return AstroBiz.worldMap.grabImage(1, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 1) return AstroBiz.worldMap.grabImage(2, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 2) return AstroBiz.worldMap.grabImage(3, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 3) return AstroBiz.worldMap.grabImage(1, 2, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 4) return AstroBiz.worldMap.grabImage(2, 2, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 5) return AstroBiz.worldMap.grabImage(3, 2, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 6) return AstroBiz.worldMap.grabImage(3, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 7) return AstroBiz.worldMap.grabImage(3, 2, REGIONWIDTH, REGIONHEIGHT);
		else return AstroBiz.worldMap.grabImage(3, 3, REGIONWIDTH, REGIONHEIGHT);
	}
	
	public Vector<Location> getLocationVector(){
		return this.mapLocations;
	}
	
	private void processButton(){
		previousOption = selectedOption;
		// Open Route
		if(selectedOption == 1);
		// Adjust Route
		else if(selectedOption == 2);
		// Buy Vehicles
		else if(selectedOption == 3){
			this.manufacturersAvailable.clear();
			this.manufacturersAvailable = ab.getScenario().getManufacturersAvailable();
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
			ab.getScenario().endTurn();
			resetSelectedOpt();
		}
	}
	
	private void resetSelectedOpt(){
		this.selectedOption = 0;
	}
	
	private void selectMfg(int index){
		this.selectedManufacturer = this.manufacturersAvailable.elementAt(index);
	}

	@Override
	public void setVM(VM vm) {
		this.regionVm = (REGIONVM)vm;
	}

}
