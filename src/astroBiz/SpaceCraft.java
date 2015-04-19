package astroBiz;

/**
 * Contains all information pertinent to a space craft.
 * @author Matt Bangert
 *
 */
public class SpaceCraft {
	int craftAge;				// Age of craft
	int craftCapacity;			// Passenger Capacity of craft
	int craftCondition;			// Current condition of craft
	int craftFuel;				// Fuel cost of craft
	int craftMaintenanceCost;	// Maintenance cost of craft
	int craftRange;				// Range of craft
	int craftSpeed;
	int craftReliability;		// Reliability of craft
	String craftType;			// Type of craft
	
	SpaceCraft(String type){
		switch(type){
		case "shuttleSmall":
			craftType = "shuttleSmall";
			craftCapacity = 10;
			craftFuel = 250;
			craftRange = 2500;
			break;
		case "shuttleMedium":
			craftCapacity = 15;
			craftFuel = 325;
			craftRange = 3250;
			break;
		case "shuttleLarge":
			craftCapacity = 25;
			craftFuel = 440;
			craftRange = 4400;
			break;
		case "cruiserMedium":
			craftCapacity = 50;
			craftFuel = 900;
			craftRange = 9000;
			break;
		case "cruiserLarge":
			craftCapacity = 100;
			craftFuel = 1150;
			craftRange = 11500;
			break;
		case "extendedMedium":
			craftCapacity = 250;
			craftFuel = 2500;
			craftRange = 25000;
			break;
		case "extendedLarge":
			craftCapacity = 300;
			craftFuel = 2750;
			craftRange = 27500;
			break;
		}
	}
	
	public int getCraftCapacity(SpaceCraft sc){
		return sc.craftCapacity;
	}
	
	public int getCraftFuel(SpaceCraft sc){
		return sc.craftFuel;
	}
	
	public int getCraftRange(SpaceCraft sc){
		return sc.craftRange;
	}
	
	public String getCraftType(SpaceCraft sc){
		return sc.craftType;
	}
	
	public void setCraftCapacity(SpaceCraft sc, int capacity){
		sc.craftCapacity = capacity;
	}
	
	public void setCraftFuel(SpaceCraft sc, int fuel){
		sc.craftFuel = fuel;
	}
	
	public void setCraftRange(SpaceCraft sc, int range){
		sc.craftRange = range;
	}
	
	public void setCraftType(SpaceCraft sc, String type){
		sc.craftType = type;
	}
}
