import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class NWindow extends JFrame{
	
	BufferedImage scrib;
	Canvas canvas;
	
	public class Canvas extends JPanel{
		public void paintComponent(Graphics g){
			g.drawImage(scrib,getWidth()/2-scrib.getWidth()/2,getHeight()/2-scrib.getHeight()/2,scrib.getWidth(),scrib.getHeight(),this);
		}
	}
	
	public void nibber(){
		draw(scrib, new Color(rando(0,255),rando(0,255),rando(0,255)),0,scrib.getWidth()/2,scrib.getHeight()/2,scrib.getWidth(),scrib.getHeight());
		for(int i=0;i<rando(4,5);i++){
																	//T
			draw(scrib,new Color(rando(0,255),rando(0,255),rando(0,255)),rando(0,2),rando(0,scrib.getWidth()-1),rando(0,scrib.getHeight()-1),rando(10,scrib.getWidth()/2),rando(10,scrib.getHeight()/2));
		}
	}
	
	public int rando(int l, int u){
		return (int)(Math.random()*(u-l+1)+l);
	}
	
	public NWindow(int w, int h){
		canvas = new Canvas();
		scrib = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		nibber();
		add(canvas);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBackground(Color.GRAY);
		setSize(800,600);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new NWindow(1000,1000);
	}

	public void draw(BufferedImage b, Color c, int t, int x, int y, int w, int h){
		Graphics2D g = b.createGraphics();
		g.setColor(c);
		x = x-(w/2);
		y = y-(h/2);
		if(t==0){
			g.fillRect(x, y, w, h);
		}else if(t==1){
			g.fillOval(x, y, w, h);
		}else if(t==2){
			g.fillRoundRect(x, y, w, h, rando(0,100), rando(0,100));
		}
	}
}
