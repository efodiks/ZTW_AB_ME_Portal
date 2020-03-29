import React from 'react';
import LoginForm from './LoginForm.jsx';
import { Container, Row, Col } from 'react-bootstrap';

const Login = () => {

    return (
        <Container>
            <Row className="justify-content-center" style={{margin:"10em 0em 10em 0em"}}>
                    <Col lg={6}>
                        <LoginForm/>   
                    </Col>
            </Row>
        </Container>
    );
}

export default Login;