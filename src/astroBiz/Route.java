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
	
	/**
	 * The number of SpaceCraft currently employed on this Route.
	 */
	private int routeCraftNumber;
	
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
