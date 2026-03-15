import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import { Link } from "react-router-dom";
import Navbar from "./Navbar"; // Import Navbar
import './Home.css';


function Home() {
  return (
    <div>
      {/* Navigation Bar */}
      <Navbar /> 

      {/* Hero Section */}
      <header className="text-center text-dark py-5" style={{ backgroundColor: "#ffa07a	" }}>
        <h2>Welcome to BullBear Pulse</h2>
        <p className="lead">Your go-to platform for real-time stock market insights.</p>
      </header>

      {/* Features Section */}
      <section className="container text-center my-5">
        <div className="row">          
          <div className="col-md-4">
            <i className="fas fa-bullhorn fa-3x text-danger"></i>
            <h3 className="mt-3"><Link to="/live-trends">Top Gainers & Losers</Link></h3>
            <p>Track the stocks making the biggest moves.</p>
          </div>
          <div className="col-md-4">
            <i className="fas fa-3x text-primary"></i>
            <h3 className="mt-3"></h3>
            <p></p>
          </div>
          <div className="col-md-4">
            <i className="fas fa-search fa-3x text-primary"></i>
            <h3 className="mt-3"><Link to="/smart-analysis">Smart Stock Analysis</Link></h3>
            <p>Analyze your favorite stocks with advanced tools.</p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <div class="footerdiv">
      <footer className="text-white text-center p-3" style={{ backgroundColor: "#343a40" }}>
        <p>&copy; 2025 BullBear Pulse. All rights reserved.</p>
      </footer>
      </div>
    </div>
  );
}

export default Home;
