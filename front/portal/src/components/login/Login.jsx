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
            <Row className="justify-content-center" style={{margin:"10em 0em 10em 0em"}}>
                <Col lg={6}>
                    <div>`Stan: {firstName}, {lastName}`</div>
                    <LoginForm/>
                </Col>
            </Row>
        </Container>
    );
};

export default connect(mapStateToProps)(Login);