package pi;

import util.Timer;

public class Driver {
	private static long sequentialRuntime;
	private static final int ITERATIONS = 20000000;
	// update the live value each UPDATE_EACH cycles 
	public static final int UPDATE_EACH = 10000;

	public static void main(String[] args) throws Exception {
		
		// Sequential
		PiApproximation sequential = new pi.sequential.PiSequential();
		test("Sequential version", sequential);
		System.out.println();
		
		// Threads
		PiApproximation threads = new pi.threads.PiThreads();
		test("Threads version", threads);
		System.out.println();
		
		// Live
		pi.live.PiLive live = new pi.live.PiLive();
		test("Live version", live);
		System.out.println("     Live Pi: "+live.liveValue()+"\n");
		
		// Live with sync
		pi.live.sync.PiLiveWithSync liveWithSync = new pi.live.sync.PiLiveWithSync();
		test("Live version with sync", liveWithSync);
		System.out.println("     Live Pi: "+liveWithSync.liveValue()+"\n");
		
		// Live with atomic
		pi.live.atomic.PiLiveWithAtomic liveWithAtomic = new pi.live.atomic.PiLiveWithAtomic();
		test("Live version with atomic", liveWithAtomic);
		System.out.println("     Live Pi: "+liveWithAtomic.liveValue()+"\n");
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
		Timer.log("Time: ");
		if(sequentialRuntime == 0)
			sequentialRuntime = Timer.getRuntime();
		else
			System.out.printf("Speed-up: %.2f\n",sequentialRuntime/1.0/Timer.getRuntime());
		System.out.println("     Real Pi: "+3.141592653589793238462643383279D);
		System.out.println("Estimated Pi: "+pi);
	}

	private static void warmup(PiApproximation piApproximation)
			throws Exception {
		piApproximation.computePi(ITERATIONS);
		piApproximation.computePi(ITERATIONS);
	}
}