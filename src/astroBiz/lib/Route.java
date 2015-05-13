package astroBiz.lib;

import java.util.Vector;

import astroBiz.info.STANDING;

/**
 * Contains all the information required for a route to operate for a Business.
 * @author Matt Bangert
 *
 */
public class Route{
	private static final int au = 150000000;	//	Approximate AU to KM
	private static final int drawWidth = 736;	//	Region width in Pixels
	private static final int mercury = 15324;	//	Mercury Circumference in KM
	private static final int venus = 38013;		//	Venus Circumference in KM
	private static final int earth = 40030;		//	Earth Circumference in KM
	private static final int luna = 10920;		//	Luna Circumference in KM
	private static final int mars = 21337;		//	Mars Circumference in KM
	private static final int jupiterS = 0;		//	Jupiter Sector width in KM
	private static final int saturnS = 0;		//	Saturn Sector width in KM
	private static final int uranusS = 0;		//	Uranus Sector width in KM
	private static final int neptuneS = 0;		//	Neptune Sector width in KM
	

	private Vector<SpaceCraft> craft = new Vector<SpaceCraft>();

	private double routeDistance;
	private int routeCraftNumber;
	private int routeFlightsPerWeek;

	private int routeFare;
	private Location routeHome;
	private Location routeDestination;
	
	public Route(){
	}
	
	public Route(Location hub, Location dest){
		routeHome = hub;
		routeDestination = dest;
	}
	
	public Route(Location home, Location dest, SpaceCraft craft, int num, int fare){
	}
	
	/**
	 * Method to calculate the total cost of opening a route. Note that this a one time cost, deducted at
	 * the time of the route's opening and has no effect on a Quarterly basis. It is, in essence, an initial
	 * logistics cost.
	 * @param begin	The Location from which this Route departs.
	 * @param end	The Location at which this Route arrives.
	 * @param busi	The Business which will be operating this Route.
	 * @return		The cost [in K] of opening this Route.
	 */
	public int calcCostToOpen(Location begin, Location end, Business busi){
		int cost = 0;
		double distance = calcDistance(begin,end);
		
		if(begin.getAU() == end.getAU())
			cost = (int)(distance * getCPAU(distance) * getRepAdjust(busi, end.getFaction()) + 25000);
		else
			cost = (int)(distance * getCPAU(distance) * getRepAdjust(busi, end.getFaction()));

		return cost;
	}
	
	/**
	 * Method to determine the number of AUs per pixel of a given region.
	 * @param regionID	The region to be evaluated.
	 * @return
	 */
	private double getAUPP(int regionID){
		switch(regionID){
		case 0:	//	Mercury
			return ((double)mercury/(double)drawWidth/(double)au);
		case 1:	//	Venus
			return ((double)venus/(double)drawWidth/(double)au);
		case 2:	//	Earth
			return ((double)earth/(double)drawWidth/(double)au);
		case 3:	//	Luna
			return ((double)luna/(double)drawWidth/(double)au);
		case 4:	//	Mars
			return ((double)mars/(double)drawWidth/(double)au);
		case 5:	//	Jupiter Sector
			return ((double)jupiterS/(double)drawWidth/(double)au);
		case 6:	//	Saturn Sector
			return ((double)saturnS/(double)drawWidth/(double)au);
		case 7:	//	Uranus Sector
			return ((double)uranusS/(double)drawWidth/(double)au);
		case 8:	//	Neptune Sector
			return ((double)neptuneS/(double)drawWidth/(double)au);
		default:
			return 0.0;		
		}
	}
	
	/**
	 * Method to determine a route's Cost Per Astronomical Unit [CPAU]
	 * @param distance	The distance the route covers.
	 * @return	Cost [in K] per AU traversed.
	 */
	private int getCPAU(double distance){
		int cpau = 40000000;
		if(distance >= 0.002)	cpau = 17500000;
		if(distance >= 0.01)	cpau = 3300000;
		if(distance >= 0.05)	cpau = 650000;
		if(distance >= 0.1)		cpau = 315000;
		if(distance >= 0.5)		cpau = 62000;
		if(distance >= 1)		cpau = 30000;
		if(distance >= 2)		cpau = 14750;
		if(distance >= 4)		cpau = 6950;
		if(distance >= 8)		cpau = 3350;
		if(distance >= 16)		cpau = 1625;
		if(distance >= 32)		cpau = 800;
		if(distance >= 64)		cpau = 390;
		return cpau;
	}
	
