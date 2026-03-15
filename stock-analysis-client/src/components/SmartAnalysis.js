import React, { useEffect, useState } from "react";
import Navbar from "./Navbar"; // Import Navbar
import "bootstrap/dist/css/bootstrap.min.css";
import SentimentDoughnutChart from "./SentimentDoughnutChart";


const SmartAnalysis = () => {
  const [stockData, setStockData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [companyList, setCompanyList] = useState([]);
  const [filteredList, setFilteredList] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [sentimentData, setSentimentData] = useState([]);

  useEffect(() => {
    setCompanyList([
      "TCS",
      "Infosys",
      "AAPL",
      "HDFC Bank",
      "ICICI Bank",
      "IBM",
      "HCL Technologies",
      "Bajaj Finance"
    ]);
  }, []);

    // Filter list when search text changes
    useEffect(() => {
      if (searchText.trim() === "") {
        setFilteredList([]);
      } else {
        const results = companyList.filter(name =>
          name.toLowerCase().includes(searchText.toLowerCase())
        );
        setFilteredList(results);
      }
    }, [searchText, companyList]);
 
    // Handle selection
  const handleSelect = (name) => {
    setSearchText(name);
    setFilteredList([]);
  };

  const handleSearch = async () => {
    if (!searchText) return;
    try {
      setLoading(true);
      setError("");

      // Replace with your backend endpoint
      const response = await fetch("http://localhost:8080/api/stocks/getStockPrices/"+searchText);
      const priceData = await response.json();
      console.log(priceData);
      setStockData(priceData);

      const gainersResponse = await fetch("http://localhost:8080/api/stocks/getSentiments/"+searchText);
      const sentimentData = await gainersResponse.json();
      setSentimentData(sentimentData);
    } catch (err) {
      setError("Could not fetch data. Try again.");
      setStockData(null);
    } finally {
      setLoading(false);
      setFilteredList([]);
    }
  };

  // Example sentiment values
  const positive = sentimentData.positive;
  const negative = sentimentData.negative;
  const neutral = sentimentData.neutral;

  const chartData = {
    labels: ["Positive", "Neutral", "Negative"],
    datasets: [
      {
        label: "Sentiment",
        data: [
          sentimentData.Positive,
          sentimentData.Neutral,
          sentimentData.Negative
        ],
        backgroundColor: ["#28a745", "#ffc107", "#dc3545"],
        borderWidth: 1,
      }
    ]
  };

  return (
    <div>
     {/* Navigation Bar */}
     <Navbar /> 

     {/* Page Header */}
     <header className="text-center text-white py-4" style={{ backgroundColor: "#ff6f61" }}>
       <h2 className="fw-bold">📊 Smart Analysis of a Stock</h2>
       <p>Uncover Trends, Sentiment & Growth Potential Instantly!</p>
     </header>

    <div className="container mt-5">      

      {/* Search Input */}
      <div className="container mt-4" style={{ maxWidth: "400px" }}>
      <div className="input-group">
      <input
        type="text"
        className="form-control"
        placeholder="Search company..."
        value={searchText}
        onChange={(e) => setSearchText(e.target.value)}
      />      
      <button className="btn btn-success" onClick={handleSearch}>Analyze</button>
      </div>

      {filteredList.length > 0 && (
        <ul className="list-group"  style={{
          position: "absolute",
          zIndex: 1000,
          width: "100%",
          maxWidth: "400px"
        }}>
          {filteredList.map((name, index) => (
            <li
              key={index}
              className="list-group-item list-group-item-action"
              onClick={() => handleSelect(name)}
              style={{ cursor: "pointer" }}
            >
              {name}
            </li>
          ))}
        </ul>
      )}

    </div>

      {loading && <p className="text-center">🔄 Loading analysis...</p>}
      {error && <p className="text-danger text-center">{error}</p>}
      
      <div className="container mt-4" style={{ maxHeight: "10px" }}></div>
      
  <div className="container mt-4">
  <div className="row">
    {/* Stock Data */}
    {stockData && ( 
      <div className="col-md-4 mb-4">
      <div className="p-3 border rounded bg-light">
        
        {/* Replace with your stockData content */}
        <h4 className="mb-3 text-primary fw-bold">Stock Data of {stockData.name || searchText.toUpperCase()}</h4>

 <table>
  <tr>
    <td><span className="fw-bold">Current Price: </span></td>
    <td><span className="badge bg-success fs-6">$ {stockData.open}</span></td>
  </tr>
  <tr>
    <td><span className="fw-bold">High Price: </span></td>
    <td> <span className="badge bg-warning text-dark fs-6">$ {stockData.high}</span></td>
  </tr>
  <tr>
  <td><span className="fw-bold">Low Price: </span></td>
    <td><span className="badge bg-danger fs-6">$ {stockData.low}</span></td>
  </tr>
  <tr>
  <td><span className="fw-bold">Open Price: </span></td>
    <td><span className="badge bg-info text-dark fs-6">$ {stockData.close}</span></td>
  </tr>
  <tr>
    <td><span className="fw-bold">Volume: </span></td>
    <td><span className="badge bg-secondary fs-6">$ {stockData.volume}</span></td>
  </tr>
</table>
             
      </div>
    </div>
    )}

    {/* Doughnut Chart */}   
       
      {positive && negative && neutral && (
      <div className="col-md-4 mb-10">
      <div className="p-3 border rounded bg-light">
      <h4 className="mb-10 text-primary fw-bold">Sentiment data of {stockData.name || searchText.toUpperCase()}</h4>
      <div className="col-md-6">
          <SentimentDoughnutChart
            positive={positive}
            negative={negative}
            neutral={neutral}
          />
      </div>
        </div>
    </div>
      )}
    
  </div>
</div>

     
    </div>
    
      {/* Footer */}
      <footer className="text-white text-center p-3" style={{ backgroundColor: "#343a40" }}>
        <p>&copy; 2025 BullBear Pulse. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default SmartAnalysis;
