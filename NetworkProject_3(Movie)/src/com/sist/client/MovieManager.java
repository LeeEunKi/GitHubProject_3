package com.sist.client;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.*;
import com.sist.data.*;
import com.sist.main.NetworkMain;
public class MovieManager extends JPanel implements MouseListener{
    public PosterCard[] MovieVOs=new PosterCard[6];
    public JPanel pan=new JPanel();
    public ControllerPanel cp;
    public MovieManager(ControllerPanel cp) {
    	this.cp=cp;
    }
    public void cardPrint(ArrayList<MovieVO> list)
    {
    	setLayout(null);
    	//JPanel p=new JPanel();
    	pan.setLayout(new GridLayout(2,3));
    	int i=0;
    	for(MovieVO m:list)
    	{
    		MovieVOs[i]=new PosterCard(m);
    		pan.add(MovieVOs[i]);
    		i++;
    	}
    	
    	pan.setBounds(10, 55, 900, 800);
    	add(pan);
    	for(int j=0;j<MovieVOs.length;j++) {
    		MovieVOs[j].addMouseListener(this);
    	}
    	
    }
    public void cardInit(ArrayList<MovieVO> list)
    {
    	for(int i=0;i<list.size();i++)
    	{
    		
    		MovieVOs[i].poster.setIcon(null);
    		MovieVOs[i].title.setText("");
   
    	}
    	pan.removeAll();
		pan.validate();
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<MovieVOs.length;i++) {
			if(e.getSource()==MovieVOs[i]) {
				String title=MovieVOs[i].title.getText();
				for(int j=0;j<MovieSystem.getList().size();j++) {
					MovieVO m=MovieSystem.getList().get(j);
					if(m.getTitle().equals(title)) {
						cp.df.genre.setText("장르|"+m.getGenre());
						cp.df.title.setText("제목|"+m.getTitle());
						cp.df.director.setText("감독|"+m.getDirector());
						cp.df.regdate.setText("개봉일|"+m.getRegdate());
						cp.df.showuser.setText("관객수|"+m.getShowUser());
						cp.df.grade.setText("관람등급|"+m.getGrade());
						cp.df.score.setText("평점| ★"+m.getScore());
						cp.df.actor.setText("출연|"+m.getActor());
						
						if(m.getStory().length()>55) {
							String s="";
							s+=m.getStory().substring(0,55);
							s+="\n";
							s+=m.getStory().substring(55, 55*2)+"...";
							cp.df.story.setText("줄거리|\n"+s);
						}
						else {
							cp.df.story.setText("줄거리|"+m.getStory());
						}
						
						cp.df.time.setText("러닝타임|"+m.getTime());
						cp.df.video.setText(m.getKey());
						
						try {
							URL url=new URL(m.getPoster());
							Image img=NetworkMain.getImage(new ImageIcon(url), 250,400);
							cp.df.posterLa.setIcon(new ImageIcon(img));
							
							
						} catch (Exception ex) {
							// TODO: handle exception
						}
						break;
					}
				}
				//cp.card.show(cp,"DF"); // 화면 전환
				cp.tab.setSelectedIndex(2); //상세보기로 전환
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}