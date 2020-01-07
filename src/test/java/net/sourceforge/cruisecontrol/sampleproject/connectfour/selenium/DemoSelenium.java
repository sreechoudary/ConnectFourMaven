/**
 * 
 */
package net.sourceforge.cruisecontrol.sampleproject.connectfour.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author sreenivasrao.m
 *
 */
public class DemoSelenium {
	
	private WebDriver driver;
	
	@Test				
	public void testEasy() {	
		driver.get("https://www.google.com/");  
		String title = driver.getTitle();				 
		Assert.assertTrue(title.contains("Free Selenium Tutorials")); 		
	}	
	@BeforeTest
	public void beforeTest() {	
	    driver = new FirefoxDriver();  
	}		
	@AfterTest
	public void afterTest() {
		driver.quit();			
	}

	@Test
	public void test() {
		Assert.fail("Not yet implemented");
	}

}
