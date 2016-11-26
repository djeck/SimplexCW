package render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import compute.Fft;
import compute.Sound;

/**
 * @author djeck
 *
 */
public class ReceiverWindow extends JFrame{
	private static final long serialVersionUID = -4387118746713650656L;
	
	private double fft[] = new double[1024];
	private JLabel container;
	private Graphe fig;
	private WaterFall water;
	
	
	public ReceiverWindow() {
		setTitle("SimplexCW");
		
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		container = new JLabel();
		container.setBackground(Color.white);
		container.setLayout(new GridLayout(2,1));
		
		fig = new Graphe(fft, 44100);
		container.add(fig);
		
		water = new WaterFall(44100);
		container.add(water);
		
		container.setVisible(true);
		getContentPane().add(container, BorderLayout.CENTER);
		setVisible(true);
	}
	public void simpleDemo() {
		System.out.println("start");
		
		Sound player = new Sound("1000hz.wav");
        byte[] voix = player.getSamples();
        double tab[] = Sound.toDouble(voix);
        
		fft = Fft.modulus(Fft.fft(Fft.realPart(tab)));
		int max = 0;
		for(int i = 0; i<fft.length;i++)
			if(fft[max]>fft[i])
				max=i;
		
		water.addData(fft);
		fig.resetData(fft);
		System.out.println("Frame rate: "+player.getFormat().getFrameRate());
		System.out.println("Frame size: "+player.getFormat().getFrameSize());
		System.out.println("Frame sample size: "+player.getFormat().getSampleSizeInBits()+"bits");
		System.out.println("Frequency: "+Fft.getFrequency(max,tab.length,(int)player.getFormat().getFrameRate())+"("+max+"/"+tab.length+")");
	}
}
