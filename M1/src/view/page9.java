package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import engine.Game;

@SuppressWarnings("serial")
public class page9 extends JFrame {
	public page9(Game g) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1366,768);
		setTitle("Game Over");
		JLabel winner = new JLabel(g.checkGameOver().getName()+" Wins!");
		winner.setHorizontalAlignment(JLabel.CENTER);
		winner.setVerticalAlignment(JLabel.CENTER);
		winner.setFont(new Font("verdana",Font.BOLD,20));
		add(winner);
	}
}
