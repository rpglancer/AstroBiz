package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Vector;

import astroBiz.AstroBiz;
import astroBiz.info.ENTITY_TYPE;
import astroBiz.info.FontInformation;
import astroBiz.info.HALIGN;
import astroBiz.info.VALIGN;
import astroBiz.lib.Business;
import astroBiz.lib.Location;
import astroBiz.lib.Manufacturer;
import astroBiz.lib.Scenario;
import astroBiz.lib.SpaceCraft;
import astroBiz.lib.TextWindow;
import astroBiz.util.Confirmation;
import astroBiz.util.ImageUtilities;
import astroBiz.util.textUtilities;

public class RegionView implements Manager, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5748594383657019059L;
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
		VM_OPEN_ROUTE_DEST,
		VM_OPEN_ROUTE_LOC,
		VM_OPEN_ROUTE,
		VM_ORDER_CONFIRM,
		VM_REGION,
		VM_REGIONSWAP,;

		@Override
		public int getOpt() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	private REGIONVM regionVm = REGIONVM.VM_REGION;
	private Vector<Location> locationsAvailable = new Vector<Location>();
	private Vector<Manufacturer> manufacturersAvailable = new Vector<Manufacturer>();
	private Manufacturer selectedManufacturer;
	private Scenario scenario;
	private SpaceCraft selectedSpaceCraft;
	private byte activeRegion = 2;
	private int selectedLocation = -1;
	private int selectedOption = 0;
	private int previousOption = selectedOption;
	private Confirmation c = new Confirmation();
	private TextWindow textWin;
	
	private boolean isActive = false;
	private ENTITY_TYPE type = ENTITY_TYPE.VIEW_MANAGER;

	private AstroBiz ab;
	private BufferedImage[] buttons;	// Contains the buttons displayed on the regional map.
	
	private Font sectorfont = new Font("arial", Font.BOLD, 15);

	public RegionView(AstroBiz astrobiz, Scenario scenario){
		this.ab = astrobiz;
		buttons = new BufferedImage[12];
		int i = 0;
		for(int y = 1; y <= 4; y++){
			for(int x = 1; x <= 3; x++){
				buttons[i] = AstroBiz.regionButtons.grabImage(x, y, BUTTONWIDTH, BUTTONHEIGHT);
				i++;
			}
		}
		this.scenario = scenario;
	}
	
	public void tick(){
	}
	
	public void render(Graphics g){
		int x = 0;
		int y = 470;
		g.setColor(Color.white);
		textUtilities.drawStringMultiLine(g, FontInformation.debug, x, y, 800, regionVm.toString());
		
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) drawBuySelectModel(g);
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) drawBuySelectMfg(g);
		else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY) drawBuySelectModel(g);
		else if(regionVm == REGIONVM.VM_ORDER_CONFIRM) drawOrderConfirm(g);
		else if(regionVm == REGIONVM.VM_OPEN_ROUTE) drawOpenRoute(g);
		else if(regionVm == REGIONVM.VM_OPEN_ROUTE_DEST) drawOpenDest(g);
		else if(regionVm == REGIONVM.VM_OPEN_ROUTE_LOC) drawOpenLoc(g);
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
		
		Business b = ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness());
		
		g.setColor(Color.black);
		g.fillRect(sx, sy, width, height);
		
		//	Draw Planet Orbit Lines
		g.setColor(Color.DARK_GRAY);
		//	Region Neptune
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Uranus
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Saturn
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Jupiter
		g.drawOval(px, py, pwidth, pheight);
		px+=10; py+=10; pwidth-=20; pheight-=20;
		//	Region Mars
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Earth
		if(b.regionContainsHub(2))g.setColor(b.getColor());
		else g.setColor(Color.darkGray);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Venus
		if(b.regionContainsHub(1))g.setColor(b.getColor());
		else g.setColor(Color.darkGray);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		//	Region Mercury
		if(b.regionContainsHub(0))g.setColor(b.getColor());
		else g.setColor(Color.darkGray);
		g.drawOval(px, py, pwidth, pheight);
		px+=6; py+=6; pwidth-=12; pheight-=12;
		
		// Draw Planets
		//	Neptune
		g.setColor(Color.darkGray);
		g.fillOval(40, 368, 17, 17);
		g.setColor(Color.BLACK);
		g.fillOval(41, 369, 15, 15);
		//	Uranus
		g.setColor(Color.darkGray);
		g.fillOval(73, 424, 17, 17);
		g.setColor(Color.BLACK);
		g.fillOval(74, 425, 15, 15);
		//	Saturn
		g.setColor(Color.darkGray);
		g.fillOval(148, 400, 17, 17);
		g.setColor(Color.BLACK);
		g.fillOval(149, 401, 15, 15);
		//	Jupiter
		g.setColor(Color.darkGray);
		g.fillOval(117, 331, 22, 22);
		g.setColor(Color.BLACK);
		g.fillOval(118, 332, 20, 20);
		//	Mars
		g.setColor(Color.darkGray);
		g.fillOval(75, 395, 8, 8);
		g.setColor(Color.BLACK);
		g.fillOval(76, 396, 6, 6);
		//	Earth
		if(b.regionContainsHub(2))g.setColor(Color.white);
		else g.setColor(Color.darkGray);
		g.fillOval(135, 367, 8, 8);
		if(b.regionContainsHub(2))g.setColor(b.getColor());
		else g.setColor(Color.black);
		g.fillOval(136, 368, 6, 6);
	}
	
	private void drawBuySelectModel(Graphics g){
		SpaceCraft temp;
		if(c.getIsActive()) temp = selectedSpaceCraft;
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) temp = selectedManufacturer.getModeslAvailable(ab.getScenario()).elementAt(selectedOption);
		else temp = selectedSpaceCraft;
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
		strlen = m.stringWidth(temp.getCapacity() + "s");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 640 - strlen, 128, 32, temp.getCapacity() + "s");
		
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
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 512, 160, 32, "Maint:");
//		textUtilities.drawString(g, 512, 160 + 8, "M/R:");
		
		//	Reliability Value Box
		g.setColor(Color.black);
		g.fillRect(576, 160, 64, 32);
		g.setColor(Color.white);
		g.setFont(FontInformation.modelstat);
		strlen = m.stringWidth(temp.getMaintR() + "");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 640 - strlen, 160, 32, temp.getMaintR() + "");
		
		//	# in Use
		g.setColor(Color.gray);
		g.fillRect(384, 192, 64, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 384, 192, 32, "#USE");
		
		//	# in Use Value Box
		g.setColor(Color.black);
		g.fillRect(448, 192, 64, 32);
		g.setColor(Color.white);
		g.setFont(FontInformation.modelstat);
		strlen = m.stringWidth(busi.getCraftInService(temp) + "");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 512 - strlen, 192, 32, busi.getCraftInService(temp) + "");
		
		//	# Hangar
		g.setColor(Color.gray);
		g.fillRect(512, 192, 64, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 512, 192, 32, "#HGR");
		
		//	# in Hangar Value Box
		g.setColor(Color.black);
		g.fillRect(576, 192, 64, 32);
		g.setColor(Color.white);
		g.setFont(FontInformation.modelstat);
		strlen = m.stringWidth(busi.getCraftInHangar(temp) + "");
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 640 - strlen, 192, 32, busi.getCraftInHangar(temp) + "");
		
		//	Footer Box
		g.setColor(Color.gray);
		g.fillRect(160, 224, 480, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 160, 224, 32, "Introduced: " + temp.getYearIntroduced());
		g.setFont(FontInformation.modelheader);
		strlen = m.stringWidth("Cost: " + temp.getCost() + "K");
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 640 - strlen, 224, 32, "Cost: " + temp.getCost() + "K");
		
		//	Corporate Box
		g.setColor(busi.getColor());
		g.fillRect(352, 256, 288, 32);
		g.setColor(Color.white);
		g.setFont(FontInformation.modelheader);
		strlen = m.stringWidth("Balance: " + busi.getAccountBalance() + "K");
		textUtilities.drawStringCenterV(g, FontInformation.modelheader, 640 - strlen, 256, 32, "Balance: " + busi.getAccountBalance() + "K");
	
