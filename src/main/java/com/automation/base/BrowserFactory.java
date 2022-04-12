package com.automation.base;

import com.automation.util.ExtentReporterNG;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.logging.Logger;

public class BrowserFactory {
    Logger logger = Logger.getLogger(BrowserFactory.class.getName());
    public WebDriver getDriverInstance(String browserName) {
        WebDriver driver = null;
        if (browserName.equalsIgnoreCase("CHROME")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
        } else if(browserName.equalsIgnoreCase("FIREFOX")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("IE")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else {
            logger.info("No Matching Browser found for : " + browserName);
        }
        if (driver==null)
            System.exit(0);
        return driver;
    }
}
