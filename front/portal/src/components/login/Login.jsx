import React from 'react';
import LoginForm from './LoginForm.jsx';
import {Col, Container, Row} from 'react-bootstrap';
import {connect} from "react-redux";
import {makeLoginRequest} from "./actions";
import {Redirect} from "react-router-dom";

const mapStateToProps = state => {
    console.log(state);
    return {
        authorized: state.loginState.authorized
    }
};

const mapDispatchToProps = dispatch => {
    return {
        handleLogin: loginDTO => {
            dispatch(makeLoginRequest(loginDTO))
        }
    }
};

const Login = ({authorized, handleLogin}) => {
    return (
        authorized ? <Redirect to={"/dashboard"}/> :
            <Container>
                <Row className="justify-content-center" style={{margin: "10em 0em 10em 0em"}}>
                    <Col lg={6}>
                        <LoginForm handleLogin={handleLogin}/>
                    </Col>
                </Row>
            </Container>
    );
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);