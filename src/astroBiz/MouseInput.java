package astroBiz;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import astroBiz.AstroBiz.STATE;
import astroBiz.view.RegionView;


/**
 * MouseInput is here for future use, the initial builds should be 100% operable with the Keyboard.
 * @author Matt Bangert
 *
 */
public class MouseInput implements MouseMotionListener, MouseListener{
	
	private AstroBiz astrobiz;
	
	MouseInput(AstroBiz a){
		this.astrobiz = a;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		switch(AstroBiz.State){
		
		case LOCATIONVIEW:
			if(mx >= 736 && mx <= 736+32){
				if(my >= 32 && my <= 32 + 32){
					AstroBiz.State = AstroBiz.STATE.REGIONVIEW;
				}
			}
			break;
		// End LOCATIONVIEW
			
		case MENU:
			// Play Button
			if(mx >= AstroBiz.WIDTH / 2 - 50 && mx <= AstroBiz.WIDTH / 2 - 50 + 100){
				if(my >= 150 && my <= 200){
					// Pressed Play
					AstroBiz.State = AstroBiz.STATE.SCENARIOSETUP;
				}
			}
			
			// Quit Button
			if(mx >= AstroBiz.WIDTH / 2 - 50 && mx <= AstroBiz.WIDTH / 2 - 50 + 100){
				if(my >= 300 && my <= 350){
					// Pressed Quit
					System.exit(1);
				}
			}
			break;
		// End MENU
			
		case REGIONVIEW:
			RegionView rv = astrobiz.getRegion();
			Vector<Location> lv = rv.getLocationVector();
			int regionID = rv.getRegionID(rv.getRegionX(), rv.getRegionY());
			Location current = null;
			
			for(int i = 0; i < lv.size(); i++){
				current = lv.elementAt(i);
				if(current.getLocationRegion() == regionID){
					if(mx >= current.getLocationX() && mx <= current.getLocationX() + 16){
						if(my >= current.getLocationY() && my <= current.getLocationY() + 16){
							System.out.println(current.getLocationName() + " @ " + current.getLocationRegion());
							AstroBiz.State = STATE.LOCATIONVIEW;
							astrobiz.getLocationView().setLocationView(current);
						}
					}
				}
			}
			break;
		// End REGIONVIEW
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseMoved(MouseEvent e) {
	}

}
