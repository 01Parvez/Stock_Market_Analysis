import React from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark" style={{ backgroundColor: "#C04000" }}>
      <div className="container">
      <h1 className="display-4 fw-bold"><font color="#FFFFFF">BullBear Pulse</font></h1>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
          <li className="nav-item"><a className="nav-link" href="http://localhost:3000"><font size="5">Home</font></a></li>
              <li className="nav-item"><a className="nav-link" href="/market-news"><font size="5">Market News</font></a></li>
              <li className="nav-item"><a className="nav-link" href="/contact"><font size="5">Contact</font></a></li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
