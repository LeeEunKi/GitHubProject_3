package com.sist.client;
import java.util.*;
import java.awt.*;
import javax.swing.*;

import com.sist.data.*;

public class HomeForm extends JPanel{
	public JButton b1,b2; // 이전, 다음 버튼
	public JButton m1,m2;
	public MovieManager mm;
	public MovieSystem ms = new MovieSystem();
	public JLabel pageLa=new JLabel("0 page / 0 pages");
	public HomeForm(ControllerPanel cp)
	{
		mm=new MovieManager(cp); 
		
		
		JPanel p=new JPanel();
		
		m1=new JButton("상영중");
		m2=new JButton("개봉예정");
		
		b1=new JButton("이전");
		b2=new JButton("다음");
		
		
		// 배치
		setLayout(null);
		p.setBounds(0,0,840,35);
		p.add(m1);
		p.add(m2);
		add(p);
		
		mm.setBounds(0, 0, 840, 780);
		add(mm);
		
		JPanel p1=new JPanel();
		p1.add(b1);
		p1.add(pageLa);
		p1.add(b2);
		p1.setBounds(0, 790, 840, 30);
		add(p1);
		
		
		
		//시작과 동시에 데이터를 받는다
		ArrayList<MovieVO> list=MovieSystem.movieVOListData(1,1);
		mm.cardPrint(list);
	
	}

}
