import React from 'react';
import LoginForm from './LoginForm.jsx';
import {Col, Container, Row} from 'react-bootstrap';
import {connect} from "react-redux";
import {doLoginRequest} from "./actions";
import {Redirect} from "react-router-dom";
import {push} from "connected-react-router";


const mapStateToProps = state => {
    return {
        authorized: state.loginState.authorized
    }
};

const mapDispatchToProps = dispatch => {
    return {
        handleLogin: loginDTO => dispatch(doLoginRequest(loginDTO)),
        handleRegisterLink: () => dispatch(push('/register'))
    }
};

const Login = ({authorized, handleLogin, handleRegisterLink}) => {
    return (
        authorized ? <Redirect to={"/feed"}/> :
            <Container>
                <Row className="justify-content-center" style={{margin: "10em 0em 10em 0em"}}>
                    <Col lg={6}>
                        <LoginForm handleLogin={handleLogin} handleRegisterLink={handleRegisterLink}/>
                    </Col>
                </Row>
            </Container>
    );
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);