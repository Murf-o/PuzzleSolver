import { useState, useEffect} from "react";
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Alert } from "react-bootstrap";
import { createContext, useContext } from "react";
import "./Grid.css";
import { Accordion, Spinner } from "react-bootstrap";


function getRandColor(){
var makeColorCode = '0123456789ABCDEF';
  var code = '#';
  for (var count = 0; count < 6; count++) {
     code =code+ makeColorCode[Math.floor(Math.random() * 16)];
  }
  return code;
}

export function MyGrid(){
	var colorSet = new Set();
	const[blocksPlaced, setBlocksPlaced] = useState(new Set());
	const[movementMap, setMovementMap] = useState(new Map());
	//console.log('ip=', window.location.host);
	let ip = window.location.host;
	let parse = ip.indexOf(":");
	ip = ip.substring(0, parse);
	console.log("ip: ", ip);
	
	let r = 4;
	let c = 4;
	if(localStorage.getItem("rowSize") !== null){
		r = parseInt(localStorage.getItem("rowSize"));
	}
	if(localStorage.getItem("colSize") !== null){
		c = parseInt(localStorage.getItem("colSize"));
	}
	
	const[rowSize, setRowSize] = useState(r);
	const[colSize, setColSize] = useState(c);
	const[loading, setLoading] = useState(false);
	
	const[grid, setGrid] = useState(new Array(rowSize*colSize).fill(0));
	
	const [color, setColor] = useState('lightGrey');
	const [invalidAlert, setInvalidAlert] = useState(false);
	
	const[goalBlockMode, setGoalBlockMode] = useState(false);
	const[goalBlock, setGoalBlock] = useState(undefined);
	const[solutionFound, setSolutionFound] = useState(false);
	const[initialGrid, setInitialGrid] = useState(new Array);
	const[moves, setMoves] = useState(new Array);
	const[finalGrid, setFinalGrid] = useState(new Array);
	const[invalidGoalBlock, setInvalidGoalBlock] = useState(false);
	const[noSolution, setNoSolution] = useState(false);
	const[noGoalSelectedError, setNoGoalSelectedError] = useState(false);
	const[movement, setMovement] = useState("b");
	const[activelyBlocking, setActivelyBlocking] = useState(false);
	const[newColorError, setNewColorError] = useState(false);
	const[movementArr, setMovementArr] = useState(new Array);
	const[movementValueArr, setMovementValueArr] = useState(new Array);
	//actively blocking, set goal block to it, error
	const[blockNotPlacedErr, setBlockNotPlacedErr] = useState(false);
	const[error, setError] = useState(false);
	
	useEffect(() =>{
		setGrid(new Array(rowSize*colSize).fill(0));
	}, [rowSize, colSize]);
	
	useEffect(()=>{
		setColor(getRandColor);
	}, [])
	
	const handleSubmit = (event) =>{
		
		
		let row = event.target[0].value;
		let col = event.target[1].value;
		if(!event.currentTarget.checkValidity()){
			event.preventDefault();
			event.stopPropagation();
			event.currentTarget.classList.add("was-validated");
			return;
		}
		setRowSize(row);
		setColSize(col);
		localStorage.setItem("rowSize", row);
		localStorage.setItem("colSize", col);
		/*document.querySelectorAll(".pixels").forEach((pixel) =>{
			
			pixel.style.backgroundColor = "lightGrey";
		})*/
		event.currentTarget.classList.add("was-validated");
		
	}
	
	function postPuzzle(grid, rowSize, colSize, goalBlock){
		//console.log("posting");
		//convert to 2d array
		let newGrid = new Array(rowSize).fill(0).map(() => new Array(colSize));
		let row = 0;
		for(let i = 0; i < rowSize*colSize; i += +colSize){
			for(let j = 0;j < colSize; ++j)
			{
				newGrid[row][j] = grid[i+j];
			}
			++row;
		}
		
		post(newGrid, rowSize, colSize, goalBlock);
	}

	const post = async (grid, rowsize, colsize, goalBlock) =>{
		let error = false;
		
		//convert js map to be compatible for JSON.stringify
		const myMap = Object.fromEntries(movementMap);
		
		
		const response = await fetch(`http://${ip}:8080/api/murf/puzzlesolver/createdPuzzle`,{
			method: "POST",
			headers: {
			   'Accept': 'application/json',
			   'Content-Type': 'application/json',
			 },
			body: JSON.stringify({
				"grid": grid,
				"rowSize": rowsize,
				"colSize": colsize,
				"goalBlock": goalBlock,
				"movementMap": myMap
			})
		}).catch(() =>{
			setLoading(false);
			//console.log("fetch failed");
			error =true;
			//set error here
			return;
		});
		if(response == undefined){
			setLoading(false);
			setError(true);
			return;
		}
		const solution = await response.json().catch(async(e) =>{
			setLoading(false);
			setError(true);
			error = true;
			return;
		})
		setLoading(false);
		if(solution == undefined){
			setError(true);
			return;
		}
		//500 internal SERVER ERROR
		if(solution.status == 500){
			setError(true);
			return;
		}
		//console.log(solution);
		
		if(!error){
			//if no solution
			if(!solution.solvable){
				setNoSolution(true);
				return
			}
			setSolutionFound(true);
			setInitialGrid(solution.initialGrid);
			setMoves(solution.moves);
			setFinalGrid(solution.finalGrid);
		}
	}
	
	return(
		<div className="PuzzleGrid">
			
			<Container fluid className="pb-2" style={{backgroundColor: "black"}}>
				
				<h1 className="pt-2 text-light lh-1 text-center" >Puzzle Size</h1>
				<h1 className="text-warning lh-1 text-center">{rowSize}x{colSize}</h1>
				
				<form className="needs-validation" noValidate onSubmit={handleSubmit}>
					<div className="input-group mb-2 mx-auto w-50" >
						<label className="input-group-text bg-warning">Row</label>
							<div className="form-floating me-1">
								<input defaultValue={r} max={30} required min={1} type="number" className="form-control" id="rowInput" placeholder="New Row #"/>
								<label htmlFor="rowInput">New Row #</label>
							</div>
							
						<label className="input-group-text bg-warning ms-1">Column</label>
							<div className="form-floating">
								<input defaultValue={c} max={30} maxLength={2} required  min={1} type="number" className="form-control" id="colInput" placeholder="New Column #"/>
								<label htmlFor="colInput">New Column #</label>
							</div>
						<button type="submit" className=" ms-5 btn btn-secondary">Update</button>
					</div>	
				</form>
			</Container>
			<ColorContext.Provider value={{setBlockNotPlacedErr, blocksPlaced, setActivelyBlocking, color, setColor, goalBlockMode, setGoalBlockMode, goalBlock, setGoalBlock, setInvalidGoalBlock}}>
				<Container fluid className="pt-5">	
				<Row className="align-items-center">
					<Col className="puzzleCol">
						<Col className="center my-auto">
							<h4>Current Color: </h4>
							<div className="ms-3 my-auto">
							<Pixel finalColor={color}/>
							</div>
						</Col>
						<Col className="center" hidden={!goalBlock}>
							<h4>Goal Block Color: </h4>
							<div className="ms-3 my-auto">
							<Pixel finalColor={goalBlock} />
							</div>
						</Col>
						<Col className="center" hidden={goalBlock}>
							<h4>No Goal Block Selected</h4>
						</Col>
					</Col>
					<Col className="puzzleCol mx-auto">
						<div className={"mx-auto mygrid" + `${(colSize >= 21) ? " ms-5" : ""}`} style={{ display: 'grid', gridTemplateRows: 'repeat(3, 1fr)', gridTemplateColumns: `repeat(${colSize}, 1fr)`, width: '210px', margin: '0 auto', columnGap: "0"}}>
						{
							grid.map((arr, arrIndex) =>{
									
									return(<Pixel  row={Math.floor(arrIndex/colSize)} col={(arrIndex%colSize)} grid={grid} colSize={colSize} key={arrIndex}/>);
								})
						}</div>
					</Col>
					<Col></Col>
				</Row>
					<Col className="mt-4 text-center">
						<Button active={movement == "n" ? true: false} type="button" variant="outline-secondary" className="mx-auto me-2" onClick={() => setMovement("n")}> 
						N</Button>
						<Button active={movement == "h" ? true: false} type="button" variant="outline-dark" className="mx-auto me-2" onClick={() => setMovement("h")}> 
						H</Button>
						<Button active={movement == "v" ? true: false} type="button" variant="outline-danger" className="mx-auto me-2" onClick={() => setMovement("v")}> 
						V</Button>
						<Button active={movement == "b" ? true: false} type="button" variant="outline-info" className="mx-auto me-2" onClick={() => setMovement("b")}> 
						B</Button>
						<Button onClick={()=>{
							//'color' is the current color
							//verify here whether block being added is valid
							if(!validBlock(grid, color, colSize)){
								//console.log("invalid");
								setInvalidAlert(true);
								return;
							}
							movementMap.set(color, movement);
							blocksPlaced.add(color);
							movementArr.push(color);
							movementValueArr.push(movement);
							//console.log("movement map set: ", movementMap.get(color));
							let randColor = getRandColor;
							
							while(colorSet.has(randColor)){
								randColor = getRandColor;
							}
							colorSet.add(randColor);
							setColor(randColor);
							
							setInvalidAlert(false);
							setActivelyBlocking(false);
							}
						} type="button" variant="outline-primary" className="mx-auto">Add a new Block</Button>
					<Button onClick={() =>{
						if(activelyBlocking){
							setNewColorError(true);
							return;
						}
						colorSet.delete(color);
						let randColor = getRandColor();
						while(colorSet.has(randColor)){
							randColor = getRandColor;
						}
						colorSet.add(randColor);
						setColor(randColor);
						//change color
					}} type="button" variant="outline-info" className="mx-auto ms-2">New Color</Button>
					
					
					</Col>
					<Col className="mt-4 text-center">
						<Button onClick={() =>{
							goalBlockMode ? setGoalBlockMode(false) : setGoalBlockMode(true);
							}
						} type="button" variant="outline-dark" className="mx-auto me-5" >{goalBlockMode ? "Cancel":"Set Goal Block" }</Button>
						<Button hidden={loading}  onClick={() => {
							if(goalBlock === undefined){setNoGoalSelectedError(true); return;}
							setLoading(true);
							postPuzzle(grid, rowSize, colSize, goalBlock);
							
							}} type="button" variant="outline-danger" className="mx-auto">Solve</Button>
						<Button variant="dark" hidden={!loading} disabled>
						 	<Spinner animation="border" variant="info" as="span"/>
						 </Button>
					</Col>
					<Col className="mt-4 text-center">
						<Button type="button" variant="outline-secondary" className="mx-auto" onClick={() => window.location.reload()}>Reset</Button>
						<Alert className="w-50 mx-auto mt-2" variant="danger" dismissible show={error} onClose={()=>{setError(false)}}>
							<Alert.Heading>Error Solving</Alert.Heading>
							<p>Error Communicating with Server, try a different puzzle, or try again later.</p>
							<p>Thank you</p>
						</Alert>
						<Alert className="w-50 mx-auto mt-2" variant="warning" dismissible show={blockNotPlacedErr} onClose={()=>{setBlockNotPlacedErr(false)}}>
							<Alert.Heading>Cannot set Goal Block</Alert.Heading>
							<p>You Cannot set a goal block that hasn't been placed -- Add the block first!</p>
						</Alert>
						<Alert className="w-50 mx-auto mt-2" variant="warning" dismissible show={invalidAlert} onClose={()=>{setInvalidAlert(false)}}>
							<Alert.Heading>Invalid Block</Alert.Heading>
							<p>Block you're attempting to place is not valid! (Not a square or rectangle)</p>
						</Alert>
						<Alert className="w-50 mx-auto mt-2" variant="warning" dismissible show={invalidGoalBlock} onClose={()=>{setInvalidGoalBlock(false)}}>
							<Alert.Heading>Invalid Goal Block</Alert.Heading>
							<p>Block you're attempting to select as the Goal Block is not valid. (Not a Block) Select Another</p>
						</Alert>
						<Alert className="w-50 mx-auto mt-2" variant="success" dismissible show={noSolution} onClose={()=>{setNoSolution(false)}}>
							<Alert.Heading>No Solution</Alert.Heading>
							<p>No Solution Exists for this Puzzle!</p>
						</Alert>
						<Alert className="w-50 mx-auto mt-2" variant="info" dismissible show={noGoalSelectedError} onClose={()=>{setNoGoalSelectedError(false)}}>
							<Alert.Heading>No Goal Block Selected</Alert.Heading>
							<p>You Must Set a Goal Block Before We can solve this Puzzle</p>
						</Alert>
						<Alert className="w-50 mx-auto mt-2" variant="dark" dismissible show={newColorError} onClose={()=>{setNewColorError(false)}}>
							<Alert.Heading>Unable to change New Color</Alert.Heading>
							<p>Finish placing your block, or remove it, before changing colors</p>
						</Alert>
					</Col>
					<Accordion className="mt-3">
						<Accordion.Item eventKey="0"> 
								<Accordion.Header>Block  Movement Info</Accordion.Header>
									<Accordion.Body>
									{
										movementArr.map((key, ind) =>{
											let val;
											if(movementValueArr[ind] == "b"){val = "Both"}
											else if(movementValueArr[ind] == "h"){val = "Horizontal"}
											else if(movementValueArr[ind] == "v"){val = "Vertical"}
											else{val = "None"}
											return(<h5 key={ind}><Pixel finalColor={key}/>: {val}</h5>);
										})
									}
									</Accordion.Body>
							</Accordion.Item>
						
					</Accordion>
					<div hidden={!solutionFound}>
						<Accordion className="mt-3">
							<Accordion.Item eventKey="0"> 
								<Accordion.Header>Initial Grid</Accordion.Header>
									<Accordion.Body>
										<div className="mx-auto mygrid" style={{ display: 'grid', gridTemplateRows: 'repeat(3, 1fr)', gridTemplateColumns: `repeat(${colSize}, 1fr)`, width: '210px', margin: '0 auto', columnGap: "0"}}>
										 {	
											initialGrid.map((arr, rowI)=>{
												return(<>
												{
													arr.map((elem, colI) =>{
														if(elem === "Z"){elem = goalBlock;}
														return <Pixel key={(rowI*colSize)+colI} finalColor={elem} row={rowI +1} col={colI+1}/>;
													})
												}</>);
											})
										}
										</div>
									</Accordion.Body>
							</Accordion.Item>
						</Accordion>
						<Accordion className="mt-1">
							<Accordion.Item eventKey="0"> 
								<Accordion.Header>Final Grid</Accordion.Header>
									<Accordion.Body>
										<div className="mx-auto mygrid" style={{ display: 'grid', gridTemplateRows: 'repeat(3, 1fr)', gridTemplateColumns: `repeat(${colSize}, 1fr)`, width: '210px', margin: '0 auto', columnGap: "0"}}>
										 {	
											finalGrid.map((arr, rowI)=>{
												return(<>
												{
													arr.map((elem, colI) =>{
														if(elem === "Z"){elem = goalBlock;}
														if(elem === "."){elem = "lightGrey"}
														return <Pixel key={(rowI*colSize)+colI} finalColor={elem} row={rowI +1} col={colI+1}/>;
													})
												}</>);
											})
										}
										</div>
									</Accordion.Body>
							</Accordion.Item>
						</Accordion>
						<Accordion className="mt-1 mb-5">
							<Accordion.Item eventKey="0"> 
								<Accordion.Header>Moves</Accordion.Header>
									<Accordion.Body>
									 {							
										moves.map((move, mIndex)=>{
											if(mIndex == 0){
												return (<h3 key={mIndex}>{move}</h3>);
											}
											let words = move.split(" ");
											let elem = words[1];
											words.shift();
											words.shift();
											let newMove = words.join(" ");
											
											if(elem == "Z"){elem = goalBlock;}
											
											return (<h3 key={mIndex}>Piece <Pixel key={mIndex} finalColor={elem} /> {newMove}</h3>);
										})
									 }
									</Accordion.Body>
							</Accordion.Item>
						</Accordion>
					</div>
				</Container>
			</ColorContext.Provider>
		</div>
	);
}

