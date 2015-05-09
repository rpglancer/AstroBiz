package astroBiz.lib;
import java.awt.Color;
import java.util.Vector;

/**
 * Contains all the necessary information for a space transport company.
 * @author Matt Bangert
 *
 */
public class Business {
	private boolean isPlayerOwned = false;
	
	private int advertisingCosts = 0;
	private int maintenanceCosts = 0;
	private int serviceCosts = 0;
	
	private Color businessColor;
	private Integer accountBalance = 0;
	private Location headQuarters = null;
	private String businessName = "Untitled Company";
	
	private Vector<Location> regionalHubs = new Vector<Location>();
	Vector<Route> businessRoutes = new Vector<Route>();
	private	Vector<SpaceCraft> spaceCraftHangar = new Vector<SpaceCraft>();
	
	public void addCraft(SpaceCraft sc){
		this.spaceCraftHangar.addElement(sc);
	}

	public void addToAccount(int amount){
		accountBalance += amount;
	}

	
	public Color getColor(){
		return businessColor;
	}
	
	public Integer getAccountBalance(){
		return this.accountBalance;
	}

	public int getAdCosts(){
		return this.advertisingCosts;
	}
	
	SpaceCraft getCraft(int index){
		return spaceCraftHangar.get(index);
	}

	public int getCraftInHangar(SpaceCraft type){
		int count = 0;
		if(spaceCraftHangar.size() == 0) return count;
		else{
			for(int i = 0; i < spaceCraftHangar.size(); i++){
				if(spaceCraftHangar.elementAt(i).getName() == type.getName()) count++;
			}
		}
		return count;
	}
	
	public int getCraftInService(SpaceCraft type){
		int count = 0;
		if(businessRoutes.size() == 0) return count;
		else{
			for(int i = 0; i < businessRoutes.size(); i++){
				if(businessRoutes.elementAt(i).getCraftModel() == type.getName()){
					count += businessRoutes.elementAt(i).getCraftCount();
				}
			}
		}
		return count;
	}
	
	public Vector<SpaceCraft> getHangar(){
		return this.spaceCraftHangar;
	}

	public Vector<Location> getHubs(){
		return this.regionalHubs;
	}
	
	public Location getHQ(){
		return this.headQuarters;
	}

	public boolean getIsPlayerOwned(){
		return this.isPlayerOwned;
	}
	
	public int getMaintCosts(){
		return this.maintenanceCosts;
	}
	
	public String getName(){
		return this.businessName;
	}
	
	public Vector<Route> getRoutes(){
		return this.businessRoutes;
	}

	public int getServiceCosts(){
		return this.serviceCosts;
	}
	
	public void setColor(int r, int g, int b){
		this.businessColor = new Color(r,g,b);
	}

	public void setColor(Color color){
		this.businessColor = color;
	}
	
	public void setHQ(Location hq){
		this.businessName = hq.getLocationName() + " Airlines";
		if(this.regionalHubs == null) this.regionalHubs = new Vector<Location>();
		this.regionalHubs.addElement(hq);
		this.headQuarters = hq;
		hq.setHQ(this);
	}

	public void setName(String name){
		this.businessName = name;
	}
	
	public void setPlayerOwned(boolean p){
		this.isPlayerOwned = p;
	}
		
	public void subFromAccount(int amount){
		accountBalance -= amount;
	}
}