package astroBiz.lib;
import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

import astroBiz.info.FACTION;
import astroBiz.info.STANDING;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -404228488998039409L;

	private boolean isPlayerOwned = false;
	
	private int advertisingCosts = 0;			
	private int maintenanceCosts = 0;
	private int serviceCosts = 0;
	
	private Color businessColor;
	private Faction affiliation;						// Contains the Faction this Business is a part of / incorporated in
	private Integer accountBalance = 0;	
	private Location headQuarters = null;
	private String businessName = "Untitled Company";
	
	private Vector<Location> regionalHubs = new Vector<Location>();				// 	Vector of all this businesses regional Hubs
	private Vector<Route> businessRoutes = new Vector<Route>();					// 	Vector of all this businesses routes
	private	Vector<SpaceCraft> spaceCraftHangar = new Vector<SpaceCraft>();		//	Business Hangar, contains all idle Business SpaceCraft.
	private Vector<STANDING> standings = new Vector<STANDING>();				//	Current standings with other factions, defaulted to the STANDINGs of affiliation.
	
	public void addCraftToHangar(SpaceCraft sc){
		this.spaceCraftHangar.addElement(sc);
	}

	public void addToAccount(int amount){
		accountBalance += amount;
	}

	public Color getColor(){
		return businessColor;
	}
	
	public Integer getAccountBalance(){
		return this.accountBalance;
	}

	public int getAdCosts(){
		return this.advertisingCosts;
	}
	
	public Faction getAffiliation(){
		return affiliation;
	}
	/**
	 * Method to return SpaceCraft element from this Businesses spaceCraftHangar Vector at specified index.
	 * @param index Desired element in the hangar.
	 * @return	The specified SpaceCraft from the spaceCraftHangar.
	 */
	SpaceCraft getCraft(int index){
		return spaceCraftHangar.get(index);
	}
/**
 * Method to return all Business SpaceCraft.
 * @return	Vector of all SpaceCraft belonging to this Business.
 */
	public Vector<SpaceCraft> getCraftAll(){
		Vector<SpaceCraft> craft = new Vector<SpaceCraft>();
		if(spaceCraftHangar.size() > 0){
			for(int i = 0; i < spaceCraftHangar.size(); i++){
				craft.addElement(spaceCraftHangar.elementAt(i));
			}
		}
		if(businessRoutes.size() > 0){
			for(int i = 0; i < businessRoutes.size(); i++){
				Route route = businessRoutes.elementAt(i);
				for(int x = 0; x < route.getCraft().size(); x++){
					craft.addElement(route.getCraft().elementAt(x));
				}
			}
		}
		return craft;
	}

	public boolean getCraftAvailable(SpaceCraft type){
		if(spaceCraftHangar.contains(type))
			return true;
		else
			return false;
	}

	public Vector<SpaceCraft>getCraftAvailableByType(SpaceCraft type){
		Vector<SpaceCraft> avail = new Vector<SpaceCraft>();
		for(int i = 0; i < spaceCraftHangar.size(); i++){
			if(spaceCraftHangar.elementAt(i).getName() == type.getName()){
				avail.addElement(spaceCraftHangar.elementAt(i));
			}
		}
		return avail;
	}
	
	/**
 * Method to return all SpaceCraft types owned by a Business.
 * @return Vector of all SpaceCraft types belonging to a Business.
 */
	public Vector<SpaceCraft> getCraftTypes(){
		Vector<SpaceCraft> craft = new Vector<SpaceCraft>();
		if(spaceCraftHangar.size() > 0){
			for(int i = 0; i < spaceCraftHangar.size(); i++){
				if(!craft.contains(spaceCraftHangar.elementAt(i))){
					craft.addElement(spaceCraftHangar.elementAt(i));
				}
			}
		}
		if(businessRoutes.size() > 0){
			for(int i = 0; i < businessRoutes.size(); i++){
				Route route = businessRoutes.elementAt(i);
				for(int x = 0; x < route.getCraft().size(); x++){
					if(!craft.contains(route.getCraft().elementAt(x))){
						craft.addElement(route.getCraft().elementAt(x));
					}
				}
			}
		}
		return craft;
	}
