package astroBiz.lib;

import java.util.Vector;

import astroBiz.info.STANDING;

/**
 * Contains all the information required for a route to operate for a Business.
 * @author Matt Bangert
 *
 */
public class Route{

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
	
	public int calcCostToOpen(Location begin, Location end, Business busi){

		int absX = 0;
		int absY = 0;
		int cost = 0;
		
		double aupp_b = 0;
		double aupp_e = 0;
		double distance = calcDistance(begin,end);
		double repAdjust = 1;
		
		if(begin.getRegion() == 2)
			aupp_b = .0000002; //.00016;
		if(end.getRegion() == 2)
			aupp_e = .0000002; //.00016;
		//	Destination closer
		if(begin.getAU() > end.getAU()){
			
		}
		//	Destination farther
		if(begin.getAU() < end.getAU()){
			
		}
		//	Same Region
		else{
			absX = Math.abs(begin.getX() - end.getX());
			absY = Math.abs(begin.getY() - end.getY());
			double py = (absX * absX) + (absY * absY);
			py = Math.sqrt(py);
			py *= aupp_b;
			distance += py;
			System.out.println(distance + ", " + py);
		}
				
		if(busi.getStandings().elementAt(end.getFaction().getfID()) == STANDING.ALLY)
			repAdjust = 0.75;
		if(busi.getStandings().elementAt(end.getFaction().getfID()) == STANDING.WARM)
			repAdjust = 0.90;
		if(busi.getStandings().elementAt(end.getFaction().getfID()) == STANDING.NEUTRAL)
			repAdjust = 1;
		if(busi.getStandings().elementAt(end.getFaction().getfID()) == STANDING.COOL)
			repAdjust = 1.15;
		if(busi.getStandings().elementAt(end.getFaction().getfID()) == STANDING.HOSTILE)
			repAdjust = 1.30;
		
		cost = (int)(distance * getCPAU(distance) * repAdjust);

		return cost;
	}
	
	private double getAUPP(int regionID){
		switch(regionID){
		case 0:
			return .0000001;
		case 1:
			return .0000002;
		case 2:
			return .0000002;
		case 3:
			return .00000005;
		case 4:
			return .0000001;
		case 5:
			return 0;
		case 6:
			return 0;
		case 7:
			return 0;
		case 8:
			return 0;
		default:
			return 0.0;
				
		}
	}
	
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
	
	public double calcDistance(Location begin, Location end){
		double distance = 0.0;
		if(begin.getRegion() == end.getRegion())
			distance = 0.001;
		else
			distance = Math.abs(begin.getAU() - end.getAU());
		return distance;
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
