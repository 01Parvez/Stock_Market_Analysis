package com.trading.stockanalysis.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.stockanalysis.modal.HighLowResponse;
import com.trading.stockanalysis.modal.Stock;

//@RestController
//@RequestMapping("/api/stocks")
public class DataController {
	
	static List<Stock> stocks;
	
//	//@GetMapping("/top-gainers")
//    public List<Stock> getTopGainers() {
//		System.out.println("Entering top-gainers");
//		 return List.of(
//		            new Stock("AAPL", "Apple Inc.", "IT", "Software", 185.00, 5.50, 3.06),
//		            new Stock("GOOGL", "Alphabet Inc.", "IT", "Software", 135.50, 4.25, 3.24)
//		        );
//    }
//
//    //@GetMapping("/top-losers")
//    public List<Stock> getTopLosers() {
//    	System.out.println("Entering top-losers");
//    	 return List.of(
//    	            new Stock("TSLA", "Tesla Inc.", "Manufacturing", "Auto", 210.00, -15.50, -6.88),
//    	            new Stock("AMZN", "Amazon Inc.", "Retail", "Retail", 125.75, -8.25, -6.16)
//    	        );
//    }
//
//    //@GetMapping("/52week-high-low")
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
//	
//	 public static List<Stock> readNasdaqJson(String filePath) {
//	        List<Stock> stocks = new ArrayList<>();
//	        ObjectMapper mapper = new ObjectMapper();
//
//	        try {
//	            JsonNode root = mapper.readTree(new File(filePath));
//	            for (JsonNode node : root) {
//	                String symbol = node.get("symbol").asText();
//	                String name = node.get("name").asText();
//	                String sector = node.has("sector") ? node.get("sector").asText() : "Unknown";
//	                String industry = node.has("industry") ? node.get("industry").asText() : "Unknown";
//	                stocks.add(new Stock(symbol, name, sector, industry, 0.0, 0.0, 0.0));
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//
//	        return stocks;
//	    }
	 

//		static {
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


}