/**
 * Method to return a count of SpaceCraft elements in the spaceCraftHangar Vector of the specified type.
 * @param type	The type of SpaceCraft to be counted.
 * @return	The number of SpaceCraft of the specified type found in the spaceCraftHangar Vector.
 */
	public int getCraftInHangar(SpaceCraft type){
		int count = 0;
		if(spaceCraftHangar.size() == 0) return count;
		else{
			for(int i = 0; i < spaceCraftHangar.size(); i++){
				if(spaceCraftHangar.elementAt(i).getName() == type.getName()) count++;
			}
		}
		return count;
	}
	
	public int getCraftInService(SpaceCraft type){
		int count = 0;
		if(businessRoutes.size() == 0) return count;
		else{
			for(int i = 0; i < businessRoutes.size(); i++){
				if(businessRoutes.elementAt(i).getCraftModel() == type.getName()){
					count += businessRoutes.elementAt(i).getCraftCount();
				}
			}
		}
		return count;
	}
	
	public Vector<SpaceCraft> getHangar(){
		return this.spaceCraftHangar;
	}

	public Vector<SpaceCraft> getCraftForRoute(double distance){
		Vector<SpaceCraft> routeCraft = new Vector<SpaceCraft>();
		for(int i = 0; i < spaceCraftHangar.size(); i++){
			if(spaceCraftHangar.elementAt(i).getRange() >= distance){
				routeCraft.addElement(spaceCraftHangar.elementAt(i));
			}
		}
		return routeCraft;
	}
	
	public Location getHub(int regionID){
		for(int i = 0; i < regionalHubs.size(); i++){
			if(regionalHubs.elementAt(i).getRegion() == regionID)
				return regionalHubs.elementAt(i);
		}
		return null;
	}

	public Vector<Location> getHubs(){
		return this.regionalHubs;
	}
	
	public Location getHQ(){
		return this.headQuarters;
	}

	public boolean getIsPlayerOwned(){
		return this.isPlayerOwned;
	}
	
	public int getMaintCosts(){
		return this.maintenanceCosts;
	}
	
	public String getName(){
		return this.businessName;
	}
	
	public Vector<Route> getRoutes(){
		return this.businessRoutes;
	}

	public int getServiceCosts(){
		return this.serviceCosts;
	}

	public Vector<STANDING> getStandings(){
		return standings;
	}
	
	public boolean regionContainsHub(int regionID){
		for(int i = 0; i < regionalHubs.size(); i++){
			if(regionalHubs.elementAt(i).getRegion() == regionID) return true;
		}
		return false;
	}
	
	public void setColor(int r, int g, int b){
		this.businessColor = new Color(r,g,b);
	}

	public void setColor(Color color){
		this.businessColor = color;
	}
	
	public void setHQ(Location hq){
		this.businessName = hq.getName() + " Airlines";
		if(this.regionalHubs == null) this.regionalHubs = new Vector<Location>();
		this.regionalHubs.addElement(hq);
		this.headQuarters = hq;
		affiliation = hq.getFaction();
		for(int i = 0; i < FACTION.values().length; i++){
			standings.addElement(affiliation.getStanding(i));
		}
		System.out.println("Business Standings Vector Size: " + standings.size());
		hq.setHQ(this);
	}

	public void setName(String name){
		this.businessName = name;
	}
	
	public void setPlayerOwned(boolean p){
		this.isPlayerOwned = p;
	}
	
	public void setAccountBalance(int balance){
		accountBalance = balance;
	}
	
	public void subFromAccount(int amount){
		accountBalance -= amount;
	}
	public void takeCraftFromHangar(SpaceCraft sc){
		for(int i = 0; i < spaceCraftHangar.size(); i++){
			if(spaceCraftHangar.elementAt(i) == sc)
				spaceCraftHangar.removeElementAt(i);
		}
	}
}