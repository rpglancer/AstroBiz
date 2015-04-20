package astroBiz;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	AstroBiz astrobiz;
	
	public KeyInput(AstroBiz astrobiz){
		this.astrobiz = astrobiz;
	}	
	
	public void keyPressed(KeyEvent e){
		astrobiz.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		astrobiz.keyReleased(e);
	}

}
