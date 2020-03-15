package com.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.utils.DateUtils;
import com.datadriven.framework.utils.ExtentReportManager;

public class BaseUI {

	public  WebDriver driver;
	public  Properties prop;
	public  ExtentReports report = ExtentReportManager.getReportInstance();
	public  ExtentTest logger;

	SoftAssert softAssert = new SoftAssert();
	
	
	/****************************** * Invoke Browser***********************************/
	// invoke browser
	public  void invokeBrowser(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
				driver = new ChromeDriver();
			}

			else if (browserName.equalsIgnoreCase("mozilla")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver");
				driver = new FirefoxDriver();
			} else {
				driver = new SafariDriver();
			}

		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/ObjectRepository/projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
			}
		}

	}

	/**************************** Open URL **********************************/
	// Open the static website URL

	public void openURL(String websiteURLKey) {
		try {
			driver.get(prop.getProperty(websiteURLKey));
			reportPass(websiteURLKey + " Identified Succesfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/**************************** Close Browser **********************************/

	// To close the browser instance
	public void tearDown() {
		driver.close();
	}

	/**************************** Quit Browser **********************************/

	// To quit the browser instance
	public void quitBrowser() {
		driver.quit();
	}

	/**************************** Enter Text **********************************/

	// Enter the text in Text Fields
	public void enterText(String xpathKey, String data) {
		try {
			getElement(xpathKey).sendKeys(data);
			reportPass(data + "- Entered successfully in locator Element : " + xpathKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/**************************** Click Element **********************************/

	// To click the element
	public void elementClick(String xpathKey) {
		try {
			getElement(xpathKey).click();
			reportPass(xpathKey + "Element clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/***************************** Identify Element **********************************/

	// To identify the Page WebElement
	public WebElement getElement(String locatorKey) {
		WebElement element = null;

		try {
			if (locatorKey.endsWith("_id")) {
				element = driver.findElement(By.id(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);

			} else if (locatorKey.endsWith("_class")) {
				element = driver.findElement(By.className(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);

			} else if (locatorKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);

			} else if (locatorKey.endsWith("_linkText")) {
				element = driver.findElement(By.linkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identified : " + locatorKey);

			} else {
				reportFail("Failing the testcase, Invalid locator " + locatorKey);
			}
		} catch (Exception e) {

			// fail test case and report error
			reportFail(e.getMessage());
			e.printStackTrace();

			Assert.fail("Failing the test case : " + e.getMessage());
		}
		return element;

	}

	/****************** Assertion Function ******************************/

	public void assertTrue(boolean flag) {
		
			softAssert.assertTrue(flag);	
		}
	
	public void assertFalse(boolean flag) {
		
		softAssert.assertFalse(flag);	
	}
	
	public void assertEquals(String actual, String expected) {
		
		softAssert.assertEquals(actual, expected);	
	}


	
	/****************** Reporting Function ******************************/

	public boolean isElementPresent(String locatorKey) {
		try {
			if (getElement(locatorKey).isDisplayed()) {
				reportPass(locatorKey + " : Element is displayed");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementSelected(String locatorKey) {
		try {
			if (getElement(locatorKey).isSelected()) {
				reportPass(locatorKey + " : Element is selected");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementEnabled(String locatorKey) {
		try {
			if (getElement(locatorKey).isEnabled()) {
				reportPass(locatorKey + " : Element is enabled");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	/****************** Reporting Function ****************/

	public  void reportFail(String reportString) {

		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);

	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);

	}
	

	@AfterMethod
	public void afterTest() {

		softAssert.assertAll();
		 driver.quit();
	}

	/****************** Capture Screen Shot ****************/

	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "/ScreenShots/" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "/ScreenShots/" + DateUtils.getTimeStamp() + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@AfterTest
	public void endReport() {
		report.flush();

	}

}
