package astroBiz.info;

public enum FACTION {
	FAC00 	(0, "North American Union", 	"NAU", 1, 1, STANDING.ALLY, STANDING.WARM, STANDING.NEUTRAL, STANDING.COOL, STANDING.HOSTILE, STANDING.NEUTRAL, STANDING.WARM, STANDING.COOL),
	FAC01	(1, "Eurasian Federation", 		"UAF", 2, 1, STANDING.WARM, STANDING.ALLY, STANDING.COOL, STANDING.NEUTRAL, STANDING.HOSTILE, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC02	(2, "Transhumanist Collective",	"THC", 3, 1, STANDING.NEUTRAL, STANDING.COOL, STANDING.ALLY, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC03	(3, "Neo-Islamic State", 		"NIS", 4, 1, STANDING.COOL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.ALLY, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC04	(4, "The Imperium", 			"IMP", 1, 2, STANDING.HOSTILE, STANDING.HOSTILE, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.ALLY, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC05	(5, "FACTION 05", 				"F05", 2, 2, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.ALLY, STANDING.NEUTRAL, STANDING.NEUTRAL),
	FAC06	(6, "Free States", 				"FS" , 3, 2, STANDING.WARM, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.ALLY, STANDING.NEUTRAL),
	FAC07	(7, "BRIC Alliance", 			"BA" , 4, 2, STANDING.COOL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.NEUTRAL, STANDING.ALLY);
	
	private int fID;
	private int flagCol;
	private int flagRow;
	private String name;
	private String abrv;
	private STANDING repFac00;
	private STANDING repFac01;
	private STANDING repFac02;
	private STANDING repFac03;
	private STANDING repFac04;
	private STANDING repFac05;
	private STANDING repFac06;
	private STANDING repFac07;
	
	
	FACTION(int fID, String name, String abrv, int col, int row, STANDING r0, STANDING r1, STANDING r2, STANDING r3, STANDING r4, STANDING r5, STANDING r6, STANDING r7){
		this.fID = fID;
		this.flagCol = col;
		this.flagRow = row;
		this.name = name;
		this.abrv = abrv;
		this.repFac00 = r0;
		this.repFac01 = r1;
		this.repFac02 = r2;
		this.repFac03 = r3;
		this.repFac04 = r4;
		this.repFac05 = r5;
		this.repFac06 = r6;
		this.repFac07 = r7;
	};
	
	public int getID(){
		return this.fID;
	}
	
	public int getFlagCol(){
		return flagCol;
	}
	
	public int getFlagRow(){
		return flagRow;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getAbrv(){
		return this.abrv;
	}
	
	public STANDING getRepWith(int fac){
		if(fac == 0) return this.repFac00;
		if(fac == 1) return this.repFac01;
		if(fac == 2) return this.repFac02;
		if(fac == 3) return this.repFac03;
		if(fac == 4) return this.repFac04;
		if(fac == 5) return this.repFac05;
		if(fac == 6) return this.repFac06;
		if(fac == 7) return this.repFac07;
		else return STANDING.NEUTRAL;
	}
}