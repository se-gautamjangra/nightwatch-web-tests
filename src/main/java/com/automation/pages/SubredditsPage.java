package com.automation.pages;

import com.automation.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SubredditsPage extends TestBase {

    @FindAll({@FindBy(xpath="//div[@class='subscription-box']//li/a[@class='title']")})
    List<WebElement> subscribedSubreddits;

    @FindBy(tagName = "h1")
    WebElement redditHeading;

    // Initializing the Page Objects:
    public SubredditsPage() {
        PageFactory.initElements(driver, this);
    }

    public int getSubscribedSubredditsCount() {
        System.out.println("size = " + subscribedSubreddits.size());
        return subscribedSubreddits.size();
    }

    public void printSubscribedSubreddits() {
        System.out.println(" List of subscribed subreddits: ");
        for (WebElement element : subscribedSubreddits) {
            getText(element);
            System.out.println(element.getText());
        }
    }

    public String viewSubscribedSubredditsByName(String redditName) {
        String subRedditURL="NO Subreddit found";
        for (WebElement element : subscribedSubreddits) {
            if (element.getText().equalsIgnoreCase(redditName)) {
                subRedditURL = element.getAttribute("href");
                clickElement(element);
                waitForPageLoad();
                break;
            }
        }
        return subRedditURL;
    }

    public String getRedditHeading() {
        return getText(redditHeading);
    }
}
