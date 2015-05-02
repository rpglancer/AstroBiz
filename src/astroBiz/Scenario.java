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
	
	private int activeBusiness;
	
	private int scenarioDifficultyLevel;
	private int scenarioCurrentYear;
	private int scenarioEndingYear;
	private int scenarioHubsRequired;
	private int scenarioQuarter;
	private int scenarioStartingYear;
	
	private Vector<Business> scenarioBusinesses = new Vector<Business>();
	
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
	}
	
	public Vector<Business> getBusinesses(){
		return this.scenarioBusinesses;
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
	
}