package astroBiz.info;

public enum FACILITY {
	PORT_CITY		(20),
	PORT_METRO		(35),
	PORT_REGION		(50),
	PORT_PLANETARY	(75),
	PORT_STELLAR	(100);
	
	private int slots;
	
	FACILITY(int slots){
		this.slots = slots;
	}
	
	public int getSlots(){
		return slots;
	}
}
