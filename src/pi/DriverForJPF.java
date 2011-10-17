package pi;

import util.Timer;

public class DriverForJPF {
	private static long sequentialRuntime;
	private static final int ITERATIONS = 20000000;
	// update the live value each UPDATE_EACH cicles 
	public static final int UPDATE_EACH = 1000;

	public static void main(String[] args) throws Exception {
		// Live
		pi.live.PiLive live = new pi.live.PiLive();
		test("Live version", live);
		System.out.println("Live value: "+live.liveValue()+"\n");
	}

	
	private static void test(String version, PiApproximation piApproximation) throws Exception {
		// warm-up
		warmup(piApproximation);
		if(sequentialRuntime == 0)
			warmup(piApproximation);
		
		Timer.start();
		double pi = piApproximation.computePi(ITERATIONS);
		Timer.stop();
		System.out.println(version);
		System.out.println("-----------------------------");
		System.out.println("Pi: "+pi);
		Timer.log("Time: ");
		if(sequentialRuntime == 0)
			sequentialRuntime = Timer.getRuntime();
		else
			System.out.printf("Speed-up: %.2f\n",sequentialRuntime/1.0/Timer.getRuntime());
	}

	private static void warmup(PiApproximation piApproximation)
			throws Exception {
		piApproximation.computePi(ITERATIONS);
		piApproximation.computePi(ITERATIONS);
	}
}