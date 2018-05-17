package Druking;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//class Thread1 implements Runnable{
//
//	@Override
//	public void run() {
//		Frame fr = new Frame();
//		
//		
//	}
//	
//}
public class Frame extends JFrame {
	REngine startt;
	JLabel Image = new JLabel(" ");
	JTextArea reply = new JTextArea(15, 30);
	public Frame() {
		//exe();
		init();
	}
	
	public void init() {
		setSize(1000, 700);	
		setTitle("네이버뉴스 댓글 크롤링");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		JPanel p1 = new JPanel(new FlowLayout());
		JPanel p2 = new JPanel(new FlowLayout());
		JPanel p3 = new JPanel(new BorderLayout());
		c.setLayout(new BorderLayout());
		JLabel druking = new JLabel("유사 댓글(드루킹)");
		
		JScrollPane scrollPane = new JScrollPane(reply);
		
		JTextField link = new JTextField(60);
		JButton btn = new JButton("검색");

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = link.getText();
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter("URL.txt"));
					out.write(str);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Rstart();
			}
		});		
		
		c.add(BorderLayout.NORTH, p1);
		c.add(BorderLayout.WEST, p2);
		c.add(BorderLayout.EAST, p3);
		p1.add(new JLabel("네이버뉴스 링크     "));
		p1.add(link);
		p1.add(btn);
		p2.add(Image);
		p3.add(BorderLayout.NORTH, druking);
		p3.add(BorderLayout.WEST, scrollPane);
		
		setVisible(true);

	}
	//REnging 실행 후 R 읽고 Java GUI창에 wordcloud 그리기
	public void Rstart() {		
		startt = new REngine(); //R start
		startt.createREngine();
		System.out.println("clouddddd.R 읽기");
		startt.readScript("clouddddd.R");
		
		if(startt.isSuccessful()) {
			System.out.println("Wordcloud 그리기");			
			ImageIcon cloudImg = new ImageIcon("cloud.jpg");
			setImg(cloudImg);
			System.out.println("wordcloud 성공");
		}
		else {
			System.out.println("R 파일을 읽을 수 없슴다");
		}
	}
	
	//wordcloud그리면서 GUI에 중복된 글 적기	
	public void setImg(ImageIcon img) {
		Image.setIcon(img);
		add("Center", Image);
		setVisible(true);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("druking.txt"), "euc-kr"));
			String line = null;
			while((line = br.readLine()) != null) {
				reply.setText(reply.getText() + line + "\n");
			}			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
