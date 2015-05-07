package astroBiz.lib;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * Linked List Controller
 * <br>
 * <br>Easily render all Entities without having to loop
 * <br>through individual lists with a tangle of for loops.
 * @author Matt Bangert
 *
 */
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

	public boolean containsEntity(Entity block){
		for(int i = 0; i < e.size(); i++){
			ent = e.get(i);
			if(block == ent) return true;
		}
		return false;
	}

	public void purge(){
		for(int i = 0; i < e.size(); i++){
			ent = e.get(i);
			removeEntity(ent);
		}
	}
	
	public void removeEntity(Entity block){
		e.remove(block);
	}
}
