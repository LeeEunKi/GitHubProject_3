package com.sist.client;
import java.awt.*;
import javax.swing.*;

public class ControllerPanel extends JPanel{
	public CardLayout card = new CardLayout();
	public HomeForm hf;
	public DetailForm df;
	public MovieFindForm mf=new MovieFindForm();
	public ChatForm cf=new ChatForm();
	//public LoginForm lf=new LoginForm();
	public NewsForm nf=new NewsForm();
	public JTabbedPane tab= new JTabbedPane();
	public ControllerPanel() {
		
		hf=new HomeForm(this);
		df=new DetailForm(this);
		//setLayout(card);
		setLayout(new BorderLayout());
		//이동경로 = 순서
		//add("LF",lf);
		add("Center",tab);
		tab.addTab("홈",hf);
		tab.addTab("검색",mf);
		tab.addTab("상세보기",df);
		tab.addTab("뉴스",nf);
		tab.addTab("채팅",cf);
	}

}
