package astroBiz;
import java.awt.Color;
import java.util.Vector;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business {
	private double businessAccountBalance;
	private Color businessColor;
	private boolean businessIsPlayerOwned;
	private double businessOperatingCosts;
	private	Vector<SpaceCraft> businessHangar;
	private Location businessHQ;
	private Vector<Location> businessHubs;
	
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
	
	public Color getColor(){
		return businessColor;
	}
	
	SpaceCraft getCraft(int index){
		return businessHangar.get(index);
	}
	
	public String getName(){
		return this.businessName;
	}
	
	void setColor(Business business, int r, int g, int b){
		business.businessColor = new Color(r,g,b);
	}
	
	void setHQ(Location hq){
		this.businessName = hq.getLocationName() + " Airlines";
		this.businessHQ = hq;
	}
	
	void setBusinessPlayerOwned(boolean p){
		this.businessIsPlayerOwned = p;
	}
	
	void subBusinessAccountBalance(int amount){
		businessAccountBalance -= amount;
	}
}