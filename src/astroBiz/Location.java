package astroBiz;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location {
//	private int locationCategory;			
	private int locationDemandBusiness;				// The location's demand for business [0 - 255]
	private int locationDemandTourism;				// The location's demand for tourism  [0 - 255]
	private int locationDemandIndustry;				// The location's demand for industry [0 - 255]
	private int locationDevelopment;				// The location's developmental level [0 - 255]
	private String locationName;					// The location's name.
	private int locationSize;						// The location's population
	private int locationSlotAvailable;				// The location's available slots for landing SpaceCraft on a Route
	private int locationSlotCost;					// The cost per slot to lease an available slot
	private int locationSlotTotal;					// The location's total slots
	private int locationRegion;						// The region at which this location is situated
	private int locationX;							// The X coordinate of the location
	private int locationY;							// The Y coordinate of the location
	
	private BufferedImage locationSprite;			// The sprite that represents this location
	
	/**
	 * Default constructor for a Location.
	 */
	Location(BufferedImage sprite){
		this.locationSprite = sprite;
	}

	public void generateLocation(int y, int x, int r){
		Random rand = new Random();
//		this.locationCategory = rand.nextInt(5);
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
		
	}
	
	BufferedImage getSprite(){
		return locationSprite;
	}
/*
	int getLocationCategory(){
		return locationCategory;
	}
*/
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

	String getLocationName(){
		return locationName;
	}	

	int getLocationSize(){
		return locationSize;
	}

	int getLocationSlotAvailable(){
		return locationSlotAvailable;
	}

	int getLocationSlotCost(){
		return locationSlotCost;
	}

	int getLocationSlotTotal(){
		return locationSlotTotal;
	}

	int getLocationX(){
		return locationX;
	}	

	int getLocationY(){
		return locationY;
	}
	
	int getLocationRegion(){
		return this.locationRegion;
	}

	/*
	void setLocationCategory(int category){
		locationCategory = category;
	}
*/
	
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
