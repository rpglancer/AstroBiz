package astroBiz.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import astroBiz.AstroBiz;
import astroBiz.info.HALIGN;
import astroBiz.info.VALIGN;
import astroBiz.lib.SpriteSheet;

/**
 * All non-standard methods for doing things to Strings go in this class.
 * @author Matt Bangert
 *
 */
public class textUtilities{
	public static Map<Character, Point> textMap = new HashMap<Character, Point>();
	public static SpriteSheet textSheet = AstroBiz.textSheet;

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
	 * Method to add a character to the end of the specified String.
	 * @param string (String) The String to which the character will be added.
	 * @param c	(char) The character to add.
	 * @return (String) A new String comprised of the original String and the specified character.
	 */
	public static String addEndChar(String string, char c){
		string += c;
		return string;
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
	 * Method to draw a String to a desired Horizontal alignment. This method is probably redundant
	 * and could be combined with {@link #drawStringToBox(Graphics, Font, Rectangle, HALIGN, VALIGN, String)} but
	 * it has yet to be depreciated. Nevertheless, do not rely too heavily on this method.
	 * @param g		Graphics to be drawn to
	 * @param f		The font to be used
	 * @param x		The X coordinate of the text
	 * @param y		The Y coordinate of the text
	 * @param w		The width of the text
	 * @param h		The height of the text
	 * @param ha	The horizontal alignment of the text
	 * @param text	The actual text
	 */
	private static void drawStringAligned(Graphics g, Font f, int x, int y, int w, int h, HALIGN ha, String text){
		FontMetrics fm = g.getFontMetrics(f);
		int strlen = fm.stringWidth(text);
		if(ha == HALIGN.CENTER) x = (x + w) - (w / 2) - (strlen / 2);
		if(ha == HALIGN.RIGHT) x = x + w - strlen;
		y+= fm.getAscent() - fm.getDescent();
		Font tempf = g.getFont();
		g.setFont(f);
		g.drawString(text, x, y);
		g.setFont(tempf);
	}
	
	public static void drawStringCenterV(Graphics g, Font f, int x, int y, int height, String text){
		Font oldfont = g.getFont();
		FontMetrics m = g.getFontMetrics(f);
		y += (height - getTextHeight(g,f,text)) / 2 + m.getAscent();
		g.setFont(f);
		g.drawString(text, x, y);
		g.setFont(oldfont);
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
		Font oldfont = g.getFont();
		g.setFont(f);
		FontMetrics m = g.getFontMetrics(f);
		y += m.getAscent();
		if(m.stringWidth(text) < lineWidth){
			g.drawString(text, x, y);
		}
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(m.stringWidth(currentLine + " " + words[i]) < lineWidth){
					currentLine += " " + words[i];
				}
				else{
					g.drawString(currentLine, x, y);
					y += m.getAscent();
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0){
				g.drawString(currentLine, x, y);
			}
		}
		g.setFont(oldfont);
	}

	/**
	 * Automagically splits a string over multiple lines and adds padding [spacing] around the string.
	 * <br><b>Note:</b> <i>The string must contain spaces to be split!</i><br>
	 * @param g		Graphics
	 * @param f		The font being used for the string.
	 * @param x		String X coordinate
	 * @param y		String Y coordinate
	 * @param lineWidth		How wide the string can be before splitting.
	 * @param padding		The amount, in pixels, of padding to be used around the String.
	 * @param text			The actual string.
	 */
	public static void drawStringMultiLine(Graphics g, Font f, int x, int y, int lineWidth, int padding, String text){
		Font oldfont = g.getFont();
		FontMetrics m = g.getFontMetrics(f);
		g.setFont(f);
		y+=m.getAscent();
		x+=padding;
		lineWidth-=padding;
		if(m.stringWidth(text) < lineWidth){
			g.drawString(text, x, y);
		}
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(m.stringWidth(currentLine + " " + words[i]) < lineWidth){
					currentLine += " " + words[i];
				}
				else{
					g.drawString(currentLine, x, y);
					y += m.getAscent();
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0){
				g.drawString(currentLine, x, y);
			}
		}
		g.setFont(oldfont);
	}	
	
