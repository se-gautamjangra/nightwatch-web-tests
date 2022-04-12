package com.automation.base;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private DriverFactory() {}

    private static DriverFactory instance = new DriverFactory();

    public static DriverFactory getInstance() {
        return instance;
    }
    ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public void setDrivers(WebDriver driver) {
        drivers.set(driver);
    }

    public WebDriver getDriver() {
        return drivers.get();
    }

    public void closeDriver() {
        drivers.get().close();
        drivers.remove();
    }
}
