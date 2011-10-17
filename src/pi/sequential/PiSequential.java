package pi.sequential;

import java.util.Random;

import pi.PiApproximation;

public class PiSequential implements PiApproximation {

	public double computePi(long iterations) {
		Random rand = new java.util.Random();
		
		long inside = 0;
		for (int j = 0; j < iterations; j++) {
			double x = rand.nextDouble();
			double y = rand.nextDouble();
			double lenght = x * x + y * y;
			if (lenght < 1.0)
				inside++;
		}
		return ((double)inside)/iterations * 4;
	}
}