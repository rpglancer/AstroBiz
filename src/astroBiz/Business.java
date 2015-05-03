package astroBiz;
import java.awt.Color;
import java.util.Vector;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business {
	private Integer accountBalance = 0;
	private Color businessColor;
	private boolean isPlayerOwned = false;
	private double advertisingCosts = 0;
	private double maintenanceCosts = 0;
	private double serviceCosts = 0;
	private	Vector<SpaceCraft> spaceCraftHangar = null;
	private Location headQuarters = null;
	private Vector<Location> regionalHubs = null;
	
	String businessName = "Untitled Company";
	Vector<Route> businessRoutes = new Vector<Route>();
	
	/**
	 * Default constructor to make a new Business.
	 */
	Business(){
		this.regionalHubs = new Vector<Location>();
		this.spaceCraftHangar = new Vector<SpaceCraft>();
	}
	
	void addCraft(SpaceCraft sc){
		this.spaceCraftHangar.addElement(sc);
	}

	void addBusinessAccountBalance(int amount){
		accountBalance += amount;
	}
	
	public Color getColor(){
		return businessColor;
	}
	
	public Integer getAccountBalance(){
		return this.accountBalance;
	}
	
	SpaceCraft getCraft(int index){
		return spaceCraftHangar.get(index);
	}
	
	public Vector<Location> getHubs(){
		return this.regionalHubs;
	}
	
	public Location getHQ(){
		return this.headQuarters;
	}
	
	public Vector<Route> getRoutes(){
		return this.businessRoutes;
	}
	
	public String getName(){
		return this.businessName;
	}
	
	void setColor(Business business, int r, int g, int b){
		business.businessColor = new Color(r,g,b);
	}
	
	public void setColor(Color color){
		this.businessColor = color;
	}
	
	public void setHQ(Location hq){
		this.businessName = hq.getLocationName() + " Airlines";
		if(this.regionalHubs == null) this.regionalHubs = new Vector<Location>();
		this.regionalHubs.addElement(hq);
		this.headQuarters = hq;
	}
	
	public void setBusinessPlayerOwned(boolean p){
		this.isPlayerOwned = p;
	}
	
	public void setName(String name){
		this.businessName = name;
	}
	
	public void subBusinessAccountBalance(int amount){
		accountBalance -= amount;
	}
}