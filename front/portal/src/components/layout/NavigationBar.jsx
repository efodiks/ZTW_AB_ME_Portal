import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import {doLogOut} from './action';
import {connect} from 'react-redux';
import {NavLink} from 'react-router-dom';

const mapStateToProps = state => {
    return {
        authorized: state.loginState.authorized
    }
};

const mapDispatchToProps = dispatch => {
    
    return {
        handleLogOut: () => dispatch(doLogOut())
    }
}

const NavigationBar = ({authorized, handleLogOut}) => {

    return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky="top">
        <Navbar.Brand>Portal</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
            <Nav>
                {authorized &&
                    <Nav.Link as={NavLink} to='/feed'>Feed</Nav.Link>}
                {authorized && 
                    <Nav.Link as={NavLink} to='/dashboard/posts'>My posts</Nav.Link>}
                {authorized &&
                    <Nav.Link as={NavLink} to='/dashboard/addpost'>Add post</Nav.Link>}
                {authorized && <Nav.Link onClick={handleLogOut}>Log out</Nav.Link>}
            </Nav>
        </Navbar.Collapse>
    </Navbar>
    );
}

export default connect(mapStateToProps, mapDispatchToProps)(NavigationBar);