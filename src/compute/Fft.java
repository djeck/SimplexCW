package compute;

/**
 * Class for Fast Fourier Translation (discrete)
 * 
 * @author djeck
 * @version 0.1
 */
public class Fft {
	/**
	 * 
	 * @param s sampled periodic signal, in complex, with s[x][0] the real part
	 * @return signal's FFT
	 */
	public static double[][] fft(double[][] s) {
		int N = s.length; /*signal's length: should be 2**n with n natural */
		if (N == 1)
			return s; /* recursive call's end*/
		int Ndiv2 = N / 2;
		double[][] fs = new double[N][2]; /*f's FFT*/

		/* separate signal, even and odd index */
		double[][] pair = new double[Ndiv2][2];
		double[][] impair = new double[Ndiv2][2];
		for (int k = 0; k < Ndiv2; k++) {
			pair[k] = s[2 * k];
			impair[k] = s[2 * k + 1];
		}

		/* recursive FFT's compute */
		pair = fft(pair);
		impair = fft(impair);

		/* reconstruct */
		for (int k = 0; k < N; k++) {
			fs[k][0] = pair[k % Ndiv2][0] + impair[k % Ndiv2][0]
					* Math.cos(2 * Math.PI * k / N) + impair[k % Ndiv2][1]
					* Math.sin(2 * Math.PI * k / N);
			fs[k][1] = pair[k % Ndiv2][1] + impair[k % Ndiv2][1]
					* Math.cos(2 * Math.PI * k / N) - impair[k % Ndiv2][0]
					* Math.sin(2 * Math.PI * k / N);
		}

		return fs;
	}
	/**
	 * Reverse Fast Fourier Transformation
	 * @param fs Fourier translation, Complex
	 * @return real signal
	 */
	public static double[][] ifft(double[][] fs) {	
	    int N = fs.length; // signal length
	    double[][] s = new double[N][2];

	    /* complex conjugate of fs */
	    for (int k=0; k<N; k++){
		s[k][0]=fs[k][0];
		s[k][1]=-fs[k][1];
	    }
	    
	    s = fft(s);
	    
	    for (int k=0; k<N; k++){
		s[k][0]=s[k][0]/N;
		s[k][1]=-s[k][1]/N;
	    }
	    
	    return s;
	}
	/**
	 * Get frequency corresponding to FFT's index
	 * @param index
	 * @param tabLength array's length
	 * @param samplingRate sampling rate
	 * @return frequency
	 */
	public static int getFrequency(int index,int tabLength, int samplingRate) {
		return samplingRate*index/tabLength;
	}
	public static double[] modulus(double[][] signal) {
		int X = signal.length;
		double sortie[] = new double[X];
		for(int i=0; i < X;i++)
		{
			sortie[i] = signal[i][0]*signal[i][0]+signal[i][1]+signal[i][1];
		}
		return sortie;
	}
	public static double[][] realPart(double[] signal) {
		int X = signal.length;
		double sortie[][] = new double[X][2];
		for(int i=0; i < X;i++)
		{
			sortie[i][0] = signal[i];
			sortie[i][1] = 0;
		}
		return sortie;
	}
}
