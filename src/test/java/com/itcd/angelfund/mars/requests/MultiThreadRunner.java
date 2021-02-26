package com.itcd.angelfund.mars.requests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRunner {
	
	MyRunnable[] myRunnables;
	
	public MultiThreadRunner(MyRunnable[] myRunnables) {
		this.myRunnables = myRunnables;
	}
	
	public void run() throws InterruptedException {
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		for (MyRunnable myRunnable : myRunnables) {
			threadPool.execute(myRunnable);
		}
	}
}
