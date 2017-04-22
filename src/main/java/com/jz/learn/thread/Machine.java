package com.jz.learn.thread;

public class Machine extends Thread {
	private int i = 0;
	private static int count = 0;
	@Override
	public void run() {
		for (i=0; i < 50; i++) {
			System.out.println(currentThread().getName()+":"+i);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//不要重写start方法
	@Override
	public synchronized void start() {
		super.start();
		//主线程执行下面代码
		System.out.println(currentThread().getName()+"第："+(++count) +"个线程启动");
	}
	
	public static void main(String[] args) {
		Machine machine = new Machine();
		machine.setName("machine");
		machine.start();
		
		Machine machine2 = new Machine();
		machine2.setName("machine2");
		machine2.start();
		
		machine.run();
	}
}
