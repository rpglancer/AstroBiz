package astroBiz;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

	/*
	public Rectangle newGameButton = new Rectangle(AstroBiz.WIDTH/2 - 50, 150, 100, 50);
	public Rectangle loadGameButton = new Rectangle(AstroBiz.WIDTH/2 - 50, 225, 100, 50);
	public Rectangle quitGameButton = new Rectangle(AstroBiz.WIDTH/2 - 50, 300, 100, 50);
	*/
	
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
		case MENU:
			// Play Button
			if(mx >= AstroBiz.WIDTH / 2 - 50 && mx <= AstroBiz.WIDTH / 2 - 50 + 100){
				if(my >= 150 && my <= 200){
					// Pressed Play
					AstroBiz.State = AstroBiz.STATE.GAME;
				}
			}
			
			// Quit Button
			if(mx >= AstroBiz.WIDTH / 2 - 50 && mx <= AstroBiz.WIDTH / 2 - 50 + 100){
				if(my >= 300 && my <= 350){
					// Pressed Play
					System.exit(1);
				}
			}
			break;
		case GAME:
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
