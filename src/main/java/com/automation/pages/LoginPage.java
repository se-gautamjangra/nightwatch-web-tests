package com.automation.pages;

import com.automation.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    @FindBy(xpath = "//a[@role='button'][text()='Log In']")
    WebElement loginButton;

    @FindBy(xpath = "//input[@id='loginUsername']")
    WebElement username;

    @FindBy(xpath = "//input[@id='loginPassword']")
    WebElement password;

    @FindBy(xpath = "//button[@type='submit'][contains(text(),'Log In')]")
    WebElement submitLogin;

    @FindBy(xpath = "//iframe[contains(@src,'https://www.reddit.com/login/')]")
    WebElement loginFrame;

    @FindBy(xpath = "//button[@id=\"USER_DROPDOWN_ID\"]")
    WebElement userIdDropdown;

    // Initializing the Page Objects:
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public void userLoggedIn(String uname, String pwd) {
        clickElement(loginButton);
        waitForPageLoad();
        driver.switchTo().frame(loginFrame);
        sendKeys(username,uname);
        sendKeys(password,pwd);
        clickElement(submitLogin);
        driver.switchTo().defaultContent();
    }
    public boolean isUserIDVisible() {
        highLightElement(userIdDropdown);
        return userIdDropdown.isDisplayed();
    }
}
