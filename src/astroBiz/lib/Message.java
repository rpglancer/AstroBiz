package astroBiz.lib;

import java.io.Serializable;


public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9033134393548233628L;
	private MSGTYPE msgType;
	private Business msgBusiness;
	private Location msgLocation;
	private Manufacturer msgManufacturer;
	private SpaceCraft msgSpaceCraft;
	private int[] var = new int[4];
	
	
	public static enum MSGTYPE{
		MSG_NEGOTIATE,
		MSG_ORDER;
	}

	public Message(Business busi, Manufacturer mfg, SpaceCraft sc, int qty, int year, int qtr){
		this.msgType = MSGTYPE.MSG_ORDER;
		this.msgBusiness = busi;
		this.msgManufacturer = mfg;
		this.msgSpaceCraft = sc;
		this.var[0] = qty;
		this.var[1] = year;
		this.var[2] = qtr;
	}
	
	public Business getBusiness(){
		return this.msgBusiness;
	}
	
	public int[] getVar(){
		return this.var;
	}
	
	public Location getLocation(){
		return this.msgLocation;
	}
	
	public Manufacturer getMfg(){
		return this.msgManufacturer;
	}
	
	public SpaceCraft getCraft(){
		return this.msgSpaceCraft;
	}
	
	public MSGTYPE getType(){
		return this.msgType;
	}
}
