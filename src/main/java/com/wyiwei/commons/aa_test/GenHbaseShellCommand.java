package com.wyiwei.commons.aa_test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.wyiwei.commons.excel.ExcelUtils;

public class GenHbaseShellCommand {
	
	public static void main(String[] args) throws Exception {
		System.out.println("开始...");
		
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\埋数模板Y.xlsx";
//		genHbaseCommandFromExcelY(filePath, commandFilePath);
		
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\分期通\\20181112\\分期通20181112.xlsx";
		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\H709埋数\\20181115\\H709埋数01.xlsx";
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\产品余额信息\\产品余额信息-理财20181107.xlsx";
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\产品余额信息\\产品信息参数-理财20181107.xlsx";
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\产品余额信息\\智能投顾\\20181109\\产品信息参数-智能投顾20181109.xlsx";
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\产品余额信息\\智能投顾\\20181109\\产品余额信息-智能投顾20181109.xlsx";
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\产品余额信息\\理财\\20181109\\产品余额信息-理财20181109.xlsx";
//		String filePath = "D:\\CCB文档Temp\\CCB测试\\埋数\\产品余额信息\\理财\\20181112\\产品信息参数-理财20181112.xlsx";
		String commandFilePath = filePath.substring(0, filePath.lastIndexOf("."))+".hbase";
//		genHbaseCommandFromExcelX(filePath, commandFilePath);
		genHbaseCommandFromExcelY(filePath, commandFilePath);
	}
	
	/**
	 * 
	 * @param filePath
	 * @param commandFilePath
	 * @throws Exception
	 */
	public static void genHbaseCommandFromExcelY(String filePath, String commandFilePath) throws Exception{
		File commandFile = new File(commandFilePath);
		if (commandFile.exists()) {
			commandFile.delete();
		}
		int dataSheetNum = 2;
		int settingSheetNum = 1;
		int cellNameColNum = 1;
		int cellTypeColNum = 3;
		Workbook workbook = ExcelUtils.getWorkbook(filePath);
		String tableName = ExcelUtils.getCellValue(workbook, settingSheetNum, 2, 4);
		int rowCount = ExcelUtils.getRowCount(workbook, dataSheetNum);
		int colCount = ExcelUtils.getColCount(workbook, dataSheetNum, 1);
		String filterFlag = ExcelUtils.getCellValue(workbook, settingSheetNum, 2, 3);
		
		// 获取拼接rowkey的字段
		int settingSheetRowCount = ExcelUtils.getRowCount(workbook, settingSheetNum);
		List<String> rowkeyFieldList = new ArrayList<>();
		for (int i=2; i<=settingSheetRowCount; i++) {
			String fieldName = ExcelUtils.getCellValue(workbook, settingSheetNum, i, 1);
			if (StringUtils.isBlank(fieldName)) {
				continue;
			}
			rowkeyFieldList.add(fieldName);
		}
		
		// 需要过滤的字段名字
		List<String> filterFieldList = new ArrayList<>();
		for (int i=2; i<=settingSheetRowCount; i++) {
			String fieldName = ExcelUtils.getCellValue(workbook, settingSheetNum, i, 2);
			if (StringUtils.isBlank(fieldName)) {
				continue;
			}
			filterFieldList.add(fieldName);
		}
		
		// 拼装Hbase shell 命令
		for (int c=4; c<=colCount; c++) {
			Map<String, String> rowkeyFieldNameValueMap = new HashMap<String, String>();
			List<String> commandList = new ArrayList<>();
			for (int r=1; r<=rowCount; r++) {
				String cellName = ExcelUtils.getCellValue(workbook, dataSheetNum, r, cellNameColNum);
				String cellValue = ExcelUtils.getCellValue(workbook, dataSheetNum, r, c);
				rowkeyFieldNameValueMap.put(cellName, cellValue);
			}
			for (int r=1; r<=rowCount; r++) {
				String cellName = ExcelUtils.getCellValue(workbook, dataSheetNum, r, cellNameColNum);
				String cellType = ExcelUtils.getCellValue(workbook, dataSheetNum, r, cellTypeColNum);
				String cellValue = ExcelUtils.getCellValue(workbook, dataSheetNum, r, c);
				String rowkey = buildRowkey(rowkeyFieldList, rowkeyFieldNameValueMap);
				String hbaseCommand = buildHbaseCommand(tableName, rowkey, cellName, cellValue, cellType);
				if (filterFlag.equals("F1")) {
					if (filterFieldList.contains(cellName)) {
						commandList.add(hbaseCommand);
					}
				} else {
					commandList.add(hbaseCommand);
				}
			}
			commandList.add("");
			FileUtils.writeLines(commandFile, "UTF-8", commandList, true);
		}
		
		System.out.println("Gen hbase-shell command done!");
	}
	
