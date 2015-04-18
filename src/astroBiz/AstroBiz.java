package astroBiz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AstroBiz extends JPanel{
	
	int y = 0;
	int x = 0;
	
	private void moveObj(){
		x = x + 1;
		y = y + 1;
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("AstroBiz");
		AstroBiz Game = new AstroBiz();
		frame.add(Game);
		frame.setSize(800, 480);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true){
			Game.moveObj();
			Game.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics blit = (Graphics2D) g;
		blit.setColor(Color.RED);
		blit.fillRect(x, y, 10, 10);
	}
}
