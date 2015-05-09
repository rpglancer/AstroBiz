package astroBiz.lib;
import java.awt.image.BufferedImage;

import astroBiz.AstroBiz;
import astroBiz.info.FACILITY;
import astroBiz.info.FACTION;
import astroBiz.info.LOCINFO;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location {	

	@Deprecated
	private Boolean[] isHub = {false, false, false, false};
	
	private BufferedImage factionFlag;
	
	private Business isHqOf = null;
	private Business isHubOf = null;
	
	private double population;
	
	private int locationDemandBusiness;				// The location's demand for business [0 - 255]
	private int locationDemandTourism;				// The location's demand for tourism  [0 - 255]
	private int locationDemandIndustry;				// The location's demand for industry [0 - 255]
	private int locationDevelopment;				// The location's developmental level [0 - 255]
	private int locationRegion;						// The region at which this location is situated
	private int[] slotAllocated = {0,0,0,0};		// The number of slots allocated to businesses
	private int locationSlotCost;					// The cost per slot to lease an available slot
	private int slotTotal;							// The location's total slots
	private int locationX;							// The X coordinate of the location
	private int locationY;							// The Y coordinate of the location
	
	private FACILITY facilityType = FACILITY.PORT_CITY;
	private FACTION owner;
	private LOCATIONTYPE locationType = LOCATIONTYPE.LT_TOWN;
	
	private String locationName;					// The location's name.
	private String locationID;
	
	private static SpriteSheet spritesheet = AstroBiz.regionSprites;

	public static enum LOCATIONTYPE{
		LT_TOWN,
		LT_CITY;
	}
	
	public Location(LOCINFO li){
		this.locationName = li.getName();
		this.locationID = li.getID();
		this.locationRegion = li.getRegion();
		this.locationDemandBusiness =  li.getDemandBusiness();
		this.locationDemandIndustry = li.getDemandIndustry();
		this.locationDemandTourism = li.getDemandTourism();
		this.locationX = li.getX();
		this.locationY = li.getY();
		this.population = li.getPopulation();
		if(this.population > 1.0) locationType = LOCATIONTYPE.LT_CITY;
		if(population > 5.0) facilityType = FACILITY.PORT_REGION;
		else if(population > 2.5) facilityType = FACILITY.PORT_METRO;
		slotTotal = facilityType.getSlots();
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

	public String getID(){
		return this.locationID;
	}
	
	public boolean getLocationIsHub(){
		if(isHqOf != null || isHubOf != null) return true;
		return false;
	}
	
	public String getLocationName(){
		return locationName;
	}	

	public int getSlotAvailable(){
		int count = 0;
		for(int i = 0; i < 4; i++){
			count += slotAllocated[i];
		}
		return slotTotal - count;
	}

	public int getLocationSlotCost(){
		return locationSlotCost;
	}

	public int getSlotTotal(){
		return slotTotal;
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
	public void setHub(Business b){
		isHubOf = b;
	}
	
	public void setHQ(Business b){
		isHqOf = b;
		isHubOf = b;
	}
	@Deprecated
	public void setLocationIsHub(boolean tf, int business){
		this.isHub[business] = tf;
	}
	
	void setLocationName(String name){
		locationName = name;
	}

	void setLocationSlotCost(int cost){
		locationSlotCost = cost;
	}
	void setLocationSlotTotal(int slot){
		slotTotal = slot;
	}
	
	void setLocationX(int x){
		locationX = x;
	}
	void setLocationY(int y){
		locationY = y;
	}
}
