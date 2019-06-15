package com.wyiwei.commons;

public class Temp {

	public static Temp TEMP = null;
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed");
		Temp.TEMP = this;
	}
	
	public void isAlive() {
		System.out.println("Yes, I am alive !");
	}
	
	public static void main(String[] args) throws InterruptedException {
		TEMP = new Temp();
		TEMP = null;
		System.gc();
		Thread.sleep(500);
		if (TEMP != null) {
			TEMP.isAlive();
		} else {
			System.out.println("Temp t is dead");
		}
		
		System.out.println("-----------------------------");
		TEMP = null;
		System.gc();
		Thread.sleep(500);
		if (TEMP != null) {
			TEMP.isAlive();
		} else {
			System.out.println("Temp t is dead");
		}
	}
}
