package astroBiz;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

import astroBiz.util.BufferedImageLoader;
import astroBiz.util.textUtilities;
import astroBiz.view.LocationView;
import astroBiz.view.MainMenu;
import astroBiz.view.MainMenu.MENUSELECT;
import astroBiz.view.RegionView;
import astroBiz.view.ScenarioView;

public class AstroBiz extends Canvas implements Runnable{

	/*
	 * Constants
	 */
	private static final long serialVersionUID = 1477754103243231171L;	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final int VERSION = 0;
	public static final int MAJOR = 0;
	public static final int MINOR = 2;
	public static final int PATCH = 0;
	public final String TITLE = "AstroBiz Prototype version " + VERSION + "." + MAJOR + "." + MINOR + "." + PATCH;

	/*
	 * Buffered Images
	 */
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	/*
	 * Sprite Sheets
	 */
	public static SpriteSheet employeeSprites = null;
	public static SpriteSheet regionButtons = null;
	public static SpriteSheet regionSprites = null;
	public static SpriteSheet textSheet = null;
	public static SpriteSheet worldMap = null;

	/*
	 * Views
	 */
	private LocationView locationView = null;
	private MainMenu mainMenu = null;
	private RegionView regionView = null;
	private ScenarioView scenarioView = null;
	
	public static enum STATE{
		MENU,
		REGIONVIEW,
		SCENARIOSETUP,
		LOCATIONVIEW
	};

	/*
	 * Game State information
	 */
	private Scenario activeScenario;
	
	/*
	 * Operational Variables
	 */
	public static STATE State = STATE.MENU;
	private boolean running = false;
	private Thread thread;
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			BufferedImage temp = loader.loadImage("../../data/astrobizEmployeeSprites.png");
			employeeSprites = new SpriteSheet(temp);
			temp = loader.loadImage("../../data/astrobizbuttons.png");
			regionButtons = new SpriteSheet(temp);
			temp = loader.loadImage("../../data/astrobizworldicons.png");
			regionSprites = new SpriteSheet(temp);
			temp = loader.loadImage("../../data/astrobizmap.png");
			worldMap = new SpriteSheet(temp);
			temp = loader.loadImage("../../data/astrobiztext.png");
			textSheet = new SpriteSheet(temp);
			textUtilities.init();
		}catch(IOException e){
			e.printStackTrace();
		}
		addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput(this));
		this.addMouseMotionListener(new MouseInput(this));
		mainMenu = new MainMenu();
		locationView = new LocationView(null);
		regionView = new RegionView(this);
		scenarioView = new ScenarioView(this);
		activeScenario = new Scenario();
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
//		int updates = 0;		// Tick counter
//		int frames = 0;			// FPS counter
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				render();		// Tick Limited FPS
//				frames++;		// Tick Limited FPS
//				updates++;
				delta--;
			}
//			render();			// Processor Limited FPS
//			frames++;			// Processor Limited FPS
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
//				updates = 0;
//				frames = 0;
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
		case REGIONVIEW:
			if(scenarioView != null) scenarioView = null;
			regionView.tick();
			break;
		case SCENARIOSETUP:
			scenarioView.tick();
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
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		switch(State){
		case LOCATIONVIEW:
			locationView.render(g, this);
			break;
		case MENU:
			mainMenu.render(g);
			break;
		case REGIONVIEW:
			regionView.render(g);
			break;
		case SCENARIOSETUP:
			scenarioView.render(g);
			break;
		default:
			break;
		}

		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(AstroBiz.State){

		case SCENARIOSETUP:
			scenarioView.keyAction(e);
			break;
			
		case REGIONVIEW:
			regionView.keyAction(e);

			break;		// End REGIONVIEW
			
		case MENU:
			switch(key){
			case KeyEvent.VK_UP:
				mainMenu.cycleMenuPrev();
				break;
			case KeyEvent.VK_DOWN:
				mainMenu.cycleMenuNext();
				break;
			case KeyEvent.VK_ENTER:
				if(mainMenu.getMenuStatus() == MENUSELECT.NEWGAME)
					AstroBiz.State = AstroBiz.STATE.SCENARIOSETUP;
				if(mainMenu.getMenuStatus() == MENUSELECT.LOADGAME)
					break;
				if(mainMenu.getMenuStatus() == MENUSELECT.QUITGAME)
					System.exit(1);
				System.gc();
				break;
			}
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
		astrobiz.setFocusable(true);
		astrobiz.requestFocus();
		
		JFrame frame = new JFrame(astrobiz.TITLE);
		frame.add(astrobiz);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		astrobiz.start();
	}

	/*
	 * With the SpriteSheets now being public static these
	 * methods are pointless and should be removed once
	 * everything depending upon them has been updated to
	 * access the SpriteSheets in a static manner.
	 */
	public SpriteSheet getEmployeeSprites(){
		return employeeSprites;
	}
	
	public SpriteSheet getRegionSprites(){
		return regionSprites;
	}
	
	public LocationView getLocationView(){
		return this.locationView;
	}
	
	public MainMenu getMainMenu(){
		return this.mainMenu;
	}
	
	public RegionView getRegion(){
		return this.regionView;
	}

	public Scenario getScenario(){
		return this.activeScenario;
	}
	
	public void setScenario(Scenario scenario){
		activeScenario = scenario;
	}
}
