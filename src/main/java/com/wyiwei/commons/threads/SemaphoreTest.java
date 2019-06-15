package com.wyiwei.commons.threads;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore类用户多线程对访问量的控制，通过对有限数量的许可的控制，从而控制同时访问量
 * @author hasee
 *
 */
public class SemaphoreTest {

	private Semaphore sem = new Semaphore(0);// 可以为0，但是使用之前必须先释放一个许可

	public void test() {
		sem.release(1);
		test1();
	}

	public void test1() {

		Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					sem.acquire();
					long waitTime = 1000L;
					System.out.println(MessageFormat.format("线程：{0}， 等待{1}毫秒开始...", Thread.currentThread().getName(), waitTime));
					Thread.sleep(waitTime);
					System.out.println(MessageFormat.format("线程：{0}， 等待{1}毫秒结束！", Thread.currentThread().getName(), waitTime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					sem.release();
				}
			}
		};

		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i=0; i<50; i++) {
			es.execute(run);
			System.out.println("执行"+i);
		}
		
		try {
			Thread.sleep(60*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		es.shutdown();

	}
}
