package com.wyiwei.commons.aa_test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class GenSolrFile1000w {

	public static void main(String[] args) throws IOException {
		genFile();
	}
	
	public static void genFile() throws IOException {
		System.out.println("开始...");
		long start = System.currentTimeMillis();
		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\H709埋数\\solr-h709-1000w.txt";
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		int totalRows = 10000000;
		int cacheSize = 10000;
		List<String> lines = new ArrayList<>();
		for (int i=1; i<=totalRows; i++) {
			StringBuilder builder = new StringBuilder();
			String h_rowkey = "TDRK"+StringUtils.leftPad(""+i, 10, '0');
			String txn_cfm_dt = "2018-01-01T00:00:00Z";
			String scr_txn_mkt_id = "053";
			String cfm_pcsg_txnsrlno = "SRLNO"+StringUtils.leftPad(""+i, 10, '0');
			int txn_cfm_sn = i;
			String fnd_aply_tpcd = "143";
			String fnd_txn_aply_dt = "2018-01-01T00:00:00Z";
			String scr_pd_ecd = "000693";
			String scr_txn_br_id = "110000000";
			String tfr_sign_accno = "7217000110011";
			String scr_txn_accno = "90121530001";
			builder.append(h_rowkey).append(",")
					.append(txn_cfm_dt).append(",")
					.append(scr_txn_mkt_id).append(",")
					.append(cfm_pcsg_txnsrlno).append(",")
					.append(txn_cfm_sn).append(",")
					.append(fnd_aply_tpcd).append(",")
					.append(fnd_txn_aply_dt).append(",")
					.append(scr_pd_ecd).append(",")
					.append(scr_txn_br_id).append(",")
					.append(tfr_sign_accno).append(",")
					.append(scr_txn_accno);
			lines.add(builder.toString());
			if (i % cacheSize == 0) {
				FileUtils.writeLines(new File(filePath), "UTF-8", lines, true);
				lines.clear();
			}
		}
		FileUtils.writeLines(new File(filePath), "UTF-8", lines, true);
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end - start) + "ms");
		System.out.println("done!");
	}
}
