package astroBiz.lib;

import astroBiz.info.FACTION;
import astroBiz.info.STANDING;

public class Faction {
	private int fID;
	private String name;
	private String abrv;
	private STANDING[] standing = new STANDING[8];
	
	public Faction(FACTION f){
		fID = f.getID();
		name = f.getName();
		abrv = f.getAbrv();
		for(int i = 0; i < 8; i++){
			standing[i] = f.getRepWith(i);
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
