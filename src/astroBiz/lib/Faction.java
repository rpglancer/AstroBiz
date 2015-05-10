package astroBiz.lib;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import astroBiz.AstroBiz;
import astroBiz.info.FACTION;
import astroBiz.info.STANDING;

public class Faction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6738499337097328072L;
	
	private int fID;
	private int flagCol;
	private static int flagHeight = 64;
	private int flagRow;
	private static int flagWidth = 96;
	
	private String abrv;
	private String name;
	
	//	Holds defaulted STANDING values toward the other Factions.
	private STANDING[] standing = new STANDING[8];
	
	public Faction(FACTION f){
		fID = f.getID();
		name = f.getName();
		abrv = f.getAbrv();
		flagCol = f.getFlagCol();
		flagRow = f.getFlagRow();
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
	
	public BufferedImage getFlag(){
		return AstroBiz.factionFlags.grabImage(flagCol, flagRow, Faction.flagWidth, Faction.flagHeight);
	}
	
	public String getName(){
		return this.name;
	}
	
	// Probably String abrv rather than int with, not sure.
	public STANDING getStanding(int with){
		return this.standing[with];
	}
}
