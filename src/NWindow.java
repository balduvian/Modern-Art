import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class NWindow extends JFrame{
	
	BufferedImage scrib;
	Canvas canvas;
	LinkedList<BButton> buttons = new LinkedList<BButton>();
	int width = 400;
	int height = 400;
	
	public class BButton{
		int x;
		int y;
		String name;
		boolean open = true;
		int w;
		int h;
		int fullx=0;
		int fully=0;
		
		public BButton(String n, int x, int y, int w, int h){
			name = n;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}
	
	public class Canvas extends JPanel{
		public void paintComponent(Graphics g){
			int offx = getWidth()/2-scrib.getWidth()/2;
			int offy = getHeight()/2-scrib.getHeight()/2;
			g.drawImage(scrib,offx,offy,scrib.getWidth(),scrib.getHeight(),this);
			for(int i=0;i<buttons.size();i++){
				BButton b = buttons.get(i);
				g.setColor(Color.LIGHT_GRAY);
				g.fill3DRect(offx+b.x, offy+scrib.getHeight()+b.y, b.w, b.h, b.open);
				g.setColor(Color.BLACK);
				g.setFont(new Font(Font.SANS_SERIF,0,30));
				g.drawString(b.name, offx+b.x+10, offy+scrib.getHeight()+b.y+(b.h-10));
				b.fullx = b.x+offx;
				b.fully = offy+scrib.getHeight()+b.y;
			}
		}
	}
	
	public void nibber(int times){
		draw(scrib, new Color(rando(0,255),rando(0,255),rando(0,255)),0,scrib.getWidth()/2,scrib.getHeight()/2,scrib.getWidth(),scrib.getHeight());
		for(int i=0;i<times;i++){
																	//T
			draw(scrib,new Color(rando(0,255),rando(0,255),rando(0,255)),rando(0,2),rando(0,scrib.getWidth()-1),rando(0,scrib.getHeight()-1),rando(10,scrib.getWidth()/2),rando(10,scrib.getHeight()/2));
			//
		}
	}
	
	public int rando(int l, int u){
		return (int)(Math.random()*(u-l+1)+l);
	}
	
	public NWindow(){
		canvas = new Canvas();
		buttons.add(new BButton("save",0,10,140,40));
		buttons.add(new BButton("generate",150,10,140,40));
		scrib = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		addMouseListener(new Clicks());
		add(canvas);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(800,600);
		setBackground(Color.GRAY);
		setVisible(true);
	}
	
	public class Clicks implements MouseListener{

		public void mouseClicked(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
			Rectangle m = new Rectangle(e.getX(),e.getY(),1,1);
			for(int i = 0;i<buttons.size();i++){
				BButton b = buttons.get(i);
				Rectangle r = new Rectangle(b.fullx,b.fully,b.w,b.h);
				if(m.intersects(r)){
					b.open=false;
					if(i==0){
						File f = new File("art.png");
						try {
							ImageIO.write(scrib, "PNG", f);
						}catch (IOException arg0){}
						for(int v=0;v<20;v++){
							scrib = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
							nibber(200);
							File ff = new File("art"+v+".png");
							try {
								ImageIO.write(scrib, "PNG", ff);
							}catch (IOException arg0){}
						}
					}else if(i==1){
						scrib = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
						nibber(200);
					}
				}
			}
		}
		public void mouseReleased(MouseEvent e) {
			for(int i = 0;i<buttons.size();i++){
				BButton b = buttons.get(i);
				b.open=true;
			}
		}
		
	}
	
	public static void main(String[] args){
		NWindow n = new NWindow();
		while(true){
			n.canvas.repaint();
		}
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
