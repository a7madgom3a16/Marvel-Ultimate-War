package view;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import engine.Game;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;

@SuppressWarnings("serial")
public class page7 extends JFrame implements ActionListener {
	private Game s;
	private JPanel board = new JPanel();
	private JPanel action = new JPanel();
	private JButton[][] boardList = new JButton[5][5];
	private ArrayList<JButton> actionList = new ArrayList<JButton>();
	private Object[] directions = {"Left","Up","Down","Right"};
	private JTextArea stats;
	
	public page7(Game g) {
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		s=g;
		setTitle("Board");
		add(board);
		add(action);
		setVisible(true);
		setSize(1366,768);
		board.setBounds(0,0,900,600);
		board.setLayout(new GridLayout(5,5));
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				JButton c = new JButton("");
				board.add(c);
				boardList[i][j] = c;
				c.addActionListener(this);
			}
		}
		fillBoard();
		action.setBounds(0,611,1353,120);
		action.setLayout(new GridLayout(1,0));
		for(int i=0;i<s.getCurrentChampion().getAbilities().size()+4;i++) {
			JButton c = new JButton("");
			action.add(c);
			actionList.add(c);
			c.addActionListener(this);
		}
		fillAction();
		stats = new JTextArea(g.getCurrentChampion().toString());
		stats.setFont(new Font("verdana", Font.BOLD, 20));
		stats.setBackground(Color.white);
		stats.setOpaque(true);
		stats.setBounds(990,0,360,600);
		add(stats);
	}
	
    public void fillBoard() {
    	for(int i=4;i>-1;i--) {
    		for(int j=0;j<5;j++) {
    			if ((s.getBoard())[i][j] != null) {
    				if ((s.getBoard())[i][j] instanceof Cover) {
    					boardList[i][j].setText("Cover");
    				}
    				else if ((s.getBoard())[i][j] instanceof Champion) {
    					boardList[i][j].setText(((Champion)(s.getBoard())[i][j]).getName());
    				}
    			}
    		}
    	}
    }
    
    public void fillAction() {
    	int i=0;
    	for(i=0;i<s.getCurrentChampion().getAbilities().size();i++) {
    		actionList.get(i).setText(s.getCurrentChampion().getAbilities().get(i).getName());
    	}
    	actionList.get(i).setText("Use Leader Ability");
    	actionList.get(i+1).setText("Attack");
    	actionList.get(i+2).setText("Move");
    	actionList.get(i+3).setText("End Turn");
    }
    
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		try {
			s.loadAbilities("Abilities.csv");
		} catch (IOException e3) {}
		try {
			s.loadChampions("Champions.csv");
		} catch (IOException e3) {}
		if(((JButton)e.getSource()).getText().equals("Use Leader Ability")) {
			try {
				s.useLeaderAbility();
				dispose();
				new page7(s);
			} catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException e1) {
				JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(((JButton)e.getSource()).getText().equals("Attack")) {
			ImageIcon icon = new ImageIcon("Directions.jpg");
			int x = JOptionPane.showOptionDialog(null,"Choose attack direction","Target",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,icon,directions,0);
			switch(x) {
			case 0:try {
					s.attack(Direction.LEFT);
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			case 1:try {
					s.attack(Direction.UP);
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			case 2:try {
					s.attack(Direction.DOWN);
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			case 3:try {
					s.attack(Direction.RIGHT);
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			}
			dispose();
			new page7(s);
		}
		else if(((JButton)e.getSource()).getText().equals("Move")) {
			ImageIcon icon = new ImageIcon("Directions.jpg");
			int x = JOptionPane.showOptionDialog(null,"Choose direction","Target",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,icon,directions,0);
			switch(x) {
			case 0:try {
					s.move(Direction.LEFT);
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			case 1:try {
					s.move(Direction.UP);
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			case 2:try {
					s.move(Direction.DOWN);
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			case 3:try {
					s.move(Direction.RIGHT);
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}break;
			}
			dispose();
			new page7(s);
		}
		else if(((JButton)e.getSource()).getText().equals("End Turn")) {
			s.endTurn();
			dispose();
			new page7(s);
		}
		else if(((!(((JButton)e.getSource()).getText()).equals("")) || (!(((JButton)e.getSource()).getText()).equals("Cover"))) && (s.findAbilityByName(((JButton)e.getSource()).getText()) != null)) {
			if(s.findAbilityByName(((JButton)e.getSource()).getText()).getCastArea().equals(AreaOfEffect.DIRECTIONAL)) {
				ImageIcon icon = new ImageIcon("Directions.jpg");
				int x = JOptionPane.showOptionDialog(null,"Choose direction","Target",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,icon,directions,0);
				switch(x) {
				case 0:try {
						s.castAbility(s.findAbilityByName(((JButton)e.getSource()).getText()),Direction.LEFT);
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
					}break;
				case 1:try {
						s.castAbility(s.findAbilityByName(((JButton)e.getSource()).getText()),Direction.UP);
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
					}break;
				case 2:try {
						s.castAbility(s.findAbilityByName(((JButton)e.getSource()).getText()),Direction.DOWN);
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
					}break;
				case 3:try {
						s.castAbility(s.findAbilityByName(((JButton)e.getSource()).getText()),Direction.RIGHT);
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
					}break;
				}
				dispose();
				new page7(s);
			}
			else if(s.findAbilityByName(((JButton)e.getSource()).getText()).getCastArea().equals(AreaOfEffect.SINGLETARGET)) {
				dispose();
				new page8(s,s.findAbilityByName(((JButton)e.getSource()).getText()));
			}
			else {
				try {
					s.castAbility(s.findAbilityByName(((JButton)e.getSource()).getText()));
				} catch (AbilityUseException | NotEnoughResourcesException	| CloneNotSupportedException e1) {
					JOptionPane.showConfirmDialog(null,e1.getMessage(),"Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if(s.checkGameOver() != null) {
			dispose();
			new page9(s);
		}
	}
}
