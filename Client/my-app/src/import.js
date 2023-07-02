import { useState } from "react";
import { Col, Container, DropdownButton } from "react-bootstrap";
import { Carousel, Alert } from "react-bootstrap";
import ExampleOne from './example1.jpg';
import ExampleTwo from './example2.jpg';
import ExampleThree from './example3.jpg';
import {Accordion, Spinner, Button} from "react-bootstrap";

import "./ImportPage.css";

export function ImportPage(){
	let colorSet = new Set();
	
	//piece number to color
	let myMap = new Map();
	
	var _validFileExtensions = [".txt", ".doc", ".data"]; 
	const[dataShow, setDataShow]  = useState(false);
	const[alertShow, setAlertShow]  = useState(false);
	
	const[data, setData] = useState(null);
	
	const[moves, setMoves] = useState(new Array);
	const[initialGrid, setInitialGrid]  = useState(new Array);
	const[finalGrid, setFinalGrid]  = useState(new Array);
	const[colSize, setColSize] = useState(6);
	const[loading, setLoading] = useState(false);
	const[error, setError] = useState(false);
	const[errConnectionRefused, setErrConnectionRefused] = useState(false);
	
	const handleSubmit = (event) =>{
		
		event.preventDefault();
		let file = event.currentTarget[0].value;
		let valid = false;
		for(let i = 0; i < _validFileExtensions.length; ++i){
			let ext = _validFileExtensions[i];
			if(ext.toLowerCase() === file.substr(file.length-ext.length, file.length).toLowerCase()){
				valid = true;
			}
		}
		
		if(!valid){
			event.preventDefault();
			event.stopPropagation();
			let message = "Invalid file extension.\nValid file extensions:";
			for(let i = 0; i < _validFileExtensions.length; ++i){
				message = message.concat(" (" + _validFileExtensions[i] +")");
			}
			
			alert(message);
			return;
		}
		
		
		if(!event.currentTarget.checkValidity()){
			event.preventDefault();
			event.stopPropagation();
			event.currentTarget.classList.add("was-validated");
			return;
		}
		setLoading(true);
		postPuzzle(event.currentTarget[0].files[0]);
	}
	
	const postPuzzle = async e =>{
		let reader = new FileReader();
		let text;
		//gets called once reader is done reading file
		reader.onload = function () {
		    text = reader.result;
		    post(text);
		  };
		
		reader.readAsText(e);
		
		//console.log("uploaded something");
		
		
	}
	
	const post = async (text) =>{
		let errorTmp = false;
		
		const response = await fetch("http://localhost:8080/api/murf/puzzlesolver/import",{
			method: "POST",
			headers: {
			   'Accept': 'application/json',
			   'Content-Type': 'application/json',
			 },
			body: JSON.stringify({
				data: text
			})
		}).catch(async ()=>{
			//console.log("json catch fetch error");
			errorTmp = true;
			setErrConnectionRefused(true);
			return;
		});
		if(response == undefined){
			setLoading(false);
			return;
		}
		const solution = await response.json().catch(() =>{
			//console.log("json catch error");
			errorTmp = true;
			setError(true);
			setLoading(false);
			return;
		})
		
		//file uploaded succesfully
		//console.log(solution);
		setLoading(false);
		if(solution == undefined){
			console.log("undefined");
			return;
		}
		//500 internal SERVER ERROR
		if(solution.status == 500){
		//	console.log("internal server error");
			setError(true);
			return;
		}
		if(errorTmp){
			return;
		}
		setDataShow(true);
		setAlertShow(true);
		setMoves(solution.moves);
		setInitialGrid(solution.initialGrid);
		
		setColSize(solution.initialGrid[0].length);
		setFinalGrid(solution.finalGrid);
		
	}

	
	return (
		<Container className="import">
			<Accordion className="mt-3">
					<Accordion.Item eventKey="0"> 
						<Accordion.Header className="formatA">Format</Accordion.Header>
							<Accordion.Body>
							<div className="col-1 w-50 justify-content-left">
								<h3 className="">Import Format</h3>
								<p className="">First Line: (row size) (col size)</p>
								<p className="">Other Lines: (row) (col) (w) (h) (movement)</p>
								<p>Movement can be one of the following: "b", "h", "v", "n"</p>
								<p>REMINDER: The Second Line is read as the Goal Block: <Pixel finalColor={"goalBlock"}/></p>
							</div>
							</Accordion.Body>
					</Accordion.Item>
			</Accordion>
			<form className="input-group mt-5 w-50 pt-5 mx-auto" onSubmit={handleSubmit}>
			  <input required type="file" className="form-control" id="inputGroupFile02" accept=".txt,.doc" />
			  <button hidden={loading} type="submit" role="button" className="btn btn-outline-dark">Upload</button>
			 <Button variant="dark" hidden={!loading} disabled>
			 	<Spinner animation="border" variant="info" as="span"/>
			 </Button>
			 
			</form>
			<Alert className="w-50 mx-auto mt-2" dismissible show={error} onClose={() => setError(false)} variant="danger">
				<Alert.Heading>Error Uploading: ERROR 500</Alert.Heading>
					<p>Please try a different puzzle, or try again later.</p>
					<p>Thank you</p>
			</Alert>
			<Alert className="w-75 mx-auto mt-2" dismissible show={errConnectionRefused} onClose={() => setErrConnectionRefused(false)} variant="danger">
				<Alert.Heading>Error Uploading: ERROR CONNECTION REFUSED -- FETCH FAILED</Alert.Heading>
					<p>Please try again later.</p>
					<p>Thank you</p>
			</Alert>
			<div hidden={!dataShow}>
				<Alert className="w-50 mx-auto mt-2" variant="primary" dismissible show={alertShow} onClose={()=>{setAlertShow(false)}}>
					<Alert.Heading>Upload was successful!</Alert.Heading>
					<p>Click on the dropsdowns to look at the data!</p>
				</Alert>
				<Accordion>
					<Accordion.Item eventKey="0"> 
						<Accordion.Header>Initial Grid</Accordion.Header>
							<Accordion.Body>
								<div className="mx-auto mygrid" style={{ display: 'grid', gridTemplateRows: 'repeat(3, 1fr)', gridTemplateColumns: `repeat(${colSize}, 1fr)`, width: '210px', margin: '0 auto', columnGap: "0"}}>
								 {	
									initialGrid.map((arr, rowI)=>{
										//console.log("inside arr: ",arr);
										return(<>
										{
											
											arr.map((elem, colI) =>{
												
												if(elem === "Z"){elem = "goalBlock";}
												else if(elem === "."){elem = "lightGrey"}
												else if(myMap.has(elem)){
													elem = myMap.get(elem);
												}
												else{
													let ckey = elem;
													elem = getRandColor();
													while(colorSet.has(elem)){
														elem = getRandColor();
													}
													colorSet.add(elem);
													myMap.set(ckey, elem);
												}
												return <Pixel key={(rowI*colSize)+colI} finalColor={elem} row={rowI +1} col={colI+1}/>;
											})
										}</>);
									})
								}
								</div>
							</Accordion.Body>
					</Accordion.Item>
				</Accordion>
				<Accordion>
					<Accordion.Item eventKey="0"> 
						<Accordion.Header>Final Grid</Accordion.Header>
							<Accordion.Body>
								<div className="mx-auto mygrid" style={{ display: 'grid', gridTemplateRows: 'repeat(3, 1fr)', gridTemplateColumns: `repeat(${colSize}, 1fr)`, width: '210px', margin: '0 auto', columnGap: "0"}}>
								 {	
									finalGrid.map((arr, rowI)=>{
										return(<>
										{
											arr.map((elem, colI) =>{
												if(elem === "Z"){elem = "goalBlock";}
												else if(elem === "."){elem = "lightGrey"}
												else{
													elem = myMap.get(elem);
												}
		
												return <Pixel key={(rowI*colSize)+colI} finalColor={elem} row={rowI +1} col={colI+1}/>;
											})
										}</>);
									})
								}
								</div>
							</Accordion.Body>
					</Accordion.Item>
				</Accordion>
				<Accordion>
					<Accordion.Item eventKey="0"> 
						<Accordion.Header>Move List</Accordion.Header>
							<Accordion.Body>
							 {	
								moves.map((move, mIndex)=>{
									if(mIndex == 0){
										return (<h3 key={mIndex}>{move}</h3>);
									}
									let words = move.split(" ");
									let elem = words[1];
									if(elem == "Z"){
										elem = "goalBlock";
										words.shift();
										words.shift();
										let newMove = words.join(" ");
										return (<h3 key={mIndex}>Piece <Pixel finalColor={elem} /> {newMove}</h3>);	
									}
									
									elem = myMap.get(words[1]);
									words.shift();
									words.shift();
									let newMove = words.join(" ");
									
									return (<h3 key={mIndex}>Piece <Pixel finalColor={elem} /> {newMove}</h3>);
								})
							}
							</Accordion.Body>
					</Accordion.Item>
				</Accordion>
				
			</div>
			
			<div className="row pt-5 ">
				
				<div className="col-4 w-100"> 
					<Carousel className="pt-3 mx-auto w-50 pb-5" fade>
					  <Carousel.Item>
					    <img
					      className="d-block myImg"
					      src={ExampleOne}
					      alt="4x4 puzzle"
					      
					    />
					    <Carousel.Caption className="bg-secondary">
					      <h3>4x4 Puzzle</h3>
					      <p>Blocks with 'b' can move both Horizontally and Vertically</p>
					    </Carousel.Caption>
					  </Carousel.Item>
					  <Carousel.Item>
					    <img
					      className="d-block myImg"
					      src={ExampleTwo}
					      alt="6x6 puzzle"
					    />
					
					    <Carousel.Caption className="bg-secondary">
					      <h3>6x6 Puzzle</h3>
					      <p>Restraints on directions (h, v)</p>
					      <p>Blocks with 'h' can only move horizontally</p>
					      <p>Blocks with 'v' can only move vertically</p>
					    </Carousel.Caption>
					  </Carousel.Item>
					</Carousel>
				</div>
				
			</div>
		</Container>
	);
}

function Pixel ({row, col, finalColor}) {

  if(finalColor == "goalBlock"){
	  return <button className="finalGridPixels goalBlock" row={row} col={col} style={{ height: '20px', width: '20px', margin: '1px' }} />
  }
  if(finalColor !== "lightGrey")
  	return <button className="finalGridPixels" row={row} col={col} color={finalColor} style={{ height: '20px', width: '20px', backgroundColor: finalColor, margin: '1px' }} />
	
else
	return <button className="finalGridPixels" row={row} col={col} color={finalColor} style={{ height: '20px', width: '20px', backgroundColor: finalColor, margin: '1px' }} />
}

function getRandColor(){
var makeColorCode = '0123456789ABCDEF';
  var code = '#';
  for (var count = 0; count < 6; count++) {
     code =code+ makeColorCode[Math.floor(Math.random() * 16)];
  }
  return code;
}