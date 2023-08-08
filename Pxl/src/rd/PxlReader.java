package rd;

import java.awt.Robot;
import Txt.TEditor;
import java.awt.Color;
import java.util.ArrayList;

public class PxlReader {
	String imFile;
	ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<Color> OnScreen = new ArrayList<Color>();
	int x1;
	int y1;
	int x2;
	int y2;
	int focus;
	
	public PxlReader(String path) {
		changeFile(path);
		update(colors);
	}
	
	public void changeFile(String filepath) {
		try {
			if(!filepath.substring(filepath.length()-4).equals(".txt"))
				filepath+=".txt";
		}catch(Exception e) {
			filepath+=".txt";
		}
		imFile = filepath;
		update(colors);
	}
	
	private void update(ArrayList<Color> color) {
		try {
		color.clear();
		} catch(Exception e) {
		}
		TEditor tx = new TEditor(imFile);
		String temp = tx.readLine(1);
		x1 = Integer.parseInt(temp.substring(0,temp.indexOf(",")));
		temp = temp.substring(temp.indexOf(",")+1);
		y1 = Integer.parseInt(temp.substring(0,temp.indexOf(",")));
		temp = temp.substring(temp.indexOf(",")+1);
		x2 = Integer.parseInt(temp.substring(0,temp.indexOf(",")));
		temp = temp.substring(temp.indexOf(",")+1);
		y2 = Integer.parseInt(temp.substring(0,temp.indexOf(",")));
		temp = temp.substring(temp.indexOf(",")+1);
		focus = Integer.parseInt(temp);
		for(int i=2;i<=tx.numLines();i++) {
			temp = tx.readLine(i);
			while(!temp.equals("")) {
				color.add(getColor(temp.substring(0,temp.indexOf("|"))));
				temp = temp.substring(temp.indexOf("|")+1);
			}
		}
	}
	
	public boolean check() {
		return check(colors.size()/20,5);
	}
	
	
	public boolean check(int bpix,int flux) {
		getMonitor();
		int wpix=0;
		for(int i=0;i<colors.size();i++) {
			if(!(colors.get(i).getRed()>=(OnScreen.get(i).getRed()-flux)&&colors.get(i).getRed()<=(OnScreen.get(i).getRed()+flux))&&(colors.get(i).getGreen()>=(OnScreen.get(i).getGreen()-flux)&&colors.get(i).getGreen()<=(OnScreen.get(i).getGreen()+flux))&&(colors.get(i).getBlue()>=(OnScreen.get(i).getBlue()-flux)&&colors.get(i).getBlue()<=(OnScreen.get(i).getBlue()+flux))) {
				wpix++;
				if(wpix>=bpix)return false;
			}
		}
		return true;
	}
	
	private void getMonitor() {
		try {
			Robot rob = new Robot();
		
		for(int y=y1;y<=y2;y+=focus) {
			for(int x=x1;x<=x2;x+=focus) {
				OnScreen.add(rob.getPixelColor(x,y));
			}
		}
		} catch(Exception e) {
			System.out.println("Error creating robot: "+e);
		}
	}
	
	private Color getColor(String ac) {
		String r=ac.substring(0,ac.indexOf(","));
		ac = ac.substring(ac.indexOf(",")+1);
		String g=ac.substring(0,ac.indexOf(","));
		String b=ac.substring(ac.indexOf(",")+1);
		return new Color(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b));
	}
	
	public static void main(String[] args) {
		PxlReader ab = new PxlReader("Img");
		System.out.println(ab.check());
	}
}
