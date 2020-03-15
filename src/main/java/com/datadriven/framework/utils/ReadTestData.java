package com.datadriven.framework.utils;

/*
*
* This class is not going to be used in framework.
*
*/


public class ReadTestData {

	public static void main(String[] args) {

		ReadExcelDataFile readdata = new ReadExcelDataFile(
				System.getProperty("user.dir") + "/src/main/java/testData/MockData_TestManagement.xlsx");
		String sheetName = "Feature1";
		String testName = "Test Three";

		// Start Row of Test Case
		int startRowNum = 0;

		while (!readdata.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)) {
			startRowNum++;
		}
		
		System.out.println("Test starts from Row Number : " + startRowNum);
		
		int StartTestColumn = startRowNum+1;
		int StartTestRow = startRowNum+2;
		
		// Number of Rows in Test Case
		int rows = 0;
		while(!readdata.getCellData(sheetName, 0, StartTestRow+rows).equals("")) {
			rows++;
		}
		System.out.println("Total Number of Rows in Test : " + " "+ testName + "is - " +rows);
	
		// Find Number of columns in test
		int colmns = 0;
		while(!readdata.getCellData(sheetName, colmns, StartTestColumn).equals("")) {
			colmns++;
		}
		System.out.println("Total Number of Columns in Test : " + " "+ testName + "is - " +colmns);
		
		for(int rowNum=startRowNum; rowNum<StartTestRow+rows; rowNum++) {
			for(int colNum =0; colNum < colmns; colNum++) {
				System.out.println(readdata.getCellData(sheetName, colNum, rowNum));
				readdata.getCellData(sheetName, colNum, rowNum);
			}
		}
	}
}
