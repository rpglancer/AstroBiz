package astroBiz;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import astroBiz.AstroBiz.STATE;
import astroBiz.view.RegionView;
import astroBiz.view.ScenarioView;



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
					AstroBiz.State = AstroBiz.STATE.SCENARIOVIEW;
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
			
		case GAME:
			// Don't use this. Legacy from prototyping and testing things.
			break;
		// End Game
			
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
			
		case SCENARIOCONFIRM:
			break;	
		// End SCENARIOCONFIRM
			
		case SCENARIOVIEW:
			// Scenario 1
			if(mx >= 64 && mx <= 64 + ScenarioView.SBWIDTH){
				if(my >= 48 && my <= 48 + ScenarioView.SBHEIGHT){			
					astrobiz.getScenario().setScenario(0);
					AstroBiz.State = AstroBiz.STATE.SCENARIOCONFIRM;
				}
			}
			// End Scenario 1
			
			// Scenario 2
			if(mx >= 64 && mx <= 64 + ScenarioView.SBWIDTH){
				if(my >= 112 && my <= 112 + ScenarioView.SBHEIGHT){
					// Scenario 2
					astrobiz.getScenario().setScenario(1);
					AstroBiz.State = AstroBiz.STATE.SCENARIOCONFIRM;
				}
			}
			// Scenario 2
			
			// Scenario 3
			if(mx >= 64 && mx <= 64 + ScenarioView.SBWIDTH){
				if(my >= 176 && my <= 176 + ScenarioView.SBHEIGHT){
					// Scenario 3
					astrobiz.getScenario().setScenario(2);
					AstroBiz.State = AstroBiz.STATE.SCENARIOCONFIRM;
				}
			}
			// End Scenario 3
			
			// Scenario 4
			if(mx >= 64 && mx <= 64 + ScenarioView.SBWIDTH){
				if(my >= 240 && my <= 240 + ScenarioView.SBHEIGHT){
					// Scenario 4
					astrobiz.getScenario().setScenario(3);
					AstroBiz.State = AstroBiz.STATE.SCENARIOCONFIRM;
				}
			}
			// End Scenario 4
			break;
		// End SCENARIOVIEW
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		switch(AstroBiz.State){
		case MENU:
			// Play Button
			Rectangle newB = astrobiz.getMainMenu().getNewGameButton();
			Rectangle quiB = astrobiz.getMainMenu().getQuitGameButton();
			if(mx >= newB.x && mx <= newB.x + newB.width &&
					my >= newB.y && my <= newB.y + newB.height){
				astrobiz.getMainMenu().setButtonStatus(0, true);
				astrobiz.getMainMenu().setButtonStatus(1, false);
				astrobiz.getMainMenu().setButtonStatus(2, false);
			}
			else if(mx >= quiB.x && mx <= quiB.x + quiB.width &&
					my >= quiB.y && my <= quiB.y + quiB.height){
				astrobiz.getMainMenu().setButtonStatus(0, false);
				astrobiz.getMainMenu().setButtonStatus(1, false);
				astrobiz.getMainMenu().setButtonStatus(2, true);
			}
			else{
				astrobiz.getMainMenu().setButtonStatus(0, false);
				astrobiz.getMainMenu().setButtonStatus(1, false);
				astrobiz.getMainMenu().setButtonStatus(2, false);
			}
			break;
		default:
			break;
		}
	}

}
