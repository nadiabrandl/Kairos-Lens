package com.kairosLens.service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ArticleFetcher {

	public String fetchArticleText(String url) throws IOException {
	    Connection connection = Jsoup.connect(url)
	        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Safari/605.1.15")
	        .referrer("https://www.google.com/")
	        .header("Accept-Language", "en-US,en;q=0.9")
	        .header("Accept-Encoding", "gzip, deflate")
	        .header("Connection", "keep-alive")
	        .timeout(10000)
	        .ignoreHttpErrors(true)
	        .followRedirects(true);

	    Response response = connection.execute();

	    if (response.statusCode() == 403) {
	        return "❗ Access denied while fetching this article. Please try a different source.";
	    }

	    Document doc = response.parse();
	    
	    // Try AllSides-specific container
	    Element mainContent = doc.selectFirst("div.news-body");

	    if (mainContent == null) {
	        Elements paragraphs = doc.select("p");
	        if (paragraphs.isEmpty()) return "";

	        return paragraphs.stream()
	                         .map(Element::text)
	                         .reduce("", (a, b) -> a + "\n" + b).trim();
	    }
	    return mainContent.text().trim();
	}
}
