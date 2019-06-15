package com.wyiwei.commons.zzzplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class Shuangsq {

	public static void main(String[] args) {
		System.out.println("\n\n\n");
		for (int j=0; j<2; j++) {
			List<String> reds = new ArrayList<>();
			Random random = new Random();
			while (true) {
				String red = StringUtils.leftPad(1+random.nextInt(33)+"", 2, '0');
				if (!reds.contains(red)) {
					reds.add(red);
					if (reds.size() == 6) break;
				}
			}
			String blue = StringUtils.leftPad(1+random.nextInt(16)+"", 2, '0');
			String[] redArr = reds.toArray(new String[reds.size()]);
			Arrays.sort(redArr);
			String result = "";
			for (int i=0; i<redArr.length - 1; i++) {
				result = result.concat(redArr[i]+", ");
			}
			result = result.concat(redArr[redArr.length-1]).concat(" + ").concat(blue);
			System.out.println(result);
		}
	}
}
