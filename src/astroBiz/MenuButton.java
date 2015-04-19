package astroBiz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuButton extends JButton implements ActionListener{
	ImageIcon iconMap, iconRoute, iconHangar;
	public MenuButton(){
		iconMap = new ImageIcon(this.getClass().getResource("/data/iconMap.png"));
		this.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		setIcon(iconMap);
	}

}
