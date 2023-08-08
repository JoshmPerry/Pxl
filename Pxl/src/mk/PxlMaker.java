package mk;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Txt.TEditor;
import java.awt.Color;
import java.awt.Rectangle;

public class PxlMaker{
	
	int imgnum;
	
	public PxlMaker() {
		imgnum=1;
	}
	
	//quality is 1><4000, space between pixels
	public void CreateImage(int x1,int y1, int x2, int y2, int quality, String filepath) {
		try {
		Robot rob = new Robot();
		try {
			if(!filepath.substring(filepath.length()-4).equals(".txt"))
				filepath+=".txt";
		}catch(Exception e) {
			filepath+=".txt";
		}
		TEditor tx = new TEditor(filepath);
		tx.eraseEntireFile();
		tx.writeln(x1+","+y1+","+x2+","+y2+","+quality);
		String abc ="";
		for(int y=y1;y<=y2;y+=quality) {
			for(int x=x1;x<=x2;x+=quality) {
				abc+=fromColor(rob.getPixelColor(x,y))+"|";
			}
			tx.writeln(abc);
			abc="";
		}
		imgnum++;
		}catch(Exception e) {
			System.out.println("Error creating robot: "+e);
		}
	}
	public void CreateImage(int x1,int y1,int x2, int y2,int quality) {
		CreateImage(x1,y1,x2,y2,quality,"Img"+imgnum+".txt");
	}
	public void CreateImage(int x1,int y1, int x2, int y2) {
		int quality = (x2+y2-x1-y1)/40;
		CreateImage(x1,y1,x2,y2,quality);
	}
	public void CreateImage(int x1,int y1, int x2, int y2, String filepath) {
		int quality = (x2+y2-x1-y1)/40;
		CreateImage(x1,y1,x2,y2,quality,filepath);
	}
	
	private String fromColor(Color a) {
		int red = a.getRed();
		int green = a.getGreen();
		int blue = a.getBlue();
		return red+","+green+","+blue;
	}
	
	public static void main(String[] args) {
		PxlMaker ab = new PxlMaker();
	}
}