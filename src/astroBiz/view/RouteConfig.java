package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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
import astroBiz.lib.Route;
import astroBiz.lib.Scenario;
import astroBiz.lib.SpaceCraft;
import astroBiz.util.textUtilities;

/**
 * Manages configuration and implementation of Routes.
 * @author Matt Bangert
 *
 */
public class RouteConfig implements Manager {
	public static enum RCVM implements VM{
		SELECT_DEPART,
		SELECT_DEST,
		SELECT_CRAFT,
		SELECT_QTY,
		SELECT_FARE;

		@Override
		public int getOpt() {
			return 0;
		}
	}
	
	private double selectedFare = 1.0;
	private int previousOption = 0;
	private int selectedOption = 0;
	private int selectedQty = 0;
	
	private Boolean isActive = false;
	private Business busi;
	private Scenario scenario;
	private SpaceCraft selectedCraft;
	private RegionView rvm;
	private Route route;
	private ENTITY_TYPE type = ENTITY_TYPE.VIEW_MANAGER;
	private RCVM rcvm = RCVM.SELECT_DEPART;
	
	public RouteConfig(RegionView rvm){
		this.rvm = rvm;
		AstroBiz.getController().addEntity(this);
	}
	
	public void setActiveBusi(Business busi){
		this.busi = busi;
	}
	
	public void setScenario(Scenario scenario){
		this.scenario = scenario;
	}
	
	private void cycleLeft(){
		if(selectedOption > 0)
			selectedOption--;
	}
	
	private void cycleRight(){
		int maxOpt = 0;
		if(rcvm == RCVM.SELECT_CRAFT)
			maxOpt = busi.getCraftTypes().size() - 1;
		if(rcvm == RCVM.SELECT_DEST)
			maxOpt = scenario.getLocationsAvailable(rvm.getActiveRegion()).size() - 1;
		if(rcvm == RCVM.SELECT_FARE)
			maxOpt = 22;
		if(rcvm == RCVM.SELECT_QTY)
			maxOpt = busi.getCraftInHangar(selectedCraft);	
		if(selectedOption < maxOpt)
			selectedOption++;
	}

	private void selectCraft(Graphics g){
		Vector<SpaceCraft> avail = busi.getCraftTypes();
		if(avail.size() == 0){
			AstroBiz.textWin.updateText("We currently do not own any vehicles.");
		}
		else{
			Draw.drawSpaceCraftModelStats(g, avail.elementAt(selectedOption), busi);
			AstroBiz.textWin.updateText("Select a vehicle to use for this route.");
		}
	}
	
	private void selectDepart(Graphics g){
		String s = "";
		Draw.drawRegion(g, rvm.getActiveRegion());
		Draw.drawRegionLocations(g, scenario, rvm.getActiveRegion());
		
		if(busi.regionContainsHub(rvm.getActiveRegion())){
			route = new Route();
			route.setRouteHome(busi.getHub(rvm.getActiveRegion()));
			s = "Route will depart from " + busi.getHub(rvm.getActiveRegion()).getName() + ". Select a destination for this route.";
		}
		else{
			s = "We currently have no hubs in this region.";
		}
		
		if(!AstroBiz.textWin.isActive()){
			AstroBiz.textWin.updateText(s);
			AstroBiz.textWin.setActive(true);
		}
	}
	
	private void selectDest(Graphics g){
		if(scenario.getLocationsAvailable(rvm.getActiveRegion()).size() == 0){
			Draw.drawRegion(g, rvm.getActiveRegion());
			Draw.drawRegionLocations(g, scenario, rvm.getActiveRegion());
			if(!AstroBiz.textWin.isActive()){
				AstroBiz.textWin.updateText("We currently have no slots available in this region.");
				AstroBiz.textWin.setActive(true);
			}		
		}
		else{
			Location loc = scenario.getLocationsAvailable(rvm.getActiveRegion()).elementAt(selectedOption);
			Draw.drawRegion(g, rvm.getActiveRegion());
			Draw.drawRegionLocations(g, scenario, rvm.getActiveRegion());
			g.setColor(Color.GREEN);
			g.drawOval(loc.getX(), loc.getY(), 16, 16);
			g.setColor(Color.darkGray);
			Rectangle r = new Rectangle(32,12,736,328);
			g.setColor(Color.white);
			g.drawImage(loc.getFaction().getFlag(), 32 + textUtilities.getTextWidth(g, FontInformation.modelheader, "DPRT: ["+route.getRouteHome().getID()+"] " + route.getRouteHome().getName()), 12, 28, 16, null);
			g.setColor(Color.cyan);
			g.drawRect(32 + textUtilities.getTextWidth(g, FontInformation.modelheader, "DPRT: ["+route.getRouteHome().getID()+"] " + route.getRouteHome().getName()), 12, 28, 16);
			g.setColor(Color.white);
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.LEFT, VALIGN.TOP, "DPRT: ["+route.getRouteHome().getID()+"] " + route.getRouteHome().getName());
			double distance = 0;
			String s = "";
			if(route.calcDistance(route.getRouteHome(), loc) < 0.001){
				distance = (int)(route.calcDistance(route.getRouteHome(), loc) * 150000000);
				s = distance + "KM";
			}
			else{
				distance = route.calcDistance(route.getRouteHome(), loc);
				DecimalFormat df = new DecimalFormat("#.####");
				String trunc = df.format(distance);
				s = trunc + "AU";
			}
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.RIGHT, VALIGN.TOP, "Distance: " + s);
			textUtilities.drawStringToBox(g, FontInformation.modelheader, r, HALIGN.RIGHT, VALIGN.BOTTOM, "ARIV: ["+loc.getID()+"] " + loc.getName());	
			String st = "Route will depart from " + route.getRouteHome().getName() + ", arrive at " + loc.getName()
					+ ", cross " + s + " and cost $" + route.calcCostToOpen(route.getRouteHome(), loc, busi) + "K to open.";
			AstroBiz.textWin.updateText(st);
		}
	}
	
	private void selectQty(Graphics g){
		int x = 160 - 8;
		int y = 96 - 8;
		int w = 480 + 16;
		int h = 128 + 16;
		Rectangle stat = new Rectangle();
		Rectangle window = new Rectangle(x,y,w,h);
		for(int i = 0; i <= 3; i++){
			if(i < 3)
				Draw.drawWindow(g, window, Color.black, busi.getColor());
			x+=2;
			y+=2;
			w-=4;
			h-=4;
			window.setBounds(x, y, w, h);
			}
		g.setColor(Color.white);
//		Selected Model Picture Box
		stat.setBounds(160, 96, 192, 128);
		Draw.drawWindow(g, stat, Color.blue, Color.white);
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.LEFT, VALIGN.BOTTOM, selectedCraft.getName());
		textUtilities.drawStringToBox(g, FontInformation.modelstat, stat, HALIGN.RIGHT, VALIGN.BOTTOM, "x"+selectedOption);
