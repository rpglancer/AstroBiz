package astroBiz;

/**
 * Contains all information pertinent to a space craft.
 * @author Matt Bangert
 *
 */
public class SpaceCraft {
	private int craftAge = 0;				// Age of craft
	private int craftCapacity = 0;			// Passenger Capacity of craft
	private int craftCondition = 0;			// Current condition of craft
	private int craftFuel = 0;				// Fuel cost of craft
	private int craftMaintenanceCost = 0;	// Maintenance cost of craft
	private int craftRange = 0;				// Range of craft
	private int craftSpeed = 0;				// The speed at which the craft travels.
	private int craftReliability = 0;		// Reliability of craft
	private int craftYearIntroduced = 0;	// The year the craft was introduced by the Manufacturer.
	private int craftYearRetired = 0;		// The year production of the craft was ceased by the Manufacturer.
//	private String craftType;			// Type of craft
	
//	SpaceCraft(String type){
//	}
	
	public int getCraftCapacity(SpaceCraft sc){
		return sc.craftCapacity;
	}
	
	public int getCraftFuel(SpaceCraft sc){
		return sc.craftFuel;
	}
	
	public int getCraftRange(SpaceCraft sc){
		return sc.craftRange;
	}
	
//	public String getCraftType(SpaceCraft sc){
//		return sc.craftType;
//	}
	
	public int getYearIntroduced(){
		return this.craftYearIntroduced;
	}
	
	public int getYearRetired(){
		return this.craftYearRetired;
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
	
//	public void setCraftType(SpaceCraft sc, String type){
//		sc.craftType = type;
//	}
	
}
