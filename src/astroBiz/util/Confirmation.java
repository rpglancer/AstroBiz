package astroBiz.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import astroBiz.view.Manager;
import astroBiz.view.VM;

public class Confirmation {
	private Manager manager;
	private VM prev;
	private VM next;
	int x, y;
	private byte opt = 0;
	private Boolean isActive = false;

	public void render(Graphics g){
		switch(opt){
		case 0:
			g.setColor(Color.blue);
			g.drawString("YES", 32, 32);
			break;
		case 1:
			g.setColor(Color.red);
			g.drawString("NO", 32, 32);
			break;
		}
	}
	private void confirmOption(){
		if(opt == 0){
			isActive = false;
			System.out.println(next);
			manager.setVM(next);
			this.manager = null;
			this.prev = null;
			this.next = null;
		}
		else{
			isActive = false;
			System.out.println(prev);
			manager.setVM(prev);
			this.manager = null;
			this.prev = null;
			this.next = null;
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
		this.manager = manager;
		this.prev = prev;
		this.next = next;
		this.opt = 0;
		setIsActive();
	}
	
	public boolean getIsActive(){
		return this.isActive;
	}
	
	private void setIsActive(){
		this.isActive = true;
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
