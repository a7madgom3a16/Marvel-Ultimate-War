package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.Player;
import model.world.Champion;

@SuppressWarnings("serial")
public class page4 extends JFrame implements ActionListener{
	private JPanel l = new JPanel();
	private Player p1;
	private Player p2;
	public page4 (Player s1,Player s2) {
	p1 = s1;
	p2 = s2;
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.add(l);
	setVisible(true);
	setSize(1366,768);
		for(int i=0;i<3;i++) {
			String name = p1.getTeam().get(i).getName();
			JButton c = new JButton(name);
			l.add(c);
			c.addActionListener(this);
		}
	setTitle(p1.getName()+" Choose Your Leader");
	}
	
	public void actionPerformed(ActionEvent e){
		Champion c = null;
		String n = ((JButton)e.getSource()).getText();
		for(int i=0;i<3;i++)
			if(n.equals(p1.getTeam().get(i).getName()))
				 c = p1.getTeam().get(i);
		p1.setLeader(c);
		((JButton)e.getSource()).setEnabled(false);
		dispose();
		try {
			new page5(p1,p2);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
