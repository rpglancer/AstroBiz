package astroBiz.lib;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import astroBiz.info.ENTITY_TYPE;

public abstract interface Entity{
	public boolean isActive();
	public ENTITY_TYPE getType();
	public void tick();
	public void render(Graphics g);
	public void setActive(boolean b);
	public void keyAction(KeyEvent e);
}
