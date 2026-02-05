package com.selenium.app.baseConfiguration;

import com.selenium.app.resources.DriverPoolManager;
import com.selenium.app.utility.Data;
import org.testng.annotations.*;


public class BaseTest {

    protected DriverPoolManager driverManager;

    @BeforeClass(alwaysRun = true)
    public void setupTest() {
        try {
            String browser = System.getProperty("browser");
            if (browser == null || browser.trim().isEmpty()) {
                browser = "chrome";
            }
            System.out.println("Starting browser: " + browser);
            driverManager = new DriverPoolManager();
            driverManager.startBrowser(browser);
            
            if (driverManager.getDriver() == null) {
                throw new RuntimeException("WebDriver initialization failed - driver is null");
            }
            System.out.println("WebDriver initialized successfully");
        } catch (Exception e)
        {
            System.out.println("ERROR: Failed to initialize WebDriver");
            e.printStackTrace();
            throw new RuntimeException("Test setup failed", e);
        }
        System.out.println(Data.TestStart);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        try
        {
            if(driverManager != null) {
                driverManager.stopDriver();
                System.out.println("WebDriver closed successfully");
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR: Failed to cleanup WebDriver");
            e.printStackTrace();
        }
        System.out.println(Data.TestComplete);
    }
}
