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

	/**
	 * Constants
	 */
	private static final long serialVersionUID = 1477754103243231171L;	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final int VERSION = 0;
	public static final int MAJOR = 0;
	public static final int MINOR = 1;
	public static final int PATCH = 0;
	public final String TITLE = "AstroBiz Prototype version " + VERSION + "." + MAJOR + "." + MINOR + "." + PATCH;

	/**
	 * Buffered Images
	 */
	//	TODO: Convert to SpriteSheets
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage worldMap = null;
	private BufferedImage regionButtons = null;
	
	/**
	 * Sprite Sheets
	 */
	private SpriteSheet employeeSprites = null;

	/**
	 * Views
	 */
	private LocationView locationView;
	private MainMenu mainMenu;
	private RegionView regionView;
	private ScenarioView scenarioView;
	
	// The enums should probably be moved to their own class
	// and utilized with getters and setters, this is just
	// a bit of a proof of concept.
	public static enum STATE{
		MENU,
		GAME,
		REGIONVIEW,
		SCENARIOCONFIRM,
		SCENARIOVIEW,
		LOCATIONVIEW,
	};

	/**
	 * Game State information
	 */
	private Scenario activeScenario;
	
	/**
	 * Operational Variables
	 */
	public static STATE State = STATE.MENU;
	private boolean running = false;
	private Thread thread;
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			BufferedImage temp = loader.loadImage("../data/astrobizEmployeeSprites.png");
			employeeSprites = new SpriteSheet(temp);
			spriteSheet = loader.loadImage("../data/astrobizworldicons.png");
			worldMap = loader.loadImage("../data/astrobizmap.png");
			regionButtons = loader.loadImage("../data/astrobizbuttons.png");
//			employeeSprites = loader.loadImage("../data/astrobizEmployeeSprites.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		mainMenu = new MainMenu();
		locationView = new LocationView(null);
		regionView = new RegionView(this);
		scenarioView = new ScenarioView(this);
		activeScenario = new Scenario();
		addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput(this));	
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
				render();		// Tick Limited FPS
				frames++;		// Tick Limited FPS
				updates++;
				delta--;
			}
//			render();			// Processor Limited FPS
//			frames++;			// Processor Limited FPS
			
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
		switch(State){
		case MENU:
			break;
		case LOCATIONVIEW:
			break;
		case GAME:
			break;
		case REGIONVIEW:
			regionView.tick();
			break;
		default:
			break;
		}
		// Everything updated in the game world is updated on a tick?
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		switch(State){
		case GAME:			// Game was really a prototyping state and probably will be cut sooner or later. Don't use it.
			break;
		case LOCATIONVIEW:
			locationView.render(g);
			break;
		case MENU:
			mainMenu.render(g);
			break;
		case REGIONVIEW:
			regionView.render(g);
			break;
		case SCENARIOCONFIRM:
			scenarioView.render(g);
			break;
		case SCENARIOVIEW:
			scenarioView.render(g);
			break;
		}

		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:
			regionView.setRegionY(regionView.getRegionY() - 1);
			break;
		case KeyEvent.VK_DOWN:
			regionView.setRegionY(regionView.getRegionY() + 1);
			break;
		case KeyEvent.VK_RIGHT:
			regionView.setRegionX(regionView.getRegionX() + 1);
			break;
		case KeyEvent.VK_LEFT:
			regionView.setRegionX(regionView.getRegionX() - 1);
			break;
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		astrobiz.start();
	}
	
	public BufferedImage getRegionButtons(){
		return regionButtons;
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public SpriteSheet getEmployeeSprites(){
		return employeeSprites;
	}
	
	public BufferedImage getWorldMap(){
		return worldMap;
	}
	
	public LocationView getLocationView(){
		return this.locationView;
	}
	
	public RegionView getRegion(){
		return this.regionView;
	}

	public Scenario getScenario(){
		return this.activeScenario;
	}
}