	/**
	 * Method to draw a string to the inside of a Rectangle.
	 * @param g		Graphics to be drawn to
	 * @param f		Font to be used
	 * @param box	Rectangle area to draw inside
	 * @param ha	Horizontal alignment of the text
	 * @param va	Vertical alignment of the text
	 * @param text	Duh, the actual text.
	 */
	public static void drawStringToBox(Graphics g, Font f, Rectangle box, HALIGN ha, VALIGN va, String text){
		int lc = getLineCount(g, f, box.x + box.width - box.x, text);
		FontMetrics fm = g.getFontMetrics(f);
		int y = box.y;
		int w = box.width;
		if(va == VALIGN.TOP) y = box.y;
		if(va == VALIGN.MIDDLE){
			if(lc == 1) y = (box.y + box.height / 2) - (getTextHeight(g,f,text) / 2 - fm.getDescent());
			else y = box.y + box.height/2 - (getTextHeight(g,f,text)/2) * lc + (fm.getDescent()/2 * lc);
		}
		if(va == VALIGN.BOTTOM) y = box.y + box.height - getTextHeight(g,f,text) * lc + (fm.getDescent() * lc);
		String[] words = text.split(" ");
		String currentLine = words[0];
		for(int i = 1; i < words.length; i++){
			if(fm.stringWidth(currentLine + " " + words[i]) < box.x + box.width - box.x){
				currentLine += " " + words[i];
			}
			else{
				drawStringAligned(g, f, (int)box.getX(), y, w, fm.getHeight(), ha, currentLine);
				y += fm.getAscent();
				currentLine = words[i];
			}
		}
		if(currentLine.trim().length() > 0){
			drawStringAligned(g,f, (int)box.getX(), y, w, fm.getHeight(), ha, currentLine);
		}
	}
	
	/**
	 * Method to determine how many lines a string will occupy given a specified width before triggering a new line.
	 * @param g
	 * @param f				Font being used by the text
	 * @param lineWidth		Width of a line before triggering a new line
	 * @param text			The text
	 * @return				The total number of lines the text will occupy
	 */
	public static int getLineCount(Graphics g, Font f, int lineWidth, String text){
		int count = 1;
		FontMetrics m = g.getFontMetrics(f);
		if(m.stringWidth(text) < lineWidth) return count;
		else{
			String[] words = text.split(" ");
			String currentLine = words[0];
			for(int i = 1; i < words.length; i++){
				if(m.stringWidth(currentLine + " " + words[i]) < lineWidth){
					currentLine += " " + words[i];
				}
				else{
					count++;
					currentLine = words[i];
				}
			}
			if(currentLine.trim().length() > 0){
				
			}
		}
		return count;
	}
	
	/**
	 * Method to determine precisely the height of a given String.
	 * @param g		
	 * @param f		Font being used by the text
	 * @param text	The text
	 * @return		The height value of a String's bounds.
	 */
	public static int getTextHeight(Graphics g, Font f, String text){
		Graphics temp = g.create(0, 0, 800, 480);
		FontMetrics m = g.getFontMetrics(f);
		temp.clipRect(0, 0, m.stringWidth(text), m.getHeight());
		Rectangle2D bounds = m.getStringBounds(text, temp);
		int theight = (int)bounds.getHeight();
		temp.dispose();
		return theight;
	}
	
	public static int getTextWidth(Graphics g, Font f, String text){
		Graphics temp = g.create(0, 0, 800, 480);
		FontMetrics m = g.getFontMetrics(f);
		temp.clipRect(0, 0, m.stringWidth(text), m.getHeight());
		Rectangle2D bounds = m.getStringBounds(text, temp);
		int twidth = (int)bounds.getWidth();
		temp.dispose();
		return twidth;
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