package com.sist.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DetailForm extends JPanel implements ActionListener{
	public ControllerPanel cp;
	JLabel posterLa=new JLabel();
	JLabel title=new JLabel();
	JLabel director = new JLabel();
	JLabel genre = new JLabel();
	JLabel regdate = new JLabel();
	JLabel grade = new JLabel();
	JLabel score = new JLabel();
	JLabel showuser = new JLabel();
	JLabel time = new JLabel();
	JLabel actor = new JLabel();
	JTextArea story = new JTextArea();
	JLabel video = new JLabel();
	JButton b1,b2;
	
	public DetailForm(ControllerPanel cp) {
		b1=new JButton("목록");
		b2=new JButton("예고편");
		this.cp=cp;
		//setBackground(Color.cyan);
		//배치
		setLayout(null);
		posterLa.setBounds(10, 0,250,450);
		title.setBounds(285, 30, 400, 35);
		director.setBounds(285, 70, 400, 35);
		genre.setBounds(285, 110, 400, 35);
		grade.setBounds(350,150,400,35);
		regdate.setBounds(285, 150, 400, 35);
		score.setBounds(285, 190, 400, 35);
		time.setBounds(285, 230, 400, 35);
		actor.setBounds(285, 270, 400, 35);
		showuser.setBounds(285,310,400,35);
		story.setBounds(285,350,600,65);
		video.setBounds(285, 500, 400, 35);
		video.setVisible(false);
		
		JPanel p=new JPanel();
		p.add(b1);p.add(b2);
		p.setBounds(365, 450, 400, 35);
		
		add(p); add(score);
		add(posterLa);add(title);
		add(director);add(story);
		add(genre);add(regdate);
		add(time);add(actor);
		add(showuser);add(video);
		
		b1.addActionListener(this);
		b2.addActionListener(this);				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == b1) {
			//cp.card.show(cp, "HF");
			cp.tab.setSelectedIndex(0); //홈으로 이동
		} else if (e.getSource() == b2) {
			try {
				//System.out.println(video.getText());
				Runtime.getRuntime().exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe "
						+ "http://youtube.com/embed/" + video.getText());
			} catch (Exception ex) {
			}
		}
	}
}

