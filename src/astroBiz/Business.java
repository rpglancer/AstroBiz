package astroBiz;
import java.util.Vector;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business {
	/**
	 * The operating bank account balance of the Business.
	 */
	double businessAccountBalance;
	
	/**
	 * Business is controlled by a human player.
	 */
	boolean businessIsPlayerOwned;
	
	/**
	 * The current operations expenditures of the Business.
	 */
	double businessOperatingCosts;
	
	/**
	 * The currently owned but unused SpaceCraft of the Business.
	 */
	private	Vector<SpaceCraft> businessHangar;
	
	/**
	 * The name of the business.
	 */
	String businessName;
	
	/**
	 * The currently operating Route of the Business.
	 */
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
	
	/**
	 * Method for adding a SpaceCraft to the businessHangar.
	 * @param sc The SpaceCraft to be added to the businessHangar.
	 */
	void addCraft(SpaceCraft sc){
		if(this.businessHangar == null){
			this.businessHangar = new Vector<SpaceCraft>();
			this.businessHangar.addElement(sc);
		}
		else{
			this.businessHangar.addElement(sc);
		}
	}

	/**
	 * Method to add a specified amount to the businessAccountBalance.
	 * @param amount The amount of money to be added.
	 */
	void addBusinessAccountBalance(int amount){
		businessAccountBalance += amount;
	}
	
	/**
	 * Method for getting SpaceCraft information from the businessHangar.
	 * @param index The location in the businessHanger at which the SpaceCraft resides.
	 * @return The SpaceCraft specified.
	 */
	SpaceCraft getCraft(int index){
		return businessHangar.get(index);
	}
	
	/**
	 * Method to subtract a specified amount from the businessAccountBalance.
	 * @param amount The amount of money to be subtracted.
	 */
	void subBusinessAccountBalance(int amount){
		businessAccountBalance -= amount;
	}
}