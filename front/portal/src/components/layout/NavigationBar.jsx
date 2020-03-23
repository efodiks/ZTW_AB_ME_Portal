import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';

const NavigationBar = () => {

    return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
        <Navbar.Brand href="#home">Portal</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
            <Nav>
                <Nav.Link href="#account">My account</Nav.Link>
            </Nav>
        </Navbar.Collapse>
    </Navbar>
    );
}

export default NavigationBar;