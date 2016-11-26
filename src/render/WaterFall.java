package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

/**
 * @author djeck
 *
 */
public class WaterFall extends JLabel implements MouseListener, ComponentListener {

	private int buffSize = 100; // time
	private int length = 64; // frequency
	private int rapport=1; // pixel number per value
	private double buffer[][];
	private int offset=0; // offset
	enum Orientation {verticale, horizontale};
	private Orientation orientation = Orientation.horizontale;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5334033984954035230L;
	
	/**
	 * 
	 * @param length data's length
	 */
	public WaterFall(int length) {
		super();
		this.length = length;
		addMouseListener(this);
		addComponentListener(this);
		buffer = new double[this.length][buffSize];
	}
	/**
	 * 
	 * @param length data's length
	 * @param buffSize number of data to keep (temporal buffer's length)
	 */
	public WaterFall(int length, int buffSize) {
		super();
		this.length = length;
		this.buffSize = buffSize;
		addMouseListener(this);
		addComponentListener(this);
		buffer = new double[this.length][this.buffSize];
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		for(int i=offset; i<buffSize+offset;i++)
		{
			for(int z=0; z<length; z++)
			{
				if(buffer[z][i%buffSize]==0)
					g.setColor(Color.red);
				else
					g.setColor(Color.green);
				
				if(orientation == Orientation.horizontale){
					g.fillRect(z*rapport, (i-offset)*rapport, rapport, rapport);
				} else {
					g.fillRect((i-offset)*rapport, z*rapport, rapport, rapport);
				}
			}
		}
	}
	
	public void addData(double[] data) {
		int i=0;
		for(; i<length && i<data.length; i++)
			buffer[i][offset]=data[i];
		for(; i<length ; i++)
			buffer[i][offset]=0;
		offset++;
		if(offset>=buffSize)
			offset=0;
		repaint();
	}
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton()==1)
		for(int i=0; i<length; i++)
			buffer[i][offset]=1;
		else
			for(int i=0; i<length; i++)
				buffer[i][offset]=0;
		offset++;
		if(offset>=buffSize)
			offset=0;
		repaint();
		System.out.println("button: "+arg0.getButton());
		
	}
	public void componentResized(ComponentEvent arg0) {
		if(arg0.getComponent().getHeight()<arg0.getComponent().getWidth()) {
			if(orientation == Orientation.horizontale)
			{
				rapport = arg0.getComponent().getHeight()/buffSize;
			}
			else
				rapport = arg0.getComponent().getHeight()/length;
		}
		else {
			if(orientation == Orientation.verticale)
			{
				rapport = arg0.getComponent().getHeight()/buffSize;
			}
			else
				rapport = arg0.getComponent().getHeight()/length;
		}
		
		
	}
	public void componentShown(ComponentEvent arg0) {
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	public void componentHidden(ComponentEvent arg0) {
	}
	public void componentMoved(ComponentEvent arg0) {
	}
}
