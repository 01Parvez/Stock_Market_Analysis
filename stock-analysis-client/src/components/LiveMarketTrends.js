import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import Navbar from "./Navbar"; // Import Navbar




const LiveMarketTrends = () => {
  // Sample stock data (Replace with API calls in real-world apps)
  // const topGainers = [
  //   { name: "TCS", price: "₹3,800", change: "+2.5%" },
  //   { name: "Reliance", price: "₹2,750", change: "+1.8%" },
  //   { name: "Infosys", price: "₹1,600", change: "+3.2%" },
  // ];

  // const topLosers = [
  //   { name: "HDFC Bank", price: "₹1,450", change: "-2.1%" },
  //   { name: "ICICI Bank", price: "₹950", change: "-1.6%" },
  //   { name: "SBI", price: "₹620", change: "-3.4%" },
  // ];

  // const fiftyTwoWeekHighsLows = [
  //   { name: "L&T", high: "₹3,200", low: "₹2,500", current: "₹3,100" },
  //   { name: "Bharti Airtel", high: "₹1,100", low: "₹800", current: "₹1,050" },
  // ];

  const [topGainers, setTopGainers] = useState([]);
  const [topLosers, setTopLosers] = useState([]);
  const [weekHighLow, setWeekHighLow] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch Top Gainers
        const gainersResponse = await fetch("http://localhost:8080/api/stocks/top-gainers");
        const gainersData = await gainersResponse.json();
        setTopGainers(Array.isArray(gainersData) ? gainersData : []);

        // Fetch Top Losers
        const losersResponse = await fetch("http://localhost:8080/api/stocks/top-losers");
        const losersData = await losersResponse.json();
        setTopLosers(Array.isArray(losersData) ? losersData : []);

        // Fetch 52-Week High/Low
        const highLowResponse = await fetch("http://localhost:8080/api/stocks/52week-high-low");
        const highLowData = await highLowResponse.json();
        setWeekHighLow(Array.isArray(highLowData) ? highLowData : []);

      } catch (error) {
        console.error("Error fetching stock data:", error);
      }
    };

    fetchData();
  }, []);


  return (
    <div>

      {/* Navigation Bar */}
      <Navbar /> 

      {/* Page Header */}
      <header className="text-center text-white py-4" style={{ backgroundColor: "#ff6f61" }}>
        <h2 className="fw-bold">📊 Top gainers & losers</h2>
        <p>Track the latest stock movements in real time!</p>
      </header>

      <div className="container my-5">
        {/* Top Gainers Section */}
        <div className="card mb-4 shadow-lg rounded">
          <div className="card-header bg-success text-white fw-bold">
            <i className="fas fa-arrow-up"></i> Top Gainers
          </div>
          <ul className="list-group list-group-flush">
            {topGainers.map((stock, index) => (
              <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                <span>
                  <img src="/assets/images/bull-icon.png" alt="Bull" style={{ width: "20px", marginRight: "10px" }} />
                  {stock.name}
                </span>
                <span>{stock.price} ({stock.change})</span>
              </li>
            ))}
          </ul>
        </div>

        {/* Top Losers Section */}
        <div className="card mb-4 shadow-lg rounded">
          <div className="card-header bg-danger text-white fw-bold">
            <i className="fas fa-arrow-down"></i> Top Losers
          </div>
          <ul className="list-group list-group-flush">
            {topLosers.map((stock, index) => (
              <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                <span>
                  <img src="/assets/images/bear-icon.png" alt="Bear" style={{ width: "20px", marginRight: "10px" }} />
                  {stock.name}
                </span>
                <span>{stock.price} ({stock.change})</span>
              </li>
            ))}
          </ul>
        </div>

        {/* 52-Week Highs and Lows Section */}
        <div className="card mb-4 shadow-lg rounded">
          <div className="card-header bg-warning text-dark fw-bold">
            <i className="fas fa-chart-line"></i> 52-Week Highs & Lows
          </div>
          <ul className="list-group list-group-flush">
            {weekHighLow.map((stock, index) => (
              <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                <span>
                  <img src="/assets/images/52-week-high-low.png" alt="High Low" style={{ width: "20px", marginRight: "10px" }} />
                  {stock.name}
                </span>
                <span>High: {stock.high} | Low: {stock.low} | Current: {stock.current}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      {/* Footer */}
      <footer className="text-white text-center p-3" style={{ backgroundColor: "#343a40" }}>
        <p>&copy; 2025 BullBear Pulse. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default LiveMarketTrends;

