package astroBiz.lib;

import java.awt.Graphics;
import java.util.LinkedList;

import astroBiz.info.ENTITY_TYPE;

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
			if(e.get(i).isActive()){
				ent = e.get(i);
				ent.tick();
			}
		}
//		for(int i = 0; i < e.size(); i++){
//			ent = e.get(i);
//			ent.tick();
//		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < e.size(); i++){
			ent = e.get(i);
			if(ent.isActive()){
				ent.render(g);
			}
		}
	}
	public void addEntity(Entity block){
		if(block.getType() == ENTITY_TYPE.VIEW_MANAGER){
			e.push(block);
		}
		else if(block.getType() == ENTITY_TYPE.TEXT_WINDOW){
			int i = this.lastIndexOf(ENTITY_TYPE.VIEW_MANAGER);
			if(i == -1)e.push(block);
			else e.add(i+1, block);
		}
		else if(block.getType() == ENTITY_TYPE.CONFIRMATION){
			e.addLast(block);
		}
	}
	
	private int lastIndexOf(ENTITY_TYPE type){
		int index = -1;
		for(int i = 0; i < e.size(); i++){
			if(e.get(i).getType() == type) index = i;
		}
		return index;
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

	public void purge(ENTITY_TYPE type){
		for(int i = 0; i < e.size(); i++){
			if(e.get(i).getType() == type){
				ent = e.get(i);
				removeEntity(ent);
			}
		}
	}
	
	public void removeEntity(Entity block){
		e.remove(block);
	}
}
