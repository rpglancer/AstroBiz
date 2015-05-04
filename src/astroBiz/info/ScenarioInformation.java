package astroBiz.info;

/**
 * Scenario Constants
 * <br>
 * Contains all information for selectable scenarios.
 * @author Matt Bangert
 *
 */
public class ScenarioInformation {
	
	public static enum SI{
		SCEN1 (scenarioInfoName[0], scenarioInfoDescription[0], 2200, 2230),
		SCEN2 (scenarioInfoName[1], scenarioInfoDescription[1], 2225, 2240),
		SCEN3 (scenarioInfoName[2], scenarioInfoDescription[2], 2240, 2270),
		SCEN4 (scenarioInfoName[3], scenarioInfoDescription[3], 2265, 2295);
			
		private final String name;
		private final String desc;
		private final int yearStart;
		private final int yearEnd;
		
		SI(String name, String desc, int yearStart, int yearEnd){
			this.name = name;
			this.desc = desc;
			this.yearStart = yearStart;
			this.yearEnd = yearEnd;
		}
		
		public String getDesc(){
			return this.desc;
		}
		public String getName(){
			return this.name;
		}
		public int getYearEnd(){
			return this.yearEnd;
		}
		public int getYearStart(){
			return this.yearStart;
		}
	}
	

	public static String[] scenarioInfoName = {
			"Scenario 1: The New Dawn",
			"Scenario 2: Scenario Name Number 2",
			"Scenario 3: Scenario Name Number 3",
			"Scenario 4: Scenario Name Number 4"
	};
	
	public static final String[] scenarioInfoDescription = {
			"After a century of conflict the human race once again sets its eyes upon the stars. Offworld mining and trading begins to flourish as the demand for resources vastly outpaces the ability of the Earth to provide them. The empires of humanity once again embark on campaigns of conquest to determine which of them will control the vast resources of the solar system.",
			"Description 2",
			"Description 3",
			"Description 4"
	};

	@Deprecated
	public static final int[] scenarioInfoStartingYear = {
			2200,
			2225,
			2240,
			2265
	};
	
	@Deprecated
	public static final int[] scenarioInfoEndingYear = {
			2230,
			2255,
			2270,
			2295
	};
	
	@Deprecated
	public static final int[] scenarioInfoHubsRequired = {
			3,
			5,
			7,
			9
	};
	
	@Deprecated
	public static final String[] scenarioInfoMapPath = {
		"../../data/astrobizmap.png",
		"../../data/astrobizmap.png",
		"../../data/astrobizmap.png",
		"../../data/astrobizmap.png"
	};
}
