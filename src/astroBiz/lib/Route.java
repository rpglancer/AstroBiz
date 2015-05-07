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
	@Deprecated
	private int routeCraftNumber;
	private int routeFlightsPerWeek;

	private int routeFare;
	private Location routeHome;
	private Location routeDestination;
	
	Route(){
		routeCraft = null;
		routeCraftNumber = 0;
		routeDestination = null;
		routeFlightsPerWeek = 0;
		routeFare = 0;
		routeHome = null;
	}
	
	/**
	 * Constructor including all the necessary information for this Route to operate.
	 * @param home The departing Location of the Route.
	 * @param dest The destination Location of the Route.
	 * @param craft The type of SpaceCraft to be employed on the Route.
	 * @param num	The number of SpaceCraft to be employed on the Route.
	 * @param fare	The per ticket fare of the Route.
	 */
	Route(Location home, Location dest, SpaceCraft craft, int num, int fare){
		routeHome = home;
		routeDestination = dest;
		routeCraft = craft;
		routeCraftNumber = num;
		routeFare = fare;
		routeDistance = calcRouteDistance(this);
	}
	
	private int calcRouteDistance(Route r){
		int d = 0;
		if(r.routeHome == null || r.routeDestination == null){
			return 0;
		}
		else{
			int x, y;
			x = r.routeHome.getLocationX() - r.routeDestination.getLocationX();
			y = r.routeHome.getLocationY() - r.routeDestination.getLocationY();
			if(y < 0){
				y = -y;
			}
			if(x < 0){
				x = -x;
			}
			d = (x*x) + (y*y);
			d = (int) Math.sqrt(d);
			return d;
		}
		
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
