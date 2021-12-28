package com.reddit.tests;

import com.automation.base.TestBase;
import com.automation.pages.LoginPage;
import com.automation.pages.PostPage;
import com.automation.pages.SubredditsPage;
import org.testng.Assert;
import org.testng.annotations.*;

public class RedditTest extends TestBase {
    LoginPage loginPage;
    PostPage postPage;
    SubredditsPage subredditsPage;

    public RedditTest() {
        super();
    }

    @Parameters({ "execution-type" })
    @BeforeClass
    public void setUp(@Optional("non-headless") String value) {
        initialization(value);
        loginPage = new LoginPage();
        postPage = new PostPage();
        subredditsPage = new SubredditsPage();
    }

    @AfterClass
    public void tearDown() {
        closeDriver();
    }

    /**
     * Testcase - check if user is able to login
     */
    @Test(priority = 1)
    public void verifyUserLogin() {
        loginPage.userLoggedIn(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertTrue(loginPage.isUserIDVisible());
    }

    /**
     * Testcase - check if user is able to upvote a post
     */
    @Test(priority = 2)
    public void verifyUpvotePost() {
        int voteCount = postPage.getVoteCount();
        postPage.upVotePost();
        int voteCountAfterUpVote = postPage.getVoteCount();
        Assert.assertNotEquals(voteCount, voteCountAfterUpVote);
    }

    /**
     * Testcase - check if user is able to downvote a post
     */
    @Test(priority = 3)
    public void verifyDownvotePost() {
        int voteCount = postPage.getVoteCount();
        postPage.downVotePost();
        int voteCountAfterUpVote = postPage.getVoteCount();
        Assert.assertNotEquals(voteCount, voteCountAfterUpVote);
    }

    /**
     * Testcase - check if user is able to see my subscribed subreddits
     */
    @Test(priority = 5)
    public void verifySubscribedSubreddits() {
        navigateToURL(prop.getProperty("SubscribedSubredditURL"));
        subredditsPage.printSubscribedSubreddits();
        int subRedditsCount = subredditsPage.getSubscribedSubredditsCount();
        Assert.assertEquals(17, subRedditsCount);
    }

    /**
     * Testcase - check if user is able view one of subscribed subreddits
     */
    @Test(priority = 6)
    public void verifyViewSubscribedSubreddits() {
        String redditURL = subredditsPage.viewSubscribedSubredditsByName(prop.getProperty("SubscribedSubredditsName"));
        String getCurrentURL = driver.getCurrentUrl();
        Assert.assertEquals(redditURL, getCurrentURL);
        Assert.assertEquals(prop.getProperty("SubscribedSubredditsName"), subredditsPage.getRedditHeading());
    }
}