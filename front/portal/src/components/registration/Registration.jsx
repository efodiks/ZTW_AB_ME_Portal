import React from 'react';
import RegistrationForm from './RegistrationForm.jsx';
import { Row, Col, Container } from 'react-bootstrap';

const Registration = () => {
    
    return (
        <Container>
                <Row className="justify-content-center">
                    <Col lg={6}>
                        <RegistrationForm/>
                    </Col>
                </Row>
        </Container>
    );
}

export default Registration;