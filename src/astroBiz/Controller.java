package astroBiz;

import java.awt.Graphics;
import java.util.LinkedList;

public class Controller{
	
	private LinkedList<Entity> e = new LinkedList<Entity>();

	Entity ent;
	
	public void tick() {
		for(int i = 0; i < e.size(); i++){
			ent = e.get(i);
			ent.tick();
		}
	}
	public void render(Graphics g) {
		for(int i = 0; i < e.size(); i++){
			ent = e.get(i);
			ent.render(g);
		}
		
	}
	public void addEntity(Entity block){
		e.add(block);
	}
	public void removeEntity(Entity block){
		e.remove(block);
	}
}
