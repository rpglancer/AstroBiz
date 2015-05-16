package astroBiz.lib;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import astroBiz.AstroBiz;
import astroBiz.info.FACILITY;
import astroBiz.info.LOCINFO;

/**
 * Contains all the information for a Location on the Map.
 * @author Matt Bangert
 *
 */
public class Location implements Serializable{	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2298445659460937295L;

	@Deprecated
	private Boolean[] isHub = {false, false, false, false};

	private Business isHqOf = null;
	private Business isHubOf = null;
	
	private double au;
	private double population;
	
	private int demandBusi;						// The location's demand for business [0 - 255]
	private int demandTour;						// The location's demand for tourism  [0 - 255]
	private int demandInd;						// The location's demand for industry [0 - 255]
	private int region;							// The region at which this location is situated
	private int[] slotAllocated = {0,0,0,0};	// The number of slots allocated to businesses
	private int[] slotUsed = {0,0,0,0};
	private int slotCost;						// The cost per slot to lease an available slot
	private int slotTotal;						// The location's total slots
	private int x;								// The X coordinate of the location
	private int y;								// The Y coordinate of the location
	
	private FACILITY facility = FACILITY.PORT_CITY;
	private Faction faction;
	private LOCATIONTYPE type = LOCATIONTYPE.LT_TOWN;
	
	private String name;					// The location's name.
	private String id;
	
	private static SpriteSheet spritesheet = AstroBiz.regionSprites;

	public static enum LOCATIONTYPE{
		LT_TOWN,
		LT_CITY;
	}
	
	public Location(LOCINFO li, Scenario s){
		name = li.getName();
		id = li.getID();
		region = li.getRegion();
		demandBusi =  li.getDemandBusiness();
		demandInd = li.getDemandIndustry();
		demandTour = li.getDemandTourism();
		x = li.getX();
		y = li.getY();
		if(region == 0)
			au = 0.25;
		if(region == 1)
			au = 0.5;
		if(region == 2)
			au = 1.0;
		if(region == 3)
			au = 1.002;
		if(region == 4)
			au = 2.0;
		if(region == 5)
			au = 8.0;
		if(region == 6)
			au = 16.0;
		if(region == 7)
			au = 32.0;
		if(region == 8)
			au = 64.0;
		this.population = li.getPopulation();
		if(this.population > 1.0) type = LOCATIONTYPE.LT_CITY;
		if(population > 5.0) facility = FACILITY.PORT_REGION;
		else if(population > 2.5) facility = FACILITY.PORT_METRO;
		slotTotal = facility.getSlots();
		switch(li.getOwner()){
		case FAC00:
			faction = s.getFactions().elementAt(0);
			break;
		case FAC01:
			faction = s.getFactions().elementAt(1);
			break;
		case FAC02:
			faction = s.getFactions().elementAt(2);
			break;
		case FAC03:
			faction = s.getFactions().elementAt(3);
			break;
		case FAC04:
			faction = s.getFactions().elementAt(4);
			break;
		case FAC05:
			faction = s.getFactions().elementAt(5);
			break;
		case FAC06:
			faction = s.getFactions().elementAt(6);
			break;
		case FAC07:
			faction = s.getFactions().elementAt(7);
			break;
		}
	}

	public BufferedImage getSprite(Scenario s){
		if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getHQ()) return Location.spritesheet.grabImage(6, 1, 16, 16);
		for(int i = 0; i < s.getBusinesses().elementAt(s.getActiveBusiness()).getHubs().size(); i++){
			if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getHubs().elementAt(i)) return Location.spritesheet.grabImage(5, 1, 16, 16);
		}
		for(int i = 0; i < s.getBusinesses().elementAt(s.getActiveBusiness()).getRoutes().size(); i++){
			if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getRoutes().elementAt(i).getRouteDestination() && type == LOCATIONTYPE.LT_CITY){
				return Location.spritesheet.grabImage(4, 1, 16, 16);
			}
			else if(this == s.getBusinesses().elementAt(s.getActiveBusiness()).getRoutes().elementAt(i).getRouteDestination() && type == LOCATIONTYPE.LT_TOWN){
				return Location.spritesheet.grabImage(2, 1, 16, 16);
			}
		}
		if(type == LOCATIONTYPE.LT_TOWN) return Location.spritesheet.grabImage(1, 1, 16, 16);
		if(type == LOCATIONTYPE.LT_CITY) return Location.spritesheet.grabImage(3, 1, 16, 16);
		return Location.spritesheet.grabImage(1, 1, 16, 16);
	}

	public int getDemandBusi(){
		return demandBusi;
	}

	public int getDemandTour(){
		return demandTour;
	}

	public int getDemandInd(){
		return demandInd;
	}
	
	public double getAU(){
		return au;
	}

	public String getID(){
		return id;
	}
	
	public boolean getLocationIsHub(){
		if(isHqOf != null || isHubOf != null) return true;
		return false;
	}
	
	public String getName(){
		return name;
	}	

	public Faction getFaction(){
		return faction;
	}
	
	public double getPopulation(){
		return population;
	}
	
	public int getSlotAvailable(){
		int count = 0;
		for(int i = 0; i < 4; i++){
			count += slotAllocated[i];
		}
		return slotTotal - count;
	}

	public int getSlotAvailableFor(int business){
		return slotAllocated[business] - slotUsed[business];
	}
	
	public int getSlotAllocatedFor(int business){
		return slotAllocated[business];
	}
	
	public int getSlotCost(){
		return slotCost;
	}

	public int getSlotTotal(){
		return slotTotal;
	}

	public LOCATIONTYPE getLocationType(){
		return type;
	}
	
	public int getX(){
		return x;
	}	

	public int getY(){
		return y;
	}
	
	public int getRegion(){
		return region;
	}
	
	void setDemandBusi(int demand){
		demandBusi = demand;
	}
	void setDemandTour(int demand){
		demandTour = demand;
	}
	void setDemandInd(int demand){
		demandInd = demand;
	}
	public void setHub(Business b){
		isHubOf = b;
	}
	
	public void setHQ(Business b){
		isHqOf = b;
		isHubOf = b;
	}
	
	public void freeSlots(int business, int qty){
		slotUsed[business] -= qty;
	}
	
	public void useSlots(int business, int qty){
		slotUsed[business]+=qty;
	}
	
	@Deprecated
	public void setLocationIsHub(boolean tf, int business){
		this.isHub[business] = tf;
	}
	
	void setName(String name){
		this.name = name;
	}

	void setLocationSlotCost(int cost){
		slotCost = cost;
	}
	void setLocationSlotTotal(int slot){
		slotTotal = slot;
	}
	
	void setX(int x){
		this.x = x;
	}
	void setY(int y){
		this.y = y;
	}
	
	void setSlotAllocation(int business, int amount){
		slotAllocated[business] = amount;
	}
	
}
