package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import engine.Game;
import exceptions.AbilityUseException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughResourcesException;
import model.abilities.Ability;
import model.world.Champion;
import model.world.Cover;

@SuppressWarnings("serial")
public class page8 extends JFrame implements ActionListener{
	private Game s;
	private Ability t;
	private JPanel l = new JPanel();
	private JButton[][] q = new JButton[5][5];
	public page8(Game g,Ability a) {
		s=g;
		t=a;
		setTitle("Choose Target");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1366,768);
		setVisible(true);
		l.setBounds(0,0,1352,732);
		l.setVisible(true);
		add(l);
		JButton[][] lList = new JButton[5][5];
		l.setLayout(new GridLayout(5,5));
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				JButton c = new JButton();
				c.addActionListener(this);
				l.add(c);
				lList[i][j] = c;
				q[i][j] = lList[i][j];
			}
		}
		for(int i=4;i>-1;i--) {
    		for(int j=0;j<5;j++) {
    			if ((s.getBoard())[i][j] != null) {
    				if ((s.getBoard())[i][j] instanceof Cover) {
    					lList[i][j].setText("Cover");
    				}
    				else if ((s.getBoard())[i][j] instanceof Champion) {
    					lList[i][j].setText(((Champion)(s.getBoard())[i][j]).getName());
    				}
    			}
    		}
    	}
	}

	public void actionPerformed(ActionEvent e) {
		int i=0;
		int j=0;
		for(i=0;i<5;i++)
			for(j=0;j<5;j++)
				if (q[i][j].equals(e.getSource()))
					break;
		try {
			s.castAbility(t,i-1,j-1);
		} catch (AbilityUseException | InvalidTargetException | NotEnoughResourcesException | CloneNotSupportedException e1) {
			JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
		}
		dispose();
		new page7(s);
	}
}
