package astroBiz.lib;

import java.awt.Color;
import java.awt.Graphics;

import astroBiz.info.FontInformation;
import astroBiz.util.textUtilities;


public class TextWindow implements Entity {
	private int x, y, width, height;
	private String text = "";
	private String textOutput = "";
	private int tickSpeed = 3;
	private int tickCount;
	
	private Boolean doesTick = false;
	
	public TextWindow(int x, int y, int width, int height, String text, boolean doestick){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.doesTick = doestick;
		if(this.doesTick){
			this.text = text;
			this.textOutput += this.text.charAt(0);
		}
		else{
			this.text = text;
			this.textOutput = text;
		}
	}

	@Override
	public void tick() {
		if(doesTick){
			if(tickCount == tickSpeed){
				tickCount = 0;
				if(text.length() != textOutput.length()){
					int i = textOutput.length();
					textOutput += text.charAt(i);
				}
			}
			else tickCount++;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.fillRect(100, 100, 100, 100);
		g.setColor(Color.white);
		textUtilities.drawStringMultiLine(g, FontInformation.chitchat, x, y, width, textOutput);
	}

	@Override
	public void getX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getY() {
		// TODO Auto-generated method stub
		
	}

}
