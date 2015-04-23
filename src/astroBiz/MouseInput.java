package astroBiz;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;



public class MouseInput implements MouseListener{
	
	private AstroBiz astrobiz;
	
	MouseInput(AstroBiz a){
		this.astrobiz = a;
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
			break;		// End LOCATIONVIEW
			
		case MENU:
			// Play Button
			if(mx >= AstroBiz.WIDTH / 2 - 50 && mx <= AstroBiz.WIDTH / 2 - 50 + 100){
				if(my >= 150 && my <= 200){
					// Pressed Play
					//AstroBiz.State = AstroBiz.STATE.REGIONVIEW;
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
			break;		// End MENU
			
		case GAME:
			// Don't use this. Legacy from prototyping and testing things.
			break;		// End Game
			
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
							astrobiz.getLocationView().setLocationView(current);
							AstroBiz.State = AstroBiz.STATE.LOCATIONVIEW;
							// TODO: Code to display location.
						}
					}
				}
			}
			break;		// End REGIONVIEW
			
		case SCENARIOCONFIRM:
			break;		// End SCENARIOCONFIRM
			
		case SCENARIOVIEW:
			if(mx >= 32 && mx <= 32 + ScenarioView.SBWIDTH){
				if(my >= 32 && my <= 32 + ScenarioView.SBHEIGHT){
					// Scenario 1
					astrobiz.getScenario().setScenario(0);
					AstroBiz.State = AstroBiz.STATE.REGIONVIEW;
				}
			}
			
			if(mx >= 32 && mx <= 32 + ScenarioView.SBWIDTH){
				if(my >= 144 && my <= 32 + ScenarioView.SBHEIGHT){
					// Scenario 2
					astrobiz.getScenario().setScenario(1);
					AstroBiz.State = AstroBiz.STATE.REGIONVIEW;
				}
			}
			
			if(mx >= 32 && mx <= 32 + ScenarioView.SBWIDTH){
				if(my >= 256 && my <= 32 + ScenarioView.SBHEIGHT){
					// Scenario 3
					astrobiz.getScenario().setScenario(2);
					AstroBiz.State = AstroBiz.STATE.REGIONVIEW;
				}
			}
			
			if(mx >= 32 && mx <= 32 + ScenarioView.SBWIDTH){
				if(my >= 368 && my <= 32 + ScenarioView.SBHEIGHT){
					// Scenario 4
					astrobiz.getScenario().setScenario(3);
					AstroBiz.State = AstroBiz.STATE.REGIONVIEW;
				}
			}		
			break;		// End SCENARIOVIEW
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
