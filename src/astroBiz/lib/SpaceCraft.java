package astroBiz.lib;

import java.awt.image.BufferedImage;

import astroBiz.info.CraftInformation.CI;

/**
 * Contains all information pertinent to a space craft.
 * @author Matt Bangert
 *
 */
public class SpaceCraft{
	// TODO: Remove craft from all these variable names.
	private int cost = 0;
	private String craftName;
	private int craftAge = 0;				// Age of craft
	private int craftCapacity = 0;			// Passenger Capacity of craft
	private int craftFuel = 0;				// Fuel cost of craft
	private int fuelE;						// Fuel efficiency
	private int maintenanceRating = 0;		// Maintenance cost of craft
	private double range = 0;				// Range of craft
	private int craftSpeed = 0;				// The speed at which the craft travels.
	private int craftYearIntroduced = 0;	// The year the craft was introduced by the Manufacturer.
	private int craftYearRetired = 0;		// The year production of the craft was ceased by the Manufacturer.
	private BufferedImage sprite;
	
	public SpaceCraft(CI ci){
		this.cost = ci.getCost();
		this.craftName = ci.getName();
		this.craftYearIntroduced = ci.getIntro();
		this.craftYearRetired = ci.getRetire();
		this.craftCapacity = ci.getCapacity();
		this.maintenanceRating = ci.getMaintR();
		this.range = ci.getRange();
		this.craftSpeed = ci.getSpeed();
		this.fuelE = ci.getFuelE();
	}
	public SpaceCraft(SpaceCraft sc){
		this.cost = sc.cost;
		this.craftName = sc.craftName;
		this.craftYearIntroduced = sc.craftYearIntroduced;
		this.craftYearRetired = sc.craftYearRetired;
		this.craftCapacity = sc.craftCapacity;
		this.maintenanceRating = sc.maintenanceRating;
		this.range = sc.range;
		this.craftSpeed = sc.craftSpeed;
		this.fuelE = sc.fuelE;
	}
	
	public int getCost(){
		return this.cost;
	}
	
	public int getCraftCapacity(){
		return this.craftCapacity;
	}
	
	public int getFuelE(){
		return this.fuelE;
	}
	public int getMaintR(){
		return this.maintenanceRating;
	}
@Deprecated	
	public int getCraftFuel(){
		return this.craftFuel;
	}
	
	public double getRange(){
		return this.range;
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
	
}
