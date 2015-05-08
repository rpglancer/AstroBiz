package astroBiz.lib;

import java.util.Vector;

/**
 * Contains all the information required for a route to operate for a Business.
 * @author Matt Bangert
 *
 */
public class Route{

	private Vector<SpaceCraft> craft = new Vector<SpaceCraft>();
	@Deprecated
	private SpaceCraft routeCraft;
	private int routeDistance;
	private int routeCraftNumber;
	private int routeFlightsPerWeek;

	private int routeFare;
	private Location routeHome;
	private Location routeDestination;
	
	Route(){
	}
	
	Route(Location home, Location dest, SpaceCraft craft, int num, int fare){
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
		routeCraft = sc;
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
