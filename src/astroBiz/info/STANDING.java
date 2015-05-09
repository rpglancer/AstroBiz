package astroBiz.info;

public enum STANDING {
	ALLY 	(0),
	WARM	(1),
	NEUTRAL	(2),
	COOL	(3),
	HOSTILE	(4);
	
	private int standing;
	
	STANDING(int standing){
		this.standing = standing;
	}
	
	public int getStanding(){
		return standing;
	}
}
