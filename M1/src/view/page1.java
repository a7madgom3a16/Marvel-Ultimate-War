package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class page1 extends JFrame implements ActionListener {
	JButton s ;
	JButton exit;
	public page1() {
		setSize(1366,768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Marvel Game");
		setVisible(true);
		JLabel l = new JLabel();
        ImageIcon icon = new ImageIcon("Start.jpg"); 
        l.setIcon(icon);
        l.setBounds(0,0,1350,730);
		JLabel p1 = new JLabel();
		p1.setVerticalAlignment(JLabel.CENTER);
		p1.setHorizontalAlignment(JLabel.CENTER);
		s = new JButton("Start");
		s.setBounds(600, 525, 150, 50);
		s.addActionListener(this);
		s.setBackground(Color.black);
		s.setOpaque(true);
		s.setBorderPainted(true);
		s.setForeground(Color.white);
		exit = new JButton("Exit");
		exit.setBounds(600, 600, 150, 50);
		exit.addActionListener(this);
		exit.setBackground(Color.black);
		exit.setOpaque(true);
		exit.setBorderPainted(true);
		exit.setForeground(Color.white);
		add(s);
		add(exit);
		add(p1);
		add(l);
		repaint();
		revalidate();
	}
	public static void main(String[]args) throws IOException {
		new page1();
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == s) {
			this.dispose();
			new page2();
		}else if(e.getSource() == exit)
			System.exit(0);
	}
}
