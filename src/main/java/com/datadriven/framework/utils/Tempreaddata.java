package com.datadriven.framework.utils;

public class Tempreaddata {
		
		public String DataxlsxPath = System.getProperty("user.dir") + "/src/main/java/testData/MockData_TestManagement.xlsx";
	
	public static void main(String[] args) {

		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir") + "/src/main/java/testData/MockData_TestManagement.xlsx");

		int totalRows = readData.getRowCount("SampleData");

		System.out.println("Total number of rows : " + totalRows);
		System.out.println(readData.getCellData("Sheet1", 0, 3));
		System.out.println(readData.getCellData("Sheet1", 0, 4));
		System.out.println(readData.getCellData("Sheet1", 1, 4));
//		System.out.println(readData.getCellData("Sheet1", 1, 5));
//      index out of bound will not execute no error will display. exception not handled.

		System.out.println(readData.getColumnCount("SampleData"));

	}

}
