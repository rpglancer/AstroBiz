package astroBiz.lib;

import java.io.Serializable;
import java.util.Vector;

import astroBiz.info.CRAFTINFO;
import astroBiz.info.MFGINFO;

public class Manufacturer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1295916427609105506L;
	private Faction affiliation;
	private String name = "Untitled MFG";
	private String symbol = "NONE";
	private Vector<SpaceCraft> models = new Vector<SpaceCraft>();
	private int yearDissolved = 0;
	private int yearIncorporated = 0;
	private int x = 0;
	private int y = 0;
	
	public Manufacturer(MFGINFO mfg, Scenario s){
		this.name = mfg.getCorpName();
		this.symbol = mfg.getSymbol();
		this.yearDissolved = mfg.getDis();
		this.yearIncorporated = mfg.getInc();
		this.x = mfg.getX();
		this.y = mfg.getY();
		switch(mfg.getFaction()){
		case FAC00:
			affiliation = s.getFactions().elementAt(0);
			break;
		case FAC01:
			affiliation = s.getFactions().elementAt(1);
			break;
		case FAC02:
			affiliation = s.getFactions().elementAt(2);
			break;
		case FAC03:
			affiliation = s.getFactions().elementAt(3);
			break;
		case FAC04:
			affiliation = s.getFactions().elementAt(4);
			break;
		case FAC05:
			affiliation = s.getFactions().elementAt(5);
			break;
		case FAC06:
			affiliation = s.getFactions().elementAt(6);
			break;
		case FAC07:
			affiliation = s.getFactions().elementAt(7);
			break;
		}
		loadModels();
	}
	
	public Faction getAffiliation(){
		return affiliation;
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
		for(CRAFTINFO ci : CRAFTINFO.values()){
			if(ci.getMfg() == this.symbol){
				this.models.addElement(new SpaceCraft(ci));
			}
		}
	}
}
