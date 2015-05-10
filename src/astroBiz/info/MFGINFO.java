package astroBiz.info;

public enum MFGINFO {
	DEVCO 	("Developer Corporation", 		"DCO", FACTION.FAC00, 2200, 2300, 128, 128),
	GRUNDER ("Grunder Heavy Industries AG",	"GHI", FACTION.FAC00, 2200, 2250, 240, 64),
	SOKOLOV ("Sokolov Design Bureau", 		"SDB", FACTION.FAC00, 2200, 2225, 64, 296);
	
	private final String name;
	private final String symbol;
	private final FACTION faction;
	private final int incorporated;
	private final int dissolved;
	private final int x;
	private final int y;
	
	MFGINFO(String name, String symbol, FACTION faction, int inc, int dis, int x, int y){
		this.name = name;
		this.symbol = symbol;
		this.faction = faction;
		this.incorporated = inc;
		this.dissolved = dis;
		this.x = x;
		this.y = y;
	}
	
	public String getCorpName(){
		return this.name;
	}
	
	public FACTION getFaction(){
		return faction;
	}
	
	public String getSymbol(){
		return this.symbol;
	}
	
	public int getInc(){
		return this.incorporated;
	}
	
	public int getDis(){
		return this.dissolved;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}	
}
