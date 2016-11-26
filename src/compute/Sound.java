package compute;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Manage Sound
 * @author djeck
 * @version 0.0
 */
public class Sound {
	private AudioFormat format;
    private byte[] samples;
    
    /**
     * Constructor
     * @param fileName sound source path
     */
	public Sound(String fileName){
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
            format = stream.getFormat();
            samples = getSamples(stream);
        }
        catch (UnsupportedAudioFileException e){
            e.printStackTrace();
    }
    catch (IOException e){
            e.printStackTrace();
        }
    }
	
	public AudioFormat getFormat() {
		return format;
	}
	
	
	public byte[] getSamples(AudioInputStream stream){
        int length = (int)(stream.getFrameLength() * format.getFrameSize());
        byte[] samples = new byte[length];
        DataInputStream in = new DataInputStream(stream);
        try{
            in.readFully(samples);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return samples;
    }
	
	public byte[] getSamples(){
        return samples;
    }
	
	/**
	 * 
	 * @param b stereo bit array
	 * @return part of the data corresponding to left
	 */
	public static byte[] leftVoice(byte[] b){
        byte[] resultat = new byte[b.length];
        for(int i = 0 ; i < b.length ; i +=4){
            resultat[i/2] = b[i];
            resultat[i/2+1] = b[i+1];
        }
        return resultat;
    }

	/**
	 * 
	 * @param b stereo bit array
	 * @return part of the data corresponding to right
	 */
    public static byte[] rightVoice(byte[] b){
        byte[] resultat = new byte[b.length];
        for(int i = 0 ; i < b.length ; i +=4){
            resultat[i/2] = b[i+2];
            resultat[i/2+1] = b[i+3];
        }
        return resultat;
    }
    public static double[] toDouble(byte[] b) {
    	double[] doubles = new double[b.length / 3];
    	for (int i = 0, j = 0; i != doubles.length; ++i, j += 3) {
    	  doubles[i] = (double)( (b[j  ] & 0xff) | 
    	                        ((b[j+1] & 0xff) <<  8) |
    	                        ( b[j+2]         << 16));
    	}
    	return doubles;
    }
    
    public static AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
   
}