function validBlock(grid, color, colSize){
	let firstOcc = undefined;
	let lastOcc = undefined;
	let widestOcc = -1;
	let narrowestOcc = colSize;
	for(let i = 0; i < grid.length; ++i){
		if(firstOcc === undefined && grid[i] == color){
			firstOcc = i;
			if(i%colSize > widestOcc){widestOcc = i%colSize}
			if(i%colSize < narrowestOcc){narrowestOcc = i%colSize}
		}
		else if(firstOcc !== undefined && grid[i] == color){
			lastOcc = i;//bottom or widecoordinate
			//get most wide coordinate
			if(i%colSize > widestOcc){widestOcc = i%colSize}
			if(i%colSize < narrowestOcc){narrowestOcc = i%colSize}
		}
	}
	if(firstOcc == undefined){return false;}
	else if(lastOcc == undefined){return true;}	//block of size 1\
	
	
	for(let i = firstOcc; i <= lastOcc; i += colSize){
		let tmp = 0;
		for(let j = narrowestOcc; j <= widestOcc; ++j){
			let index = i + tmp;
			++tmp;
			if(grid[index] != color){return false;}
		}
	}
	return true;
}

const ColorContext = createContext({
  color: 'lightGrey',
  setColor: () => {},
  goalBlockMode: false,
  setGoalBlockMode: () => {},
  goalBlock: undefined,
  setGoalBlock: () =>{},
  setInvalidGoalBlock: () =>{},
  setActivelyBlocking: () =>{},
  blocksPlaced: new Map(),
  setBlockNotPlacedErr: () => {},
})

