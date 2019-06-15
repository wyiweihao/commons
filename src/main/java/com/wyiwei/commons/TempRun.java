package com.wyiwei.commons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TempRun {
	
	public static void main(String[] args) throws IOException {
		String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		String path = "D:\\Java\\workspace\\my\\Practice_WorkSpace\\aa_commons\\src\\main\\java\\com\\wyiwei\\aa_commons\\temp.dat";
		List<String> list = new ArrayList<>();
		for (int i=1; i<=1000000; i++) {
			String s2 = str + i;
			list.add(s2);
			if (list.size() == 10000) {
				FileUtils.writeLines(new File(path), "UTF-8", list, true);
				list.clear();
			}
		}
		FileUtils.writeLines(new File(path), "UTF-8", list, true);
		System.out.println("done");
	}
}
