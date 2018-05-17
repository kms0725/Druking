package Druking;

import java.io.BufferedReader;
import java.io.FileReader;

import org.rosuda.JRI.Rengine;

public class REngine {
	public static Rengine re;
	private boolean isRunning = false;
	private enum SCRIPT_PROCESSING {success, fail};
	private SCRIPT_PROCESSING state;
	
	public REngine() {
		
	}
	
	public boolean isSuccessful() {
		if(state == SCRIPT_PROCESSING.success)
			return true;
		else
			return false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void createREngine() {
		String[] Rargs = {"--no-save"};
		re = new Rengine(Rargs, false, null);
		System.out.println("R Engine !!");
		
		if(!re.waitForR()) {
			System.out.println("R Engine 로딩 실패!");
			isRunning = false;
			return;
		}
		isRunning = true;
		System.out.println("R Engine 성공 !!");
		init1();
	}
	private void init1() {
//		System.out.println("Install Package....");
//		re.eval("install.packages(\"KoNLP\")");
//		re.eval("install.packages(\"RSelenium\")");
//		re.eval("install.packages(\"wordcloud\")");
//		re.eval("install.packages(\"rvest\")");
//		re.eval("install.packages(\"RColorBrewer\")");
//		re.eval("install.packages(\"httr\")");
//		re.eval("install.packages(\"stringr\")");
//		System.out.println("Pakage Install Done.");
	}
	public void readScript(String filePath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String textLine;
			while(true) {
				textLine = reader.readLine();
				if(textLine == null)
					break;
				re.eval(textLine);
			}
			state = SCRIPT_PROCESSING.success;
			reader.close();
		}
		catch(Exception e) {
			state = SCRIPT_PROCESSING.fail;
			e.printStackTrace();
		}
	}
	public void close() {
		re.eval("q()");
	}
}
