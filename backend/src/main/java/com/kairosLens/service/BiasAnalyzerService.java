package com.kairosLens.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kairosLens.dto.BiasAnalysisResult;
import com.kairosLens.dto.PoliticalAlignment;

import java.io.IOException;

@Service
public class BiasAnalyzerService {
	
	BiasAnalysisResult result = new BiasAnalysisResult();
	ObjectMapper mapper = new ObjectMapper();
	
    @Value("${openai.api.key}")
    private String openAiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final OkHttpClient client;

    public final BiasAnalyzerService(OkHttpClient client){
        this.client = client;
    }

    public String analyzeBias(String articleContent) throws IOException {
    	if (articleContent.contains("❗ Access denied while fetching article")) {   	    
    	    result.setBiasSummary("This article could not be analyzed due to access restrictions from the website.");
    	    result.setTone("N/A");
    	    result.setReflectionPrompt("Try a different article that is publicly accessible.");
    	    return mapper.writeValueAsString(result);
    	}
    	
    	String prompt = """
    			You are a media bias analyst.

                Return ONLY valid JSON using this structure:
                {
                  "biasSummary": "A short summary of the article's bias",
                  "tone": "Neutral, emotional, persuasive, etc.",
                  "reflectionPrompt": "A reflective question the reader should consider",
                  "politicalAlignment": "Left, Center-Left, Center, Center-Right, or Right"
                }

                Do not include any explanation or formatting, only the JSON object.

                Article:
                """ + articleContent;
    	
        String requestBodyJson = String.format("""
        		{
        		  "model": "gpt-3.5-turbo",
        		  "messages": [
        		    { "role": "system", "content": "You are a critical media analyst that identifies bias in content and helps readers reflect on what they consume." },
        		    { "role": "user", "content": "%s" }
        		  ]
        		}
        		""", escapeJson(prompt));

        Request request = new Request.Builder()
        	    .url(OPENAI_API_URL)
        	    .post(RequestBody.create(requestBodyJson, MediaType.get("application/json")))
        	    .addHeader("Content-Type", "application/json")
        	    .addHeader("Authorization", "Bearer " + openAiApiKey)
        	    .build();

        int maxRetries = 3;
        int delay = 2000; // start at 2 seconds

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 429) {
                    System.out.println("Rate limited by OpenAI. Waiting and retrying... Attempt " + attempt);
                    Thread.sleep(delay);
                    delay *= 2; // exponential backoff
                } else if (!response.isSuccessful()) {
                    throw new IOException("Unexpected response: " + response);
                } else {               	
                	String rawJson = response.body().string();
                	// Step 1: Extract the AI message
                	JsonNode root = mapper.readTree(rawJson);
                	String content = root.get("choices").get(0).get("message").get("content").asText();
                	System.out.println("GPT Raw Response:\n" + content);

                	// Clean the response of markdown formatting
                	String cleanedJson = extractJsonFromMarkdown(content);
                	System.out.println("Cleaned JSON:\n" + cleanedJson);

                	// Step 3: Parse that content into our DTO
                    try {
                        JsonNode parsed = mapper.readTree(content);
                        
                        result.setBiasSummary(parsed.get("biasSummary").asText());
                        result.setTone(parsed.get("tone").asText());
                        result.setReflectionPrompt(parsed.get("reflectionPrompt").asText());

                        String alignmentText = parsed.get("politicalAlignment").asText().toUpperCase().replace("-", "_");
                        try {
                            PoliticalAlignment alignment = PoliticalAlignment.valueOf(alignmentText);
                            result.setPoliticalAlignment(alignment);
                        } catch (IllegalArgumentException e) {
                            System.err.println("⚠️ Unknown political alignment: " + alignmentText);
                        }

                        return mapper.writeValueAsString(result);
                    } catch (JsonProcessingException e) {
                        System.err.println("⚠️ Failed to parse GPT response:\n" + content);
                        throw new IOException("Invalid JSON returned by OpenAI", e);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted during retry delay.", e);
            }
        }
        return """
        		{
        		  "error": "Rate limit exceeded. Please wait and try again later.",
        		  "status": 429
        		}
        		""";
    
    }
    
    private String escapeJson(String input) {
        return input
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "");
    }
    
    private String extractJsonFromMarkdown(String input) {
        if (input == null) return "";

        // Try to extract using regex first
        String regex = "(?s)```json\\s*(\\{.*?})\\s*```";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }
        // Fallback: strip back-ticks manually
        return input.replaceAll("(?s)```(?:json)?", "").trim();
    }
}
