package astroBiz.lib;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import astroBiz.info.FontInformation;
import astroBiz.util.textUtilities;


public class TextWindow implements Entity {
	private Boolean doesTick = false;
	private Boolean hasSprite = false;
	
	private BufferedImage sprite;
	
	private int tickCount;
	private int tickSpeed = 0;
	private int height;
	private int width;
	private int x;
	private int y;
	
	private String text = "";
	private String textOutput = "";
	
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
	
	public TextWindow(String text, BufferedImage sprite){
		this.x = 96;
		this.y = 352;
		this.width = 672;
		this.height = 96;
		this.sprite = sprite;
		this.text = text;
		this.textOutput += this.text.charAt(0);
		this.doesTick = true;
		this.hasSprite = true;
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
		g.setColor(Color.white);
		if(hasSprite){
			g.drawImage(sprite, 32, 320, null);
			textUtilities.drawStringMultiLine(g, FontInformation.chitchat, x+64, y, width-64, textOutput);
		}
		else{
			textUtilities.drawStringMultiLine(g, FontInformation.chitchat, x, y, width, textOutput);
		}
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
