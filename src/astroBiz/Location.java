package astroBiz;
import java.util.Random;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location {
	/**
	 * The category of the Location.
	 * Included as a placeholder only, it currently has no use.
	 */
	private int locationCategory;
	/**
	 * The amount of business demand at the Location.
	 */
	private int locationDemandBusiness;
	/**
	 * The amount of tourist demand at the Location.
	 */
	private int locationDemandTourism;
	/**
	 * The amount of industrial demand at the Location.
	 */
	private int locationDemandIndustry;
	/**
	 * The developmental level of the Location.
	 * <br><br>
	 * Generally speaking a higher developmental level has more facilites [read: slots] available for Route usage by a Business. The population is <i>generally</i> greater and the fares higher.
	 */
	private int locationDevelopment;
	/**
	 * The name of the Location.
	 */
	private String locationName;
	/**
	 * The population of a Location.
	 * <br><br>
	 * The number of people requiring travel in and/or out of this Location increases with size.
	 */
	private int locationSize;
	/**
	 * The number of slots currently available for lease by a Business to be used on Routes.
	 */
	private int locationSlotAvailable;
	/**
	 * The negotiating price per available slot.
	 * <br><br>
	 * The higher the demand and the lower the amount of slots available, the higher the cost per slot.
	 */
	private int locationSlotCost;
	/**
	 * The total number of slots available for lease by a Business to be used on Routes.
	 * <br><br>
	 * The more developed a Location is the more slots will be available.
	 */
	private int locationSlotTotal;
	/**
	 * The X coordinate of this Location.
	 */
	private int locationX;
	/**
	 * The Y coordinate of this Location.
	 */
	private int locationY;
	
	/**
	 * Default constructor for a Location.
	 */
	Location(){
		locationCategory = 0;
		locationDemandBusiness = 0;
		locationDemandTourism = 0;
		locationDemandIndustry = 0;
		locationDevelopment = 0;
		locationName = "";
		locationSize = 0;
		locationSlotAvailable = 0;
		locationSlotCost = 0;
		locationSlotTotal = 0;
		locationX = 0;
		locationY = 0;
	}
	/**
	 * A Method for generating the values of a Location.
	 * @param y	The maximum Y value [between 0 and y] for this Location.
	 * @param x The maximum X value [between 0 and x] for this Location.
	 */
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
		setLocationX(rand.nextInt(x));
		setLocationY(rand.nextInt(y));
	}
	/**
	 * A Method to get the category of the Location.
	 * @return The category of the Location.
	 */
	int getLocationCategory(){
		return locationCategory;
	}
	/**
	 * A Method to get the business demand of the Location.
	 * @return The business demand of the Location [0-255].
	 */
	int getLocationDemandBusiness(){
		return locationDemandBusiness;
	}
	/**
	 * A Method to get the tourism demand of the Location.
	 * @return The tourism demand of the Location [0-255].
	 */
	int getLocationDemandTourism(){
		return locationDemandTourism;
	}
	/**
	 * A Method to get the industrial demand of the Location.
	 * @return The industrial demand of the Location [0 - 255].
	 */
	int getLocationDemandIndustry(){
		return locationDemandIndustry;
	}
	/**
	 * A Method to get the current developmental level of the Location.
	 * @return	The developmental level of the Location [0 - 255?].
	 */
	int getLocationDevelopment(){
		return locationDevelopment;
	}
	/**
	 * A Method to get the name of the Location.
	 * @return	The name of the Location.
	 */
	String getLocationName(){
		return locationName;
	}	
	/**
	 * A Method to get the current population of a Location.
	 * @return	The population of a Location [0 - 12,000,000?]
	 */
	int getLocationSize(){
		return locationSize;
	}
	/**
	 * A Method to get the currently available number of slots at the Location.
	 * @return The number of slots available at the Location [0 - 100?]
	 */
	int getLocationSlotAvailable(){
		return locationSlotAvailable;
	}
	/**
	 * A Method to get the current negotiating price per slot at the Location.
	 * @return	The current negotiating price per slot at the Location.
	 */
	int getLocationSlotCost(){
		return locationSlotCost;
	}
	/**
	 * A Method to get the total number of slots at the Location.
	 * @return	The total number of slots at the Location.
	 */
	int getLocationSlotTotal(){
		return locationSlotTotal;
	}
	/**
	 * A Method to get the X coordinate of the Location.
	 * @return	The X coordinate of the Location.
	 */
	int getLocationX(){
		return locationX;
	}	
	/**
	 * A Method to get the Y coordinate of the Location.
	 * @return The Y coordinate of the Location.
	 */
	int getLocationY(){
		return locationY;
	}
	/**
 * Sets the category of a location.
 * The values for these locations have yet to be determined.
 * @param category	the category of the location.
 */
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
