package com.sist.server;
import java.io.*; // 통신 ==> 메모리/파일/네트워크 입출력
import java.util.*; // StringTokenizer
import com.sist.common.*;

import java.net.*; // 네트워크
/*
 *    ===============
 *      접속만 담당 ==> 교환 소켓 =>1개만 생성
 *        접속시에 클라이언트의 정보(IP,PORT)
 *    ===============
 *      통신만 담당 ==> 통신 소켓 => Client 개수만큼 생성
 *                              ---------- => 쓰레드
 *    ===============
 *      => 클라이언트 정보 공유 => 내부 클래스 (멤버클래스)
 *      
 */
// 내부클래스 => AI에서 100% 사용

public class Server implements Runnable{
	private ServerSocket ss; //접속을 담당하는 클래스
	private final int PORT=3355; //0~65535 => (0~1023) 사용중
	private Vector<Client> waitVc=
			new Vector<Client>(); //클라이언트 정보 저장(IP,PORT,ID...)
	// 서버 구동
	public Server() {
		try {
			System.out.println("Server Start...");
			while (true) {
				ss = new ServerSocket(PORT);
				//
				// => 사이트(String) / 채팅
				// NodeJS => 스프링 대체
			}
		}catch(Exception ex) {}
		
	}
	public void run() {
		//클라이언트 접속시 마다 처리
		try {
			while (true) {
				Socket s = ss.accept(); // 클라이언트가 접속시에 호출
				// s= 클라이언트에 대한 정보 (IP,PORT => Socket)
				// s=> Thread에 전송 후에 통신이 가능하게 만든다
				Client client = new Client(s);
				client.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server=new Server();
		new Thread(server).start();

	}
	
	class Client extends Thread{
		String id,name,sex;
		Socket s; //클라이언트 접속 정보
		BufferedReader in;
		OutputStream out;
		public Client(Socket s) {
			// 클라이언트와 연결
			try {
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				out=s.getOutputStream();
				// 서버에서는 클라이언트들의 정보를 알고있다
				// 클라이언트는 서버정보
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 실제 통신 => Thread 가 동작 => 호출 시에 start()
		public void run() {
			try {
				while(true) {
					//클라이언트의 요청사항 받기
					String msg= in.readLine();
					//어떤 요구 사항인wl
					StringTokenizer st=new StringTokenizer(msg,"|");
					int protocol=Integer.parseInt(st.nextToken());
					//login.jsp
					//chat.jsp
					//send.jsp
					//end.jsp
					switch(protocol)
					{
					case Function.LOGIN: //로그인 처리
					{
						id=st.nextToken();
						name=st.nextToken();
						sex=st.nextToken();
						
						// 접속된 모든 사람에게 전송
						messageALL(Function.LOGIN+"|"+id+"|"+name+"|"+sex);
						
						//저장
						waitVc.add(this);
						
						//로그인 하는 사람 처리
						messageTo(Function.MYLOG+"|"+name);
						for(Client client:waitVc) {
							messageTo(Function.LOGIN+"|"
									+client.id+"|"
									+client.name+"|"
									+client.sex);
						}
					}
						break;
					case Function.CHAT: //채팅 처리
					{
						messageALL(Function.CHAT+"|["+name+"]"+st.nextToken());
					}
						break;
					case Function.SEND: // 쪽지보내기
						break;
					case Function.END: //종료하기
					{
						messageALL(Function.END+"|"+id);
						for(int i=0;i<waitVc.size();i++) {
							Client client=waitVc.get(i);
							if(id.equals(client.id)) {
								messageTo(Function.MYEND+"|");
								waitVc.remove(i);
								in.close();
								out.close();
								break;
							}
						}
					}
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//개인마다
		public void messageTo(String msg)
		{
			try {
				out.write((msg+"\n").getBytes());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		public void messageALL(String msg) {
			try {
				for(Client client:waitVc) {
					client.messageTo(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		}
	}

}
