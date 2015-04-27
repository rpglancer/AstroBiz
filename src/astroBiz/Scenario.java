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
	
	public int getCurrentYear(){
		return this.scenarioCurrentYear;
	}
	
	public void setScenario(int scenario){
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
			this.scenarioDifficultyLevel = 0;
		else
			this.scenarioDifficultyLevel = difficulty;
	}
}