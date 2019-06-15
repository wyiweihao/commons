package com.wyiwei.commons.aa_test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.wyiwei.commons.ListSorts;
import com.wyiwei.commons.file_operate.FileUtils;

public class CaiStatisticsUtils {

	private static Map<Integer, Integer> RED_MAP = new HashMap<>();
	private static Map<Integer, Integer> BLUE_MAP = new HashMap<>();
	private static String RECORDS_FILE_PATH = "D:\\workspace\\workspace-my\\aa_commons\\src\\main\\resources\\cai_statistics.data";

	public static void main(String[] args) throws IOException {
		CaiStatisticsUtils utils = new CaiStatisticsUtils();
		utils.init();
		utils.readRecords();
		List<Integer> redBalls = utils.getSmallerRateRedBalls();
		Integer blueBall = utils.getSmallerRateBlueBalls();
		utils.printResult(redBalls, blueBall);
	}

	public void init() {
		for (int i=1; i<=33; i++) {
			RED_MAP.put(i, 0);
		}
		for (int i=1; i<=16; i++) {
			BLUE_MAP.put(i, 0);
		}
	}

	public void readRecords() throws IOException {
		List<String> records = FileUtils.readLines(new File(RECORDS_FILE_PATH), "UTF-8");
		int allTime = 0;
		for (String record : records) {
			if (StringUtils.isBlank(record)) {
				continue;
			}
			String[] arr = record.split(",");
			for (int i=0; i<6; i++) {
				int tmpValu = RED_MAP.get(Integer.valueOf(arr[i].trim())).intValue();
				tmpValu++;
				RED_MAP.put(Integer.valueOf(arr[i].trim()), tmpValu);
				allTime++;
			}
			int tmpValu = BLUE_MAP.get(Integer.valueOf(arr[6].trim())).intValue();
			tmpValu++;
			BLUE_MAP.put(Integer.valueOf(arr[6].trim()), tmpValu);
		}
		for (Integer key : RED_MAP.keySet()) {
			System.out.println(key+": "+new BigDecimal(RED_MAP.get(key)).divide(new BigDecimal(allTime), 20, RoundingMode.HALF_UP));
		}
	}

	public List<Integer> getSmallerRateRedBalls(){
		List<Integer> result = new ArrayList<>();
		for (int i=0; i<6; i++) {
			Set<Map.Entry<Integer, Integer>> entrySet = RED_MAP.entrySet();
			List<Map.Entry<Integer, Integer>> entries = new ArrayList<>();
			for (Map.Entry<Integer, Integer> entry : entrySet) {
				entries.add(entry);
			}
			Integer smallestVal = null;
			Integer smallestKey = null;
			for (int j=0; j<entries.size()-1; j++) {
				Integer key = entries.get(j).getKey();
				Integer v = RED_MAP.get(key);
				if (j==0) {
					smallestKey = key;
					smallestVal = v;
				}
				smallestKey = v.compareTo(smallestVal)<=0?key:smallestKey;
				smallestVal = v.compareTo(smallestVal)<=0?v:smallestVal;
			}
			result.add(smallestKey);
			RED_MAP.remove(smallestKey);
		}
		return result;
	}

	public Integer getSmallerRateBlueBalls(){
		Set<Map.Entry<Integer, Integer>> entrySet = BLUE_MAP.entrySet();
		List<Map.Entry<Integer, Integer>> entries = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : entrySet) {
			entries.add(entry);
		}
		Integer smallestVal = null;
		Integer smallestKey = null;
		for (int j=0; j<entries.size()-1; j++) {
			Integer key = entries.get(j).getKey();
			Integer v = BLUE_MAP.get(key);
			if (j==0) {
				smallestKey = key;
				smallestVal = v;
			}
			smallestKey = v.compareTo(smallestVal)<=0?key:smallestKey;
			smallestVal = v.compareTo(smallestVal)<=0?v:smallestVal;
		}
		return smallestKey;
	}

	public void printResult(List<Integer> redBalls, Integer blueBall) {
		ListSorts.sort(redBalls, false, true, "value");
		System.out.println(redBalls+" + "+blueBall);
	}
}
