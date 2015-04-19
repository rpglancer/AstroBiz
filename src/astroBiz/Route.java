package astroBiz;

/**
 * Contains all the information required for a route to operate for a Business.
 * @author Matt Bangert
 *
 */
public class Route {

	/**
	 * The SpaceCraft currently employed on this Route.
	 */
	private SpaceCraft routeCraft;
	
	private int routeDistance;
	
	/**
	 * The number of SpaceCraft currently employed on this Route.
	 */
	private int routeCraftNumber;
	
	private int routeFlightsPerWeek;
	
	/**
	 * The Location to which this route is destined.
	 */
	private Location routeDestination;
	
	/**
	 * The per ticket fare of this Route.
	 */
	private int routeFare;
	
	/** 
	 * The Location from which this route departed.
	 */
	private Location routeHome;
	
	/**
	 * Default constructor for this Route.
	 */
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
	
	int calcRouteDistance(Route r){
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
	
	/**
	 * A Method to get the type of SpaceCraft currently employed on this Route.
	 * @return The type of SpaceCraft employed on this Route.
	 */
	SpaceCraft getRouteCraft(){
		return routeCraft;
	}
	
	/**
	 * A Method to get the number of currently employed SpaceCraft on this Route.
	 * @return The number of SpaceCraft currently employed on this Route.
	 */
	int getRouteCraftNumber(){
		return routeCraftNumber;
	}
	
	/**
	 * A Method to get the destination Location of this Route.
	 * @return The destination of this Route.
	 */
	Location getRouteDestination(){
		return routeDestination;
	}
	
	/**
	 * A Method to get the per ticket fare of this Route.
	 * @return The per ticket fare of this Route.
	 */
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
