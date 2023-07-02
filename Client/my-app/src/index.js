import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import "./App.css";
import { MyGrid } from "./Grid";
import { Navigation } from "./Navigation";
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ImportPage } from "./import";



const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	<React.StrictMode>
 	 	<App />
 	 </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
