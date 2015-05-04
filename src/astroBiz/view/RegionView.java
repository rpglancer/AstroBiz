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
	private static enum REGIONBUTTON{
		RB_ROUTEOPEN,
		RB_ROUTEADJUST,
		RB_SLOTBID,
		RB_BUY,
		RB_BUDGET,
		RB_VENTURE,
		RB_HUBOPEN,
		RB_AD,
		RB_MEETING,
		RB_STATUS,
		RB_SETTING,
		RB_TURN;
	}
	private REGIONVM regionVm = REGIONVM.VM_REGION;
	private REGIONBUTTON regionButton = REGIONBUTTON.RB_ROUTEOPEN;
	private Vector<Location> mapLocations = new Vector<Location>();
	private Vector<Manufacturer> manufacturersAvailable = new Vector<Manufacturer>();
	private byte activeRegion = 2;
	private byte selectedMfg = 0;

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
// TODO: Find a way of populating the regions with locations that doesn't involve manual creation of every one.
		for(int l = 0; l < LocationInformation.earthLocationNames.length; l++){
			Location location = new Location();
			location.generateEarthLocation(l);
			mapLocations.addElement(location);
		}
		for(int l = 0; l < 8; l++){
			Location location = new Location();
			location.generateLunaLocation(l);
			mapLocations.addElement(location);
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
		g.setColor(Color.red);
		if(regionButton == REGIONBUTTON.RB_ROUTEOPEN){
			BufferedImage temp = buttons[0];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[0].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 192, 320, null);
		}
		if(regionButton == REGIONBUTTON.RB_ROUTEADJUST){
			BufferedImage temp = buttons[1];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[1].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 288, 320, null);
		}
		if(regionButton == REGIONBUTTON.RB_SLOTBID){
			BufferedImage temp = buttons[2];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[2].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 384, 320, null);
		}
		if(regionButton == REGIONBUTTON.RB_BUY){
			BufferedImage temp = buttons[3];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[3].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 480, 320, null);
		}
		if(regionButton == REGIONBUTTON.RB_BUDGET){
			BufferedImage temp = buttons[4];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[4].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 576, 320, null);
		}
		if(regionButton == REGIONBUTTON.RB_VENTURE){
			BufferedImage temp = buttons[5];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[5].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 672, 320, null);
		}
		if(regionButton == REGIONBUTTON.RB_HUBOPEN){
			BufferedImage temp = buttons[6];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[6].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 192, 384, null);
		}
		if(regionButton == REGIONBUTTON.RB_AD){
			BufferedImage temp = buttons[7];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[7].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 288, 384, null);
		}
		if(regionButton == REGIONBUTTON.RB_MEETING){
			BufferedImage temp = buttons[8];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[8].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 384, 384, null);
		}
		if(regionButton == REGIONBUTTON.RB_STATUS){
			BufferedImage temp = buttons[9];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[9].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 480, 384, null);
		}
		if(regionButton == REGIONBUTTON.RB_SETTING){
			BufferedImage temp = buttons[10];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[10].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 576, 384, null);
		}
		if(regionButton == REGIONBUTTON.RB_TURN){
			BufferedImage temp = buttons[11];
			temp = ImageUtilities.colorizeImage(temp, new Color(buttons[11].getRGB(48, 32)), Color.red);
			g.drawImage(temp, 672, 384, null);
		}
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
			if(i == selectedMfg){
				textUtilities.boxText(g, this.manufacturersAvailable.elementAt(i).getX(), this.manufacturersAvailable.elementAt(i).getY(), 4, Color.darkGray, Color.green, this.manufacturersAvailable.elementAt(i).getSymbol());
				textUtilities.drawString(g, AstroBiz.WIDTH - (this.manufacturersAvailable.elementAt(i).getName().length() * 16), 0, this.manufacturersAvailable.elementAt(i).getName());
			}
			else textUtilities.boxText(g, this.manufacturersAvailable.elementAt(i).getX(), this.manufacturersAvailable.elementAt(i).getY(), 4, Color.darkGray, Color.black, this.manufacturersAvailable.elementAt(i).getSymbol());
		}
	}
	
	private void cycleButtonDown(){
		if(regionButton == REGIONBUTTON.RB_ROUTEOPEN) regionButton = REGIONBUTTON.RB_HUBOPEN;
		else if(regionButton == REGIONBUTTON.RB_ROUTEADJUST) regionButton = REGIONBUTTON.RB_AD;
		else if(regionButton == REGIONBUTTON.RB_SLOTBID) regionButton = REGIONBUTTON.RB_MEETING;
		else if(regionButton == REGIONBUTTON.RB_BUY) regionButton = REGIONBUTTON.RB_STATUS;
		else if(regionButton == REGIONBUTTON.RB_BUDGET) regionButton = REGIONBUTTON.RB_SETTING;
		else if(regionButton == REGIONBUTTON.RB_VENTURE) regionButton = REGIONBUTTON.RB_TURN;
	}
	
	private void cycleButtonLeft(){	
		if(regionButton == REGIONBUTTON.RB_VENTURE) regionButton = REGIONBUTTON.RB_BUDGET;
		else if(regionButton == REGIONBUTTON.RB_BUDGET) regionButton = REGIONBUTTON.RB_BUY;
		else if(regionButton == REGIONBUTTON.RB_BUY) regionButton = REGIONBUTTON.RB_SLOTBID;
		else if(regionButton == REGIONBUTTON.RB_SLOTBID) regionButton = REGIONBUTTON.RB_ROUTEADJUST;
		else if(regionButton == REGIONBUTTON.RB_ROUTEADJUST) regionButton = REGIONBUTTON.RB_ROUTEOPEN;
		
		else if(regionButton == REGIONBUTTON.RB_TURN) regionButton = REGIONBUTTON.RB_SETTING;
		else if(regionButton == REGIONBUTTON.RB_SETTING) regionButton = REGIONBUTTON.RB_STATUS;
		else if(regionButton == REGIONBUTTON.RB_STATUS) regionButton = REGIONBUTTON.RB_MEETING;
		else if(regionButton == REGIONBUTTON.RB_MEETING) regionButton = REGIONBUTTON.RB_AD;
		else if(regionButton == REGIONBUTTON.RB_AD) regionButton = REGIONBUTTON.RB_HUBOPEN;
	}
	
	private void cycleButtonRight(){
		if(regionButton == REGIONBUTTON.RB_ROUTEOPEN) regionButton = REGIONBUTTON.RB_ROUTEADJUST;
		else if(regionButton == REGIONBUTTON.RB_ROUTEADJUST) regionButton = REGIONBUTTON.RB_SLOTBID;
		else if(regionButton == REGIONBUTTON.RB_SLOTBID) regionButton = REGIONBUTTON.RB_BUY;
		else if(regionButton == REGIONBUTTON.RB_BUY) regionButton = REGIONBUTTON.RB_BUDGET;
		else if(regionButton == REGIONBUTTON.RB_BUDGET) regionButton = REGIONBUTTON.RB_VENTURE;
		
		else if(regionButton == REGIONBUTTON.RB_HUBOPEN) regionButton = REGIONBUTTON.RB_AD;
		else if(regionButton == REGIONBUTTON.RB_AD) regionButton = REGIONBUTTON.RB_MEETING;
		else if(regionButton == REGIONBUTTON.RB_MEETING) regionButton = REGIONBUTTON.RB_STATUS;
		else if(regionButton == REGIONBUTTON.RB_STATUS) regionButton = REGIONBUTTON.RB_SETTING;
		else if(regionButton == REGIONBUTTON.RB_SETTING) regionButton = REGIONBUTTON.RB_TURN;
	}
	
	private void cycleButtonUp(){
		if(regionButton == REGIONBUTTON.RB_HUBOPEN) regionButton = REGIONBUTTON.RB_ROUTEOPEN;
		else if(regionButton == REGIONBUTTON.RB_AD) regionButton = REGIONBUTTON.RB_ROUTEADJUST;
		else if(regionButton == REGIONBUTTON.RB_MEETING) regionButton = REGIONBUTTON.RB_SLOTBID;
		else if(regionButton == REGIONBUTTON.RB_STATUS) regionButton = REGIONBUTTON.RB_BUY;
		else if(regionButton == REGIONBUTTON.RB_SETTING) regionButton = REGIONBUTTON.RB_BUDGET;
		else if(regionButton == REGIONBUTTON.RB_TURN) regionButton = REGIONBUTTON.RB_VENTURE;
	}
	
	private void cycleMfgNext(){
		if(selectedMfg < manufacturersAvailable.size() - 1){
			selectedMfg++;
		}
		else selectedMfg = 0;	
	}
	
	private void cycleMfgPrev(){
		if(selectedMfg > 0){
			selectedMfg--;
		}
		else selectedMfg = (byte) (manufacturersAvailable.size() - 1);
	}
	
	public void keyAction(KeyEvent e){
		switch(e.getKeyCode()){

		case KeyEvent.VK_DOWN:
			if(regionVm == REGIONVM.VM_REGION)cycleButtonDown();
			break;
		case KeyEvent.VK_ENTER:
			if(regionVm == REGIONVM.VM_REGION){
				if(regionButton == REGIONBUTTON.RB_BUY){
					this.manufacturersAvailable.clear();
					this.manufacturersAvailable = ab.getScenario().getManufacturersAvailable();
					regionVm = REGIONVM.VM_BUY_SELECT_MFG;
				}
			}
			break;
		case KeyEvent.VK_LEFT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleMfgPrev();
			else if(regionVm == REGIONVM.VM_REGION)cycleButtonLeft();
			break;
		case KeyEvent.VK_UP:
			if(regionVm == REGIONVM.VM_REGION)cycleButtonUp();
			break;
		case KeyEvent.VK_RIGHT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleMfgNext();
			else if(regionVm == REGIONVM.VM_REGION)cycleButtonRight();
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

}
