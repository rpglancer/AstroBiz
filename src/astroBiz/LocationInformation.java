package astroBiz;


/**
 * Contains arrays of all available locations to be populated into a region.
 * Note that this is a terrible way of doing this and a more suitable way should be found.
 * @author Matt Bangert
 *
 */
public class LocationInformation {
	
	public static String[] earthLocationNames = {
		"Los Angeles",
		"New York", 
		"Hong Kong", 
		"Tokyo", 
		"Mumbai", 
		"Beijing", 
		"London", 
		"Sao Paolo"
		};
	
	public static int[] earthLocationDemandBusiness = {
		110, 
		145, 
		200, 
		64, 
		50, 
		220, 
		172, 
		100
		};
	
	public static int[] earthLocationDemandIndustry = {
		43, 
		31, 
		64, 
		27, 
		144, 
		200, 
		75, 
		131
		};
	
	public static int[] earthLocationDemandTourism = {
		164, 
		180, 
		142, 
		212, 
		23, 
		101, 
		224, 
		197
		};

	// TODO: Make an array of arrays for the Location X,Y
	public static int[] earthLocationX = {
		150, 
		255, 
		625, 
		685, 
		550, 
		640, 
		395, 
		300
		};
	
	public static int[] earthLocationY = {
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
