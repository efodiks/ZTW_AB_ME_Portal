import React from 'react';
import { Link } from 'react-router-dom';
import { Form, Button, InputGroup, Card } from "react-bootstrap";

const LoginForm = () => {
    
    const signFormStyle = {
        width: "3em",
        display: "inline"
    };

    return (
        <Card body style={{margin:"10em 0em 10em 0em"}}>
            <Form style={{padding:"2em"}}>
                <Form.Group controlId="registrationEmail">
                    <Form.Label>Email address</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>@</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="email" placeholder="Email" required/>
                        <Form.Control.Feedback type="invalid">Please enter your email.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
    
                <Form.Group controlId="validationCustomUsername">
                    <Form.Label>Password</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>*</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="password" placeholder="Password" required/>
                        <Form.Control.Feedback type="invalid">Please enter the password.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>
            
                <div className="d-flex justify-content-center">
                    <Link to='/dashboard' style={{width:"100%", display:"contents"}}>
                        <Button id="signIn" variant="info" type="submit" style={{width:"30%"}}>
                            Sign in
                        </Button>
                    </Link>
                </div>
            </Form>
        </Card>
    );
}

export default LoginForm;