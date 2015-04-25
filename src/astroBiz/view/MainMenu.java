package astroBiz.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Displays the Main Menu at the start of the game.
 * @author Matt Bangert
 *
 */
public class MainMenu {
	
	public Rectangle newGameButton = new Rectangle(astroBiz.AstroBiz.WIDTH/2 - 50, 150, 100, 50);
	//public Rectangle newGameButton = new Rectangle(AstroBiz.WIDTH/2 - 50, 150, 100, 50);
	public Rectangle loadGameButton = new Rectangle(astroBiz.AstroBiz.WIDTH/2 - 50, 225, 100, 50);
	public Rectangle quitGameButton = new Rectangle(astroBiz.AstroBiz.WIDTH/2 - 50, 300, 100, 50);
	Font fnt0 = new Font("arial", Font.BOLD, 50);
	Font fnt1 = new Font("arial", Font.BOLD, 15);
	Graphics2D g2d;
	
	boolean buttonStatus[] = {false, false, false};
	
	public void render(Graphics g){	
		g2d = (Graphics2D) g;
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("AstroBiz", 300, 100);
		g.setFont(fnt1);
		g.setColor(getButtonColor(0));
		g.drawString("New Game", newGameButton.x + 14, newGameButton.y + 32);
		g2d.draw(newGameButton);getClass();
		g.setColor(getButtonColor(1));
		g.drawString("Load Game", loadGameButton.x + 14, loadGameButton.y + 32);
		g2d.draw(loadGameButton);
		g.setColor(getButtonColor(2));
		g.drawString("Quit Game", quitGameButton.x + 14, quitGameButton.y + 32);
		g2d.draw(quitGameButton);
	}
	
	public Color getButtonColor(int button){
		if(buttonStatus[button]){
			return Color.green;
		}
		else{
			return Color.white;
		}
	}
	
	public void setButtonStatus(int button, boolean status){
		if(button < 0 || button > 2)
			return;
		this.buttonStatus[button] = status;
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
