package astroBiz;

import java.util.Vector;

import astroBiz.info.ManufacturerInformation;
import astroBiz.info.ManufacturerInformation.MI;
import astroBiz.info.ScenarioInformation;

/**
 * Contains the information about the currently active scenario.
 * @author Matt Bangert
 *
 */
public class Scenario {
	
	private String scenarioName;
	private String scenarioDescription;
	
	private int activeBusiness;
	
	private int scenarioDifficultyLevel;
	private int scenarioCurrentYear;
	private int scenarioEndingYear;
	private int scenarioHubsRequired;
	private int scenarioQuarter;
	private int scenarioStartingYear;
	
	private Vector<Business> scenarioBusinesses = new Vector<Business>();
	private Vector<Manufacturer> scenarioManufacturers = new Vector<Manufacturer>();
	private Vector<Manufacturer> scenarioManufacturersAvailable = new Vector<Manufacturer>();
	
	public Scenario(){
		for(int i = 0; i < 4; i++){
			Business b = new Business();
			if(i == 0) b.setColor(b, 255, 0, 0);
			if(i == 1) b.setColor(b, 0, 255, 0);
			if(i == 2) b.setColor(b, 0, 0, 255);
			if(i == 3) b.setColor(b, 240, 140, 0);
			b.addBusinessAccountBalance(1500000);
			scenarioBusinesses.addElement(b);
		}
		for(MI mi : MI.values()){
			scenarioManufacturers.addElement(new Manufacturer(mi));
		}
	}
	
	public Vector<Business> getBusinesses(){
		return this.scenarioBusinesses;
	}
	
	public Vector<Manufacturer> getManufacturers(){
		return this.scenarioManufacturers;
	}
	
	public Vector<Manufacturer> getManufacturersAvailable(){
		return this.scenarioManufacturersAvailable;
	}
	
	public int getActiveBusiness(){
		return this.activeBusiness;
	}
	
	public int getCurrentYear(){
		return this.scenarioCurrentYear;
	}
	
	public String getScenarioName(){
		return this.scenarioName;
	}
	
	public String getScenarioDescription(){
		return this.scenarioDescription;
	}
	
	public void loadScenario(int scenario){
		scenarioName = ScenarioInformation.scenarioInfoName[scenario];
		scenarioDescription = ScenarioInformation.scenarioInfoDescription[scenario];
		scenarioStartingYear = ScenarioInformation.scenarioInfoStartingYear[scenario];
		scenarioEndingYear = ScenarioInformation.scenarioInfoEndingYear[scenario];
		scenarioHubsRequired = ScenarioInformation.scenarioInfoHubsRequired[scenario];
		scenarioCurrentYear = scenarioStartingYear;
		scenarioQuarter = 1;
		updateManufacturersAvailable();
	}
	
	public void setScenarioDifficulty(int difficulty){
		if(difficulty < 1 || difficulty > 4)
			this.scenarioDifficultyLevel = 1;
		else
			this.scenarioDifficultyLevel = difficulty;
		for(int i = 0; i < 4; i++){
			scenarioBusinesses.elementAt(i).subBusinessAccountBalance((int)(scenarioBusinesses.elementAt(i).getAccountBalance() * (scenarioDifficultyLevel * 0.1)));
		}
	}
	
	public void setScenarioPlayers(int players){
		for(int i = 0; i < players; i++){
			this.scenarioBusinesses.elementAt(i).setBusinessPlayerOwned(true);
		}
	}
	
	/**
	 * Updates the Vector of available manufacturers
	 * <br>as some incorporate and some become insolvent
	 * <br>during the course of the game.
	 * <br><br>
	 * This should be used during the loading of a Scenario<br>
	 * and during a yearly rollover.
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