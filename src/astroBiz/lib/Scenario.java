package astroBiz.lib;

import java.util.Vector;

import astroBiz.info.LOCINFO;
import astroBiz.info.ManufacturerInformation.MI;
import astroBiz.info.ScenarioInformation.SI;

/**
 * Contains the information about the currently active scenario.
 * @author Matt Bangert
 *
 */
public class Scenario {
	private AI ai = new AI();

	private String scenarioDescription;
	private String scenarioName;
	
	private int activeBusiness;
	private int scenarioCurrentYear;
	private int scenarioDifficultyLevel;
	private int scenarioEndingYear;
	private int scenarioHubsRequired;
	private int scenarioQuarter;
	private int scenarioStartingYear;
	
	private static Queue msgQueue = new Queue();
	
	private Vector<Business> scenarioBusinesses = new Vector<Business>();
	private Vector<Location> mapLocations = new Vector<Location>();
	private Vector<Manufacturer> scenarioManufacturers = new Vector<Manufacturer>();
	private Vector<Manufacturer> scenarioManufacturersAvailable = new Vector<Manufacturer>();
		
	public Scenario(){
		for(int i = 0; i < 4; i++){
			Business b = new Business();
			if(i == 0) b.setColor(255, 0, 0);
			if(i == 1) b.setColor(0, 255, 0);
			if(i == 2) b.setColor(0, 0, 255);
			if(i == 3) b.setColor(240, 140, 0);
			b.addToAccount(1500000);
			scenarioBusinesses.addElement(b);
		}
		for(LOCINFO li : LOCINFO.values()){
			mapLocations.addElement(new Location(li));
		}
		for(MI mi : MI.values()){
			scenarioManufacturers.addElement(new Manufacturer(mi));
		}
	}
	
	public void endTurn(){
		if(scenarioQuarter == 4){
			scenarioQuarter = 1;
			scenarioCurrentYear++;
		}
		else scenarioQuarter++;
		if(activeBusiness == 3) activeBusiness = 0;
		else activeBusiness++;
		processQueue();
	}
	
	public AI getAI(){
		return this.ai;
	}
	
	public int getActiveBusiness(){
		return this.activeBusiness;
	}
	
	public Vector<Business> getBusinesses(){
		return this.scenarioBusinesses;
	}

	public int getCurrentYear(){
		return this.scenarioCurrentYear;
	}

	public int getEndingYear(){
		return this.scenarioEndingYear;
	}

	public int getHubsRequired(){
		return this.scenarioHubsRequired;
	}
	
	public Vector<Location> getLocations(){
		return mapLocations;
	}
	
	public Vector<Manufacturer> getManufacturers(){
		return this.scenarioManufacturers;
	}
	
	public Vector<Manufacturer> getManufacturersAvailable(){
		updateManufacturersAvailable();
		return this.scenarioManufacturersAvailable;
	}
	
	public int getMaxOrderQty(int craftCost){
		int count = 0;
		while(craftCost * count < scenarioBusinesses.elementAt(activeBusiness).getAccountBalance()){
			count++;
			if(count == 10)return count;
		}
		return count;
	}

	public int getQuarter(){
		return this.scenarioQuarter;
	}

	public String getScenarioDescription(){
		return this.scenarioDescription;
	}
	
	public String getScenarioName(){
		return this.scenarioName;
	}
		
	public void loadScenario(int scenario){
		if(scenario == 0){
			scenarioName = SI.SCEN1.getName();
			scenarioDescription = SI.SCEN1.getDesc();
			scenarioStartingYear = SI.SCEN1.getYearStart();
			scenarioEndingYear = SI.SCEN1.getYearEnd();
			scenarioHubsRequired = 7;
			scenarioCurrentYear = scenarioStartingYear;
			scenarioQuarter = 1;
			updateManufacturersAvailable();
		}
		else if(scenario == 1){
			scenarioName = SI.SCEN2.getName();
			scenarioDescription = SI.SCEN2.getDesc();
			scenarioStartingYear = SI.SCEN2.getYearStart();
			scenarioEndingYear = SI.SCEN2.getYearEnd();
			scenarioHubsRequired = 7;
			scenarioCurrentYear = scenarioStartingYear;
			scenarioQuarter = 1;
			updateManufacturersAvailable();
		}
		else if(scenario == 2){
			scenarioName = SI.SCEN3.getName();
			scenarioDescription = SI.SCEN3.getDesc();
			scenarioStartingYear = SI.SCEN3.getYearStart();
			scenarioEndingYear = SI.SCEN3.getYearEnd();
			scenarioHubsRequired = 7;
			scenarioCurrentYear = scenarioStartingYear;
			scenarioQuarter = 1;
			updateManufacturersAvailable();
		}
		else if(scenario == 3){
			scenarioName = SI.SCEN4.getName();
			scenarioDescription = SI.SCEN4.getDesc();
			scenarioStartingYear = SI.SCEN4.getYearStart();
			scenarioEndingYear = SI.SCEN4.getYearEnd();
			scenarioHubsRequired = 7;
			scenarioCurrentYear = scenarioStartingYear;
			scenarioQuarter = 1;
			updateManufacturersAvailable();
		}
	}
	
	public void placeOrder(Business busi, Manufacturer mfg, SpaceCraft sc, int qty){
		msgQueue.addMsg(new Message(busi, mfg, sc, qty, this.getCurrentYear(), this.getQuarter()));
	}
	
	private void processQueue(){
		for(int i = 0; i < msgQueue.getQueueSize(); i++){
			Message msg = msgQueue.getMessage(i);
			switch(msg.getType()){
			case MSG_NEGOTIATE:
				break;
			case MSG_ORDER:
				if(msg.getBusiness() == this.scenarioBusinesses.elementAt(activeBusiness)){
					int qty = msg.getVar()[0];
					int year = msg.getVar()[1];
					int qtr = msg.getVar()[2];
					if(qtr < this.scenarioQuarter || year < this.scenarioCurrentYear){
						for(int add = 0; add < qty; add++){
							msg.getBusiness().addCraft(new SpaceCraft(msg.getCraft()));
						}
						msgQueue.removeMsg(msg);
					}
				}
				break;
			}
		}
	}
	
	public void setScenarioDifficulty(int difficulty){
		if(difficulty < 1 || difficulty > 4)
			this.scenarioDifficultyLevel = 1;
		else
			this.scenarioDifficultyLevel = difficulty;
		for(int i = 0; i < 4; i++){
			scenarioBusinesses.elementAt(i).subFromAccount((int)(scenarioBusinesses.elementAt(i).getAccountBalance() * (scenarioDifficultyLevel * 0.1)));
		}
	}
	
	public void setScenarioPlayers(int players){
		for(int i = 0; i < players; i++){
			this.scenarioBusinesses.elementAt(i).setPlayerOwned(true);
		}
	}
	/**
	 * Updates the Vector of available manufacturers, as some incorporate and some become insolvent during the course of the game.
	 * <br><br>
	 * This should be used during the loading of a Scenario and during a yearly roll-over.
	 */
	private void updateManufacturersAvailable(){
		scenarioManufacturersAvailable.clear();
		for(int i = 0; i < scenarioManufacturers.size(); i++){
			if(scenarioManufacturers.elementAt(i).getYearIncorporated() <= scenarioCurrentYear && scenarioManufacturers.elementAt(i).getYearDissolved() >= scenarioCurrentYear){
				scenarioManufacturersAvailable.addElement(scenarioManufacturers.elementAt(i));
			}
		}
	}
}