package astroBiz.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * All non-standard methods for doing things to Strings go in this class.
 * @author Matt Bangert
 *
 */
public class textUtilities {

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

}
