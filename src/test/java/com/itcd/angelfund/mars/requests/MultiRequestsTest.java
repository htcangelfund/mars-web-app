package com.itcd.angelfund.mars.requests;

import org.junit.jupiter.api.Test;

public class MultiRequestsTest {
	
	@Test
	public void run() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		int count = 200;
		MyRunnable runner = new MyRunnable(count);
		MyRunnable[] myRunnables = new MyRunnable[count];
		for(int i = 0; i < count; i++) {
			myRunnables[i] = runner;
		}
		MultiThreadRunner multiThreadRunner = new MultiThreadRunner(myRunnables);
		multiThreadRunner.run();
		runner.countDownLatch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("The Application Completed About "+(endTime-startTime)/1000+"s");
	}
	
}
