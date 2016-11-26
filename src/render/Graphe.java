package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;

import compute.Fft;

public class Graphe extends JLabel implements ComponentListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = -5431461702771391617L;
	double[] tab; // tab to print
	double maxTab;
	double minTab;
	float coef = 1.7f;
	int seuil = 10;
	int samplingRate = 0; // sampling rate
	int origin = 600;
	int amplitude = 100;
	int pointerX;
	int pointerY;
	int pointerSize = 20;
	int cornerSize = 40;
	
	double wheelSens=0.1f; // pixels per wheel's scroll

	Graphe(double[] s, int rate) {
		super();
		addComponentListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);

		origin = (int) (getSize().height / coef);
		amplitude = (int) (getSize().height / (coef*coef));
		samplingRate = rate;

		tab = new double[s.length / 2];

		for (int i = 0; i < tab.length; i++)
			tab[i] = s[i];

		// maximum and minimum values
		maxTab = 0;
		for (int i = 0; i < tab.length; i++)
			if (maxTab < tab[i])
				maxTab = tab[i];

		minTab = 0;
		for (int i = 0; i < tab.length; i++)
			if (minTab > tab[i])
				minTab = tab[i];
		
		System.out.println("maxTab:"+maxTab);
		System.out.println("minTab:"+minTab);
	}
	
	public void setData(double tab[]) {
		this.tab = new double[tab.length/2];
		for(int i=0; i < this.tab.length; i++)
			this.tab[i] = tab[i];
		repaint();
	}
	
	public void resetData(double tab[]) {
		this.tab = new double[tab.length/2];
		for(int i=0; i < this.tab.length; i++)
			this.tab[i] = tab[i];
		
		// maximum and minimum values
		maxTab = 0;
		for (int i = 0; i < this.tab.length; i++)
			if (maxTab < this.tab[i])
				maxTab = this.tab[i];

		minTab = 0;
		for (int i = 0; i < this.tab.length; i++)
			if (minTab > this.tab[i])
				minTab = this.tab[i];
		System.out.println("maxTab:"+maxTab);
		System.out.println("minTab:"+minTab);
		repaint();
	}

	/**
	 * @param x on screen position
	 * @return y on screen position
	 */
	double g(int x) {
		double y = tab[(int) (tab.length * x / (double) getSize().width)];
		y -= minTab;
		y = y / (maxTab-minTab)-1;
		y = amplitude*y+origin;
		return y;
	}


	public void paintComponent(Graphics g) {
		int n;
		double val=0;
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.red);
		g.drawLine(0, origin, getSize().width, origin);
		
		g.setColor(Color.white);
		//pointer
		g.drawLine(pointerX+pointerSize,pointerY,pointerX-pointerSize,pointerY);
		g.drawLine(pointerX,pointerY+pointerSize,pointerX,pointerY-pointerSize);
		g.drawLine(pointerX,0,pointerX,cornerSize); // x
		g.drawLine(pointerX,getHeight()-cornerSize,pointerX,getHeight());
		g.drawLine(0,pointerY,cornerSize,pointerY); // y
		g.drawLine(getWidth()-cornerSize,pointerY,getWidth(),pointerY);
		
		n = (int)(tab.length*pointerX/(double)getSize().width);
		if(n<tab.length)
			val=Math.sqrt(Math.abs(tab[n]));
		
		g.drawString( Double.toString(val),pointerX+10,pointerY+20);
		g.drawString( Double.toString(Fft.getFrequency(n, tab.length*2, samplingRate)),pointerX+10,pointerY+50);
		
		for (int x = 0; x < getSize().width - 1; x++) {
			g.setColor(Color.black);
			g.drawLine(x, (int) g(x), x + 1, (int) g(x + 1));
			g.setColor(Color.green);
			
			if (x % 100 == 0)// line = frequency
			{
				n = (int)(tab.length*x/(double)getSize().width);
				g.drawString(
						Double.toString(Fft.getFrequency(n, tab.length*2, samplingRate)), x, getSize().height - 10);
				g.drawLine(x, getSize().height, x, 0);

			}
			
		}
	}

	public void componentResized(ComponentEvent arg0) {
		origin = (int) (arg0.getComponent().getHeight() / coef);
		amplitude = (int) (arg0.getComponent().getHeight() / (coef * coef));

	}

	public void mouseMoved(MouseEvent arg0) {
		pointerX = arg0.getPoint().x;
		pointerY = arg0.getPoint().y;
		repaint();
	}

	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void componentHidden(ComponentEvent arg0) {	
	}

	public void componentMoved(ComponentEvent arg0) {
	}

	public void componentShown(ComponentEvent arg0) {
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		amplitude*=1+arg0.getPreciseWheelRotation()*wheelSens;
		repaint();
	}

}
