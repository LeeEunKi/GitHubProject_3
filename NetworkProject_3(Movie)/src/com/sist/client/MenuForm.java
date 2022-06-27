package com.sist.client;
import java.awt.*;
import javax.swing.*;

public class MenuForm extends JPanel{
	public JButton homeBtn,movieBtn,chatBtn,newsBtn,exitBtn;
	// 초기화
	public MenuForm() {
		//setLayout(new FlowLayout());
		//setLayout(new GridLayout(5,1,10,10));
		//<input type= button value="
		homeBtn=new JButton("홈");
		movieBtn=new JButton("무비");
		chatBtn = new JButton("채팅");
		newsBtn = new JButton("뉴스"); // JSON => 네이버 뉴스(open API)
		exitBtn = new JButton("나가기");
		
		// 배치
		/*
		add(homeBtn);
		add(movieBtn);
		add(chatBtn);
		add(newsBtn);
		
		add(exitBtn);
		*/
	}

}
