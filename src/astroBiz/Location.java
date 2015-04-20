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
	private int locationX;
	private int locationY;
	
	private BufferedImage locationSprite;
	
	/**
	 * Default constructor for a Location.
	 */
	Location(BufferedImage sprite){
		this.locationSprite = sprite;
	}

	void generateLocation(int y, int x){
		Random rand = new Random();
		setLocationCategory(rand.nextInt(5));
		setLocationDemandBusiness(rand.nextInt(255));
		setLocationDemandTourism(rand.nextInt(255));
		setLocationDemandIndustry(rand.nextInt(255));
		setLocationDevelopment((getLocationDemandBusiness() + getLocationDemandTourism() + getLocationDemandIndustry())/3);
		setLocationName("TODO: Name generator");
		setLocationSize(getLocationDevelopment() * 10);
		setLocationSlotTotal(locationDevelopment / 10);
		setLocationSlotAvailable(getLocationSlotTotal());
		setLocationSlotCost(500);
		setLocationX(x);
		setLocationY(y);
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
