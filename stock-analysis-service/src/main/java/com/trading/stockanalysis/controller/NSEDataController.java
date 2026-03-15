package com.trading.stockanalysis.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.stockanalysis.modal.Stock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@RequestMapping("/api/stocks")
public class NSEDataController {

    private static final OkHttpClient client = new OkHttpClient();
    private static CompletableFuture<List<Stock>> gainers_future = new CompletableFuture<>();
    private static CompletableFuture<List<Stock>> losers_future = new CompletableFuture<>();

    // Yahoo Finance URL with ticker symbol
    private static final String BASE_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";

//    public static void main(String[] args) {
//        String stockSymbol = "RELIANCE.NS";  // .NS for NSE, .BO for BSE
//        //fetchStockData(stockSymbol);
//    	System.out.println("\nFetching Indian Top Gainers...\n");
//        fetchTopStocks(GAINERS_URL, "Gainers");
//
//        System.out.println("\nFetching Indian Top Losers...\n");
//        fetchTopStocks(LOSERS_URL, "Losers");
//
//        
//    }

    // Method to fetch live stock data
    public static void fetchStockData(String symbol) throws IOException {
        String url = BASE_URL + symbol;

        // HTTP Request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Async HTTP Call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("HTTP Request Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            	if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    System.out.println("Full JSON Response:\n" + responseBody); // ✅ Print Full Response
                    parseStockData(responseBody);
                } else {
                    System.out.println("Failed Response: " + response.code());
                }
            }
        });
    }

    public static void parseStockData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject chart = jsonObject.getJSONObject("chart");
            
            // Check for errors
            if (chart.getJSONArray("result") == null || chart.getJSONArray("result").length() == 0) {
                System.out.println("No data available for the requested stock.");
                return;
            }

            JSONObject result = chart.getJSONArray("result").getJSONObject(0);
            
            // Check if indicators exist
            if (!result.has("indicators")) {
                System.out.println("No indicators found. Market might be closed.");
                return;
            }

            JSONObject meta = result.getJSONObject("meta");
            JSONObject indicators = result.getJSONObject("indicators")
                                          .getJSONArray("quote")
                                          .getJSONObject(0);

            // Fetch values
            String symbol = meta.getString("symbol");
            double currentPrice = meta.optDouble("regularMarketPrice", -1);
            double open = indicators.getJSONArray("open").optDouble(0, -1);
            double high = indicators.getJSONArray("high").optDouble(0, -1);
            double low = indicators.getJSONArray("low").optDouble(0, -1);
            double volume = indicators.getJSONArray("volume").optDouble(0, -1);

            // Output the stock details
            System.out.println("\n📊 Stock Data for " + symbol + ":");
            System.out.println("Current Price: ₹" + currentPrice);
            System.out.println("Open Price: ₹" + open);
            System.out.println("Day High: ₹" + high);
            System.out.println("Day Low: ₹" + low);
            System.out.println("Volume: " + (long) volume);

        } catch (Exception e) {
            System.out.println("Error parsing stock data: " + e.getMessage());
        }
    }

    // Yahoo Finance API URLs for Gainers & Losers
    private static final String GAINERS_URL = "https://query1.finance.yahoo.com/v1/finance/screener/predefined/saved?scrIds=day_gainers";
    

    @GetMapping("/top-gainers")
    public static CompletableFuture<List<Stock>> fetchTopGainers() {
        List<Stock> stockList = new ArrayList<>();

        // Create HTTP Request
        Request request = new Request.Builder()
                .url(GAINERS_URL)
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();

        // Async Call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed to fetch top Gainers: " + e.getMessage());
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    List<Stock> stockList  = parseStockData(responseBody, "Gainers");
                    gainers_future.complete(stockList);
                } else {
                	 System.out.println("Failed to fetch top Gainers: " + response.code());
                	 gainers_future.completeExceptionally(new IOException("Failed with code: " + response.code()));
                }
            }
        });
        return gainers_future;

    }
    
    
    private static final String LOSERS_URL = "https://query1.finance.yahoo.com/v1/finance/screener/predefined/saved?scrIds=day_losers";

    @GetMapping("/top-losers")
    public static CompletableFuture<List<Stock>> fetchTopLosers() {
        // Create HTTP Request
        Request request = new Request.Builder()
                .url(LOSERS_URL)
                .addHeader("User-Agent", "Mozilla/5.0")
                .build();

        // Async Call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failed to fetch top Losers: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    List<Stock> stockList  = parseStockData(responseBody, "Losers");
                    losers_future.complete(stockList);
                } else {
                	 System.out.println("Failed to fetch top Gainers: " + response.code());
                	 losers_future.completeExceptionally(new IOException("Failed with code: " + response.code()));
                
                }
                
            }
        });
        return losers_future;
    }

    

    // Method to parse the JSON data
    // Parse and Filter Indian Stocks
    public static List<Stock> parseStockData(String jsonData, String type) {
    	List<Stock> stockList = new ArrayList<>();
    	try {
        	
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray results = jsonObject
                    .getJSONObject("finance")
                    .getJSONArray("result")
                    .getJSONObject(0)
                    .getJSONArray("quotes");

            System.out.println("📊 Top " + type + " (Indian Stocks):\n");

            int count = 0;
            
            for (int i = 0; i < results.length(); i++) {
                JSONObject stock = results.getJSONObject(i);
                String symbol = stock.optString("symbol", "N/A");

                // Check for Indian Market Stocks (.NS or .BO)
                //if (symbol.endsWith(".NS") || symbol.endsWith(".BO")) {
                    String longName = stock.optString("longName", "N/A");
                    double regularMarketPrice = stock.optDouble("regularMarketPrice", 0.0);
                    double regularMarketChangePercent = stock.optDouble("regularMarketChangePercent", 0.0);
                    double fiftyTwoWeekHigh = stock.optDouble("fiftyTwoWeekHigh", 0.0);
                    double fiftyTwoWeekLow = stock.optDouble("fiftyTwoWeekLow", 0.0);
                    
                    stockList.add(new Stock(symbol, longName, "", "", regularMarketPrice, regularMarketChangePercent, fiftyTwoWeekHigh, fiftyTwoWeekLow));
                    System.out.println((count + 1) + ". " + longName + " (" + symbol + ")");
                    System.out.println("   - Price: ₹" + regularMarketPrice);
                    System.out.println("   - Change: " + String.format("%.2f", regularMarketChangePercent) + "%\n");
                    count++;
               // }

                // Limit output to Top 5
                if (count >= 5) break;
            }
            if (count == 0) {
                System.out.println("No Indian stocks found in the top " + type + ".");
            }
        } catch (Exception e) {
            System.out.println("Error parsing " + type + " data: " + e.getMessage());
        }
    	return stockList;
    }
    
}
