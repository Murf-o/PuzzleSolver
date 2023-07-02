import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import puzzle1 from "./sixby4.jpg";
import importEx from "./importExample2.jpg";
import {Card} from "react-bootstrap"
import { useNavigate } from "react-router-dom";
import "./rules.css";

export function Rules(){
	const navigate = useNavigate();
	
	const navigateToCreate = () =>{
		let path = "/";
		navigate(path);
	}
	const navigateToImport = () =>{
		let path = "/ImportPuzzle";
		navigate(path);
	}
	
	return(
		<div className="rules">
			<Container className="myContainer" style={{overflowY: "auto"}} fluid >
				<Row>
					<h1 className="mt-3">How To Play</h1>
				</Row>
				<Row>
				<p className="ms-3 underline">Have you Ever been faced with a frightening puzzle before? Well don't fret! As this puzzle solver is guaranteed to solve your puzzle in the least amount of moves possible.</p>
					<h3 className="ms-2">Rules</h3>
					<p className="ms-2">The goal of the game is to get the "Goal Block" in your puzzle to the exit.</p>
					<p className="ms-2">The exit is the entire right side of the puzzle.</p>
					<p className="ms-2">Only Rectangular or Square pieces are allowed.</p>
					<p className="ms-2">These pieces can be set to move Horizontally ('h'), Vertically ('v'), Both ('b), or neither('n)</p>
				</Row>
				
				<Row className="">
					<Col className="w-100">
						<h3 className="ms-2 mt-3">Creating a Puzzle</h3>
						<p className="ms-2">Choose the size of your puzzle by updating the Row and Column Size numbers.</p>
						<p className="ms-2">Place a block by clicking spots on the grid and then pressing 'Add a new Block'.</p>
						<p className="ms-2">You can set or change your goal Block at any time</p>
						<p className="ms-2">Once you are ready, Hit Solve!</p>
						<p className="ms-2">There will be errors to guide you if you feel a bit confused. Now, Go Create a Puzzle by clicking "Create A Puzzle"</p>
					</Col>
					<Col className="center my-auto ms-5 ps-5">
						<Card  className="d-inline mb-5" style={{ width: '18rem', backgroundColor: "black" }}>
					      <Card.Img variant="top" src={puzzle1} />
					      <Card.Body>
					        <Card.Title style={{color: "antiquewhite"}}>6x4 Puzzle</Card.Title>
					        <Card.Text style={{color: "white"}}>
					          Create a Variety of Fun Puzzles to Solve!
					        </Card.Text>
					        <Button variant="primary" onClick={navigateToCreate}>Create A Puzzle</Button>
					      </Card.Body>
					    </Card>
					  </Col>
					<Col className="center my-auto ms-5 ps-5">
						<Card  className="d-inline" style={{ width: '30rem', backgroundColor: "black" }}>
					      <Card.Img variant="top" src={importEx} className="cardImage" />
					      <Card.Body>
					        <Card.Title style={{color: "antiquewhite"}}>Screen after Successfully Importing</Card.Title>
					        <Card.Text style={{color: "white"}}>
					          Import Your Own Puzzle
					        </Card.Text>
					        <Button variant="primary" onClick={navigateToImport}>Import A Puzzle</Button>
					      </Card.Body>
					    </Card>
					  </Col>
				</Row>
				<Row className='mb-5 pb-5'>
					<h3 className="ms-2 mb-2">Importing a Puzzle</h3>
					<p className="ms-2 mb-2" > Upload a .txt file and press Upload</p>
					<p className="ms-2 mb-2"> More Information on the format is specified on the 'Import a Puzzle' Page</p>
				</Row>
				
			</Container>
		</div>
		
		
	);
}