function Pixel ({row, col, grid, colSize, finalColor, changeColor}) {
  const {setBlockNotPlacedErr, blocksPlaced, color, goalBlockMode,setGoalBlockMode, setGoalBlock, setInvalidGoalBlock, setActivelyBlocking} = useContext(ColorContext)
  const [pixelColor, setPixelColor] = useState('lightGrey')
  
  //finalGrid
  if(finalColor !== undefined){
	  return <button className="finalGridPixels" row={row} col={col} color={finalColor} style={{ height: '20px', width: '20px', backgroundColor: finalColor, margin: '1px' }} />
  }
  
  if(changeColor !== undefined){
	  setPixelColor("lightGrey");
	  changeColor= undefined;
	  return;
  }

  return <button className="pixels" row={row} col={col} color={color} onClick={() =>{
	  	//console.log(goalBlockMode);
	  	
	  	if(goalBlockMode){
			  if(pixelColor == "lightGrey" || grid[(row*colSize) +col] == 0){
				  setInvalidGoalBlock(true);
				  return;
			  }
			  if(!blocksPlaced.has(pixelColor)){
				  setBlockNotPlacedErr(true);
				  return;
			  }
			  setGoalBlock(grid[(row*colSize) + col]);
			  setGoalBlockMode(false);
			  return;
		 }
		  //turn back to lightGrey
		  if(grid[(row*colSize) + col] == color){
			  setPixelColor("lightGrey");
			  grid[(row*colSize) + col] = 0;
			  //check if any other pixels of this color exist in grid
				let flag = false;
				for(let i = 0; i < grid.length; ++i){
					if(grid[i] == color){console.log("true flag");flag = true; break;}
				}
				//console.log("flag: ", flag);
				setActivelyBlocking(flag);
			  return;
		  }
		  //if already part of another block, cant change it
		  if(grid[(row*colSize) + col] != 0){return;}
		 grid[(row*colSize)+ col] = color;
		// console.log(grid);
		setPixelColor(color);
		//console.log("color: ", color);
		//console.log("pixel color after set: ", pixelColor);
		setActivelyBlocking(true);
		
  } } style={{ height: '20px', width: '20px', backgroundColor: pixelColor, margin: '1px' }} />
}
