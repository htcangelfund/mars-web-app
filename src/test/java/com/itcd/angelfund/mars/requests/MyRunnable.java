package com.itcd.angelfund.mars.requests;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class MyRunnable implements Runnable {

	public static CountDownLatch countDownLatch;
	private int NUM = 0;
	private String URL = "http://139.224.70.36:8443/rooms/status";
	
	public MyRunnable(int count) {
		this.countDownLatch = new CountDownLatch(count);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new HttpClient();
		httpClient.setTimeout(60000000);
		GetMethod getMethod = new GetMethod(URL);
		try {
			long startTime = System.currentTimeMillis();
			httpClient.executeMethod(getMethod);
			long endTime = System.currentTimeMillis();
			NUM++;
			System.out.println("Thread-"+NUM+" spend "+(endTime-startTime)/1000+"s");
			System.out.println("Http return status: " + getMethod.getStatusLine());
			String responseBodyAsString = getMethod.getResponseBodyAsString();
			System.out.println("Server return body: " + responseBodyAsString);
			getMethod.releaseConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			countDownLatch.countDown();
			System.out.println(countDownLatch.getCount());
		}
	}

}
