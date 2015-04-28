package astroBiz;
import java.util.Vector;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business {
	private double businessAccountBalance;
	private int[] businessColor = new int[3];
	private boolean businessIsPlayerOwned;
	private double businessOperatingCosts;
	private	Vector<SpaceCraft> businessHangar;
	private Location businessHQ;
	private Vector<Location> businessHubs;
	
	byte colorR;
	byte colorG;
	byte colorB;
	
	String businessName;
	Vector<Route> businessRoutes;
	
	/**
	 * Default constructor to make a new Business.
	 */
	Business(){
		this.businessAccountBalance = 0;
		this.businessOperatingCosts = 0;
		this.businessName = "Untitled Company";
		this.businessIsPlayerOwned = false;
	}
	
	void addCraft(SpaceCraft sc){
		if(this.businessHangar == null){
			this.businessHangar = new Vector<SpaceCraft>();
			this.businessHangar.addElement(sc);
		}
		else{
			this.businessHangar.addElement(sc);
		}
	}

	void addBusinessAccountBalance(int amount){
		businessAccountBalance += amount;
	}
	
	SpaceCraft getCraft(int index){
		return businessHangar.get(index);
	}
	
	void setBusinessHQ(Location hq){
		this.businessHQ = hq;
	}
	
	void setBusinessPlayerOwned(boolean p){
		this.businessIsPlayerOwned = p;
	}
	
	void subBusinessAccountBalance(int amount){
		businessAccountBalance -= amount;
	}
}