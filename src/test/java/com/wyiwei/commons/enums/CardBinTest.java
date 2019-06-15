package com.wyiwei.commons.enums;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class CardBinTest {
	
	public static void main(String[] args) throws Exception {
//		readCardBin();
//		test();
		test2();
	}
	
	public static void readCardBin() throws IOException {
		List<String> list = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\cardBin1.txt"));
		List<String> list2 = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\cardBin2.txt"));
		Map<String, String[]> cardBins = new HashMap<String, String[]>();
		for (String str : list) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			// 0-行号，1-行公司名，2-行名，3-空格，4-卡例，5-卡长度，6-卡bin长度，7-卡bin，7,8,9,10,11
			String[] fields = str.split(",");
			String[] freshs = new String[4];
			freshs[0] = fields[0];//行号
			freshs[1] = fields[2];//行名
			freshs[2] = fields[6];//卡bin长度
			freshs[3] = fields[7];//卡bin
			if (!cardBins.containsKey(freshs[3])) {
				cardBins.put(freshs[3], freshs);
			}
			
		}
		
		for (String str : list2) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			// 0-行号，1-行公司名，2-行名，3-卡例，4-卡长度，5-卡bin长度，6-卡bin，7,8,9,10,11
			String[] fields = str.split(",");
			String[] freshs = new String[4];
			freshs[0] = fields[0];//行号
			freshs[1] = fields[2];//行名
			freshs[2] = fields[5];//卡bin长度
			freshs[3] = fields[6];//卡bin
			if (!cardBins.containsKey(freshs[3])) {
				cardBins.put(freshs[3], freshs);
			}
		}
		
		List<String> cardNos = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\cardNos.txt"));
//		System.out.println(1);
		for (String cardNo : cardNos) {
			if (StringUtils.isBlank(cardNo)) {
				continue;
			}
			boolean flag = true;
			for (String[] fields : cardBins.values()) {
				String cardBin = cardNo.substring(0, new Integer(fields[2]));
				if (cardNo.startsWith(fields[3])) {
					flag = false;
					System.out.println(cardNo+"	"+cardBin+"	"+fields[0]+"	"+fields[1]);
					break;
				}
				
			}
			// 打印不存在卡bin的卡号
			if (flag) {
//				System.out.println(cardNo);
			}
		}
		
	}	
	
	
	public static void test() {
//		String temp = "1|2|3||4|5||";
//		String[] arr = temp.split("\\|", -1);
//		System.out.println(arr);
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		System.out.println(uuid.substring(0, 28));
	}
		
	public static void test2() throws UnsupportedEncodingException {
		String str = "我不高兴";
		System.out.println("gaga:"+str.getBytes("GBK").length);
	}
}
