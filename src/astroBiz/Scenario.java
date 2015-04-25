package astroBiz;

/**
 * Contains the information about the currently active scenario.
 * @author Matt Bangert
 *
 */
public class Scenario {
	
	private String scenarioName;	
	private String scenarioDescription;
	private int scenarioQuarter;
	private int scenarioCurrentYear;
	private int scenarioStartingYear;
	private int scenarioEndingYear;
	private int scenarioHubsRequired;
	
	Scenario(){
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
}