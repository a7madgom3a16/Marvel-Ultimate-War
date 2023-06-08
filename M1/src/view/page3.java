package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import engine.Game;
import engine.Player;
import model.world.Champion;

@SuppressWarnings("serial")
public class page3 extends JFrame implements ActionListener{
	private JFrame t;
	private JPanel l;
	private Player p1;
	private Player p2;
	public page3(Player s1,Player s2) throws IOException {
		p1 = s1;
		p2 = s2;
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		l = new JPanel();
		l.setLayout(new GridLayout(0,3));
		for(int i=0;i<Game.getAvailableChampions().size();i++) {
			String name = Game.getAvailableChampions().get(i).getName();
			JButton c = new JButton(name);
			l.add(c);
			c.addActionListener(this);
		}
		t = new JFrame();
		t.setDefaultCloseOperation(EXIT_ON_CLOSE);
		t.setTitle(p1.getName()+" Choose Your Players");
		t.add(l);
		t.setVisible(true);
		t.setSize(1366,768);
		t.repaint();
		t.revalidate();
	}

	public void actionPerformed(ActionEvent e) {
		String n = ((JButton)e.getSource()).getText(); 
		Champion c = null;
		for(int i=0;i<Game.getAvailableChampions().size();i++)
			if(n.equals(Game.getAvailableChampions().get(i).getName()))
				 c = Game.getAvailableChampions().get(i);
		p1.getTeam().add(c);
		((JButton)e.getSource()).setEnabled(false);
		if(p1.getTeam().size()==3) {
			t.dispose();
			new page4(p1,p2);
		}
	}
}
