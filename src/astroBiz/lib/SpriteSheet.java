package astroBiz.lib;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}

	/**
	 * Method for using a sprite located in a SpriteSheet.
	 * @param col	The column at which the desired sprite resides.
	 * @param row	The row at which the desired sprite resides.
	 * @param width	The width of sprites in the sprite sheet.
	 * @param height	The height of sprites in the sprite sheet.
	 * @return	The selected sprite.
	 */
	public BufferedImage grabImage(int col, int row, int width, int height){
		if(col < 1 || row < 1){
			System.out.println("ERR: grabImage column or row is 0 value. Null returned.");
			return null;
		}
//		BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
//		return img;
		return image.getSubimage(col * width - width, row * height - height, width, height);
	}
}
