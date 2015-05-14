package astroBiz.view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import astroBiz.AstroBiz;
import astroBiz.info.ENTITY_TYPE;
import astroBiz.lib.Business;
import astroBiz.lib.Draw;
import astroBiz.lib.Scenario;

public class RouteConfig implements Manager {
	public static enum RCVM implements VM{
		SELECT_DEST,
		SELECT_CRAFT,
		SELECT_QTY,
		SELECT_FARE;

		@Override
		public int getOpt() {
			return 0;
		}
	}
	
	private Boolean isActive = false;
	private Business busi;
	private Scenario scenario;
	private RegionView rvm;
	private ENTITY_TYPE type = ENTITY_TYPE.VIEW_MANAGER;
	private RCVM rcvm = RCVM.SELECT_DEST;
	
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
	
	private void selectDest(Graphics g){
		String s = "";
		Draw.drawRegion(g, rvm.getActiveRegion());
		Draw.drawRegionLocations(g, scenario, rvm.getActiveRegion());
		
		if(busi.regionContainsHub(rvm.getActiveRegion())){
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
		switch(rcvm){
		case SELECT_CRAFT:
			break;
		case SELECT_DEST:
			selectDest(g);
			break;
		case SELECT_FARE:
			break;
		case SELECT_QTY:
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
		// TODO Auto-generated method stub		
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
