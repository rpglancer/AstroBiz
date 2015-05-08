package astroBiz.lib;

import astroBiz.info.FactionInformation.FACTIONINFO;
import astroBiz.info.FactionInformation.STANDING;

public class Faction {
	private int fID;
	private String name;
	private String abrv;
	private STANDING[] standing = new STANDING[8];
	
	public Faction(FACTIONINFO fi){
		fID = fi.getID();
		name = fi.getName();
		abrv = fi.getAbrv();
		for(int i = 0; i < 8; i++){
			standing[i] = fi.getRepWith(i);
		}
	}
	
	public String getAbrv(){
		return this.abrv;
	}
	
	public int getfID(){
		return this.fID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public STANDING getStanding(int with){
		return this.standing[with];
	}
}
