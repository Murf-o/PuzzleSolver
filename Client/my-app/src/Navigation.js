import { Navbar, Nav, NavItem, NavDropdown, MenuItem, NavbarBrand, NavLink } from 'react-bootstrap';
import NavbarCollapse from "react-bootstrap/esm/NavbarCollapse";
import NavbarToggle from "react-bootstrap/esm/NavbarToggle";
import logo from './puzzleLogo4.jpg';
import { useLocation} from "react-router-dom";
import { useEffect, useState } from 'react';
import {Image} from 'react-bootstrap';


export function Navigation(){
	
	const location = useLocation();
	const [url, setUrl] = useState(null);
	useEffect(() => {
    	setUrl(location.pathname);
  	}, [location]);
	return (
		<Navbar expand="lg" bg="secondary" variant="light" collapseOnSelect>
				<NavbarBrand  className="fw-light fst-italic text-dark">
					<Image className="" src={logo} alt="Logo" height={65} width={80} roundedCircle thumbnail/>
					<h1 className="d-inline ms-2">The Sliding Block Puzzle Solver</h1>
				</NavbarBrand>
				<NavbarToggle aria-controls="responsive-navbar-nav" />
				<NavbarCollapse id="responsive-navbar-nav">
					<Nav className='nav-pills'>
						<NavLink href="/"  className={(url == '/' ? 'active bg-light' : '')}>
							Create a Puzzle
						</NavLink>
						<NavLink href="ImportPuzzle" className={"ms-2 " + (url == '/ImportPuzzle' ? 'active bg-warning' : '')}>
							Import a puzzle
						</NavLink>
						<NavLink href="rules" className={"ms-2 " + (url == '/rules' ? 'active bg-info' : '')}>
							How to Play
						</NavLink>
					</Nav>	
				</NavbarCollapse>
			</Navbar>
	);
}