	/**
	 * Method to determine the route's cost multiplier based upon the Route operator's standings with the Faction of the destination.
	 * @param busi		The business whose Faction standings are to be evaluated.
	 * @param faction	The faction in control of the desired Route destination
	 * @return			The cost multiplier for the specified destination.
	 */
	private double getRepAdjust(Business busi, Faction faction){
		if(busi.getStandings().elementAt(faction.getfID()) == STANDING.ALLY)
			return 0.75;
		if(busi.getStandings().elementAt(faction.getfID()) == STANDING.WARM)
			return 0.9;
		if(busi.getStandings().elementAt(faction.getfID()) == STANDING.NEUTRAL)
			return 1;
		if(busi.getStandings().elementAt(faction.getfID()) == STANDING.COOL)
			return 1.15;
		if(busi.getStandings().elementAt(faction.getfID()) == STANDING.HOSTILE)
			return 1.30;
		
		else return 1;
	}
	
	/**
	 * Method to calculate the distance between locations in AU.
	 * @param begin	The departing Location
	 * @param end	The arriving Location
	 * @return	The distance between Locations in AU.
	 */
	public double calcDistance(Location begin, Location end){
		double distance = 0.0;
		if(begin.getAU() == end.getAU()){
			int tempx = Math.abs(begin.getX() - end.getX());
			int tempy = Math.abs(begin.getY() - end.getY());
			double py = (tempx * tempx) + (tempy * tempy);
			py = Math.sqrt(py);
			py *= getAUPP(begin.getRegion());
			distance += py;
			return distance;
		}
		if(begin.getAU() > end.getAU()){
			distance = Math.abs(begin.getAU() - end.getAU());	//	Get the base AU difference in between regions.
			
			int total = begin.getX() + (800 - end.getX());		//	The total X distance
			int pctxbegin = begin.getX() / total;				//	The percentage of the X distance on the beginning side
			int pctxend = (800 - end.getX()) / total;			//	The percentage of the X distance on the ending side
			
			
			int tempx = begin.getX();
			int tempy = Math.abs(begin.getY() - end.getY()) * pctxbegin;
			double py = (tempx * tempx) + (tempy * tempy);
			py = Math.sqrt(py);
			py *= getAUPP(begin.getRegion());
			distance += py;
			
			tempx = 800 - end.getX();
			tempy = Math.abs(end.getY() - begin.getY()) * pctxend;
			py = (tempx * tempx) + (tempy * tempy);
			py = Math.sqrt(py);
			py *= getAUPP(end.getRegion());
			distance += py;
			return distance;
		}
		if(begin.getAU() < end.getAU()){
			distance = Math.abs(begin.getAU() - end.getAU());
			
			int total = end.getX() + (800 - begin.getX());
			int pctxbegin = (800 - begin.getX()) / total;
			int pctxend = end.getX() / total;
			
			int tempx = end.getX();
			int tempy = Math.abs(begin.getY() - end.getY()) * pctxend;
			double py = (tempx * tempx) + (tempy * tempy);
			py = Math.sqrt(py);
			py *= getAUPP(end.getRegion());
			distance += py;
			
			tempx = 800 - begin.getX();
			tempy = Math.abs(end.getY() - begin.getY()) * pctxbegin;
			py = (tempx * tempx) + (tempy * tempy);
			py = Math.sqrt(py);
			py *= getAUPP(begin.getRegion());
			distance += py;
			return distance;
		}
		System.out.println("ERR: calcDistance() returned 0.0 value.");
		return distance;
	}
	
	public Vector<SpaceCraft> getCraft(){
		return craft;
	}
	
	public int getCraftCount(){
		return craft.size();
	}
	
	public String getCraftModel(){
		if(craft.size() == 0) return null;
		else return craft.elementAt(0).getName();
	}

	public Location getRouteDestination(){
		return routeDestination;
	}
	
	int getRouteFare(){
		return routeFare;
	}

	Location getRouteHome(){
		return routeHome;
	}
	
	void setRouteCraft(SpaceCraft sc){
		craft.addElement(sc);
	}
	void setRouteCraftNumber(int number){
		routeCraftNumber = number;
	}
	void setRouteDestination(Location destination){
		routeDestination = destination;
	}
	void setRouteFare(int fare){
		routeFare = fare;
	}
	void setRouteHome(Location home){
		routeHome = home;
	}
}
