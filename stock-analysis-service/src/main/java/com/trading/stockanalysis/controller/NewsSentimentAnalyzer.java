package com.trading.stockanalysis.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trading.stockanalysis.modal.Sentiment;

@RestController
@RequestMapping("/api/stocks")
public class NewsSentimentAnalyzer {

    private static final String API_KEY = "4e3f112c5988419d949e6e2d78932831"; // Replace with your API key
    private static final String BASE_URL = "https://newsapi.org/v2/everything";

    private static final List<String> POSITIVE_KEYWORDS = Arrays.asList("gain", "up", "rise", "profit", "bull", "soar", "bullish", "well-positioned", "overweight");
    private static final List<String> NEGATIVE_KEYWORDS = Arrays.asList("loss", "down", "fall", "drop", "bear", "decline", "bearish", "plunge", "underweight");

    @GetMapping(path = "/getSentiments/{company}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Sentiment getSentiment(@PathVariable String company) {
        
    	Sentiment sentiment = new Sentiment();
    	
        String url = BASE_URL + "?q=" + company + "&language=en&sortBy=publishedAt&apiKey=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(url, String.class);

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray articles = jsonObject.getAsJsonArray("articles");

        int positive = 0, negative = 0, neutral = 0;

        for (JsonElement element : articles) {
            JsonObject article = element.getAsJsonObject();
            String title = article.get("title").getAsString().toLowerCase();
            String description = article.get("description").isJsonNull() ? "" : article.get("description").getAsString().toLowerCase();

            String content = title + " " + description;
            int score = scoreSentiment(content);

            if (score > 0) positive++;
            else if (score < 0) negative++;
            else neutral++;

            System.out.println("📰 " + title);
        }

        sentiment.setPositive(positive);
        sentiment.setNegative(negative);
        sentiment.setNeutral(neutral);
        
        System.out.println("\n📊 Sentiment Summary:");
        System.out.println("✅ Positive: " + positive);
        System.out.println("❌ Negative: " + negative);
        System.out.println("⚪ Neutral : " + neutral);
        
        return sentiment;
    }

    private static int scoreSentiment(String text) {
        int score = 0;
        for (String word : POSITIVE_KEYWORDS) {
            if (text.contains(word)) score++;
        }
        for (String word : NEGATIVE_KEYWORDS) {
            if (text.contains(word)) score--;
        }
        return score;
    }
}
