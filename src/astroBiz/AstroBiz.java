package astroBiz;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

import astroBiz.lib.Controller;
import astroBiz.lib.Scenario;
import astroBiz.lib.SpriteSheet;
import astroBiz.lib.TextWindow;
import astroBiz.util.BufferedImageLoader;
import astroBiz.util.textUtilities;
import astroBiz.view.LocationView;
import astroBiz.view.MainMenu;
import astroBiz.view.RegionView;
import astroBiz.view.ScenarioView;

public class AstroBiz extends Canvas implements Runnable{

	public static enum STATE{
		MENU,
		REGIONVIEW,
		SCENARIOSETUP,
		LOCATIONVIEW
	};	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	
	public static SpriteSheet employeeSprites = null;
	public static SpriteSheet factionFlags = null;
	public static SpriteSheet regionButtons = null;
	public static SpriteSheet regionSprites = null;
	public static SpriteSheet textSheet = null;
	public static SpriteSheet worldMap = null;
	public static STATE State = STATE.MENU;
	public static TextWindow textWin;
	
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private LocationView locationView = null;
	private MainMenu mainMenu = null;
	private Scenario activeScenario;
	private static ScenarioView scenarioView = null;
	private static RegionView regionView = null;
	private Thread thread;

	private static Controller c = new Controller();
	private static final int VERSION = 0;
	private static final int MAJOR = 0;
	private static final int MINOR = 3;
	private static final int PATCH = 0;
	private static final long serialVersionUID = 1477754103243231171L;
	private static final String TITLE = "AstroBiz Prototype version " + VERSION + "." + MAJOR + "." + MINOR + "." + PATCH;
	
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }
	
	public static Controller getController(){
		return AstroBiz.c;
	}
    
	public LocationView getLocationView(){
		return this.locationView;
	}
	
	public MainMenu getMainMenu(){
		return this.mainMenu;
	}
	
	public static RegionView getRegion(){
		return regionView;
	}

	public Scenario getScenario(){
		return this.activeScenario;
	}
    
	public static ScenarioView getSV(){
		return scenarioView;
	}
	
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
			temp = loader.loadImage("../../data/astrobizflags.png");
			factionFlags = new SpriteSheet(temp);
			temp = loader.loadImage("../../data/astrobiztext.png");
			textSheet = new SpriteSheet(temp);
			textUtilities.init();
			textWin = new TextWindow("", employeeSprites.grabImage(1, 1, 128, 128));
			textWin.setActive(false);
			c.addEntity(textWin);
		}catch(IOException e){
			e.printStackTrace();
		}
		addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput(this));
		this.addMouseMotionListener(new MouseInput(this));
		mainMenu = new MainMenu();
		mainMenu.setActive(true);
		activeScenario = new Scenario();
		locationView = new LocationView(null);
		regionView = new RegionView(this, activeScenario);
		scenarioView = new ScenarioView(this);
		c.addEntity(mainMenu);
		c.addEntity(regionView);
		c.addEntity(scenarioView);
	}
	
	public void keyPressed(KeyEvent e){
		c.keyAction(e);
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
		
		JFrame frame = new JFrame(TITLE);
		
		frame.add(astrobiz);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		astrobiz.start();
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 30.0;			//	Keep @ 30 for now, no need to run 60fps, may drop lower.
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				render();		// Tick Limited FPS
				delta--;
			}
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
			}
		}
		stop();
	}
  
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();	
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		c.render(g);
		g.dispose();
		bs.show();
	}
	
	public void setScenario(Scenario scenario){
		activeScenario = scenario;
	}

	public void save(){
    	try{
    		byte data[] = serialize(activeScenario.getLocations());
    		FileOutputStream out = new FileOutputStream("test.sav");
    		out.write(data);
    		out.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
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
		c.tick();
		// Everything updated in the game world is updated on a tick?
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
	
}
