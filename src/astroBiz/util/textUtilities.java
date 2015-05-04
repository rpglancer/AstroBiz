package astroBiz.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import astroBiz.AstroBiz;
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
	public static SpriteSheet textSheet = AstroBiz.textSheet;

	/**
	 * Default constructor?
	 */
	public textUtilities(){
	}

	/**
	 * Initialize textUtilities.<br>
	 * This absolutely <b>must</b> be done if you have any<br>
	 * intention of using textMap, which is basically required<br>
	 * for using any of the sprite based text functions contained<br>
	 * in textUtilities.
	 */
	public static void init(){
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

	/**
	 * Create a single-line text box
	 * @param g	(Graphics) Graphics to draw it with
	 * @param x	(int) Starting X coordinate of the box
	 * @param y	(int) Starting Y coordinate of the box
	 * @param borderwidth (int) The width of the box's border.
	 * @param bordercolor	(Color) The color of the box's border.
	 * @param framecolor	(Color)	The color of the interior of the box.
	 * @param text	(String) The text contained in the box.
	 */
	public static void boxText(Graphics g, int x, int y, int borderwidth, Color bordercolor, Color framecolor, String text){
		int boxwidth = text.length() * 16;
		int bcsx = x - borderwidth;
		int bcsy = y - borderwidth;
		int bcwidth = boxwidth + borderwidth * 2;
		int bcheight = charHeight + borderwidth * 2;
		g.setColor(bordercolor);
		g.fillRoundRect(bcsx, bcsy, bcwidth, bcheight, borderwidth, borderwidth);
		g.setColor(framecolor);
		g.fillRoundRect(x, y, boxwidth, charHeight, borderwidth/2, borderwidth/2);
		drawString(g, x, y, text);
	}
	
	/**
	 * Change the color of the default sprite-based text.
	 * @param img	(BufferedImage)	The sprite text character to be changed in color.
	 * @param c	(Color)	The color to which the sprite text character will be changed.
	 * @return	(BufferedImage)	The colorized spriite text character.
	 */
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
	 * @param g	(Graphics) The desired Graphics buffer.
	 * @param x	(int) The X coordinate of the text.
	 * @param y	(int) The Y coordinate of the text.
	 * @param text	(String) The string of text to draw.
	 * <br><i>Note: Sprite based text draws from top-left to bottom-right.
	 * <br>Ergo, the x,y value is the top-left corner of the text.</i>
	 */
	public static void drawString(Graphics g, int x, int y, String text){
		if(text == null) return;
		for(int i = 0; i < text.length(); i++){
			g.drawImage(textSheet.grabImage((int)textMap.get(text.charAt(i)).getX(), (int)textMap.get(text.charAt(i)).getY(), charWidth, charHeight), x, y, null);
			x+=charWidth;
		}
	}

	/**
	 * Method to draw a sprite based string of the specified color.
	 * @param g	(Graphics) The desired Graphics buffer.
	 * @param x	(int) The X coordinate of the text.
	 * @param y (int) The Y coordinate of the text.
	 * @param text (String) The string of text to draw.
	 * @param c	(Color) The desired color of the text.
	 * <br><i>Note: Sprite based text draws from top-left to bottom-right.
	 * <br>Ergo, the x,y value is the top-left corner of the text.</i>
	 */
	public static void drawString(Graphics g, int x, int y, String text, Color c){
		for(int i = 0; i < text.length(); i++){
			g.drawImage(colorizeString(textSheet.grabImage((int)textMap.get(text.charAt(i)).getX(), (int)textMap.get(text.charAt(i)).getY(), charWidth, charWidth),c), x, y, null);
			x+=charWidth;
		}
	}

	/**
	 * Method to draw sprite based text across multiple lines.
	 * @param g	(Graphics) The desired Graphics buffer.
	 * @param x	(int) The X coordinate of the text.
	 * @param y	(int) The Y coordinate of the text.
	 * @param width (int) The width [in pixels] of the String before it goes to a new line.
	 * @param text (String) The string of text to draw.
	 * <br><i>Note: Sprite based text draws from top-left to bottom-right.
	 * <br>Ergo, the x,y value is the top-left corner of the text.</i>
	 */
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

	/**
	 * Method to draw sprite based text of a specified color across multiple lines.
	 * @param g (Graphics) The desired Graphics buffer.
	 * @param x	(int) The X coordinate of the text.
	 * @param y	(int) The Y coordinate of the text.
	 * @param width	(int) The width [in pixels] of the String before it goes to a new line.
	 * @param text	(String) The string of text to draw.
	 * @param c	(Color) The desired color of the text.
	 * <br><i>Note: Sprite based text draws from top-left to bottom-right.
	 * <br>Ergo, the x,y value is the top-left corner of the text.</i>
	 */
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
	
	/**Method to draw a box around text.
	 * @deprecated
	 * <br>Use {@link #boxText(Graphics, int, int, int, Color, Color, String)}
	 * @param g (Graphics) The graphics buffer.
	 * @param x (int) The X coordinate of the box.
	 * @param y (int) The Y coordinate of the box.
	 * @param width (int) The width of the box.
	 * @param height (int) The height of the box.
	 */
	@Deprecated
	public static void drawMenuTextBox(Graphics g, int x, int y, int width, int height){
		g.setColor(Color.darkGray);
		g.drawRoundRect(x-8, y-8, width + 8, height + 8, 16, 16);
	}
	
	/**
	 * Method to draw text within a box.
	 * @deprecated
	 * <br>Use {@link #boxText(Graphics, int, int, int, Color, Color, String)}
	 * @param g (Graphics) The graphics buffer.
	 * @param f (Font) The desired Font.
	 * @param x (int) The X coordinate of the box.
	 * @param y (int) The Y coordinate of the box.
	 * @param width (int) The width of the box.
	 * @param height (int) The height of the box.
	 * @param text (String) The text to be drawn inside the box.
	 */
	@Deprecated
	public static void drawMenuTextBox(Graphics g, Font f, int x, int y, int width, int height, String text){
		g.setColor(Color.darkGray);
		g.drawRoundRect(x-8, y-8, width + 8, height + 8, 16, 16);
		g.setColor(Color.white);
		drawStringMultiLine(g, f, x + 8, y + 8, width - 8, text);
	}
	
	/**
	 * Method to determine the number of lines that text will occupy<br>
	 * if wrapped to a given width.
	 * @param text	(String) The text to analyze.
	 * @param width	(int) The width [in pixels] of the text before triggering a  new line.
	 * @return	(int) The number of lines calculated.
	 */
	public static int getLineCount(String text, int width){
		int lineCount = 1;
		if(text.length() * charWidth < width) return lineCount;
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(currentLine.length() * charWidth + words[i].length() * charWidth < width){
					currentLine += " " + words[i];
				}
				else{
					lineCount++;
					currentLine = words[i];
				}
			}
			return lineCount;
		}
	}

	/**
	 * Method to add a character to the end of the specified String.
	 * @param string (String) The String to which the character will be added.
	 * @param c	(char) The character to add.
	 * @return (String) A new String comprised of the original String and the specified character.
	 */
	public static String addEndChar(String string, char c){
		char[] charArray = new char[string.length() + 1];
		for(int i = 0; i < string.length(); i++){
			charArray[i] = string.charAt(i);
		}
		charArray[string.length()] = c;
		return new String(charArray);
	}
	
	/**
	 * Method to remove the final character of a String.
	 * @param string (String) The string from which the final character will be removed.
	 * @return (String) The modified String.
	 */
	public static String deleteEndChar(String string){
		char[] charArray = new char[string.length() - 1];
		for(int i = 0; i < string.length() - 1; i++){
			charArray[i] = string.charAt(i);
		}
		return new String(charArray);
	}
	
	/**
	 * Method to replace the character at a specific location within a String.
	 * @param position	(int) The position of the character to be replaced.
	 * @param string	(String) The string in which the character will be replaced.
	 * @param c	(char) The replacing character.
	 * @return	(String) The updated String.
	 */
	public static String replaceCharAt(int position, String string, char c){
	    char[] charArray = string.toCharArray();
	    charArray[position] = c;
	    return new String(charArray);
	}
}
