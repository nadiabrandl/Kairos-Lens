package com.kairosLens;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.kairosLens.service.ArticleFetcher;

public class ArticleFetcherTest {
	
	private final ArticleFetcher fetcher = new ArticleFetcher();

    @Test
    public void testFetchArticleFromAllSides() throws Exception {
    	System.out.println("..............................................................");
        String url = "https://www.allsides.com/news/2025-06-23-0419/media-bias-nyt-would-us-know-females-were-some-b-2-pilots-bombed-iran";
        String content = fetcher.fetchArticleText(url);

        System.out.println("📰 Fetched from AllSides:\n" + content);
        assertFalse(content.isBlank(), "Expected article content but got empty string.");
    }

    @Test
    public void testFetchFromBBC() throws Exception {
    	System.out.println("..............................................................");
        String url = "https://www.bbc.com/news/articles/cj4en8djwyko"; // change to valid link
        String content = fetcher.fetchArticleText(url);

        System.out.println("📰 Fetched from BBC:\n" + content);
        assertFalse(content.isBlank(), "Expected article content but got empty string.");
    }

    @Disabled("Skipping due to AllSides protections blocking CI runners")
    @Test
    public void testFetchFromTheConversation() throws Exception {
    	System.out.println("..............................................................");
        String url = "https://theconversation.com/we-are-all-lumped-under-one-umbrella-of-hate-when-social-attitudes-change-what-is-life-like-for-people-who-dont-agree-253464 "; // change to valid link
        String content = fetcher.fetchArticleText(url);

        System.out.println("📰 Fetched from The Conversation:\n" + content);
        assertFalse(content.isBlank(), "Expected article content but got empty string.");
    }

}
