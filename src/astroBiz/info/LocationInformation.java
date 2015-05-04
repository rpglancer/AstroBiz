package astroBiz.info;

/**
 * Contains arrays of all available locations to be populated into a region.
 * Note that this is a terrible way of doing this and a more suitable way should be found.
 * @author Matt Bangert
 *
 */
public class LocationInformation {
	public enum LI {
		ERTH_LAX ("Los Angeles", "LAX"	, 2, 110,  43, 164, 150, 120,  8.0),
		ERTH_NYC ("New York", "NYC"		, 2, 145,  31, 180, 255, 112, 11.0),
		ERTH_HK ("Hong Kong", "HK"		, 2, 200,  64, 142, 625, 150,  9.0),
		ERTH_TKO ("Tokyo", "TKO"		, 2,  64,  27, 212, 285, 120, 18.0),
		ERTH_MBI ("Mumbai", "MBI"		, 2,  50, 144,  23, 550, 155, 20.0),
		ERTH_BEI ("Beijing", "BEI"		, 2, 220, 200, 101, 640, 115, 17.0),
		ERTH_LON ("London", "LON"		, 2, 172,  75, 224, 395,  95,  5.5),
		ERTH_SPO ("Sao Paolo", "SPO"	, 2, 100, 131, 197, 300, 230,  7.5),
		ERTH_JHB ("Johannesburg", "JHB"	, 2,  75,  50, 100, 445, 240,  0.4);
		
		String name;
		String id;
		int bus, ind, tour, r, x, y;
		double pop;
		
		LI(String name, String id, int r, int bus, int ind, int tour, int x, int y, double pop){
			this.name = name;
			this.id = id;
			this.r = r;
			this.bus = bus;
			this.ind = ind;
			this.x = x;
			this.y = y;
			this.pop = pop;
		}
		public String getName(){
			return this.name;
		}
		public String getID(){
			return this.id;
		}
		public int getRegion(){
			return this.r;
		}
		public int getDemandBusiness(){
			return this.bus;
		}
		public int getDemandIndustry(){
			return this.ind;
		}
		public int getDemandTourism(){
			return this.tour;
		}
		public int getX(){
			return this.x;
		}
		public int getY(){
			return this.y;
		}
		public double getPopulation(){
			return this.pop;
		}
		
	}
@Deprecated	
	public static String[] earthLocationNames = {"Los Angeles", "New York", "Hong Kong", "Tokyo", "Mumbai", "Beijing", "London", "Sao Paolo","Johannesburg"};
@Deprecated	
	public static int[] earthLocationDemandBusiness = {110, 145, 200, 64, 50, 220, 172, 100,75};
@Deprecated	
	public static int[] earthLocationDemandIndustry = {43, 31, 64, 27, 144, 200, 75, 131,50};
@Deprecated	
	public static int[] earthLocationDemandTourism = {164, 180, 142, 212, 23, 101, 224, 197,100};
@Deprecated
	public static int[] earthLocationX = {150, 255, 625, 685, 550, 640, 395, 300,445};
@Deprecated	
	public static int[] earthLocationY = {120, 112, 150, 120, 155, 115, 95,  230,240};
@Deprecated	
	public static double[] earthPopulation = {8.0,11.0,9.0,18.0,20.0,17.0,5.5,7.5,0.4};
@Deprecated
	public static String[] lunaLocationNames = {
		"Luna 1",
		"Luna 2", 
		"Luna 3", 
		"Luna 4", 
		"Luna 5", 
		"Luna 6", 
		"Luna 7", 
		"Luna 8"
		};
@Deprecated
	public static int[] lunaLocationDemandBusiness = {
		110, 
		145, 
		200, 
		64, 
		50, 
		220, 
		172, 
		100
		};
@Deprecated	
	public static int[] lunaLocationDemandIndustry = {
		43, 
		31, 
		64, 
		27, 
		144, 
		200, 
		75, 
		131
		};
@Deprecated
	public static int[] lunaLocationDemandTourism = {
		164, 
		180, 
		142, 
		212, 
		23, 
		101, 
		224, 
		197
		};
@Deprecated
	public static int[] lunaLocationX = {
		150, 
		255, 
		625, 
		685, 
		550, 
		640, 
		395, 
		300
		};
@Deprecated
	public static int[] lunaLocationY = {
		120, 
		112, 
		150, 
		120, 
		155, 
		115, 
		95,  
		230
		};

	
}
