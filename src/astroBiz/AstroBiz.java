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
import astroBiz.view.ScenarioView.HQPLACEMENTVIEW;
import astroBiz.view.ScenarioView.SCENARIOVIEWMODE;

public class AstroBiz extends Canvas implements Runnable{

	/*
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

	/*
	 * Buffered Images
	 */
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	/*
	 * Sprite Sheets
	 */
	private SpriteSheet employeeSprites = null;
	private SpriteSheet regionButtons = null;
	private SpriteSheet regionSprites = null;
	private SpriteSheet worldMap = null;

	/*
	 * Views
	 */
	private LocationView locationView = null;
	private MainMenu mainMenu = null;
	private RegionView regionView = null;
	private ScenarioView scenarioView = null;
	private textUtilities textUtil = null;
	
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
			textUtil = new textUtilities(new SpriteSheet(temp));
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
				//System.out.println(updates + " Ticks, FPS " + frames);
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
		case REGIONVIEW:
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
			locationView.render(g);
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
			switch(this.scenarioView.getViewMode()){
			case VM_BUSI_CONFIG:
				scenarioView.keyAction(key);
				break;
			case VM_DIFF_SELECT:
				switch(key){
				case KeyEvent.VK_UP:
					scenarioView.cycleDifficultyPrev();
					break;
				case KeyEvent.VK_DOWN:
					scenarioView.cycleDifficultyNext();
					break;
				case KeyEvent.VK_ENTER:
					scenarioView.setDifficulty();
					scenarioView.setViewMode(SCENARIOVIEWMODE.VM_PLYR_SELECT);
					break;
				}
				break;	//	END VM_DIFF_SELECT
				
			case VM_PLYR_SELECT:
				switch(key){
				case KeyEvent.VK_UP:
					scenarioView.cyclePlayerPrev();
					break;
				case KeyEvent.VK_DOWN:
					scenarioView.cyclePlayerNext();
					break;
				case KeyEvent.VK_ENTER:
					//scenarioView.setPlayers();
					scenarioView.setPlayers(activeScenario);
					scenarioView.setViewMode(SCENARIOVIEWMODE.VM_SET_HQ);
					break;
				}
				break;	//	END VM_PLYR_SELECT
				
			case VM_SCEN_CONFIRM:
				switch(key){
				case KeyEvent.VK_RIGHT:
					scenarioView.setYesNo(false);
					break;
				case KeyEvent.VK_LEFT:
					scenarioView.setYesNo(true);
					break;
				case KeyEvent.VK_ENTER:
					if(scenarioView.getYesNo()){
						this.activeScenario = new Scenario();
						this.scenarioView.setScenario();
						scenarioView.setViewMode(SCENARIOVIEWMODE.VM_DIFF_SELECT);
					}
					else{
						scenarioView.setYesNo(true);
						scenarioView.setViewMode(SCENARIOVIEWMODE.VM_SCEN_SELECT);
					}
				}
				break;	//	END_VM_SCEN_CONFIRM
				
			case VM_SCEN_SELECT:
				switch(key){
				case KeyEvent.VK_UP:
					scenarioView.cycleScenarioPrev();
					break;
				case KeyEvent.VK_DOWN:
					scenarioView.cycleScenarioNext();
					break;
				case KeyEvent.VK_ENTER:
					scenarioView.setScenario();
					scenarioView.setViewMode(SCENARIOVIEWMODE.VM_SCEN_CONFIRM);
					break;
				default:
					break;
				}
				break;	//	END VM_SCEN_SELECT
				
			case VM_SET_HQ:
				switch(key){
				case KeyEvent.VK_RIGHT:
					if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.WORLD)
						scenarioView.cycleRegionNext();
					else if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.REGION)
						scenarioView.cycleLocationNext();
					
					break;
					
				case KeyEvent.VK_LEFT:
					if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.WORLD)
						scenarioView.cycleRegionPrev();
					else if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.REGION)
						scenarioView.cycleLocationPrev();
					break;
					
				case KeyEvent.VK_ESCAPE:
					if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.WORLD)
						scenarioView.setViewMode(SCENARIOVIEWMODE.VM_PLYR_SELECT);
					else if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.REGION)
						scenarioView.setHqPlacementView(HQPLACEMENTVIEW.WORLD);
					break;
					
				case KeyEvent.VK_ENTER:
					if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.WORLD){
						scenarioView.loadRegionMap(worldMap);
						scenarioView.loadLocationVector(regionView.getLocationVector());
						scenarioView.setHqPlacementView(HQPLACEMENTVIEW.REGION);
					}
					else if(scenarioView.getHqPlacementView() == HQPLACEMENTVIEW.REGION){
						if(scenarioView.getHqLocationCount() < 0)
							break;
						else{
							this.scenarioView.getHqSelectedLocation().setLocationIsHub(true);
							this.activeScenario.getBusinesses().elementAt(scenarioView.getScenarioPlayerConfigure() - 1).setHQ(this.scenarioView.getHqSelectedLocation());
						}
						if(scenarioView.getScenarioPlayerConfigure() <= scenarioView.getPlayersToConfigure()){
							scenarioView.setScenarioPlayerConfigure(this.scenarioView.getScenarioPlayerConfigure() + 1);
							if(scenarioView.getScenarioPlayerConfigure() > scenarioView.getPlayersToConfigure()){
								scenarioView.setViewMode(SCENARIOVIEWMODE.VM_BUSI_CONFIG);
								//AstroBiz.State = STATE.REGIONVIEW;
								// Move to business customization
							}
							else{
								// Allow further HQ placement.
								scenarioView.setHqPlacementView(HQPLACEMENTVIEW.WORLD);
							}		 
						}
						else{
							// Switch to AI HQ placement
						}
					}
					break;
				}
				break;	//	END VM_SET_HQ
			}
			break;	//	END SCENARIOSETUP
			
		case REGIONVIEW:
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

	public SpriteSheet getEmployeeSprites(){
		return employeeSprites;
	}
	
	public SpriteSheet getRegionButtons(){
		return this.regionButtons;
	}
	
	public SpriteSheet getRegionSprites(){
		return regionSprites;
	}

	public SpriteSheet getWorldMap(){
		return this.worldMap;
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
}
