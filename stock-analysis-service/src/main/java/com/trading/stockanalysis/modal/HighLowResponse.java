package com.trading.stockanalysis.modal;

import java.util.List;


	public class HighLowResponse {
	    private List<Stock> high;
	    private List<Stock> low;

	    public HighLowResponse(List<Stock> high, List<Stock> low) {
	        this.high = high;
	        this.low = low;
	    }

		public List<Stock> getHigh() {
			return high;
		}

		public void setHigh(List<Stock> high) {
			this.high = high;
		}

		public List<Stock> getLow() {
			return low;
		}

		public void setLow(List<Stock> low) {
			this.low = low;
		}

	    
}