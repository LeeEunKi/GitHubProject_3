package com.sist.main;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.sist.client.ChatForm;
import com.sist.client.ControllerPanel;
import com.sist.client.DetailForm;
import com.sist.client.HomeForm;
import com.sist.client.LoginForm;
import com.sist.client.MenuForm;
import com.sist.client.MovieFindForm;
import com.sist.client.NewsForm;
import com.sist.client.WaitForm;
import com.sist.common.Function;
import com.sist.data.MovieSystem;
import com.sist.data.MovieVO;

public class NetworkMain extends JFrame implements ActionListener,Runnable{
	MenuForm menu=new MenuForm();
	ControllerPanel cp=new ControllerPanel();
	WaitForm wr=new WaitForm();
	LoginForm lf=new LoginForm();
	
	int curpage=1;
	int totalpage = 0;
	int cno = 1;

	// 서버와 관련된 클래스
	Socket s;
	BufferedReader in; // 쓰레드
	OutputStream out; // 일반 유저
	// 서버 연결되는 시점 => 로그인 버튼 클릭시 연결
	JTabbedPane tab = new JTabbedPane();

	//public LoginForm lf=new LoginForm();
	public NewsForm nf=new NewsForm();

	public NetworkMain() {
		setTitle("무비챗");
		setLayout(null); //사용자 정의 = 직접배치
		cp.setBounds(10, 15, 850, 850);
		add(cp);
		wr.setBounds(980, 15, 250, 700);
		add(wr);
		
		setSize(1250,900);
//		setVisible(true);
		//X버튼 누르면 종료되도록
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//이렇게하면 X눌러도 아무것도 안함
		//버튼을 누르면 어떻게 해야 한다=이벤트 등록
		cp.hf.m1.addActionListener(this); //current movie
		cp.hf.m2.addActionListener(this); //pre movie
		cp.hf.b1.addActionListener(this); //이전
		cp.hf.b2.addActionListener(this); //다음
		
		//로그인 처리
		lf.b1.addActionListener(this);
		lf.b2.addActionListener(this);
		
		//채팅
		cp.cf.tf.addActionListener(this);//채팅 텍스트필드에 입력후 엔터누르면 넘어가게할것임.
		
		menu.chatBtn.addActionListener(this);
		menu.exitBtn.addActionListener(this);
		menu.homeBtn.addActionListener(this);
		menu.newsBtn.addActionListener(this);
		menu.movieBtn.addActionListener(this);
		
		cp.mf.btn.addActionListener(this); //movieFind 찾기버튼
		
		totalpage = MovieSystem.movieTotalPage(cno);
		cp.hf.pageLa.setText(curpage+"page/"+totalpage+"pages");
	}
	public static Image getImage(ImageIcon ii, int width, int height) {
		
		Image deimg = ii.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return deimg;
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		}catch(Exception ex) {}
		new NetworkMain();
	}
	//버튼 클릭시 처리 => 구현이 안됨 => 클릭을 하면 자동 시스템(JVM)에 의해 자동 호출
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//이전버튼
		if(e.getSource()==cp.hf.b1) {
			if(curpage>1) {
				curpage--;
				ArrayList<MovieVO> list= cp.hf.ms.movieVOListData(cno,curpage);

				cp.hf.mm.cardInit(list);
				cp.hf.mm.cardPrint(list);
				
				cp.hf.pageLa.setText(curpage+" page / "+ totalpage+" pages ");
			}

		}
		//로그인 처리
		else if(e.getSource()==lf.b1) {
			//id
			//name ===> 반드시 입력 => 유효성 검사 => JQuery
			// => 기본(보안) =>Spring Security
			String id=lf.tf1.getText();
			if(id.length()<1) {
				JOptionPane.showMessageDialog(this,"ID를 입력하세요.");
				// JSP에서는 = alert("ID를 입력하세요")
				lf.tf1.requestFocus();
				return;
			}
			
			String name=lf.tf2.getText();
			if(name.length()<1) {
				JOptionPane.showMessageDialog(this,"이름을 입력하세요.");
				// JSP에서는 = alert("ID를 입력하세요")
				lf.tf2.requestFocus();
				return;
			}
			
			String sex="";
			//남자 버튼
			if(lf.rb1.isSelected())	sex="남자";
			//여자 버튼
			else sex="여자";
			
			//서버 연결
			try {
				s=new Socket("localhost",3355);
				// 서버가 보내준 데이터를 저장된 위치
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out=s.getOutputStream(); //보내는 위치
				// TCP
				
				// 로그인 요청
				out.write((Function.LOGIN+"|"+id+"|"+name+"|"+sex+"\n").getBytes());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this,"Server가 닫혀있습니다");
			}
			// 서버에서 들어오는 데이터를 읽어서 출력
			 new Thread(this).start();
			
			
		}
		else if(e.getSource()==lf.b2) {
			System.exit(0); //0 => 정상종료 
		}
		else if(e.getSource()==cp.cf.tf) {
			//1. 채팅문자 읽기
			String msg=cp.cf.tf.getText();
			if(msg.length()<1)
				return;
			try {
				out.write((Function.CHAT+"|"+msg+"\n").getBytes());
			} catch (Exception ex) {}
			cp.cf.tf.setText("");
		}
		//다음버튼
		else if (e.getSource()==cp.hf.b2) {
			if(curpage<totalpage) {
				curpage++;
				ArrayList<MovieVO> list= cp.hf.ms.movieVOListData(cno,curpage);

				cp.hf.mm.cardInit(list);
				cp.hf.mm.cardPrint(list);
				
				cp.hf.pageLa.setText(curpage+" page / "+ totalpage+" pages ");
			}
		}
		else if(e.getSource()==menu.chatBtn) {
			cp.card.show(cp, "CF");
		}
		
		else if (e.getSource() == menu.exitBtn) {
			try {
				out.write((Function.END+"|\n").getBytes());
			}catch(Exception ex) {}
			
		}
		else if (e.getSource() == menu.homeBtn) {
			cp.card.show(cp, "HF");
		}
		else if (e.getSource() == menu.movieBtn) {
			cp.card.show(cp, "MF");
		}
		else if(e.getSource()==menu.newsBtn) {
			cp.card.show(cp, "NF");
		}

		//검색 버튼 클릭시
		else if(e.getSource() ==cp.mf.btn) {
			//1. 입력값
			String fd=cp.mf.tf.getText();
			//입력이 안된 상태
			if(fd.length()<1) { 
				JOptionPane.showMessageDialog(this, "검색어를 입력하세요.");
				cp.mf.tf.requestFocus();
				return;
			}
			ArrayList<MovieVO> flist=MovieSystem.movieFind(fd);
			// 출력된 내용 지운다
			// ==> 밑에서부터 지운다                  ---------
			for(int i=cp.mf.model.getRowCount()-1;i>=0;i--) {
				cp.mf.model.removeRow(i);
			}
			
			try {
				for(MovieVO m:flist) {
					URL url= new URL(m.getPoster());
					Image img=getImage(new ImageIcon(url),30,30);
					Object[] data= {
							new ImageIcon(img),
							m.getTitle(),
							m.getDirector()
					};
					cp.mf.model.addRow(data);
				}
			} catch (Exception e2) {}
		} 
		// 상영중
		else if (e.getSource() == cp.hf.m1) {
			
			curpage = 1;
			cno = 1;
			totalpage = MovieSystem.movieTotalPage(cno);
			ArrayList<MovieVO> list = cp.hf.ms.movieVOListData(cno, curpage);
			cp.hf.mm.cardInit(list);
			cp.hf.mm.cardPrint(list);
			cp.hf.pageLa.setText(curpage + "page/" + totalpage + "pages");	
		} 
		// 개봉예정
		else if (e.getSource() == cp.hf.m2) {
		
			curpage = 1;
			cno = 2;
			totalpage = MovieSystem.movieTotalPage(cno);
			ArrayList<MovieVO> list = cp.hf.ms.movieVOListData(cno, curpage);
			cp.hf.mm.cardInit(list);
			cp.hf.mm.cardPrint(list);
			cp.hf.pageLa.setText(curpage + "page/" + totalpage + "pages");
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				// 서버에서 보내주는 데이터를 받는다
				String msg = in.readLine();
				//System.out.println(msg);
				StringTokenizer st = new StringTokenizer(msg, "|");
				int protocol = Integer.parseInt(st.nextToken());
				switch (protocol) {
				case Function.LOGIN: {
					String[] data = { st.nextToken(), // ID
							st.nextToken(), // name
							st.nextToken() // sex
					};
					cp.cf.model.addRow(data);
				}
					break;
				case Function.MYLOG: {
					lf.setVisible(false); // 로그인은 종료
					setVisible(true); // 메인창 열기
				}
					break;
				case Function.CHAT: {
					cp.cf.ta.append(st.nextToken() + "\n");
				}
					break;
				case Function.SEND:
					break;
				case Function.END: // 남아있는 사람
				{
					String myId = st.nextToken();
					for (int i = 0; i < cp.cf.model.getRowCount(); i++) {
						String you = cp.cf.model.getValueAt(i, 0).toString();
						if (myId.equals(you)) {
							cp.cf.model.removeRow(i);
							break;
						}
					}
				}
					break;
				case Function.MYEND: // 나간사람
				{
					System.exit(0); // 종료
				}
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
