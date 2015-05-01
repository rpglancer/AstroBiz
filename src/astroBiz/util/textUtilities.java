package astroBiz.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import astroBiz.SpriteSheet;

/**
 * All non-standard methods for doing things to Strings go in this class.
 * <br>Now also contains methods to draw sprite based text.
 * @author Matt Bangert
 *
 */
public class textUtilities{
	public static int charWidth = 16;
	public static int charHeight = 16;
	
	/**
	 * Hash Map for all usable sprite based text characters.
	 */
	public static Map<Character, Point> textMap = new HashMap<Character, Point>();
/**
 * The Sprite Sheet containing all available characters.
 */
	public static SpriteSheet textSheet = null;

	/*
	 * Because fuck you String, that's why.
	 */
	public textUtilities(SpriteSheet text){
		textUtilities.textSheet = text;
		textMap.put('A', new Point(1,1)); textMap.put('B', new Point(2,1)); 
		textMap.put('C', new Point(3,1)); textMap.put('D', new Point(4,1));
		textMap.put('E', new Point(5,1)); textMap.put('F', new Point(6,1));
		textMap.put('G', new Point(7,1)); textMap.put('H', new Point(8,1));
		textMap.put('I', new Point(1,2)); textMap.put('J', new Point(2,2));
		textMap.put('K', new Point(3,2)); textMap.put('L', new Point(4,2));
		textMap.put('M', new Point(5,2)); textMap.put('N', new Point(6,2));
		textMap.put('O', new Point(7,2)); textMap.put('P', new Point(8,2));
		textMap.put('Q', new Point(1,3)); textMap.put('R', new Point(2,3));
		textMap.put('S', new Point(3,3)); textMap.put('T', new Point(4,3));
		textMap.put('U', new Point(5,3)); textMap.put('V', new Point(6,3));
		textMap.put('W', new Point(7,3)); textMap.put('X', new Point(8,3));
		textMap.put('Y', new Point(1,4)); textMap.put('Z', new Point(2,4));
		textMap.put('!', new Point(3,4)); textMap.put('@', new Point(4,4));
		textMap.put('#', new Point(5,4)); textMap.put('$', new Point(6,4));
		textMap.put('%', new Point(7,4)); textMap.put('&', new Point(8,4));
		textMap.put('a', new Point(1,5)); textMap.put('b', new Point(2,5));
		textMap.put('c', new Point(3,5)); textMap.put('d', new Point(4,5));
		textMap.put('e', new Point(5,5)); textMap.put('f', new Point(6,5));
		textMap.put('g', new Point(7,5)); textMap.put('h', new Point(8,5));
		textMap.put('i', new Point(1,6)); textMap.put('j', new Point(2,6));
		textMap.put('k', new Point(3,6)); textMap.put('l', new Point(4,6));
		textMap.put('m', new Point(5,6)); textMap.put('n', new Point(6,6));
		textMap.put('o', new Point(7,6)); textMap.put('p', new Point(8,6));
		textMap.put('q', new Point(1,7)); textMap.put('r', new Point(2,7));
		textMap.put('s', new Point(3,7)); textMap.put('t', new Point(4,7));
		textMap.put('u', new Point(5,7)); textMap.put('v', new Point(6,7));
		textMap.put('w', new Point(7,7)); textMap.put('x', new Point(8,7));
		textMap.put('y', new Point(1,8)); textMap.put('z', new Point(2,8));
		textMap.put('(', new Point(3,8)); textMap.put(')', new Point(4,8));
		textMap.put(',', new Point(5,8)); textMap.put('.', new Point(6,8));
		textMap.put('?', new Point(7,8)); textMap.put(' ', new Point(8,8));
		textMap.put('0', new Point(1,9)); textMap.put('1', new Point(2,9));
		textMap.put('2', new Point(3,9)); textMap.put('3', new Point(4,9));
		textMap.put('4', new Point(5,9)); textMap.put('5', new Point(6,9));
		textMap.put('6', new Point(7,9)); textMap.put('7', new Point(8,9));
		textMap.put('8', new Point(1,10)); textMap.put('9', new Point(2,10));
		textMap.put('+', new Point(3,10)); textMap.put('-', new Point(4,10));
		textMap.put('*', new Point(5,10)); textMap.put('/', new Point(6,10));
		textMap.put('=', new Point(7,10)); textMap.put('"', new Point(8,10));	
		textMap.put('\'', new Point(1,11)); textMap.put(':', new Point(2,11));
		textMap.put(';', new Point(3,11)); textMap.put('|', new Point(4,11));
		textMap.put('[', new Point(5,11)); textMap.put(']', new Point(6,11));
		textMap.put('{', new Point(7,11)); textMap.put('}', new Point(8,11));
	}

