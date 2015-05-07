package astroBiz.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import astroBiz.info.FontInformation;
import astroBiz.view.Manager;
import astroBiz.view.VM;

public class Confirmation {
	private static final int boxX = 96;
	private static final int boxY = 352;
	private static final int boxW = 672;
	private static final int boxH = 96;
	
	private static final int confW = 64;
	private static final int confH = 32;
	
	private static final int textX = 160;
	private static final int textY = 352;
	private static final int textW = 608;
	
	private static final int spriteX = 32;
	private static final int spriteY = 320;
	/**
	 * The Manager from which the Confirmation was requested and for which the VM is changed based upon Confirmation outcome.
	 */
	private Manager manager;
	/**
	 * The VM to which the Manager will resume upon a negative confirmation.
	 */
	private VM prev;
	/**
	 * The VM to which the Manager will proceed upon a positive confirmation.
	 */
	private VM next;
	private BufferedImage sprite = null;
	private String text = null;
	/**
	 * The coordinates to which the Confirmation selection boxes will be drawn provided a {@link #withCoords} value of true.
	 */
	int x, y;
	private byte opt = 0;
	private Boolean isActive = false;
	private Boolean withCoords = false;
	private Boolean withText = false;

	public void render(Graphics g){
		if(withText){
			g.setColor(Color.darkGray);
			g.fillRect(boxX, boxY, boxW, boxH);
			g.drawImage(sprite, spriteX, spriteY, null);
			g.setColor(Color.white);
			textUtilities.drawStringMultiLine(g, FontInformation.chitchat, textX, textY, textW, text);	
		}
		
		g.setFont(FontInformation.confirm);
		
		if(withCoords){
			if(opt == 0){
				g.setColor(Color.red);
				g.fillRect(x, y, confW, confH);
				g.setColor(Color.white);
				g.drawString("YES", x, y+28);
				g.setColor(Color.blue);
				g.fillRect(x+confW, y, confW, confH);
				g.setColor(Color.gray);
				g.drawString("NO", x+73, y+28);
			}
			else{
				g.setColor(Color.blue);
				g.fillRect(x, y, confW, confH);
				g.setColor(Color.gray);
				g.drawString("YES", x, y+28);
				g.setColor(Color.red);
				g.fillRect(x+confW, y, confW, confH);
				g.setColor(Color.white);
				g.drawString("NO", x+73, y+28);		
			}
		}
		
		else{
			if(opt == 0){
				g.setColor(Color.red);
				g.fillRect(640, 416, confW, confH);
				g.setColor(Color.white);
				g.drawString("YES", 640, 444);
				g.setColor(Color.blue);
				g.fillRect(640+confW, 416, confW, confH);
				g.setColor(Color.gray);
				g.drawString("NO", 640+73, 444);
			}
			else{
				g.setColor(Color.blue);
				g.fillRect(640, 416, confW, confH);
				g.setColor(Color.gray);
				g.drawString("YES", 640, 444);
				g.setColor(Color.red);
				g.fillRect(640+confW, 416, confW, confH);
				g.setColor(Color.white);
				g.drawString("NO", 640+73, 444);		
			}
		}
	}

	private void confirmOption(){
		if(opt == 0){
			isActive = false;
			System.out.println(next);
			manager.setVM(next);
			this.flush();
		}
		else{
			isActive = false;
			System.out.println(prev);
			manager.setVM(prev);
			this.flush();
		}
	}
	
	/**
	 * Method sets the requisite options for Confirmation.
	 * @param manager	The active Manager to which the outcome of the confirmation will apply.
	 * @param prev	The viewmode to be returned to if the confirmation is negative.
	 * @param next	The viewmode to be advanced to if the confirmation is positive.
	 * @param x	The x coordinate of the top-left point of the confirmation box.
	 * @param y	The y corrdinate of the top-left point of the confirmation box.
	 */
	public void setConfirmVM(Manager manager, VM prev, VM next, int x, int y){
		if(manager == null || prev == null || next == null) return;
		this.x = x;
		this.y = y;
		this.manager = manager;
		this.prev = prev;
		this.next = next;
		this.opt = 0;
		this.withCoords = true;
		this.isActive = true;
	}
	
	public void setConfirmVM(Manager manager, VM prev, VM next, BufferedImage sprite, String text){
		if(manager == null || prev == null || next == null) return;
		if(sprite == null || text == null) return;
		
		this.manager = manager;
		this.prev = prev;
		this.next = next;
		this.sprite = sprite;
		this.text = text;
		this.opt = 0;
		this.withText = true;
		this.isActive = true;
	}
	
	/**
	 * Allows Managers to determine whether a Confirmation is currently taking place or not.
	 * @return {@link #isActive}
	 */
	public boolean getIsActive(){
		return this.isActive;
	}
	/**
	 * Resets the Confirmation to default values.
	 */
	private void flush(){
		this.manager = null;
		this.prev = null;
		this.next = null;
		this.sprite = null;
		this.text = null;
		this.x = 0;
		this.y = 0;
		this.opt = 0;
		this.isActive = false;
		this.withCoords = false;
		this.withText = false;
	}
	
	public void keyAction(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			opt = 0;
			break;
		case KeyEvent.VK_RIGHT:
			opt = 1;
			break;
		case KeyEvent.VK_ENTER:
			confirmOption();
			break;
		}
	}
}
