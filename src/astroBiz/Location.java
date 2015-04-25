package astroBiz;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

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
	private int locationRegionX;
	private int locationRegionY;
	private int locationX;							// The X coordinate of the location
	private int locationY;							// The Y coordinate of the location
	
	private BufferedImage locationSprite;			// The sprite that represents this location
	
	/**
	 * Default constructor for a Location.
	 */
	public Location(BufferedImage sprite){
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
	
	public BufferedImage getSprite(){
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
