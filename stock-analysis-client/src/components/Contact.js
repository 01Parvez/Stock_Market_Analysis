import React, { useState } from "react";
import Navbar from "./Navbar"; // Import Navbar

const Contacts = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    message: "",
  });

  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form submitted:", formData);
    setSubmitted(true);
    setFormData({ name: "", email: "", message: "" });
  };

  return (
    <div>
    {/* Navigation Bar */}
    <Navbar /> 
    <h2 className="text-center text-dark py-5" style={{ backgroundColor: "#ffa07a	" }}>📞 Contact Us</h2>
    <div  className="container mt-5">
      
      <div className="row mt-4">
        {/* Contact Form */}
        <div className="col-md-6">
          <h4>Send us a message</h4>
          {submitted && <p className="text-success">✅ Message Sent Successfully!</p>}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">Name</label>
              <input
                type="text"
                name="name"
                className="form-control"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Email</label>
              <input
                type="email"
                name="email"
                className="form-control"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
            <div className="mb-3">
              <label className="form-label">Message</label>
              <textarea
                name="message"
                className="form-control"
                rows="4"
                value={formData.message}
                onChange={handleChange}
                required
              ></textarea>
            </div>
            <button type="submit" className="btn btn-primary">Submit</button>
          </form>
        </div>

        {/* Contact Information */}
        <div className="col-md-6">
          <h4>Our Contact Details</h4>
          <p><strong>📍 Address:</strong> 123 Market Street, Mumbai, India</p>
          <p><strong>📧 Email:</strong> support@bullbearpulse.com</p>
          <p><strong>📞 Phone:</strong> +91-1111111111</p>
          <p><strong>⏰ Working Hours:</strong> Mon-Fri, 9 AM - 6 PM</p>

          <h5>Follow Us</h5>
          <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="me-3">
            <i className="fab fa-facebook fa-2x text-primary"></i>
          </a>
          <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="me-3">
            <i className="fab fa-twitter fa-2x text-info"></i>
          </a>
          <a href="https://linkedin.com" target="_blank" rel="noopener noreferrer">
            <i className="fab fa-linkedin fa-2x text-primary"></i>
          </a>
        </div>
      </div>
    </div>
    </div>
  );
};

export default Contacts;