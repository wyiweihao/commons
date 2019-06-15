package com.wyiwei.commons.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wyiwei.commons.ThrowableUtils;

/**
 * 
 * @author arryluo
 */
public class ExcelUtils {

	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";

	public static Workbook getWorkbook(String filePath) {
		File file = new File(filePath);
		Workbook workbook = null;
		try {
			if (checkFile(file) == true) {
				// 后缀是xlsx
				workbook = new XSSFWorkbook(new FileInputStream(file));
			} else if (checkFile(file) == false) {
				// 后缀是xls
				workbook = new HSSFWorkbook(new FileInputStream(file));
			}
		} catch (FileNotFoundException e) {
			ThrowableUtils.handleDefuThrowable(e);
		} catch (IOException e) {
			ThrowableUtils.handleDefuThrowable(e);
		}
		return workbook;
	}

	public static int getRowCount(Workbook workbook, int sheetNum) {
		sheetNum--;
		return workbook.getSheetAt(sheetNum).getLastRowNum() + 1;
	}
	
	public static int getColCount(Workbook workbook, int sheetNum, int rowNum) {
		sheetNum--;
		rowNum--;
		return workbook.getSheetAt(sheetNum).getRow(rowNum).getLastCellNum();
	}
	
	public static String getCellValue(Workbook workbook, int sheetNum, int rowNum, int colNum) {
		sheetNum--;
		rowNum--;
		colNum--;
		Sheet xssfSheet = workbook.getSheetAt(sheetNum);
		// 循环行Row
		Row xssfRow = xssfSheet.getRow(rowNum);
		if (xssfRow == null) {
			return null;
		}
		Cell xssfCell = xssfRow.getCell(colNum);
		if (xssfCell == null) {
			return null;
		}
		return getValue(xssfCell, workbook);
	}

	// 检查该文件是否是xls，还是xlsx类型
	private static Boolean checkFile(File file) {
		Boolean flg = null;
		if (file.exists()) {
			String name = file.getName();
			if (name.endsWith(EXCEL_XLSX))
				flg = true;
			else if (name.endsWith(EXCEL_XLS))
				flg = false;
			else 
				throw new RuntimeException("不支持的文件类型");
		} else {
			System.out.println("文件不存在");
		}
		return flg;
	}

	// 将他们两者的父类抽取出来，这样减少代码量，同时便于管理
	private static String getValue(Cell xssfCell, Workbook workbook) {
		String value = "";
		switch (xssfCell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = String.valueOf(xssfCell.getRichStringCellValue()
					.getString());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(xssfCell)) {
				value = String.valueOf(String.valueOf(xssfCell
						.getDateCellValue()));
			} else {
				value = String.valueOf(xssfCell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(xssfCell.getBooleanCellValue());
			break;
			// 公式,
		case Cell.CELL_TYPE_FORMULA:
			// 获取Excel中用公式获取到的值,//=SUM(P4-Q4-R4-S4)Excel用这种类似的公式计算出来的值用POI无法获取，要想获取的话，就必须一下操作
			FormulaEvaluator evaluator = workbook.getCreationHelper()
			.createFormulaEvaluator();
			evaluator.evaluateFormulaCell(xssfCell);
			CellValue cellValue = evaluator.evaluate(xssfCell);
			value = String.valueOf(cellValue.getNumberValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = String.valueOf(xssfCell.getErrorCellValue());
			break;
		default:
		}
		return value;
	}
	
	public static void main(String[] args) {
		String path = "C:\\Users\\hasee\\Desktop\\123456.xls";
//		String path = "C:\\Users\\hasee\\Desktop\\工作日志2018.xlsx";
		Workbook workbook = getWorkbook(path);
		String value = getCellValue(workbook, 1, 9, 2);
		System.out.println(value);
	}
}