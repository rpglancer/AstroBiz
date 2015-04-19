package astroBiz;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class AstroBiz extends Canvas implements Runnable{
	private static final long serialVersionUID = 1477754103243231171L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public final String TITLE = "AstroBiz";

	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;

	private BufferedImage player;		// temp
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("../data/astrobizbuttons.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//temp
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		player = ss.grabImage(1, 1, 96, 64);
		
	}
	
	private synchronized void start(){
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " Ticks, FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		// Everything updated in the game world is updated on a tick?
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		// Stuff to render goes here
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		g.drawImage(player, 100, 100, this);		// temp

		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		default:
			break;
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		default:
			break;
		}
		
	}
	
	public static void main(String[] args){
		AstroBiz astrobiz = new AstroBiz();
		
		astrobiz.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		astrobiz.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		astrobiz.setMinimumSize(new Dimension(HEIGHT, WIDTH));
		
		JFrame frame = new JFrame(astrobiz.TITLE);
		frame.add(astrobiz);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		astrobiz.start();
		
	}
	
}
