package astroBiz;

public class Scenario {
	
	Scenario(){
	};
	
	private String scenarioName;	
	private String scenarioDescription;
	private int scenarioQuarter;
	private int scenarioCurrentYear;
	private int scenarioStartingYear;
	private int scenarioEndingYear;
	private int scenarioHubsRequired;
	
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