	/**
	 * 
	 * @param filePath
	 * @param commandFilePath
	 * @throws Exception
	 */
	public static void genHbaseCommandFromExcelX(String filePath, String commandFilePath) throws Exception{
		File commandFile = new File(commandFilePath);
		if (commandFile.exists()) {
			commandFile.delete();
		}
		int dataSheetNum = 2;
		int settingSheetNum = 1;
		int cellNameRowNum = 1;
		int cellTypeRowNum = 3;
		Workbook workbook = ExcelUtils.getWorkbook(filePath);
		String tableName = ExcelUtils.getCellValue(workbook, settingSheetNum, 2, 4);
		int rowCount = ExcelUtils.getRowCount(workbook, dataSheetNum);
		int colCount = ExcelUtils.getColCount(workbook, dataSheetNum, 1);
		boolean isFilterFields = ExcelUtils.getCellValue(workbook, settingSheetNum, 2, 3).equals("1")?true:false;
		
		// 获取拼接rowkey的字段
		int settingSheetRowCount = ExcelUtils.getRowCount(workbook, settingSheetNum);
		List<String> rowkeyFieldList = new ArrayList<>();
		for (int i=2; i<=settingSheetRowCount; i++) {
			String fieldName = ExcelUtils.getCellValue(workbook, settingSheetNum, i, 1);
			if (StringUtils.isBlank(fieldName)) {
				continue;
			}
			rowkeyFieldList.add(fieldName);
		}
		
		// 需要过滤的字段名字
		List<String> filterFieldList = new ArrayList<>();
		for (int i=2; i<=settingSheetRowCount; i++) {
			String fieldName = ExcelUtils.getCellValue(workbook, settingSheetNum, i, 2);
			if (StringUtils.isBlank(fieldName)) {
				continue;
			}
			filterFieldList.add(fieldName);
		}
		
		// 拼装Hbase shell 命令
		for (int r=4; r<=rowCount; r++) {
			Map<String, String> rowkeyFieldNameValueMap = new HashMap<String, String>();
			List<String> commandList = new ArrayList<>();
			for (int c=1; c<=colCount; c++) {
				String cellName = ExcelUtils.getCellValue(workbook, dataSheetNum, cellNameRowNum, c);
				String cellValue = ExcelUtils.getCellValue(workbook, dataSheetNum, r, c);
				rowkeyFieldNameValueMap.put(cellName, cellValue);
			}
			for (int c=1; c<=colCount; c++) {
				String cellName = ExcelUtils.getCellValue(workbook, dataSheetNum, cellNameRowNum, c);
				String cellType = ExcelUtils.getCellValue(workbook, dataSheetNum, cellTypeRowNum, c);
				String cellValue = ExcelUtils.getCellValue(workbook, dataSheetNum, r, c);
				String rowkey = buildRowkey(rowkeyFieldList, rowkeyFieldNameValueMap);
				String hbaseCommand = buildHbaseCommand(tableName, rowkey, cellName, cellValue, cellType);
				if (isFilterFields) {
					if (filterFieldList.contains(cellName)) {
						commandList.add(hbaseCommand);
					}
				} else {
					commandList.add(hbaseCommand);
				}
			}
			commandList.add("");
			FileUtils.writeLines(commandFile, "UTF-8", commandList, true);
		}
		
		System.out.println("Gen hbase-shell command done!");
	}
	
	/**
	 * 
	 * @param rowkeyFieldList
	 * @param rowkeyFieldNameValueMap
	 * @return
	 */
	private static String buildRowkey(List<String> rowkeyFieldList, Map<String, String> rowkeyFieldNameValueMap) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<rowkeyFieldList.size(); i++) {
			String fieldName = rowkeyFieldList.get(i);
			if (!rowkeyFieldNameValueMap.containsKey(fieldName)) {
				throw new RuntimeException("Wrong field name: "+fieldName);
			}
			if (i == (rowkeyFieldList.size()-1)) {
				builder.append(rowkeyFieldNameValueMap.get(fieldName));
			} else {
				builder.append(rowkeyFieldNameValueMap.get(fieldName)).append("|");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @param tableName
	 * @param rowkey
	 * @param cellName
	 * @param cellValue
	 * @param cellType
	 * @return
	 */
	private static String buildHbaseCommand(String tableName, String rowkey, String cellName,
											String cellValue, String cellType) {
		if ("string".equals(cellType.toLowerCase()) || "yyyymmdd".equals(cellType.toLowerCase())
			|| "long".equals(cellType.toLowerCase()) || cellType.toLowerCase().startsWith("timestamp")) {
			if (StringUtils.isBlank(cellValue)) {
				cellValue = "'"+cellName+"999'";
			} else {
				cellValue = "'"+cellValue+"'";
			}
		} else if ("double".equals(cellType.toLowerCase())) {
			if (StringUtils.isBlank(cellValue)) {
				cellValue = "[0.00].pack('G')";
			} else {
				cellValue = "["+cellValue.trim()+"].pack('G')";
			}
		} else {
			throw new RuntimeException("UNSUPPORTED DATA TYPE !!!!!");
		}
		String command = "put '"+tableName+"', '"+rowkey.trim()+"', 'cf:"+cellName+"', "+cellValue;
		return command;
	}
}
