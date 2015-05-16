package astroBiz.lib;

import java.awt.image.BufferedImage;

import astroBiz.AstroBiz;

public class Employee {
	public static enum EMPTASK{
		IDLE,
		SLOT,
		VENT;
	}
	private EMPTASK task = EMPTASK.IDLE;
	private int spriteX = 1;
	private int spriteY = 1;
	private int taskttl = 0;
	private Location location = null;
	
	public BufferedImage getSprite(){
		return AstroBiz.employeeSprites.grabImage(spriteX, spriteY, 128, 128);
	}
	
	public void beginTask(EMPTASK task, Location at, int fortime){
		this.task = task;
		this.location = at;
		this.taskttl = fortime;
	}

	public void finishTask(){
		task = EMPTASK.IDLE;
		location = null;
		taskttl = 0;
	}

	public EMPTASK queryTask(){
		return task;
	}
}
