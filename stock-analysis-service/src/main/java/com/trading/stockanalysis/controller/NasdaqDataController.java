package com.trading.stockanalysis.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.stockanalysis.modal.HighLowResponse;
import com.trading.stockanalysis.modal.Stock;

@RestController
@RequestMapping("/api/stocks")
public class NasdaqDataController {
	
	static List<Stock> stocks;
	
	private static final String API_KEY = "SNIAS5N5FC9QFW8V";
	
	static int max = 100005;
    
    static Vector<Integer> primeNumber = new Vector<Integer>();
    
//	static {
//		
//		String filePath = "C:\\Users\\DELL\\workspace\\TradeAnalysis\\nasdaq_full_tickers.json"; // Update with the actual file path
//	     
//		stocks = readNasdaqJson(filePath);
//	}

	@GetMapping("/sectors")
	public List<String> getSectors() {	    
	        
	        return stocks.stream()
	              .map(Stock::getSector)
	              .distinct()
	              .toList();
	}
	
	@GetMapping("/companies")
	public List<String> getCompanies() {	    
	        
	        return stocks.stream()
	              .map(Stock::getSymbol)
	              .distinct()
	              .toList();
	}
	
//	@GetMapping("/top-gainers")
//    public List<Stock> getTopGainers() {
//		System.out.println("Entering top-gainers");
//		 return List.of(
//		            new Stock("AAPL", "Apple Inc.", "IT", "Software", 185.00, 5.50, 3.06),
//		            new Stock("GOOGL", "Alphabet Inc.", "IT", "Software", 135.50, 4.25, 3.24)
//		        );
//    }
//
//    @GetMapping("/top-losers")
//    public List<Stock> getTopLosers() {
//    	System.out.println("Entering top-losers");
//    	 return List.of(
//    	            new Stock("TSLA", "Tesla Inc.", "Manufacturing", "Auto", 210.00, -15.50, -6.88),
//    	            new Stock("AMZN", "Amazon Inc.", "Retail", "Retail", 125.75, -8.25, -6.16)
//    	        );
//    }
//
//    @GetMapping("/52week-high-low")
//    public HighLowResponse get52WeekHighLowScripts() {
//    	System.out.println("Entering 52week high low");
//    	 List<Stock> highs = List.of(
//    	            new Stock("META", "Meta Platforms","IT", "Social Media", 325.00, 0, 0),
//    	            new Stock("NVDA", "NVIDIA Corp", "Manufacturing", "Electronics", 485.00, 0, 0)
//    	        );
//    	        
//    	        List<Stock> lows = List.of(
//    	            new Stock("INTC", "Intel Corp", "Manufacturing", "Electronics", 28.50, 0, 0),
//    	            new Stock("DIS", "Walt Disney Co", "Entertainment", "Animation", 85.00, 0, 0)
//    	        );
//    	        
//    	        return new HighLowResponse(highs, lows);
//    }
//    
    @GetMapping("/getStockPrices/{symbol}")
    public Stock getStockHistory(@PathVariable String symbol) {
    	System.out.println("Entering getStockHistory");
    	//List<Stock> stockList = new ArrayList<Stock>();
    	Stock stock = new Stock();
		try {
			// Step 1: Fetch monthly time series data
			String jsonData = fetchMonthlyTimeSeries(symbol);
			System.out.println("Original JSON Data:\n" + jsonData);
			
			
			// Step 2: Parse the JSON data
			JSONObject jsonObject = new JSONObject(jsonData);
			
			JSONObject metaData = jsonObject.getJSONObject("Meta Data");
            String lastRefreshed = metaData.getString("3. Last Refreshed");
	            
            JSONObject timeSeries = jsonObject.getJSONObject("Monthly Time Series");
            JSONObject latestData = timeSeries.getJSONObject(lastRefreshed);
			 
			 stock.setOpen((double) Double.parseDouble(latestData.getString("1. open")));
			 stock.setHigh((double) Double.parseDouble(latestData.getString("2. high")));
			 stock.setLow((double) Double.parseDouble(latestData.getString("3. low")));
			 stock.setClose((double) Double.parseDouble(latestData.getString("4. close")));
			 stock.setVolume((long) Double.parseDouble(latestData.getString("5. volume")));
			

			// Step 4: Print the updated JSON
			System.out.println("\nStock Data:"+stock);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return stock;      
    }
	
 // Method to fetch monthly time series data from Alpha Vantage
 	private static String fetchMonthlyTimeSeries(String symbol) throws Exception {
 		String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol
 				+ "&apikey=" + API_KEY;
 		URL url = new URL(urlString);
 		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 		connection.setRequestMethod("GET");

 		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 		String inputLine;
 		StringBuilder response = new StringBuilder();

 		while ((inputLine = in.readLine()) != null) {
 			response.append(inputLine);
 		}
 		in.close();

 		return response.toString();
 	}
	
    
	 public static List<Stock> readNasdaqJson(String filePath) {
	        List<Stock> stocks = new ArrayList<>();
	        ObjectMapper mapper = new ObjectMapper();

	        try {
	            JsonNode root = mapper.readTree(new File(filePath));
	            for (JsonNode node : root) {
	                String symbol = node.get("symbol").asText();
	                String name = node.get("name").asText();
	                String sector = node.has("sector") ? node.get("sector").asText() : "Unknown";
	                String industry = node.has("industry") ? node.get("industry").asText() : "Unknown";
	                stocks.add(new Stock(symbol, name, sector, industry, 0.0, 0.0, 0.0, 0.0));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return stocks;
	    }

	 

}
