package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.*;

@SuppressWarnings("serial")
public class page2 extends JFrame implements ActionListener{
	private JButton button ;
	private Player p1 ;
	private Player p2;
	private JFrame tr; 
	JTextField text1;
	JTextField text2;
	public page2() {
		tr = new JFrame();
		tr.setVisible(true);
		tr.setSize(1366,768);
		tr.setDefaultCloseOperation(EXIT_ON_CLOSE);
		tr.setTitle("Enter Names");
		JLabel l = new JLabel();
        ImageIcon icon = new ImageIcon("Names.jpg"); 
        l.setIcon(icon);
        add(l);
        l.setBounds(0,0,1350,730);
		JPanel p = new JPanel();
        GridLayout gp = new GridLayout(1,1);
        p.setLayout(gp);
		button = new JButton("Next");
		button.setSize(150,150);
		button.setBounds(50,500,150,50);
		button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFont(new Font("verdana", Font.BOLD, 20));
		text1 = new JTextField(50);
        text2 = new JTextField(50);
        text1.setFont(new Font("verdana", Font.BOLD, 20));
        text2.setFont(new Font("verdana", Font.BOLD, 20));
		JLabel label1 = new JLabel("Enter Player 1 Name:");
		Font myF = new Font("verdana", Font.BOLD, 20);
		label1.setFont(myF);
		label1.setSize(50,50);
		JLabel label2 = new JLabel("Enter Player 2 Name:");
		Font myF2 = new Font("verdana", Font.BOLD, 20);
		label2.setFont(myF2);
		label2.setSize(50,50);
		p.setPreferredSize(new Dimension(tr.getWidth(),100));
		button.addActionListener(this);
		p.add(label1);
		p.add(text1);
		p.add(label2);
		p.add(text2);
		p.setBounds(0,0,1080,1080);
		tr.add(p, BorderLayout.NORTH);
		tr.add(button);
		tr.add(l);
		tr.repaint();
		tr.revalidate();	
	}
	public static void main(String[]args)  {
		new page2();
	}

	public void actionPerformed(ActionEvent e){
		String n1 = text1.getText();
	    String n2 = text2.getText();
		p1 = new Player(n1);
	    p2 = new Player(n2);
		if(e.getSource() == button) {
			if(p1.getName().equals("") || p2.getName().equals("")) {
				int x = JOptionPane.showConfirmDialog(null,"Please Enter 2 Names","Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				if(x==0 || x==-1) {
				dispose();new page2();return;}
			}
			if(p1.getName().equals(p2.getName()) && !(p1.getName() == null || p2.getName() == null)) {
				int y = JOptionPane.showConfirmDialog(null,"Please Enter Different Names","Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
				if(y==0 || y==-1) {
				dispose();new page2();return;}
			}
			tr.dispose();
			try {
				new page3(p1,p2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	public Player getP1() {
		return p1;
	}
	public Player getP2() {
		return p2;
	}
}
