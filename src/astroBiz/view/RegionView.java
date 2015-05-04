package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.Location;
import astroBiz.Manufacturer;
import astroBiz.info.LocationInformation;
import astroBiz.info.LocationInformation.LI;
import astroBiz.util.ImageUtilities;
import astroBiz.util.textUtilities;

public class RegionView {
	private static final int REGIONWIDTH = 736;
	private static final int REGIONHEIGHT = 288;
	private static final int BUTTONHEIGHT = 64;
	private static final int BUTTONWIDTH = 96;
	
	private static enum REGIONVM{
		VM_BRIEFING,
		VM_BUY,
		VM_BUY_SELECT_MFG,
		VM_REGION,
	}

	private REGIONVM regionVm = REGIONVM.VM_REGION;
	private REGIONVM regionVmPrevious = regionVm;
	private Vector<Location> mapLocations = new Vector<Location>();
	private Vector<Manufacturer> manufacturersAvailable = new Vector<Manufacturer>();
	private byte activeRegion = 2;
	private int selectedOption = 0;
	private int previousOption = selectedOption;

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
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) drawBuySelectMfg(g);
		else if(regionVm == REGIONVM.VM_REGION){
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
		
		g.dispose();
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
	
	private void drawBuySelectMfg(Graphics g){
		g.setColor(Color.white);
		for(int i = 0; i < this.manufacturersAvailable.size(); i++){
			if(i == selectedOption){
				textUtilities.boxText(g, this.manufacturersAvailable.elementAt(i).getX(), this.manufacturersAvailable.elementAt(i).getY(), 4, Color.darkGray, Color.green, this.manufacturersAvailable.elementAt(i).getSymbol());
				textUtilities.drawString(g, AstroBiz.WIDTH - (this.manufacturersAvailable.elementAt(i).getName().length() * 16), 0, this.manufacturersAvailable.elementAt(i).getName());
			}
			else textUtilities.boxText(g, this.manufacturersAvailable.elementAt(i).getX(), this.manufacturersAvailable.elementAt(i).getY(), 4, Color.darkGray, Color.black, this.manufacturersAvailable.elementAt(i).getSymbol());
		}
	}
	
	private void cycleOptionDown(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			maxOpt = manufacturersAvailable.size() - 1;
			if(selectedOption < maxOpt) selectedOption++;
			else selectedOption = 0;
		}
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 6) selectedOption += 6;
		}
	}
	
	private void cycleOptionLeft(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			maxOpt = manufacturersAvailable.size() - 1;
			if(selectedOption > 0) selectedOption--;
			else selectedOption = maxOpt;
			
		}
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 12){
				if(selectedOption == 6) return;
				else if(selectedOption == 0) return;
				else selectedOption--;
			}
		}
	}
	
	private void cycleOptionRight(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			maxOpt = manufacturersAvailable.size() - 1;
			if(selectedOption < maxOpt) selectedOption++;
			else selectedOption = 0;
		}
		if(regionVm == REGIONVM.VM_REGION){
			if(selectedOption < 12){
				if(selectedOption == 5) return;
				else if(selectedOption == 11) return;
				else selectedOption++;
			}
		}
	}
	
	private void cycleOptionUp(){
		int maxOpt = 0;
		if(this.regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			maxOpt = manufacturersAvailable.size() - 1;
			if(selectedOption > 0) selectedOption--;
			else selectedOption = maxOpt;
		}
		if(this.regionVm == REGIONVM.VM_REGION){
			if (selectedOption > 5) selectedOption -= 6;
		}
	}
	
	public void keyAction(KeyEvent e){
		switch(e.getKeyCode()){

		case KeyEvent.VK_DOWN:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionDown();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionDown();
			break;
		case KeyEvent.VK_ENTER:
			if(regionVm == REGIONVM.VM_REGION){
				previousOption = selectedOption;
				regionVmPrevious = regionVm;
				if(selectedOption == 3){	// Buy Vehicles
					this.manufacturersAvailable.clear();
					System.out.println("manufacturersAvailable Vector cleared!");
					this.manufacturersAvailable = ab.getScenario().getManufacturersAvailable();
					System.out.println("Got " + this.manufacturersAvailable.size() + " elements for manufacturersAvailable Vector!");
					regionVm = REGIONVM.VM_BUY_SELECT_MFG;
					resetSelectedOpt();
				}
			}	
			break;
		case KeyEvent.VK_ESCAPE:
			regionVm = regionVmPrevious;
			selectedOption = previousOption;
			break;
		case KeyEvent.VK_LEFT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionLeft();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionLeft();
			break;
		case KeyEvent.VK_UP:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionUp();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionUp();
			break;
		case KeyEvent.VK_RIGHT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionRight();
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
	
	private void resetSelectedOpt(){
		this.selectedOption = 0;
	}

}
