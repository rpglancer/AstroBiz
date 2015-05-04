package astroBiz;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

import astroBiz.info.LocationInformation;
import astroBiz.info.LocationInformation.LI;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location {	
	private String locationName;					// The location's name.
	private String locationID;
	
	private Boolean[] isHub = {false, false, false, false};
	
	private int locationDemandBusiness;				// The location's demand for business [0 - 255]
	private int locationDemandTourism;				// The location's demand for tourism  [0 - 255]
	private int locationDemandIndustry;				// The location's demand for industry [0 - 255]
	private int locationDevelopment;				// The location's developmental level [0 - 255]
	private int locationRegion;						// The region at which this location is situated
	private int locationSlotAvailable;				// The location's available slots for landing SpaceCraft on a Route
	private int locationSlotCost;					// The cost per slot to lease an available slot
	private int locationSlotTotal;					// The location's total slots
	private int locationX;							// The X coordinate of the location
	private int locationY;							// The Y coordinate of the location
	
	private LOCATIONTYPE locationType = LOCATIONTYPE.LT_TOWN;
	
	private double population;
	
	private static SpriteSheet spritesheet = AstroBiz.regionSprites;

	public static enum LOCATIONTYPE{
		LT_TOWN,
		LT_CITY;
	}
	
	public Location(LI li){
		this.locationName = li.getName();
		this.locationID = li.getID();
		this.locationRegion = li.getRegion();
		this.locationDemandBusiness = li.getDemandBusiness();
		this.locationDemandIndustry = li.getDemandIndustry();
		this.locationDemandTourism = li.getDemandTourism();
		this.locationX = li.getX();
		this.locationY = li.getY();
		this.population = li.getPopulation();
		if(this.population > 1.0) locationType = LOCATIONTYPE.LT_CITY;
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

	public int getLocationDemandBusiness(){
		return locationDemandBusiness;
	}

	public int getLocationDemandTourism(){
		return locationDemandTourism;
	}

	public int getLocationDemandIndustry(){
		return locationDemandIndustry;
	}

	public int getLocationDevelopment(){
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

	public int getLocationSlotAvailable(){
		return locationSlotAvailable;
	}

	public int getLocationSlotCost(){
		return locationSlotCost;
	}

	public int getLocationSlotTotal(){
		return locationSlotTotal;
	}

	public LOCATIONTYPE getLocationType(){
		return this.locationType;
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

@Deprecated
	private int locationRegionX;
@Deprecated
	private int locationRegionY;
@Deprecated
	private int locationSize;		
@Deprecated
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
@Deprecated	
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
@Deprecated
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
@Deprecated
	int getLocationRegionX(){
		return this.locationRegionX;
	}
@Deprecated
	int getLocationRegionY(){
		return this.locationRegionY;
	}
@Deprecated
int getLocationSize(){
	return locationSize;
}
}
