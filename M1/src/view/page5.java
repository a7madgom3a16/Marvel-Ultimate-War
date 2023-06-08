package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Game;
import engine.Player;
import model.world.Champion;

@SuppressWarnings("serial")
public class page5 extends JFrame implements ActionListener{
	private JPanel l = new JPanel();
	private Player p1;
	private Player p2;
	public page5(Player s1,Player s2) throws IOException {
		p1 = s1;
		p2 = s2;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		l = new JPanel();
		l.setLayout(new GridLayout(0,3));
		for(int i=0;i<Game.getAvailableChampions().size();i++) {
			if(!p1.getTeam().contains(Game.getAvailableChampions().get(i))) {
				String name=Game.getAvailableChampions().get(i).getName();
				JButton c=new JButton(name);
				l.add(c);
				c.addActionListener(this);
			}
		}
		setTitle(p2.getName()+" Choose Your Players");
		add(l);
		setVisible(true);
		setSize(1366,768);
		repaint();
		revalidate();
	}

	public void actionPerformed(ActionEvent e) {
		String n = ((JButton)e.getSource()).getText(); 
		Champion c = null;
		for(int i=0;i<Game.getAvailableChampions().size();i++)
			if(n.equals(Game.getAvailableChampions().get(i).getName()))
				 c = Game.getAvailableChampions().get(i);
		p2.getTeam().add(c);
		((JButton)e.getSource()).setEnabled(false);
		if(p2.getTeam().size()==3) {
			dispose();
			new page6(p1,p2);
		}
	}
}


