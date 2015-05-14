package astroBiz.lib;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import astroBiz.info.CRAFTINFO;

/**
 * Contains all information pertinent to a space craft.
 * @author Matt Bangert
 *
 */
public class SpaceCraft implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8804768627180604957L;

	private BufferedImage sprite;
	
	private double range = 0;				// Range of craft
	
	private int age = 0;					// Age of craft
	private int capacity = 0;				// Passenger Capacity of craft
	private int cost = 0;					// Cost of craft
	private int fuelE;						// Fuel efficiency
	private int maintR = 0;					// Maintenance rating
	private int speed = 0;					// The speed at which the craft travels.
	private int yearIntroduced = 0;			// The year the craft was introduced by the Manufacturer.
	private int yearRetired = 0;			// The year production of the craft was ceased by the Manufacturer.
	
	private String desc;
	private String name;
	
	public SpaceCraft(CRAFTINFO ci){
		this.cost = ci.getCost();
		this.name = ci.getName();
		this.desc = ci.getDesc();
		this.yearIntroduced = ci.getIntro();
		this.yearRetired = ci.getRetire();
		this.capacity = ci.getCapacity();
		this.maintR = ci.getMaintR();
		this.range = ci.getRange();
		this.speed = ci.getSpeed();
		this.fuelE = ci.getFuelE();
	}
	
	public SpaceCraft(SpaceCraft sc){
		this.cost = sc.cost;
		this.name = sc.name;
		this.yearIntroduced = sc.yearIntroduced;
		this.yearRetired = sc.yearRetired;
		this.capacity = sc.capacity;
		this.maintR = sc.maintR;
		this.range = sc.range;
		this.speed = sc.speed;
		this.fuelE = sc.fuelE;
	}

	public int getAge(){
		return this.age;
	}
	
	public int getCapacity(){
		return this.capacity;
	}
	
	public int getCost(){
		return this.cost;
	}

	public String getDesc(){
		return this.desc;
	}
	
	public int getFuelE(){
		return this.fuelE;
	}
	public int getMaintR(){
		return this.maintR;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public BufferedImage getSprite(){
		return this.sprite;
	}
	
	public double getRange(){
		return this.range;
	}
			
	public int getYearIntroduced(){
		return this.yearIntroduced;
	}
	
	public int getYearRetired(){
		return this.yearRetired;
	}	
}
