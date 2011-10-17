package pi.threads.sharedRandom;

import java.util.Random;

import pi.PiApproximation;

public class PiThreads implements PiApproximation {

	public double computePi(long iterations) throws InterruptedException {
		int processorCount = Runtime.getRuntime().availableProcessors();
		PiApproximationThread[] threads = new PiApproximationThread[processorCount];

		for (int i = 0; i < processorCount; i++) {
			threads[i] = new PiApproximationThread(iterations/processorCount);
			threads[i].start();
		}

		double pi = 0;
		
		for (int i = 0; i < processorCount; i++) {
			threads[i].join();
			pi += threads[i].result() / processorCount;
		}
		
		return pi;
	}


	static class PiApproximationThread extends Thread {
		private double pi;
		private final long iterations;
		public PiApproximationThread(long iterations) {
			this.iterations = iterations;
		}

		@Override
		public void run() {
			Random rand = new java.util.Random();
			
			long inside = 0;
			for (int j = 0; j < iterations; j++) {
				double x = rand.nextDouble();
				double y = rand.nextDouble();
				double lenght = x * x + y * y;
				if (lenght < 1.0)
					inside++;
			}
			pi = ((double) inside) / iterations * 4;
		}
		
		public double result() {
			return pi;
		}
	}
}
