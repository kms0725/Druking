package Druking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String path = System.getProperty("user.dir");  //디렉토리 경로 받기
		BufferedWriter out1 = new BufferedWriter(new FileWriter("dir.txt")); //디렉토리 경로 dir.txt에 저장 
		out1.write(path);
		out1.close();
//		Runnable run = new Thread1();
//		Thread t1 = new Thread(run);
//		t1.start();
		Frame testFrame = new Frame();

	}

}
