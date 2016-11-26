package compute;

/**
 * Classe de demo et de test
 * 
 * @author djeck
 * @version 0.0
 */
public class Exemple {
	public static double[] simpleCos(int freq, int N) {
		double[] signal = new double[N];

		for (int i = 0; i < N; i++) {
			signal[i] = (double)Math.sin(freq*2*i*Math.PI/N);// creation de la sinusoide
		}

		return signal;
	}
	
}
