package astroBiz;

import java.awt.image.BufferedImage;

import astroBiz.info.CraftInformation.CI;

/**
 * Contains all information pertinent to a space craft.
 * @author Matt Bangert
 *
 */
public class SpaceCraft {
	private String craftName;
	private int craftAge = 0;				// Age of craft
	private int craftCapacity = 0;			// Passenger Capacity of craft
	private int craftFuel = 0;				// Fuel cost of craft
	private int craftMaintenanceRating = 0;	// Maintenance cost of craft
	private double craftRange = 0;				// Range of craft
	private int craftSpeed = 0;				// The speed at which the craft travels.
	private int craftYearIntroduced = 0;	// The year the craft was introduced by the Manufacturer.
	private int craftYearRetired = 0;		// The year production of the craft was ceased by the Manufacturer.
//	private String craftType;				// Type of craft
	private BufferedImage sprite;
	
//	SpaceCraft(String type){
//	}
	SpaceCraft(CI ci){
		this.craftName = ci.getName();
		this.craftYearIntroduced = ci.getIntro();
		this.craftYearRetired = ci.getRetire();
		this.craftCapacity = ci.getCapacity();
		this.craftMaintenanceRating = ci.getMaintR();
		this.craftRange = ci.getRange();
		this.craftSpeed = ci.getSpeed();
	}
	
	public int getCraftCapacity(SpaceCraft sc){
		return sc.craftCapacity;
	}
	
	public int getCraftFuel(SpaceCraft sc){
		return sc.craftFuel;
	}
	
	public double getCraftRange(SpaceCraft sc){
		return sc.craftRange;
	}
		
	public String getName(){
		return this.craftName;
	}
	
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
	
}
