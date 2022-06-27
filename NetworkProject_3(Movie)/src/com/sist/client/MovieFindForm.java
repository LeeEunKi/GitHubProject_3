package com.sist.client;

import javax.swing.*; //Container,Component => 경량
import java.awt.*;
import javax.swing.table.*;
/*
 *      Container (윈도우창)
 *        = JFrame => 일반 윈도우창
 *        = JPanel => 단독 실행이 불가능
 *        = JWindow : 타이블바가 없는 창
 *        = JDialog
 *      
 *      Component
 *        = 기능이 한개인 윈도우 => 단독 실행이 불가능 => JFrame, JPanel
 *        = Button
 *           = JButton ==> <input type=button>
 *           = JRadioButton ==> <input type = radio>
 *           = JCheckBox        <input type=checkbox>
 *        =입력창
 *          = 한줄 입력 : JTextField, JPasswordField
 *          	         <input type=text>
 *          		     <input type=password>
 *          = 여러줄 입력 : JTextArea(메모장) => JTextPane(word)
 *          			  <textarea>
 *          = JLabel : 보여만 준다 (이미지) <label>
 *            HTML/CSS => 암기
 *          ================================Java+HTML =>JSP
 *           JAVA / HTML =>분리 => MVC
 *           => 어노테이션 /XML => Spring 
 *        
 */

public class MovieFindForm extends JPanel {
	public JTextField tf;
	public JButton btn;
	public JTable table;
	public DefaultTableModel model;
	//초기화&배치~
	public MovieFindForm() {
		tf = new JTextField();
		btn = new JButton("검색");
		String[] col = {"","영화제목","감독"};
		Object[][] row = new Object[0][3]; //이미지, 문자열 등 다양한 걸 다 받아줄 수 있음
		model = new DefaultTableModel(row,col) {
			//익명 클래스 = 생성자 안에서 상속 없이 오버라이딩할 때 사용
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; //편집 방지
			}
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
			}
		};
		
		table = new JTable(model);
		table.setRowHeight(40); //html에서 <tr height=40>과 같음.
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		table.getTableHeader().setReorderingAllowed(false);//
		
		JScrollPane js = new JScrollPane(table);
		setLayout(null);
		tf.setBounds(10,15,200,30);
		btn.setBounds(215,15,100,30);
		js.setBounds(10,55,800,500);
		
		add(tf);
		add(btn);
		add(js);
	}

}