//		Manufacturer Representative
		if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
			if(!c.getIsActive()){
				if(!AstroBiz.getController().containsEntity(textWin)){
					textWin = new TextWindow("Nice to meet you. Which model are you interested in?", AstroBiz.employeeSprites.grabImage(1, 1, 128, 128));
					AstroBiz.getController().addEntity(textWin);
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
			if(!AstroBiz.getController().containsEntity(textWin)){
				textWin = new TextWindow("You can purchase a maximum of " + ab.getScenario().getMaxOrderQty(selectedSpaceCraft.getCost()) + " vehicles. How many would you like to purchase?", AstroBiz.employeeSprites.grabImage(1, 1, 128, 128));
				AstroBiz.getController().addEntity(textWin);
			}
		}
		
	}

	private void drawBuySelectQty(Graphics g){
		BufferedImage craftsprite = ImageUtilities.colorizeImage(AstroBiz.regionSprites.grabImage(2, 3, 16, 16), 
				new Color(AstroBiz.regionSprites.grabImage(2, 3, 16, 16).getRGB(5, 5)),
				ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()).getColor());
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
			if(this.manufacturersAvailable.elementAt(i).getModeslAvailable(ab.getScenario()).size() > 0){
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
	
	private void drawOpenDest(Graphics g){
		Location loc = locationsAvailable.elementAt(selectedOption);
		g.drawImage(getActiveRegionMap(), 32, 32, null);
		drawRegionLocations(g);
		g.setColor(Color.GREEN);
		g.drawOval(loc.getLocationX(), loc.getLocationY(), 16, 16);
	}
	
	private void drawOpenRoute(Graphics g){
		String s = "";
		Business busi = ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness());
		g.drawImage(getActiveRegionMap(), 32, 32, null);
		drawRegionLocations(g);
		
		if(busi.regionContainsHub(activeRegion)){
			s = "Route will depart from " + busi.getHub(activeRegion).getLocationName() + ". Select a destination for this route.";
		}
		else{
			s = "We currently have no hubs in this region.";
		}
		
		if(!AstroBiz.getController().containsEntity(textWin)){
			textWin = new TextWindow(s, AstroBiz.employeeSprites.grabImage(1, 1, 128, 128));
			AstroBiz.getController().addEntity(textWin);
		}
		
	}
	
	/**
	 * This is slot negotiation, not open route... hurrr.
	 * @param g
	 */
	private void drawOpenLoc(Graphics g){
		Location l = locationsAvailable.elementAt(selectedLocation);
		Font fnt1 = new Font("arial", Font.BOLD, 25);

		g.drawImage(l.getOwner().getFlag(), 64, 32, null);		//	Flag
		g.setColor(Color.CYAN);
		g.fillRect(64, 96, 288, 160); 			// City Drawing
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(352, 96, 384, 192);			// Business Ventures
		

		g.setColor(Color.GREEN);
		g.fillRect(64, 256, 288, 64);			// Slots : Flights Info
		//	Total Slots
		Rectangle totslot = new Rectangle(64,256, 144, 40);
		Rectangle slotcnt = new Rectangle(64,296, 144, 24);
		drawWindow(g, totslot, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, totslot, HALIGN.CENTER, VALIGN.MIDDLE, "Total Slots");
		drawWindow(g, slotcnt, Color.darkGray, Color.gray);
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
		drawWindow(g, pop, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.chitchat, pop, HALIGN.CENTER, VALIGN.MIDDLE, "POP");
		textUtilities.drawStringToBox(g, FontInformation.modelstat, popamt, HALIGN.CENTER, VALIGN.MIDDLE, l.getPopulation() + "M");


		drawWindow(g, busi, Color.darkGray, Color.gray);
		textUtilities.drawStringToBox(g, FontInformation.chitchat, busi, HALIGN.CENTER, VALIGN.MIDDLE, "BUSI");
		textUtilities.drawStringToBox(g, FontInformation.modelstat, busiRating, HALIGN.CENTER, VALIGN.MIDDLE, l.getLocationDemandBusiness()+"");
		
		// Draw Name On Top
		g.setColor(Color.WHITE);
		g.setFont(FontInformation.modelstat);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(l.getLocationName(), 160, 32+fm.getAscent());
		g.drawString(l.getOwner().getName(), 160, 64-fm.getDescent());
	}
	
	private void drawWindow(Graphics g, Rectangle r, Color bg, Color border){
		Graphics2D g2d = (Graphics2D)g;
		Color prev = g2d.getColor();
		g2d.setColor(bg);
		g2d.fill(r);
		g2d.setColor(border);
		g2d.draw(r);
		g2d.setColor(prev);
	}
	
	private void drawOrderConfirm(Graphics g){
		String s = "Thank you for your order of " + selectedOption + " " + selectedSpaceCraft.getName() + "s. Your order will be delivered in 3 months!";
		if(!AstroBiz.getController().containsEntity(textWin)){
			textWin = new TextWindow(s, AstroBiz.employeeSprites.grabImage(1, 1, 128, 128));
			AstroBiz.getController().addEntity(textWin);
		}
	}
	
	private void drawRegion(Graphics g){
		Scenario scen = ab.getScenario();
		Business busi = scen.getBusinesses().elementAt(scen.getActiveBusiness());
		g.setColor(busi.getColor());
		g.fillRect(32, 0, textUtilities.getTextWidth(g, FontInformation.modelstat, busi.getName()), 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelstat, 32, 0, 32, busi.getName());
		
		
		g.setColor(Color.darkGray);
		g.fillRect(544, 0, 224, 32);
		g.setColor(Color.white);
		textUtilities.drawStringCenterV(g, FontInformation.modelstat,
				800 - 32 - textUtilities.getTextWidth(g,
						FontInformation.modelstat,
						"Balance: " + busi.getAccountBalance().toString() + "K"),
						0,
						32,
						"Balance: " + busi.getAccountBalance().toString() + "K");

		g.drawImage(getActiveRegionMap(), 32, 32, null);
		g.setFont(sectorfont);
		g.setColor(Color.WHITE);
		// Prototype Render Locations
		drawRegionLocations(g);
		
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

	private void drawRegionLocations(Graphics g){
		for(int i = 0; i < scenario.getLocations().size(); i++){
			if(scenario.getLocations().elementAt(i).getLocationRegion() == activeRegion){
				g.drawImage(scenario.getLocations().elementAt(i).getSprite(scenario), scenario.getLocations().elementAt(i).getLocationX(), scenario.getLocations().elementAt(i).getLocationY(), null);
				if(scenario.getLocations().elementAt(i).getSlotAllocatedFor(scenario.getActiveBusiness()) > 0){
					g.setFont(FontInformation.modelstat);
					g.setColor(Color.white);
					g.drawString(scenario.getLocations().elementAt(i).getSlotAllocatedFor(scenario.getActiveBusiness()) + "", scenario.getLocations().elementAt(i).getLocationX() + 16, scenario.getLocations().elementAt(i).getLocationY() + 16);
				}
			}
		}
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
		if(selectedOption > 0) selectedOption--;
	/*	
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
		*/
	}
	
	private void cycleOptionRight(){
		int maxOpt = 0;
		if(regionVm == REGIONVM.VM_OPEN_ROUTE_DEST){
			maxOpt = locationsAvailable.size() - 1;
			if(selectedOption < maxOpt) selectedOption++;
		}
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
			previousOption = selectedOption;
			selectedOption = this.activeRegion;
			break;
		case KeyEvent.VK_DOWN:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionDown();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionDown();
			break;
			
		case KeyEvent.VK_ENTER:
			keyEnter();
			break;
			
		case KeyEvent.VK_ESCAPE:
			AstroBiz.getController().purge(ENTITY_TYPE.TEXT_WINDOW);
			if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
				regionVm = REGIONVM.VM_BUY_SELECT_MFG;
				selectedOption = previousOption;
			}
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
				regionVm = REGIONVM.VM_REGION;
				resetSelectedOpt();
			}
			else if(regionVm == REGIONVM.VM_OPEN_ROUTE){
				regionVm = REGIONVM.VM_REGION;
				selectedOption = previousOption;
			}
			else if(regionVm == REGIONVM.VM_REGIONSWAP){
				selectedOption = previousOption;
				regionVm = REGIONVM.VM_REGION;
			}
			break;
			
		case KeyEvent.VK_LEFT:
			cycleOptionLeft();
