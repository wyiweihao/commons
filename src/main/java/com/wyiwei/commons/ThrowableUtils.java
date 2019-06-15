package com.wyiwei.commons;

public class ThrowableUtils {
	
	public static void handleDefuThrowable(Throwable e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	
}
