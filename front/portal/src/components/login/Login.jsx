import React from 'react';
import LoginForm from './LoginForm.jsx';
import { Container, Row, Col } from 'react-bootstrap';

const Login = () => {

    return (
        <Container>
            <Row className="justify-content-center">
                    <Col lg={6}>
                        <LoginForm/>   
                    </Col>
            </Row>
        </Container>
    );
}

export default Login;