package astroBiz;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location {
	private int locationCategory;
	private int locationDemandBusiness;
	private int locationDemandTourism;
	private int locationDemandIndustry;
	private int locationDevelopment;
	private String locationName;
	private int locationSize;
	private int locationSlotAvailable;
	private int locationSlotCost;
	private int locationSlotTotal;
	private int locationRegion;
	private int locationX;
	private int locationY;
	
	private BufferedImage locationSprite;
	
	/**
	 * Default constructor for a Location.
	 */
	Location(BufferedImage sprite){
		this.locationSprite = sprite;
	}

	void generateLocation(int y, int x, int r){
		Random rand = new Random();
		this.locationCategory = rand.nextInt(5);
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
	
	BufferedImage getSprite(){
		return locationSprite;
	}

	int getLocationCategory(){
		return locationCategory;
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

	void setLocationCategory(int category){
		locationCategory = category;
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
