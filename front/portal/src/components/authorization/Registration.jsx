import React from 'react';
import RegistrationForm from './RegistrationForm.jsx';
import { Row, Col, Container } from 'react-bootstrap';
import {doRegister} from "./actions";
import {connect} from "react-redux";



const mapDispatchToProps = dispatch => {
    return {
        handleRegister: userDto => dispatch(doRegister(userDto))
    }
};

const Registration = ({handleRegister}) => {
    return (
        <Container>
                <Row className="justify-content-center" style={{margin:"10em 0em 10em 0em"}}>
                    <Col lg={6}>
                        <RegistrationForm handleRegister={handleRegister}/>
                    </Col>
                </Row>
        </Container>
    );
};

export default connect(null, mapDispatchToProps)(Registration);