package com.jz.learn.thread;

public class Sample {
	private int a;
	public int method(){
		int b = 0;
		a++;
		b=a;
		return b;
		// 局部变量b结束生命周期
	}
	public static void main(String[] args) {
		Sample sample = null;
		int a = 0;
		
		sample = new Sample();
		a = sample.method();
		System.out.println(a);
	}
}