//		Sales		
		stat.setBounds(384, 96, 64, 32);
		Draw.drawWindow(g, stat, Color.magenta, Color.green);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "SALES");
//		Load		
		stat.setBounds(384, 128, 64, 32);
		Draw.drawWindow(g, stat, Color.magenta, Color.green);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "LOAD");
//		Fare		
		stat.setBounds(384, 160, 64, 32);
		Draw.drawWindow(g, stat, Color.magenta, Color.green);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "FARE");
//		Flights		
		stat.setBounds(384, 192, 64, 32);
		Draw.drawWindow(g, stat, Color.magenta, Color.green);
		textUtilities.drawStringToBox(g, FontInformation.modelheader, stat, HALIGN.CENTER, VALIGN.MIDDLE, "FLTS");
		x = 448;
		y = 192;
		w = h = 16;
		if(rcvm == RCVM.SELECT_FARE){
			Draw.drawFareSelect(g, route,selectedOption, 454, 160);
			for(int i = 0; i < route.calcWeeklyFlights(selectedCraft, selectedQty); i++){
				g.drawImage(AstroBiz.regionSprites.grabImage(3, 4, w, h), x, y, null);
				x+=w;
				if(i == 6){
					x = 448;
					y += h;
				}
			}
			AstroBiz.textWin.updateText("Select the fare for this route.");
		}
		else if(rcvm == RCVM.SELECT_QTY){
			for(int i = 0; i < route.calcWeeklyFlights(selectedCraft, selectedOption); i++){
				g.drawImage(AstroBiz.regionSprites.grabImage(3, 4, w, h), x, y, null);
				x+=w;
				if(i == 6){
					x = 448;
					y += h;
				}
			}
			AstroBiz.textWin.updateText("Select the number of vehicles to be used on this route.");
		}
	}
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public ENTITY_TYPE getType() {
		return type;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void render(Graphics g) {
		Color prevC = g.getColor();
		Font prevF = g.getFont();
		g.setFont(FontInformation.debug);
		g.setColor(Color.white);
		g.drawString(rcvm.toString(), 0, 8);
		g.setColor(prevC);
		g.setFont(prevF);
		switch(rcvm){
		case SELECT_CRAFT:
			selectCraft(g);
			break;
		case SELECT_DEPART:
			selectDepart(g);
			break;
		case SELECT_DEST:
			selectDest(g);
			break;
		case SELECT_FARE:
			selectQty(g);
			break;
		case SELECT_QTY:
			selectQty(g);
			break;
		default:
			break;
		}
	}

	@Override
	public void setActive(boolean b) {
		this.isActive = b;
	}

	@Override
	public void keyAction(KeyEvent e) {
		switch(e.getKeyCode()){

		case KeyEvent.VK_ENTER:
			if(rcvm == RCVM.SELECT_CRAFT){
				if(busi.getCraftAvailable(busi.getCraftTypes().elementAt(selectedOption))){
					selectedCraft = busi.getCraftTypes().elementAt(selectedOption);
					previousOption = selectedOption;
					rcvm = RCVM.SELECT_QTY;
					selectedOption = 1;
				}
			}
			else if(rcvm == RCVM.SELECT_DEPART){
				if(route.getRouteHome() != null){
					rcvm = RCVM.SELECT_DEST;
				}
				else{
					setActive(false);
					AstroBiz.getRegion().setActive(true);
				}
			}
			else if(rcvm == RCVM.SELECT_DEST){
				if(scenario.getLocationsAvailable(rvm.getActiveRegion()).size() > 0){
					route.setRouteDestination(scenario.getLocationsAvailable(rvm.getActiveRegion()).elementAt(selectedOption));
					selectedOption = 0;
					rcvm = RCVM.SELECT_CRAFT;
				}
			}
			else if(rcvm == RCVM.SELECT_QTY){
				selectedQty = selectedOption;
				selectedOption = 4;
				rcvm = RCVM.SELECT_FARE;
			}
			break;
		case KeyEvent.VK_ESCAPE:
			if(rcvm == RCVM.SELECT_QTY)
				selectedOption = previousOption;
				rcvm = RCVM.SELECT_CRAFT;
				break;
		case KeyEvent.VK_LEFT:
			cycleLeft();
			break;
		case KeyEvent.VK_RIGHT:
			cycleRight();
			break;
		}
	}

	@Override
	public VM getVM() {
		return rcvm;
	}

	@Override
	public void setVM(VM vm) {
		rcvm = (RCVM)vm;
	}
}
