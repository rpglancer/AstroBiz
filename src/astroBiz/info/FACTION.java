package astroBiz.info;

public enum FACTION {
	FAC00 	(0, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC01	(1, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC02	(2, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC03	(3, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC04	(4, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC05	(5, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC06	(6, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC07	(7, "Faction Name", "Fac Abbr.", STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL);
	
	private int fID;
	private String name;
	private String abrv;
	private STANDING repFac01;
	private STANDING repFac02;
	private STANDING repFac03;
	private STANDING repFac04;
	private STANDING repFac05;
	private STANDING repFac06;
	private STANDING repFac07;
	private STANDING repFac08;
	
	
	FACTION(int fID, String name, String abrv, STANDING r1, STANDING r2, STANDING r3, STANDING r4, STANDING r5, STANDING r6, STANDING r7, STANDING r8){
		this.fID = fID;
		this.name = name;
		this.abrv = abrv;
		this.repFac01 = r1;
		this.repFac02 = r2;
		this.repFac03 = r3;
		this.repFac04 = r4;
		this.repFac05 = r5;
		this.repFac06 = r6;
		this.repFac07 = r7;
		this.repFac08 = r8;
	};
	
	public int getID(){
		return this.fID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getAbrv(){
		return this.abrv;
	}
	
	public STANDING getRepWith(int fac){
		if(fac == 0) return this.repFac01;
		if(fac == 1) return this.repFac02;
		if(fac == 2) return this.repFac03;
		if(fac == 3) return this.repFac04;
		if(fac == 4) return this.repFac05;
		if(fac == 5) return this.repFac06;
		if(fac == 6) return this.repFac07;
		if(fac == 7) return this.repFac08;
		else return STANDING.NEUTRAL;
	}
}