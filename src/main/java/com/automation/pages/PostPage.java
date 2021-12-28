package com.automation.pages;

import com.automation.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PostPage extends TestBase {

    @FindBy(xpath = "//div[@data-test-id='post-content']//button[@aria-label='upvote']//i")
    WebElement upVotePost;

    @FindBy(xpath = "//div[@data-test-id='post-content']//button[@aria-label='downvote']//i")
    WebElement downVotePost;

    @FindBy(xpath = "//div[@data-test-id=\"post-content\"]//div[contains(@id,'vote-arrows')]/div[contains(@style,'color:')]")
    WebElement voteCount;

    @FindBy(xpath = "//div[@data-test-id=\"comment-submission-form-richtext\"]//div[@role=\"textbox\"]")
    WebElement commentBox;

    @FindBy(xpath = "//button[@type=\"submit\"][text()='Comment']")
    WebElement submitComment;

    @FindAll({@FindBy(xpath="//div[@data-test-id='comment']//p")})
    List<WebElement> postComments;

    // Initializing the Page Objects:
    public PostPage() {
        PageFactory.initElements(driver, this);
    }

    public void upVotePost() {
        clickElement(upVotePost);
    }

    public void downVotePost() {
        clickElement(downVotePost);
    }

    public int getVoteCount() {
        highLightElement(voteCount);
        return Integer.valueOf(voteCount.getText());
    }

    public void addComment(String commentMessage) {
//        commentBox.clear();
//        commentBox.sendKeys(getCurrentDate() + commentMessage);
//        submitComment.click();
        sendKeys(commentBox,getCurrentDate() + commentMessage);
        clickElement(submitComment);
        waitForPageLoad();
    }

    public String getLatestComment() {
        System.out.println("Comment - " + postComments.get(0).getText());
        highLightElement(postComments.get(0));
        return getText(postComments.get(0));
    }
}