//			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionLeft();
//			else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) cycleOptionLeft();
//			else if(regionVm == REGIONVM.VM_REGION)cycleOptionLeft();
//			else if(regionVm == REGIONVM.VM_REGIONSWAP)cycleOptionLeft();
			break;
			
		case KeyEvent.VK_UP:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionUp();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionUp();
			break;
			
		case KeyEvent.VK_RIGHT:
			if(regionVm == REGIONVM.VM_BUY_SELECT_MFG) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_OPEN_ROUTE_DEST) cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGION)cycleOptionRight();
			else if(regionVm == REGIONVM.VM_REGIONSWAP)cycleOptionRight();
			break;
		default:
			break;
		}
	}
	
	private void keyEnter(){
		AstroBiz.getController().purge(ENTITY_TYPE.TEXT_WINDOW);
		if(regionVm == REGIONVM.VM_BUY_SELECT_MFG){
			previousOption = selectedOption;
			selectMfg(selectedOption);
			regionVm = REGIONVM.VM_BUY_SELECT_MODEL;
			resetSelectedOpt();
		}
		else if(regionVm == REGIONVM.VM_BUY_SELECT_MODEL){
			selectedSpaceCraft = selectedManufacturer.getModeslAvailable(ab.getScenario()).elementAt(selectedOption);
			c.setConfirmVM(this, REGIONVM.VM_BUY_SELECT_MODEL, REGIONVM.VM_BUY_SELECT_QTY, AstroBiz.employeeSprites.grabImage(1, 1, 128, 128), selectedSpaceCraft.getDesc());
			resetSelectedOpt();
		}
		else if(regionVm == REGIONVM.VM_BUY_SELECT_QTY){
			ab.getScenario().placeOrder(ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness()), this.selectedManufacturer, this.selectedSpaceCraft, this.selectedOption);
			regionVm = REGIONVM.VM_ORDER_CONFIRM;
		}
		else if(regionVm == REGIONVM.VM_OPEN_ROUTE){
			Business busi = ab.getScenario().getBusinesses().elementAt(ab.getScenario().getActiveBusiness());
			if(busi.regionContainsHub(activeRegion)){
				setLocationsAvailable();
				regionVm = REGIONVM.VM_OPEN_ROUTE_DEST;
			}
			else regionVm = REGIONVM.VM_REGION;
		}
		else if(regionVm == REGIONVM.VM_OPEN_ROUTE_DEST){
			selectedLocation = selectedOption;
			previousOption = selectedOption;
			selectedOption = 1;
			regionVm = REGIONVM.VM_OPEN_ROUTE_LOC;
		}
		else if(regionVm == REGIONVM.VM_OPEN_ROUTE_LOC){

		}
		else if(regionVm == REGIONVM.VM_ORDER_CONFIRM){
			resetSelectedOpt();
			regionVm = REGIONVM.VM_BUY_SELECT_MODEL;
		}
		else if(regionVm == REGIONVM.VM_REGION) processButton();
		else if(regionVm == REGIONVM.VM_REGIONSWAP){
			this.activeRegion = (byte)selectedOption;
			this.getActiveRegionMap();
			selectedOption = previousOption;
			regionVm = REGIONVM.VM_REGION;
		}	
	}

	private BufferedImage getActiveRegionMap(){
		if(this.activeRegion == 0) return AstroBiz.worldMap.grabImage(1, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 1) return AstroBiz.worldMap.grabImage(2, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 2) return AstroBiz.worldMap.grabImage(3, 1, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 3) return AstroBiz.worldMap.grabImage(1, 2, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 4) return AstroBiz.worldMap.grabImage(2, 2, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 5) return AstroBiz.worldMap.grabImage(3, 2, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 6) return AstroBiz.worldMap.grabImage(1, 3, REGIONWIDTH, REGIONHEIGHT);
		else if(this.activeRegion == 7) return AstroBiz.worldMap.grabImage(2, 3, REGIONWIDTH, REGIONHEIGHT);
		else return AstroBiz.worldMap.grabImage(3, 3, REGIONWIDTH, REGIONHEIGHT);
	}
	
	private void processButton(){
		previousOption = selectedOption;
		// Open Route
		if(selectedOption == 0){
			regionVm = REGIONVM.VM_OPEN_ROUTE;
			resetSelectedOpt();
		}
		//	Adjust Routes
		else if(selectedOption == 1){

		}
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

}
