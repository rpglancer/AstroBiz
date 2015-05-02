package astroBiz;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

import astroBiz.info.LocationInformation;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location {	
	private int locationDemandBusiness;				// The location's demand for business [0 - 255]
	private int locationDemandTourism;				// The location's demand for tourism  [0 - 255]
	private int locationDemandIndustry;				// The location's demand for industry [0 - 255]
	private int locationDevelopment;				// The location's developmental level [0 - 255]
	private String locationName;					// The location's name.
	private int locationSize;						// REPLACED WITH Location.population !!! DO NOT USE !!!
	private int locationSlotAvailable;				// The location's available slots for landing SpaceCraft on a Route
	private int locationSlotCost;					// The cost per slot to lease an available slot
	private int locationSlotTotal;					// The location's total slots
	private int locationRegion;						// The region at which this location is situated
	private int locationRegionX;
	private int locationRegionY;
	private int locationX;							// The X coordinate of the location
	private int locationY;							// The Y coordinate of the location
	private double population;
	
	private static enum LOCATIONTYPE{
		LT_TOWN,
		LT_CITY;
	}
	private LOCATIONTYPE locationType = LOCATIONTYPE.LT_TOWN;

	private Boolean[] isHub = {false, false, false, false};
	
	private static SpriteSheet spritesheet = null;
	
	
	/**
	 * Default constructor for a Location.
	 */
	public Location(){
		Location.spritesheet = AstroBiz.regionSprites;
	}

	public void generateLocation(int y, int x, int r){
		Random rand = new Random();
		this.locationDemandBusiness = rand.nextInt(255);
		this.locationDemandIndustry = rand.nextInt(255);
		this.locationDemandTourism = rand.nextInt(255);
		this.locationDevelopment = (this.locationDemandBusiness + this.locationDemandIndustry + this.getLocationDemandTourism()) / 3;
		this.locationName = "TODO: Names Generator";
		this.locationSize = this.locationDevelopment * 10;
		this.locationSlotTotal = this.locationDevelopment / 10;
		this.locationSlotAvailable = this.locationSlotTotal;
		this.locationSlotCost = 500;
		this.locationX = x;
		this.locationY = y;
		this.locationRegion = r;
	}
	
	// This will probably be a woefully terrible way of doing this
	// Look into making this work better.
	public void generateEarthLocation(int i){
		this.locationRegion = 2;
		this.locationX = LocationInformation.earthLocationX[i];
		this.locationY = LocationInformation.earthLocationY[i];
		this.locationName = LocationInformation.earthLocationNames[i];
		this.locationDemandBusiness = LocationInformation.earthLocationDemandBusiness[i];
		this.locationDemandIndustry = LocationInformation.earthLocationDemandIndustry[i];
		this.locationDemandTourism = LocationInformation.earthLocationDemandTourism[i];
		this.locationDevelopment = (this.locationDemandBusiness + this.locationDemandIndustry + this.locationDemandTourism) / 3;
		this.locationSize = this.locationDevelopment * 100;
		this.locationSlotTotal = this.locationDevelopment / 10;
		this.locationSlotAvailable = this.locationSlotTotal;
		this.locationSlotCost = 500;
		this.population = LocationInformation.earthPopulation[i];
		if(this.population > 1.0) locationType = LOCATIONTYPE.LT_CITY;
	}
	
	public void generateLunaLocation(int i){
		this.locationRegion = 3;
		this.locationX = LocationInformation.lunaLocationX[i];
		this.locationY = LocationInformation.lunaLocationY[i];
		this.locationName = LocationInformation.lunaLocationNames[i];
		this.locationDemandBusiness = LocationInformation.lunaLocationDemandBusiness[i];
		this.locationDemandIndustry = LocationInformation.lunaLocationDemandIndustry[i];
		this.locationDemandTourism = LocationInformation.lunaLocationDemandTourism[i];
		this.locationDevelopment = (this.locationDemandBusiness + this.locationDemandIndustry + this.locationDemandTourism) / 3;
		this.locationSize = this.locationDevelopment * 100;
		this.locationSlotTotal = this.locationDevelopment / 10;
		this.locationSlotAvailable = this.locationSlotTotal;
		this.locationSlotCost = 500;	
	}
	
	public void generateLocation(int region, Vector<Location> locationVector){
		if(region < 0 || region > 8)
			return;
		switch(region){
		case 2:
			for(int i = 0; i < 8; i++){
				generateEarthLocation(i);
			}
			break;
		case 3:
			for(int i = 0; i < 8; i++){
				generateLunaLocation(i);
			}
			break;
		default:
			break;
		}
			
	}
	
	public BufferedImage getSprite(Scenario s){
		if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getHQ()) return Location.spritesheet.grabImage(6, 1, 16, 16);
		for(int i = 0; i < s.getBusinesses().elementAt(s.getActiveBusiness()).getHubs().size(); i++){
			if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getHubs().elementAt(i)) return Location.spritesheet.grabImage(5, 1, 16, 16);
		}
		for(int i = 0; i < s.getBusinesses().elementAt(s.getActiveBusiness()).getRoutes().size(); i++){
			if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getRoutes().elementAt(i).getRouteDestination() && this.locationType == LOCATIONTYPE.LT_CITY){
				return Location.spritesheet.grabImage(4, 1, 16, 16);
			}
			else if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getRoutes().elementAt(i).getRouteDestination() && this.locationType == LOCATIONTYPE.LT_TOWN){
				return Location.spritesheet.grabImage(2, 1, 16, 16);
			}
		}
		if(this.locationType == LOCATIONTYPE.LT_TOWN) return Location.spritesheet.grabImage(1, 1, 16, 16);
		if(this.locationType == LOCATIONTYPE.LT_CITY) return Location.spritesheet.grabImage(3, 1, 16, 16);
		return Location.spritesheet.grabImage(1, 1, 16, 16);
	}

	int getLocationDemandBusiness(){
		return locationDemandBusiness;
	}

	int getLocationDemandTourism(){
		return locationDemandTourism;
	}

	int getLocationDemandIndustry(){
		return locationDemandIndustry;
	}

	int getLocationDevelopment(){
		return locationDevelopment;
	}

	public boolean getLocationIsHub(){
		for(int i = 0; i < 4; i++){
			if(isHub[i]) return isHub[i];
		}
		return false;
	}
	
	public String getLocationName(){
		return locationName;
	}	

	int getLocationSize(){
		return locationSize;
	}

	public int getLocationSlotAvailable(){
		return locationSlotAvailable;
	}

	public int getLocationSlotCost(){
		return locationSlotCost;
	}

	public int getLocationSlotTotal(){
		return locationSlotTotal;
	}

	public int getLocationX(){
		return locationX;
	}	

	public int getLocationY(){
		return locationY;
	}
	
	public int getLocationRegion(){
		return this.locationRegion;
	}

	int getLocationRegionX(){
		return this.locationRegionX;
	}
	
	int getLocationRegionY(){
		return this.locationRegionY;
	}
	
	void setLocationDemandBusiness(int demand){
		locationDemandBusiness = demand;
	}
	void setLocationDemandTourism(int demand){
		locationDemandTourism = demand;
	}
	void setLocationDemandIndustry(int demand){
		locationDemandIndustry = demand;
	}
	void setLocationDevelopment(int dev){
		locationDevelopment = dev;
	}
	public void setLocationIsHub(boolean tf, int business){
		this.isHub[business] = tf;
	}
	
	void setLocationName(String name){
		locationName = name;
	}
	void setLocationSize(int size){
		locationSize = size;
	}
	void setLocationSlotAvailable(int slots){
		locationSlotAvailable = slots;
	}
	void setLocationSlotCost(int cost){
		locationSlotCost = cost;
	}
	void setLocationSlotTotal(int slot){
		locationSlotTotal = slot;
	}
	
	void setLocationX(int x){
		locationX = x;
	}
	void setLocationY(int y){
		locationY = y;
	}
}
