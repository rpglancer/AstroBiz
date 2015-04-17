package astroBiz;

import java.util.Vector;

public class Map {
	private Vector<Location> mapLocations;
	private int mapNumLocations;
	private int mapX;							// Map width
	private int mapY;							// Map height

	Map(int y, int x, int numloc){
		this.mapLocations = new Vector<Location>();
		this.mapNumLocations = numloc;
		this.mapY = y;
		this.mapX = x;
		for(int i = 0; i < numloc; i++){
			Location l = new Location();
			l.generateLocation(mapY, mapX);
			this.mapLocations.addElement(l);
		}
	}
	
	Location getMapLocation(int index){
		return this.mapLocations.elementAt(index);
	}
	
	int getMapNumLocations(){
		return this.mapNumLocations;
	}
	
	int getMapX(){
		return this.mapX;
	}
	
	int getMapY(){
		return this.mapY;
	}
}
