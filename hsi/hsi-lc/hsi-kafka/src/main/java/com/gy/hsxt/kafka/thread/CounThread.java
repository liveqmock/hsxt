package com.gy.hsxt.kafka.thread;

public class CounThread implements Runnable {
	private String msg;
	int count;
	public CounThread(String msg){
		this.msg = msg;
	}
	@Override
	public void run() {
		System.out.println((count++)+",msg["+msg+"]");

	}

}
