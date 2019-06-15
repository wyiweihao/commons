package com.wyiwei.commons.shell_redirect;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

// 验证shell命令的重定向及管道
public class ShellRedirect {
	
	public static void main(String[] args) {
		if (args.length == 0) {
			Scanner scanner = new Scanner(System.in);
			int lineNum = 1;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(MessageFormat.format("line {0}: {1}", lineNum, line));
				lineNum++;
			}
			scanner.close();
		} else {
			File file = new File(args[0]);
			if (file.exists() && file.isFile()) {
				List<String> lines = null;
				try {
					lines = FileUtils.readLines(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (int i=0; i<lines.size(); i++) {
					System.out.println(MessageFormat.format("line {0}: {1}", i+1, lines.get(i)));
				}
 			} else {
				System.out.println("ERROR: Please enter a correct file path.");
			}
		}
		
	}
}
