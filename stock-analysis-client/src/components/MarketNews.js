import React, { useEffect, useState } from "react";
import Navbar from "./Navbar"; // Import Navbar

const MarketNews = () => {
  const [news, setNews] = useState([]);
  const apiKey = "4e3f112c5988419d949e6e2d78932831"; // Replace with your NewsAPI key

  //https://newsapi.org/v2/everything?q=india&apiKey=4e3f112c5988419d949e6e2d78932831
  //https://newsapi.org/v2/top-headlines?category=business&apiKey=4e3f112c5988419d949e6e2d78932831
  useEffect(() => {
    const fetchNews = async () => {
      try {
        const response = await fetch(
          "https://newsapi.org/v2/everything?q=indian stocks&apiKey=4e3f112c5988419d949e6e2d78932831"
        );
        const data = await response.json();
        console.info("market news:", data);
        setNews(data.articles || []);
      } catch (error) {
        console.error("Error fetching market news:", error);
      }
    };

    fetchNews();
  }, [apiKey]);

  return (   

    <div>
    <Navbar />
    <h2 className="text-center text-dark py-5" style={{ backgroundColor: "#ffa07a	" }}>📰 Market News</h2>
    <div className="container mt-4">

      
      {news.length > 0 ? (
        <div className="row">
          {news.map((article, index) => (
            <div key={index} className="col-md-6 col-lg-4">
              <div className="card mb-4 shadow-sm">
                {article.urlToImage && (
                  <img
                    src={article.urlToImage}
                    className="card-img-top"
                    alt="News"
                  />
                )}
                <div className="card-body">
                  <h5 className="card-title">{article.title}</h5>
                  <p className="card-text">{article.description}</p>
                  <a
                    href={article.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="btn btn-primary"
                  >
                    Read More
                  </a>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-muted text-center">No market news available</p>
      )}
    </div>
    </div>
  );
};

export default MarketNews;