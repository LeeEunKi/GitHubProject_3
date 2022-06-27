package com.sist.client;

import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.sist.data.MovieSystem;
import com.sist.data.MovieVO;
import com.sist.main.NetworkMain;
//네트워크
public class WaitForm extends JPanel{
	JTable table;
	DefaultTableModel model;
	JButton b1, b2;
	JLabel label;
	
	public WaitForm() {
		String[] col = {"포스터","영화 제목","평점"};
		String[][] row = new String[0][3];
		model = new DefaultTableModel(row,col){
			//익명 클래스(생성자 안에서 재정의, 상속 없이 오버라이딩)
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override //입력된 클래스가 컬럼으로 추가되도록 함
			public Class<?> getColumnClass(int columnIndex){
				return getValueAt(0,columnIndex).getClass();
			}
			
		};
		
		table = new JTable(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.setRowHeight(40);
		JScrollPane js1 = new JScrollPane(table);
		label = new JLabel("추천 영화 10선");
		setLayout(null); // 사용자 정의 배치
		label.setBounds(0,80,250,20);
		label.setFont(new Font("돋움",Font.BOLD,15));
		js1.setBounds(0,100,250,430);
		add("center",label);
		add(js1);
		

		try {
			ArrayList<MovieVO> list = MovieSystem.movie10();
			for(MovieVO m:list) {
				URL url = new URL(m.getPoster());
				Image img = NetworkMain.getImage(new ImageIcon(url),50,55);
				Object[] data = {
					new ImageIcon(img), m.getTitle(),"★ "+m.getScore()
				};
				model.addRow(data);
				
			}
		}catch(Exception ex) {}
	}

}
