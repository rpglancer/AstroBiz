package astroBiz.info;

import astroBiz.lib.Faction;


public enum LOCINFO {
//	  enum		  name		  	 id	    r  bus  ind  tou    x    y   pop        owner
//----------------------------------------------------------------------------------------------
	ERTH_LAX ("Los Angeles",	"LAX",	2, 110,  43, 164, 150, 120,  8.0, FACTION.FAC00),
	ERTH_NYC ("New York", 		"NYC",	2, 145,  31, 180, 255, 112, 11.0, FACTION.FAC00),
	ERTH_HK ("Hong Kong", 		"HK",	2, 200,  64, 142, 625, 150,  9.0, FACTION.FAC00),
	ERTH_TKO ("Tokyo", 			"TKO",	2,  64,  27, 212, 685, 120, 18.0, FACTION.FAC00),
	ERTH_MBI ("Mumbai", 		"MBI",	2,  50, 144,  23, 550, 155, 20.0, FACTION.FAC00),
	ERTH_BEI ("Beijing", 		"BEI",	2, 220, 200, 101, 640, 115, 17.0, FACTION.FAC00),
	ERTH_LON ("London", 		"LON",	2, 172,  75, 224, 395,  95,  5.5, FACTION.FAC00),
	ERTH_SPO ("Sao Paolo", 		"SPO",	2, 100, 131, 197, 300, 230,  7.5, FACTION.FAC00),
	ERTH_JHB ("Johannesburg", 	"JHB",	2,  75,  50, 100, 445, 240,  0.4, FACTION.FAC00);
	
	private FACTION owner;
	private String name;
	private String id;
	private int bus, ind, tour, r, x, y;
	private double pop;
	
	LOCINFO(String name, String id, int r, int bus, int ind, int tour, int x, int y, double pop, FACTION owner){
		this.name = name;
		this.id = id;
		this.r = r;
		this.bus = bus;
		this.ind = ind;
		this.x = x;
		this.y = y;
		this.pop = pop;
		this.owner = owner;
	}
	public String getName(){
		return this.name;
	}
	public String getID(){
		return this.id;
	}
	public int getRegion(){
		return this.r;
	}
	public int getDemandBusiness(){
		return this.bus;
	}
	public int getDemandIndustry(){
		return this.ind;
	}
	public int getDemandTourism(){
		return this.tour;
	}
	public FACTION getOwner(){
		return this.owner;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public double getPopulation(){
		return this.pop;
	}
}
