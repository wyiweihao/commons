package com.wyiwei.commons.threads;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

/**
 * ThreadLocal类用于储存线程的局部变量，它们希望将状态与某一个线程（例如，用户 ID 或事务 ID）相关联。
 * @author hasee
 *
 */
public class ThreadLocalTest {

	private static ThreadLocal<String> tl = new ThreadLocal<>();

	public void test() {
		test1();
	}

	public void test1() {

		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				String name = "";
				if ("pool-1-thread-1".equals(Thread.currentThread().getName().toString()))
					name = "张1";
				if ("pool-1-thread-2".equals(Thread.currentThread().getName().toString()))
					name = "张2";
				if ("pool-1-thread-3".equals(Thread.currentThread().getName().toString()))
					name = "张3";
				if ("pool-1-thread-4".equals(Thread.currentThread().getName().toString()))
					name = "张4";
				if ("pool-1-thread-5".equals(Thread.currentThread().getName().toString()))
					name = "张5";
					
				if (StringUtils.isEmpty(tl.get())) {
					tl.set(name);
				}
				System.out.println(MessageFormat.format("我是线程[{0}]，我的name是[{1}]", Thread.currentThread().getName(), tl.get()));
			}
		};

		ExecutorService es = Executors.newFixedThreadPool(5);
		for (int i=0; i<50; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			es.execute(run);
		}
		
		try {
			Thread.sleep(60*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		es.shutdown();

	}
}
