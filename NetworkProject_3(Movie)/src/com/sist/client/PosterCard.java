package com.sist.client;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import com.sist.data.*;
import com.sist.main.NetworkMain;

public class PosterCard extends JPanel{
	JLabel poster = new JLabel();
	JLabel title = new JLabel();
	JLabel regdate = new JLabel();

	
	public PosterCard(MovieVO m)
	{
		setLayout(null);
		poster.setBounds(5, 5, 200, 270);
		//poster.setOpaque(true); // 투명모드
		//poster.setBackground(Color.pink);
		try {
			URL url=new URL(m.getPoster());
			Image img=NetworkMain.getImage(new ImageIcon(url), 200,300);
			poster.setIcon(new ImageIcon(img));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		title.setBounds(5, 285, 168, 15);
		title.setText(m.getTitle());
		
		regdate.setBounds(5,305,168,15);
		regdate.setText(m.getRegdate());

		add(poster);
		add(title);
		add(regdate);


	}
	
}
