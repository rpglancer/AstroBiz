package astroBiz.lib;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import astroBiz.lib.Location.LOCATIONTYPE;

public class AI implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7864731597209114801L;
	private Vector<Location> locations = new Vector<Location>();
	private Random r = new Random();
	public void aiPlaceHq(Scenario s, Business b, Vector<Location>available){
		if(locations.size() > 0) locations.clear();
		for(int i = 0; i < available.size(); i++){
			if(available.elementAt(i).getLocationType() == LOCATIONTYPE.LT_CITY && !available.elementAt(i).getLocationIsHub()){
				locations.addElement(available.elementAt(i));
//				System.out.println("Added: " + available.elementAt(i).getLocationName());
			}
		}
		int loc = r.nextInt(locations.size());
		b.setHQ(locations.elementAt(loc));
//		System.out.println("Business " + b.getName() + " set set HQ to " + b.getHQ());
	}
}
