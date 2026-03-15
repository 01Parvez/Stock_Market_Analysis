import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Routes, Route } from "react-router-dom";
import "@fortawesome/fontawesome-free/css/all.min.css";
import Home from "./components/Home";
import LiveMarketTrends from "./components/LiveMarketTrends";
import MarketNews from "./components/MarketNews";
import Contacts from "./components/Contact";
import SmartAnalysis from "./components/SmartAnalysis";



function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/live-trends" element={<LiveMarketTrends />} /> {/* Correct Route */}
      <Route path="/market-news" element={<MarketNews />} />
      <Route path="/smart-analysis" element={<SmartAnalysis />} /> {/* Correct Route */}
      <Route path="/contact" element={<Contacts />} />
  </Routes>
  );
}

export default App;
