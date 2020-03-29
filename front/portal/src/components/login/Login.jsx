import React from 'react';
import LoginForm from './LoginForm.jsx';
import {Col, Container, Row} from 'react-bootstrap';
import {connect} from "react-redux";

const mapStateToProps = state => {
    return {
        firstName: state.user.firstName,
        lastName: state.user.lastName,
        token: state.token
    }
};

const Login = ({firstName, lastName, token}) => {
    return (
        <Container>
            <Row className="justify-content-center">
                <Col lg={6}>
                    <div>`Stan: {firstName}, {lastName}`</div>
                    <LoginForm/>
                </Col>
            </Row>
        </Container>
    );
};

export default connect(mapStateToProps)(Login);