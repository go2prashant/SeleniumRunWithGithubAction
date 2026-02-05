package com.selenium.app.resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverPoolManager {
    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser", "chrome");
            startBrowser(browser);
        }
    return driver;
 }


    public void startBrowser(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            createChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            createFirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            createEdgeDriver();
        } else {
            System.out.println("Browser Should Be As Defined");
        }
        setUpFullScreen();
    }

    private void createEdgeDriver() {
        driver = new EdgeDriver();
    }

    private void createFirefoxDriver() {
        driver = new FirefoxDriver();
    }

    private void createChromeDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");

    boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
    }


    public void stopDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void setUpFullScreen() {
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }
}
