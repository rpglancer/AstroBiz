package astroBiz;

import java.util.Vector;

import astroBiz.info.CraftInformation.CI_DEVCO;
import astroBiz.info.ManufacturerInformation;
import astroBiz.info.ManufacturerInformation.MI;

public class Manufacturer {
	private String name = "Untitled MFG";
	private String symbol = "NONE";
	private Vector<SpaceCraft> models = new Vector<SpaceCraft>();
	private int yearDissolved = 0;
	private int yearIncorporated = 0;
	private int x = 0;
	private int y = 0;
	
//	public Manufacturer(int index){
//		this.name = ManufacturerInformation.names[index];
//		this.yearDissolved = ManufacturerInformation.yearsDissolved[index];
//		this.yearIncorporated = ManufacturerInformation.yearsIncorporated[index];
//		this.x = ManufacturerInformation.locationsX[index];
//		this.y = ManufacturerInformation.locationsY[index];
//		loadModels();
//	}
	
	public Manufacturer(MI mi){
		this.name = mi.getCorpName();
		this.symbol = mi.getSymbol();
		this.yearDissolved = mi.getDis();
		this.yearIncorporated = mi.getInc();
		this.x = mi.getX();
		this.y = mi.getY();
		loadModels();
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSymbol(){
		return this.symbol;
	}
	
	public Vector<SpaceCraft> getModelsAll(){
		return this.models;
	}
	public Vector<SpaceCraft> getModeslAvailable(Scenario s){
		if(this.models.size() == 0) return this.models;
		else{
			Vector<SpaceCraft> temp = new Vector<SpaceCraft>();
			for(int i = 0; i < models.size(); i++){
				if(s.getCurrentYear() >= models.elementAt(i).getYearIntroduced() && s.getCurrentYear() <= models.elementAt(i).getYearRetired()){
					temp.add(models.elementAt(i));
				}
			}
			return temp;
		}
	}
	public int getYearDissolved(){
		return this.yearDissolved;
	}
	public int getYearIncorporated(){
		return this.yearIncorporated;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
	public void loadModels(){
		switch(this.symbol){
		case "DCO":
			for(CI_DEVCO devco : CI_DEVCO.values()){
				SpaceCraft temp = new SpaceCraft(devco);
				this.models.addElement(temp);
			}
			break;
		}
	}
}
