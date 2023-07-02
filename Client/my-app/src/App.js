import { MyGrid } from "./Grid";
import { Navigation } from "./Navigation";
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route, Router } from "react-router-dom";
import { ImportPage } from "./import";
import { useLocation } from "react-router-dom";
import { Rules } from "./rules";

export default function App() {
	
  
  return (
       <>
	  	<BrowserRouter>
	  	<Navigation />
	  		<Routes>
	  			<Route path="/" element={<MyGrid />} />
	  			<Route path="ImportPuzzle" element={<ImportPage />} /> 
	  			<Route path="rules" element={<Rules />} />
	  		</Routes>
	  	</BrowserRouter>
	  	
	  </>
	);
}

