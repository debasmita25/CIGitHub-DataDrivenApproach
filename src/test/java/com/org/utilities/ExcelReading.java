package com.org.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.EncryptedDocumentException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReading {

	public String path;
	public File file = null;
	public static FileInputStream fis = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;

	public ExcelReading(String path)  {
		this.path = path;
		file = new File(path);
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				workbook = new XSSFWorkbook(fis);
			} catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	/*
	 * Sheet sheet = workbook.getSheetAt(0); int index =
	 * workbook.getSheetIndex(sheet.getSheetName());
	 * System.out.println("Sheet index " + index); // Sheet index 0
	 * System.out.println("Sheet name " + sheet.getSheetName()); // Sheet name
	 * Sheet1
	 * 
	 * if (index == -1) { System.out.println("No sheet present"); }
	 * 
	 * int rowNum = sheet.getFirstRowNum(); System.out.println("first row num " +
	 * rowNum); // first row num if not present -1
	 * 
	 * Row row = sheet.getRow(0); if (row == null) {
	 * System.out.println("Row is null"); } try { Cell col = row.getCell(0);
	 * 
	 * System.out.println("cell(0) " + col); } catch (NullPointerException e) {
	 * System.out.println("NPE Exception"); }
	 */
	

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			int rowNumCount = sheet.getLastRowNum() + 1;
			return rowNumCount;
		}

	}

	// returns number of columns in a sheet
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();

	}

	// find whether sheets exists
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	public String getCellData(String sheetName, int rowNum, int colNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				cellText = String.valueOf(cell.getNumericCellValue()).substring(0,
						String.valueOf(cell.getNumericCellValue()).length() - 2);
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)));
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}
	
	public String getCellData(String sheetName, int rowNum, String colName) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";
			
			row = sheet.getRow(rowNum-1);
			if (row == null)
				return "";
			
			cell = row.getCell(col_Num);
			
			if(cell==null)
				return "";
			
			if(cell.getCellType()==CellType.STRING)
				  return cell.getStringCellValue();
			else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
				  
				  String cellText  = String.valueOf(cell.getNumericCellValue());
				  if (DateUtil.isCellDateFormatted(cell)) {
			           
					  double d = cell.getNumericCellValue();

					  Calendar cal =Calendar.getInstance();
					  cal.setTime(DateUtil.getJavaDate(d));
			            cellText =
			             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
			           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
			                      cal.get(Calendar.MONTH)+1 + "/" + 
			                      cellText;
			           
			          

			         }

				  
				  
				  return cellText;
			  }else if(cell.getCellType()==CellType.BLANK)
			      return ""; 
			  else 
				  return String.valueOf(cell.getBooleanCellValue());
			
		}
		catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist  in xls";
		}
	}
		

}
