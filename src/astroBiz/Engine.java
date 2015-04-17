package astroBiz;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Vector;

public class Engine extends Applet implements Runnable, KeyListener{
	
	private Vector<Image> engineImages;
	private Business engineActiveBusiness;
	private Vector<Business> engineBusinesses;
	private int engineNumberOfBusinesses;
	private int engineRunMode;
	private URL engineUrlBase;
	private int engineYear;
	
	public Engine(){
		engineActiveBusiness = null;
		engineBusinesses = null;
		engineNumberOfBusinesses = 0;
		engineYear = 0;
	}
	@Override
	public void init(){
		setSize(800,480);
		setBackground(Color.BLACK);
		setFocusable(true);
		Frame frame = (Frame)this.getParent().getParent();
		frame.setTitle("AstroBiz");
		addKeyListener(this);
		try{
			engineUrlBase = getDocumentBase();
		} catch(Exception e){
			// TODO: handle exception
		}
		engineRunMode = 0;
		engineImages = new Vector<Image>();
		engineImages.addElement(getImage(engineUrlBase,"data/titlescreen.png"));
	}
		@Override
	public void keyPressed(KeyEvent e){
		
	}	
	@Override
	public void keyReleased(KeyEvent e){
		
	}
		@Override
	public void keyTyped(KeyEvent e){
	}
	@Override
	public void run(){
		repaint();
	}	@Override
	public void start(){	
	}
	@Override
	public void paint(Graphics g){
		g.drawImage(engineImages.elementAt(0), 0, 0, engineImages.elementAt(0).getWidth(this), engineImages.elementAt(0).getHeight(this), this);
	}
}