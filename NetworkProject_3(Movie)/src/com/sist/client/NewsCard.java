package com.sist.client;
import java.awt.*;
import javax.swing.*;

public class NewsCard extends JPanel{
	public JLabel la;
	public JTextArea ta;
	public NewsCard() {
		la = new JLabel("");
//		la.setForeground(Color.magenta);
		ta = new JTextArea();
		ta.setEditable(false);
		
		setLayout(null);
		la.setBounds(10,15,700,30);
		ta.setBounds(10,50,700,90);
		add(la);
		add(ta);
	}
}
