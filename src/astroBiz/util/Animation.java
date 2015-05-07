package astroBiz.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	
	private BufferedImage current;
	
	private BufferedImage frame1;
	private BufferedImage frame2;
	private BufferedImage frame3;
	
	public Animation(int speed, BufferedImage frame1, BufferedImage frame2, BufferedImage frame3){
		this.speed = speed;
		this.frame1 = frame1;
		this.frame2 = frame2;
		this.frame3 = frame3;
		this.frames = 3;
	}
	
	public void drawAnimation(Graphics g, int x, int y){
		g.drawImage(current, x, y, null);
	}
	
	public void nextFrame(){
		switch(frames){
		case 3:
			if(count == 0) current = frame1;
			if(count == 1) current = frame2;
			if(count == 2) current = frame3;
			count++;
			if(count > frames) count = 0;
			break;
		}
	}
	
	public void runAnimation(){
		index++;
		if(index > speed){
			index = 0;
			nextFrame();
		}
	}
	
	public int getCount(){
		return this.count;
	}
	
	public int getSpeed(){
		return this.speed;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}

}
