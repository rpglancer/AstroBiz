package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import astroBiz.AstroBiz;
import astroBiz.lib.TextWindow;

/**
 * Displays the Main Menu at the start of the game.
 * @author Matt Bangert
 *
 */
public class MainMenu {
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
	
//	boolean buttonStatus[] = {false, false, false};
	
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
	
//	public void setButtonStatus(int button, boolean status){
//		if(button < 0 || button > 2)
//			return;
//		this.buttonStatus[button] = status;
//	}
	
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
	
}