	public static BufferedImage colorizeString(BufferedImage img, Color c){
		BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		Graphics g = temp.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		for(int y = 0; y < temp.getHeight(); y++){
			for(int x = 0; x < temp.getWidth(); x++){
				Color originalColor = new Color(temp.getRGB(x, y), true);
				if(originalColor.getAlpha() == 255) {
                    temp.setRGB(x, y, c.getRGB());
                    }
                }
			}
		return temp;
	}
	
	/**
	 * Method to draw a sprite based text string to the screen.
	 * @param g	Graphics
	 * @param x	X coordinate of the text
	 * @param y	Y coordinate of the text
	 * @param text	The string of text to draw.
	 */
	public static void drawString(Graphics g, int x, int y, String text){
		for(int i = 0; i < text.length(); i++){
			g.drawImage(textSheet.grabImage((int)textMap.get(text.charAt(i)).getX(), (int)textMap.get(text.charAt(i)).getY(), charWidth, charWidth), x, y, null);
			x+=charWidth;
		}
	}
	
	public static void drawString(Graphics g, int x, int y, String text, Color c){
		for(int i = 0; i < text.length(); i++){
			g.drawImage(colorizeString(textSheet.grabImage((int)textMap.get(text.charAt(i)).getX(), (int)textMap.get(text.charAt(i)).getY(), charWidth, charWidth),c), x, y, null);
			x+=charWidth;
		}
	}
	
	public static void drawStringMultiLine(Graphics g, int x, int y, int width, String text){
		if(text.length() * charWidth < width){
			drawString(g,x,y,text);
		}
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(currentLine.length() * charWidth + words[i].length() * charWidth < width){
					currentLine += " " + words[i];
				}
				else{
					drawString(g, x, y, currentLine);
					y += charHeight;
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0){
				drawString(g,x,y,currentLine);
			}
		}
	}

	public static void drawStringMultiLine(Graphics g, int x, int y, int width, String text, Color c){
		if(text.length() * charWidth < width){
			drawString(g,x,y,text, c);
		}
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(currentLine.length() * charWidth + words[i].length() * charWidth < width){
					currentLine += " " + words[i];
				}
				else{
					drawString(g, x, y, currentLine);
					y += charHeight;
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0){
				drawString(g,x,y,currentLine, c);
			}
		}
	}
	
	/**
	 * Automagically splits a string over multiple lines.
	 * <br><b>Note:</b> <i>The string must contain spaces to be split!</i><br>
	 * @param g		Graphics
	 * @param f		The font being used for the string.
	 * @param x		String X coordinate
	 * @param y		String Y coordinate
	 * @param lineWidth		How wide the string can be before splitting.
	 * @param text	The actual string.
	 */
	public static void drawStringMultiLine(Graphics g, Font f, int x, int y, int lineWidth, String text){
		FontMetrics m = g.getFontMetrics(f);
		if(m.stringWidth(text) < lineWidth){
			g.drawString(text, x, y);
		}
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(m.stringWidth(currentLine + words[i]) < lineWidth){
					currentLine += " " + words[i];
				}
				else{
					g.drawString(currentLine, x, y);
					y += m.getHeight();
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0){
				g.drawString(currentLine, x, y);
			}
		}
	}
	
	public static void drawMenuTextBox(Graphics g, int x, int y, int width, int height){
		g.setColor(Color.darkGray);
		g.drawRoundRect(x-8, y-8, width + 8, height + 8, 16, 16);
	}
	
	public static void drawMenuTextBox(Graphics g, Font f, int x, int y, int width, int height, String text){
		g.setColor(Color.darkGray);
		g.drawRoundRect(x-8, y-8, width + 8, height + 8, 16, 16);
		g.setColor(Color.white);
		drawStringMultiLine(g, f, x + 8, y + 8, width - 8, text);
	}
}
