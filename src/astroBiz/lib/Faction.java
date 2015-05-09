package astroBiz.lib;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import astroBiz.info.FACTION;
import astroBiz.info.STANDING;

public class Faction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6738499337097328072L;

	private BufferedImage flag;
	
	private int fID;
	
	private String abrv;
	private String name;
	
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
	
	// Probably String abrv rather than int with, not sure.
	public STANDING getStanding(int with){
		return this.standing[with];
	}
}
