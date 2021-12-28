package com.automation.base;

import com.automation.util.TestUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    private static Connection connection = null;
    public static Statement statement = null;
    protected static final Logger logger = LogManager.getLogger(TestBase.class);

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/java/com/automation"
                            + "/config/config.properties");
            prop.load(ip);
            PropertyConfigurator.configure("log4j.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialization(String executionType) {
        ChromeOptions options = new ChromeOptions();
        if (executionType.equalsIgnoreCase("HEADLESS")) {
            options.addArguments("--headless"); //for headless mode
            options.addArguments("--window-size=800,600");//The invisible browser window is only 800x600 in size
            options.addArguments("start-maximized"); // open Browser in maximized mode
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("--no-sandbox");
        }
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir")
                        + "/src/main/resources/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts()
                .implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        logger.info("Driver launched successfully...");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    public void closeDriver() {
        driver.quit();
    }

    public static void databaseConnectionSetup() {
        try {
            String dbClass = "com.mysql.cj.jdbc.Driver";
            Class.forName(dbClass).newInstance();
            connection = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("DB_USER"), prop.getProperty("DB_PASSWORD"));
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm aa ");
        String strDate = formatter.format(date);
        System.out.println("Date Format with MM/dd/yyyy : " + strDate);
        return strDate;
    }

    public static void navigateToURL(String url) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().to(url);
    }

    public void waitForOperation(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void highLightElement(WebElement element) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
        }
    }

    public void clickElement(WebElement element) {
        highLightElement(element);
        element.click();
        logger.info("Clicking on webelement - " + element);
    }

    public void sendKeys(WebElement element, String textToEnter) {
        highLightElement(element);
        logger.info("Enter text in webelement - " + element);
        element.clear();
        element.sendKeys(textToEnter);
    }

    public String getText(WebElement element) {
        highLightElement(element);
        logger.info("Getting text of webelement - " + element);
        logger.info("Webelement text - " + element.getText());
        return element.getText();
    }

    @AfterMethod
    public void onTestComplete(ITestResult result) {
        if (ITestResult.SUCCESS == result.getStatus()) {
            TestUtil.takeScreenshot(result.getMethod().getMethodName());
        }
        if (ITestResult.FAILURE == result.getStatus()) {
            TestUtil.takeScreenshot(result.getMethod().getMethodName());
        }
    }

    @BeforeSuite
    public void tearUp() {
        logger.info("*********************** Test Execution Started ***********************");
    }

    @AfterSuite
    public void tearDown() {
        logger.info("*********************** Test Execution Finished ***********************");
    }
}
