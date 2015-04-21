package astroBiz;

public class LocationView {
	private Location location;
	
	LocationView(Location l){
		this.location = l;
	}
	public void render(){
		// TODO: Actually render the location GUI.
	}
	public void tick(){
		// TODO: Determine if anything in the LV actually needs to tick.
	}
	
	public void setLocationView(Location l){
		this.location = l;
	}

}
