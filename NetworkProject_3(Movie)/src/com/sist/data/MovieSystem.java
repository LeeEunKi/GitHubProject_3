package com.sist.data;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MovieSystem {
	public static ArrayList<MovieVO> list = new ArrayList<MovieVO>();

	// 초기화
	static {
		try {
			FileInputStream fis = new FileInputStream("c:\\java_data\\movie.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			list = (ArrayList<MovieVO>) ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static ArrayList<MovieVO> getList() {
		return list;
	}

	public static ArrayList<MovieVO> movieVOListData(int cno, int page) {
		ArrayList<MovieVO> cList= new ArrayList<MovieVO>();
		// 페이지 나누기
		int j=0;
		int pagecnt=(page*6)-6;
		/*
		 *  list.size ==>700 (0~699)
		 *  cno 1=> 1
		 *  cno 2=> 100
		 *  cno 3=> 200
		 *  cno 4=> 300 
		 */
		for(int i=0; i<list.size();i++) {
		    MovieVO m= list.get(i);
				if (j < 6 && i >= (pagecnt+((cno-1)*100))) {
					cList.add(m);
					j++;
				}
		}
		return cList;
	}

	public static int movieTotalPage(int cno) {
		if(cno==1)
			return (int) (Math.ceil(109 / 6.0));
		else if(cno==2)
			return (int) (Math.ceil(34 / 6.0));
		else
			return 0;
	}

	public static void main(String[] args) {
		
		ArrayList<MovieVO> list = movieVOListData(1,11);
		for (MovieVO m : list) {
			System.out.println(m.getMno() + "." + m.getTitle());
		}

	}
	
	public static ArrayList<MovieVO> movieFind(String fd) {
		ArrayList<MovieVO> flist = new ArrayList<MovieVO>();
		for(MovieVO m:list) {
			if(m.getTitle().contains(fd)) {
				//LIKE "%fd%"
				flist.add(m);
			}
			
		}	
		return flist;		
	}
	
	public static ArrayList<MovieVO> movie10() {
		ArrayList<MovieVO> tList = new ArrayList<MovieVO>();
		int arr[] = new int[10];

		// 중복 제거 && 평점 7.5점 이상 영화만 출력
		for (int i = 0; i < 10; i++) {
			int a = (int) (Math.random() * 100) + 1;
			arr[i] = a;
			for (int j = 0; j < i; j++)
				if (arr[i] == arr[j] || list.get(a).getScore() <= 7.5) {
					i--;
					break;
				}
		}
		for (int k = 0; k < 10; k++) {
			MovieVO m = list.get(arr[k]);
			tList.add(m);
		}
		return tList;
	}

}
