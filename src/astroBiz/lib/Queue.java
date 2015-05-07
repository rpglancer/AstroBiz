package astroBiz.lib;

import java.util.LinkedList;

public class Queue {
	private LinkedList<Message> queue = new LinkedList<Message>();
	
	public void addMsg(Message msg){
		queue.add(msg);
	}
	
	public int getQueueSize(){
		return queue.size();
	}
	
	public Message getMessage(int at){
		return queue.get(at);
	}
	
	public void removeMsg(Message msg){
		queue.remove(msg);
	}
	
}
