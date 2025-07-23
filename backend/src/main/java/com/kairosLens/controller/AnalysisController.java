package com.kairosLens.controller;

import com.kairosLens.service.ArticleFetcher;
import com.kairosLens.service.BiasAnalyzerService;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final ArticleFetcher fetcher;
    private final BiasAnalyzerService analyzer;

    public AnalysisController (ArticleFetcher fetcher, BiasAnalyzerService analyzer) {
        this.fetcher = fetcher;
        this.analyzer = analyzer;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/analyze")
    public String analyze(@RequestBody Map<String, String> payload) throws Exception {
        String url = payload.get("article");
        String articleText = fetcher.fetchArticleText(url);
        System.out.println("Fetched Article Text:");
        System.out.println(articleText);

        return analyzer.analyzeBias(articleText);
    }
}
