import React from 'react';
import RegistrationForm from './RegistrationForm.jsx';
import { Row, Col, Container } from 'react-bootstrap';

const Registration = () => {
    
    return (
        <Container>
                <Row className="justify-content-center" style={{margin:"10em 0em 10em 0em"}}>
                    <Col lg={6}>
                        <RegistrationForm/>
                    </Col>
                </Row>
        </Container>
    );
}

export default Registration;