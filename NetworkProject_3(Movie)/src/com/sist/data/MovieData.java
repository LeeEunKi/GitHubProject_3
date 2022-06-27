package com.sist.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.data.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class MovieData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovieData mc = new MovieData();
		mc.movieData();
	}

	public void movieData() {
		ArrayList<MovieVO> list = new ArrayList<MovieVO>();
		// DataDAO dao=DataDAO.newInstance();// newInstance() , getInstance()
		// new => new갯수만큼 메모리가 생성 , 한개의 메모리만 사용이 가능하게 만들어 준다 (메모리 누수방지)
		String[] movie = { "current.naver", "premovie.naver" };
		try {
			int k = 1; // mno
			int c = 1; // cno => 1상영/2개봉/3전체영화
			for (String url : movie) {
				Document doc = Jsoup.connect("https://movie.naver.com/movie/running/" + url).get();
				// 출력된 HTML을 가지고 있다 (doc)

				Elements title = doc.select("dt.tit a"); // select(css)
				Elements grade = doc.select("dt.tit span");
				Elements reserve = doc.select("span.num");
				Elements etc = doc.select("dl.info_txt1 dt.tit_t1+dd");
				Elements director = doc.select("dl.info_txt1 dt.tit_t2+dd");
				Elements actor = doc.select("dl.info_txt1 dt.tit_t3+dd");
				Elements poster = doc.select("div.thumb img");
				Elements link = doc.select("div.thumb a");
				// 인접 (옆에 있는 태그) + ~
				for (int i = 0; i < title.size(); i++) {
					MovieVO vo = new MovieVO();
					try {
						vo.setMno(k);
						vo.setCno(c);
						System.out.print("cno: "+vo.getCno());
						System.out.print("/ mno: "+vo.getMno());
						
						String ss = "";
						try {
							ss = title.get(i).text();
						} catch (Exception ex) {
							ss = "";
						}			
						vo.setTitle(ss);
						System.out.println(" "+vo.getTitle());
						
						try {
							vo.setGrade(grade.get(i).text());
						} catch (Exception ex) {
							System.out.println("등급없음");
							vo.setGrade("등급없음");
						}
						System.out.println(vo.getGrade());

						try {
							ss = reserve.get(i).text();
						} catch (Exception ex) {
							ss = "";
						}
						vo.setReserve(ss);
						System.out.println(vo.getReserve());

						System.out.println(etc.get(i).text());
						// 공포, 스릴러 | 72분 | 2021.08.26 개봉

						StringTokenizer st = new StringTokenizer(etc.get(i).text(), "|");
						String genre = st.nextToken();
						String time = st.nextToken();
						String regdate = st.nextToken();
						System.out.println(genre.trim());
						vo.setGenre(genre.trim());
						System.out.println(time.trim());
						vo.setTime(time.trim());
						System.out.println(regdate.trim());
						vo.setRegdate(regdate.trim());
						System.out.println("장르: "+vo.getGenre());
						System.out.println("러닝타임: "+vo.getTime());
						System.out.println("개봉일: "+vo.getRegdate());

						try {
							ss = director.get(i).text();
						} catch (Exception ex) {
							ss = "";
						}
						vo.setDirector(ss);
						System.out.println("감독: " + vo.getDirector());
						try {
							System.out.println("출연진: " + actor.get(i).text());
							ss = actor.get(i).text();
						} catch (Exception ex) {
							ss = "";
						}
						vo.setActor(ss);
						//System.out.println("출연진: " + vo.getActor());
				
						vo.setPoster(poster.get(i).attr("src"));
						System.out.println("이미지: "+vo.getPoster());
						String strLink = "https://movie.naver.com" + link.get(i).attr("href");// 줄거리
						Document doc2 = Jsoup.connect(strLink).get();
						Element story = doc2.selectFirst("p.con_tx");
						// System.out.println(story.text());
						vo.setStory("줄거리 "+story.text()); // 수정
						System.out.println(vo.getStory());
						// <span class="count">754,989명
						String s = "";
						try {
							Element showUser = doc2.selectFirst("span.count");
							s = showUser.text().substring(0, showUser.text().indexOf("("));
						} catch (Exception ex) {
							s = "0명";
						}
						vo.setShowUser(s);
						System.out.println("관객: " + vo.getShowUser());
						try {
							Element score = doc2.selectFirst("span.st_on"); // span class="st_on
							s = score.text();
							s = s.replaceAll("[가-힣]", "");
							// 7.64
							s=s.trim();
							vo.setScore(Double.parseDouble(s));

						} catch (Exception ex) {
							s = "0.0";
							vo.setScore(Double.parseDouble(s));
						}
						System.out.println("평점: "+vo.getScore());

						// System.out.println(youtubeGetKey(title.get(i).text()));
						vo.setKey(youtubeGetKey(title.get(i).text()));
						System.out.println("유튜브: "+vo.getKey());
						
						// dao.movieInsert(vo);
						System.out.println("=========================================");
						list.add(vo);
						k++;
					} catch (Exception ex) {
					} // for을 다시 수행(증가)
						// <a href="aaa">bbb</a>
					/*
					 * 1. aaa ==> attr("속성명") => img(src),a(href) 2. bbb ==> text() 3. html() <div>
					 * <span>aaa</span> </div>
					 * 
					 * => text() => aaa => html() => <span>aaa</span> => javascript안에 있는 값을 읽을 경우 =>
					 * data()
					 */
				}
				c++;
			}
			FileOutputStream fos = new FileOutputStream("c:\\java_data\\movie.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
			fos.close();
			System.out.println("파일 저장 완료!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String youtubeGetKey(String title) {
		String key = "";
		try {
			Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=" + title).get();
			Pattern p = Pattern.compile("/watch\\?v=[^가-힣]+");
			// /watch?v=(숫자,알파벳,특수문자) +(여러개문자를 읽어 올때)
			Matcher m = p.matcher(doc.toString());
			// /watch?v=47JjBTbI6P0"
			while (m.find())// 시작하는 문자열을 찾은 경우
			{
				String s = m.group();// 찾은 문장을 읽어 온다
				key = s.substring(s.indexOf("=") + 1, s.indexOf("\""));
				break;
			}
		} catch (Exception ex) {
		}
		return key;
	}


}