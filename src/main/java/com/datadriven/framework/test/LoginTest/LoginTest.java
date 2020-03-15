package com.datadriven.framework.test.LoginTest;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.datadriven.framework.base.BaseUI;
import com.datadriven.framework.utils.TestDataProvider;

public class LoginTest extends BaseUI {
	
	public class loginTest extends BaseUI {

		@Test(dataProvider= "getTestOneData")
		public void testOne(Hashtable<String, String> dataTable) {
			
		    logger = report.createTest("Enter UserName And Password in Rediff : " );
		    
		    invokeBrowser("chrome");
			openURL("websiteURL");
			elementClick("signinBtn_xpath");
			enterText("usrnameTextbox_xpath", dataTable.get("Col1"));
			enterText("passwordTextbox_xpath", dataTable.get("Col3"));		
		}

		@AfterTest
		public void endReport() {
		report.flush();

		}
		
		@DataProvider
		public Object[][] getTestOneData(){
			return TestDataProvider.getTestData("MockData_TestManagement.xlsx", "Feature1", "Test One");
		}

		 //@Test
		public void testTwo() {
			logger = report.createTest("Open Rediff and Enter UserName");
			invokeBrowser("chrome");
			openURL("websiteURL");
			elementClick("singinBtn_Xpath");
			enterText("usrnameTextbox_Xpath", "anshulc55");
		}

		// @Test(dependsOnMethods="testTwo")
		public void testThree() {
			invokeBrowser("Mozila");
			openURL("websiteURL");
			elementClick("singinBtn_xpath");
			enterText("usrnameTextbox_Xpath", "anshulc55");
			tearDown();

		}



}}
