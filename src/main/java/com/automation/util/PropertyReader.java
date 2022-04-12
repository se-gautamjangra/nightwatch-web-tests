package com.automation.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    static Properties properties = new Properties();
    static Logger logger = Logger.getLogger(PropertyReader.class.getName());
    public static String getPropertyValue(String property) {
        FileInputStream ip = null;
        String value = null;
        try {
            ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/java/com/automation"
                            + "/config/config.properties");
        } catch (FileNotFoundException e) {
            logger.warn("Unable to find config.properties file.");
        }
        try {
            properties.load(ip);
            value = properties.getProperty(property);
        } catch (IOException e) {
            logger.warn("Unable to find "+property+" property in config.properties file.");
        }
        return value;
    }
}
