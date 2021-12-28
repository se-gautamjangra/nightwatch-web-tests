package com.automation.util;

import com.automation.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 60;
    public static long IMPLICIT_WAIT = 30;

    public static void takeScreenshot(String testName) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        try {
            FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            logger.debug("Error while taking screenshot...");
            e.printStackTrace();
        }
    }


}
