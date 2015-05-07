package astroBiz.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageUtilities {
	public static BufferedImage colorizeImage(BufferedImage image, Color from, Color to){
		BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		Graphics g = temp.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		for(int y = 0; y < temp.getHeight(); y++){
			for(int x = 0; x < temp.getWidth(); x++){
				if(temp.getRGB(x, y) == from.getRGB()){
					temp.setRGB(x, y, to.getRGB());
					}
                }
			}
		return temp;
	}
}
