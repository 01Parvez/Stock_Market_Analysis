package com.trading.stockanalysis.modal;

public class Stock {
	
    private String symbol;
    private String name;
    private String sector;
    private String industry;
    private double price;
    private double open;
    private double close;
    private long volume;
    private double change;
    private double changePercentage;
    private double high;
    private double low;
    
    public Stock() {
    	
    }
    public Stock(String symbol, String name, String sector, String industry, double price, double changePercentage, double high, double low) {
        this.symbol = symbol;
        this.name = name;
        this.sector = sector;
        this.industry = industry;
        this.price = price;
        this.high = high;
        this.low = low;
        this.changePercentage = changePercentage;
    }
    
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getChangePercentage() {
		return changePercentage;
	}
	public void setChangePercentage(double changePercentage) {
		this.changePercentage = changePercentage;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}

      
}