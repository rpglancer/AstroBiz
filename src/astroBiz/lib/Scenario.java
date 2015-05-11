package astroBiz.lib;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import astroBiz.info.FACTION;
import astroBiz.info.LOCINFO;
import astroBiz.info.MFGINFO;
import astroBiz.info.STANDING;
import astroBiz.info.ScenarioInformation.SI;

/**
 * Contains the information about the currently active scenario.
 * @author Matt Bangert
 *
 */
public class Scenario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7293537749085029522L;

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
	private Vector<Faction> scenarioFactions = new Vector<Faction>();
		
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
		for(FACTION f : FACTION.values()){
			scenarioFactions.addElement(new Faction(f));
		}
		for(LOCINFO li : LOCINFO.values()){
			mapLocations.addElement(new Location(li, this));
		}
		for(MFGINFO mfg : MFGINFO.values()){
			scenarioManufacturers.addElement(new Manufacturer(mfg, this));
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
	
	public Vector<Faction> getFactions(){
		return scenarioFactions;
	}

	public Vector<Location> getLocations(){
		return mapLocations;
	}
	
	public Vector<Location> getLocationsAvailable(int region){
		Vector<Location> available = new Vector<Location>();
		for(int i = 0; i < mapLocations.size(); i++){
			if(mapLocations.elementAt(i).getSlotAllocatedFor(activeBusiness) > 0 && 
			!scenarioBusinesses.elementAt(activeBusiness).getHubs().contains(mapLocations.elementAt(i))){
				available.addElement(mapLocations.elementAt(i));
			}
		}
		return available;
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
	
	public void allocateStartingCraft(){
		int craftForAllocation = 0;
		for(int i = 0; i < scenarioBusinesses.size(); i++){
			if(scenarioBusinesses.elementAt(i).getIsPlayerOwned()){
				if(scenarioDifficultyLevel == 1) craftForAllocation = 8;
				if(scenarioDifficultyLevel == 2) craftForAllocation = 5;
				if(scenarioDifficultyLevel == 3) craftForAllocation = 3;
				if(scenarioDifficultyLevel == 4) craftForAllocation = 0;
			}
			else{
				if(scenarioDifficultyLevel == 1) craftForAllocation = 3;
				if(scenarioDifficultyLevel == 2) craftForAllocation = 5;
				if(scenarioDifficultyLevel == 3) craftForAllocation = 8;
				if(scenarioDifficultyLevel == 4) craftForAllocation = 10;
			}
			System.out.println("Allocation: " + craftForAllocation);
			
			Vector<Manufacturer> mfg = new Vector<Manufacturer>();
			for(int bfs = 0; bfs < scenarioBusinesses.elementAt(i).getStandings().size(); bfs++){
				if(scenarioBusinesses.elementAt(i).getStandings().elementAt(bfs) == STANDING.ALLY ||
					scenarioBusinesses.elementAt(i).getStandings().elementAt(bfs) == STANDING.WARM){
					mfg.addElement(scenarioManufacturersAvailable.elementAt(bfs));
					System.out.println("Added " + scenarioManufacturersAvailable.elementAt(bfs).getName() +
							" with standing " + scenarioBusinesses.elementAt(i).getStandings().elementAt(bfs) + ".");
				}
			}
			do{
				if(mfg.size() == 0){
					System.out.println("ERR: allocatedStartingCraft() mfg Vector size of 0 ... Skipping.");
					break;
				}
				Random rng = new Random();
				int m = 0;
				if(mfg.size() > 1) m = rng.nextInt(mfg.size());
				int c = rng.nextInt(mfg.elementAt(m).getModeslAvailable(this).size());
				scenarioBusinesses.elementAt(i).addCraft(mfg.elementAt(m).getModeslAvailable(this).elementAt(c));
				craftForAllocation--;
			}while(craftForAllocation > 0);
		}
	}
	
	public void allocateStartingSlots(){
		int slotsForAllocation = 0;
		for(int i = 0; i < scenarioBusinesses.size(); i++){
			if(scenarioBusinesses.elementAt(i).getIsPlayerOwned()){
				if(scenarioDifficultyLevel == 1) slotsForAllocation = 20;
				if(scenarioDifficultyLevel == 2) slotsForAllocation = 15;
				if(scenarioDifficultyLevel == 3) slotsForAllocation = 10;
				if(scenarioDifficultyLevel == 4) slotsForAllocation = 5;
			}
			else{
				if(scenarioDifficultyLevel == 1) slotsForAllocation = 15;
				if(scenarioDifficultyLevel == 2) slotsForAllocation = 20;
				if(scenarioDifficultyLevel == 3) slotsForAllocation = 25;
				if(scenarioDifficultyLevel == 4) slotsForAllocation = 30;
			}
			//	Allocate Slots at the Businesses HQ
			scenarioBusinesses.elementAt(i).getHQ().setSlotAllocation(i, 15);
			
			Vector<Location> loc = new Vector<Location>();
			for(int l = 0; l < mapLocations.size(); l++){
				if(mapLocations.elementAt(l).getRegion() == scenarioBusinesses.elementAt(i).getHQ().getRegion() && mapLocations.elementAt(l) != scenarioBusinesses.elementAt(i).getHQ()){
					loc.addElement(mapLocations.elementAt(l));
				}
			}
			do{
				if(loc.size() == 0){
					System.out.println("ERR: allocateStartingSlots() loc Vector size of 0 ... Skipping.");
					break;
				}
				if(loc.size() == 1){
					loc.elementAt(0).setSlotAllocation(i, slotsForAllocation);
					slotsForAllocation = 0;
					break;
				}
				Random rand = new Random();
				int r = rand.nextInt(loc.size() - 1);
				int a = rand.nextInt(slotsForAllocation);
				loc.elementAt(r).setSlotAllocation(i, a);
				loc.removeElementAt(r);
				slotsForAllocation -= a;
			}while(slotsForAllocation > 0);
		}
	}
	
	public void setScenarioDifficulty(int difficulty){
		if(difficulty < 1 || difficulty > 4)
			this.scenarioDifficultyLevel = 1;
		else
			this.scenarioDifficultyLevel = difficulty;
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