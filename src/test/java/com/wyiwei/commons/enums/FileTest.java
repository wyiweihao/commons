package com.wyiwei.commons.enums;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class FileTest {
	public static void main(String[] args) throws IOException {
		uuid1();
//		uuid2();
//		testLongSql();
//		writeLinesToFile();
//		testddd();
	}
	
	public static void uuid1() throws IOException {
		List<String> list = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\rqUID1.txt"));
		List<String> newlist = new ArrayList<>();
//		List<String> list2 = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\rqUID2.txt"));
//		List<String> list2 = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\batchfile.txt"));
//		List<String> newlist2 = new ArrayList<>();
		for (String line : list) {
			line = line.trim();
//			line = "SC" + line.substring(2);
			if (StringUtils.isNotBlank(line)) {
				newlist.add(line);
//				System.out.println(line);
			}
		}
		
//		Map<String, String> map = new HashMap<String, String>();
//		for (String line : newlist) {
//			if (map.containsKey(line)) {
//				System.out.println(line);
//			}
//			map.put(line, line);
//			
//		}
//		for (String line : list2) {
//			line = line.trim();
//			if (StringUtils.isNotBlank(line)) {
//				newlist2.add(line);
//			}
//		}
//		
//		for (String line : newlist) {
//			if (!newlist2.contains(line)) {
//				System.out.println(line);
//			}
//		}
		
		StringBuilder sql = new StringBuilder();
		int num = 0;
		for (String line : newlist) {
			if (StringUtils.isBlank(line.trim())) {
				continue;
			}
			
			if (num > 0 && num%2 == 0) {
				sql.append("\n");
			}
			if (num+1==list.size()) {
				sql.append("'").append(line.trim()).append("'");
			}else {
				sql.append("'").append(line.trim()).append("', ");
			}
			num++;
		}
		System.out.println(sql);
	}
	
	public static void uuid2() throws IOException {
		List<String> list = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\notify.txt"));
		List<String> newlist = new ArrayList<>();
		for (String line : list) {
			line = line.trim();
			if (StringUtils.isNotBlank(line)) {
				newlist.add(line);
			}
		}
		
		for (String line : list) {
			if (StringUtils.isBlank(line.trim())) {
				continue;
			}
//			String flag = "feeBaseLogId";
//			String flag = "dealCode";
//			String flag = "dealMsg";
			
//			String flag = "OrigChannelSerNo";
//			String flag = "CenterDealCode";
			String flag = "CenterDealMsg";
			
//			String flag = "StatusCode";
//			String flag = "ServerStatusCode";
//			String flag = "BindCardNo";
//			String flag = "NewCardNo";
			
			if (line.contains(flag)) {
				int index1 = line.indexOf(flag) + 1 + flag.length();
				int index2 = line.lastIndexOf("</"+flag);
				System.out.println(line.substring(index1, index2));
			}
		}
	}
	
	public static void testLongSql() {
		String sql = "select * from table where id in ({0})";
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		while (true) {
			buffer.append("'").append(UUID.randomUUID()).append("', ");
			i++;
			String sql2 = MessageFormat.format(sql, buffer.toString());
			if (sql2.getBytes().length > 64 * 1024) {
				System.out.println(i);
				break;
			}
		}
	}
	
	public static void writeLinesToFile() throws IOException {
		List<String> list = FileUtils.readLines(new File("C:\\Users\\hasee\\Desktop\\sql.txt"));
		List<String[]> fieldsList = new ArrayList<>();
		List<String> sqlList = new ArrayList<>();
		List<String> repeatList = new ArrayList<>();
		
		for (String line : list) {
			line = line.trim();
			if (StringUtils.isNotBlank(line)) {
				String[] fields = new String[3];
				String[] details = line.split("	");
				int i = 0;
				for (String detail : details) {
					if (StringUtils.isNotBlank(detail)) {
						fields[i] = detail.trim();
						if (i==0) {
							if (repeatList.contains(detail.trim())) {
								System.out.println(detail.trim());
							} else {
								repeatList.add(detail.trim());
							}
						}
						i++;
						if (i==3) {
							break;
						}
					}
				}
				fieldsList.add(fields);
			}
		}
		
		
//		for (String[] detail : fieldsList) {
//			String sql = "INSERT INTO PAYOUT_SYS_ERROR_CODE_INFO (OTHER_CHANNEL_ID, OTHER_ERROR_CODE, OTHER_ERROR_MSG, ERROR_CODE, ERROR_MSG, ERROR_TYPE, CREATE_TIME, CREATE_OPERATOR, LAST_UPDATE_TIME, LAST_UPDATE_OPERATOR) VALUES ('SUPERNET', '"+detail[0]+"', '"+detail[1]+"', null, null, '"+detail[2]+"', TIMESTAMP '2017-12-26 17:04:40', null, TIMESTAMP '2017-12-26 17:04:40', null);"; 
//			sqlList.add(sql);
//		}
//		FileUtils.writeLines(new File("C:\\Users\\hasee\\Desktop\\supernetErrorCode.sql"), sqlList);
	}
	
	public static void testddd() {
		System.out.println(new Date(9223372036854775807L));
	}
	
}
