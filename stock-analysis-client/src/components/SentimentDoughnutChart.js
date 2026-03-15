import React from "react";
import { Doughnut } from "react-chartjs-2";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend
} from "chart.js";


ChartJS.register(ArcElement, Tooltip, Legend);

const SentimentDoughnutChart = ({ positive, negative, neutral }) => {
  const data = {
    labels: ["Positive", "Negative", "Neutral"],
    datasets: [
      {
        label: "Sentiment Analysis",
        data: [positive, negative, neutral],
        backgroundColor: ["#4caf50", "#f44336", "#ffeb3b"],
        borderColor: ["#388e3c", "#d32f2f", "#fbc02d"],
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "bottom",
        labels: {
          color: "#333",
          font: {
            size: 14,
            weight: "bold"
          }
        }
      },
      tooltip: {
        callbacks: {
          label: function (context) {
            return `${context.label}: ${context.raw}`;
          }
        }
      }
    }
  };

  return (
      <Doughnut data={data} options={options} />   
  );
};

export default SentimentDoughnutChart;
