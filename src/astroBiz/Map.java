package astroBiz;

import java.util.Vector;

public class Map {
	private Vector<Location> mapLocations;
	private int mapNumLocations;
	private int mapX;							// Map width
	private int mapY;							// Map height

	Map(int y, int x, int numloc){
		mapLocations = new Vector<Location>();
		mapNumLocations = numloc;
		mapY = y;
		mapX = x;
		for(int i = 0; i < numloc; i++){
			Location l = new Location();
			l.generateLocation(mapY, mapX);
			mapLocations.addElement(l);
		}
	}
	Location getMapLocation(int index){
		return mapLocations.elementAt(index);
	}
	int getMapNumLocations(){
		return mapNumLocations;
	}
	int getMapX(){
		return mapX;
	}
	int getMapY(){
		return mapY;
	}
}
