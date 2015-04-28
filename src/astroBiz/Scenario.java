package astroBiz;

import java.util.Vector;

import astroBiz.info.ScenarioInformation;
import astroBiz.view.ScenarioView.DIFFICULTYSELECT;

/**
 * Contains the information about the currently active scenario.
 * @author Matt Bangert
 *
 */
public class Scenario {
	
	private String scenarioName;
	private String scenarioDescription;
	
	private int scenarioDifficultyLevel;
	private int scenarioCurrentYear;
	private int scenarioEndingYear;
	private int scenarioHubsRequired;
	private int scenarioQuarter;
	private int scenarioStartingYear;
	
	private Vector<Business> scenarioBusinesses = new Vector<Business>();
	
	Scenario(){
		for(int i = 0; i < 4; i++){
			Business b = new Business();
			scenarioBusinesses.addElement(b);
		}
	}
	
	public Vector<Business> getBusinesses(){
		return this.scenarioBusinesses;
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
	}
	
	public void setScenarioDifficulty(int difficulty){
		if(difficulty < 1 || difficulty > 4)
			this.scenarioDifficultyLevel = 1;
		else
			this.scenarioDifficultyLevel = difficulty;
	}
	
	public void setScenarioPlayers(int players){
		for(int i = 0; i < players; i++){
			this.scenarioBusinesses.elementAt(i).setBusinessPlayerOwned(true);
		}
	}
	
}