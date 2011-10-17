package pi.live;

import java.util.Random;

import pi.Driver;
import pi.PiApproximation;

public class PiLive implements PiApproximation {

	long globalInside, globalTotal;

	public double computePi(long iterations) throws InterruptedException {
		int noProcessors = Runtime.getRuntime().availableProcessors();
		PiApproximationThread[] threads = new PiApproximationThread[noProcessors];

		for (int i = 0; i < noProcessors; i++) {
			threads[i] = new PiApproximationThread(iterations / noProcessors);
			threads[i].start();
		}

		double pi = 0;

		for (int i = 0; i < noProcessors; i++) {
			threads[i].join();
			pi += threads[i].result() / noProcessors;
		}

		return pi;
	}

	public double liveValue() {
		return globalInside * 4.0 / globalTotal;
	}

	class PiApproximationThread extends Thread {
		private double pi;
		private final long iterations;

		public PiApproximationThread(long iterations) {
			this.iterations = iterations;
		}

		@Override
		public void run() {
			Random rand = new java.util.Random();

			long inside = 0;
			for (int i = 0; i < iterations / Driver.UPDATE_EACH; i++) {
				int insideJ = 0;
				for (int j = 0; j < Driver.UPDATE_EACH; j++) {
					double x = rand.nextDouble();
					double y = rand.nextDouble();
					double lenght = x * x + y * y;
					if (lenght < 1.0) {
						insideJ++;
					}
				}
				inside += insideJ;
				globalInside += insideJ;
				globalTotal += Driver.UPDATE_EACH;
			}
			pi = inside * 4.0 / iterations;
		}

		public double result() {
			return pi;
		}
	}
}
