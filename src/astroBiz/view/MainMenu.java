package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import astroBiz.AstroBiz;
import astroBiz.info.ENTITY_TYPE;

/**
 * Displays the Main Menu at the start of the game.
 * @author Matt Bangert
 *
 */
public class MainMenu implements Serializable,Manager{
	private Boolean isActive = false;
	private ENTITY_TYPE type = ENTITY_TYPE.VIEW_MANAGER;
	private static final long serialVersionUID = 9122175924331276861L;

	public static enum MENUSELECT {
		NEWGAME,
		LOADGAME,
		QUITGAME
	}
	
	public Rectangle newGameButton = new Rectangle(astroBiz.AstroBiz.WIDTH/2 - 50, 150, 100, 50);
	public Rectangle loadGameButton = new Rectangle(astroBiz.AstroBiz.WIDTH/2 - 50, 225, 100, 50);
	public Rectangle quitGameButton = new Rectangle(astroBiz.AstroBiz.WIDTH/2 - 50, 300, 100, 50);

	Font fnt0 = new Font("arial", Font.BOLD, 50);
	Font fnt1 = new Font("arial", Font.BOLD, 15);

	Graphics2D g2d;
	
	private MENUSELECT menuSelect = MENUSELECT.NEWGAME;
	
	public void render(Graphics g){
		g2d = (Graphics2D) g;
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("AstroBiz", 300, 100);
		g.setFont(fnt1);
		g.setColor(getButtonColor(MENUSELECT.NEWGAME));
		g.drawString("New Game", newGameButton.x + 14, newGameButton.y + 32);
		g2d.draw(newGameButton);getClass();
		g.setColor(getButtonColor(MENUSELECT.LOADGAME));
		g.drawString("Load Game", loadGameButton.x + 14, loadGameButton.y + 32);
		g2d.draw(loadGameButton);
		g.setColor(getButtonColor(MENUSELECT.QUITGAME));
		g.drawString("Quit Game", quitGameButton.x + 14, quitGameButton.y + 32);
		g2d.draw(quitGameButton);
	}
	
	public Color getButtonColor(MENUSELECT button){
		if(menuSelect == button){
			return Color.green;
		}
		else
			return Color.white;
	}
		
	public void cycleMenuNext(){
		if(this.menuSelect == MENUSELECT.NEWGAME)
			this.menuSelect = MENUSELECT.LOADGAME;
		else if(this.menuSelect == MENUSELECT.LOADGAME)
			this.menuSelect = MENUSELECT.QUITGAME;
		else if(this.menuSelect == MENUSELECT.QUITGAME)
			this.menuSelect = MENUSELECT.NEWGAME;
	}
	
	public void cycleMenuPrev(){
		if(this.menuSelect == MENUSELECT.NEWGAME)
			this.menuSelect = MENUSELECT.QUITGAME;
		else if(this.menuSelect == MENUSELECT.LOADGAME)
			this.menuSelect = MENUSELECT.NEWGAME;
		else if(this.menuSelect == MENUSELECT.QUITGAME)
			this.menuSelect = MENUSELECT.LOADGAME;
	}
	
	public void setMenuStatus(MENUSELECT value){
		this.menuSelect = value;
	}
	
	public MENUSELECT getMenuStatus(){
		return this.menuSelect;
	}
	
	public Rectangle getNewGameButton(){
		return this.newGameButton;
	}
	
	public Rectangle getLoadGameButton(){
		return this.loadGameButton;
	}
	
	public Rectangle getQuitGameButton(){
		return this.quitGameButton;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public ENTITY_TYPE getType() {
		return type;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActive(boolean b) {
		isActive = b;
	}

	@Override
	public void keyAction(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_DOWN:
			cycleMenuNext();
			break;
		case KeyEvent.VK_ENTER:
			if(getMenuStatus() == MENUSELECT.NEWGAME){
				isActive = false;
				AstroBiz.getSV().setActive(true);
			}	
			if(getMenuStatus() == MENUSELECT.LOADGAME)
				break;
			if(getMenuStatus() == MENUSELECT.QUITGAME)
				System.exit(1);
				break;
		case KeyEvent.VK_UP:
			cycleMenuPrev();
			break;
			
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public VM getVM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVM(VM vm) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void setVMDefault() {
		// TODO Auto-generated method stub
		
	}
	
}
