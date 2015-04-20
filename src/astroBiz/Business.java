package astroBiz;
import java.util.Vector;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business {
	double businessAccountBalance;
	boolean businessIsPlayerOwned;
	double businessOperatingCosts;
	private	Vector<SpaceCraft> businessHangar;
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
	
	void subBusinessAccountBalance(int amount){
		businessAccountBalance -= amount;
	}
}