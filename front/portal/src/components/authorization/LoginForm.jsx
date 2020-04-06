import React from 'react';
import {Button, Card, Form, InputGroup} from "react-bootstrap";
import {useFormState} from "../../hooks/useFormState";

const LoginForm = ({handleLogin, handleRegisterLink}) => {

    const signFormStyle = {
        width: "3em",
        display: "inline"
    };

    const [loginDto, onChange] = useFormState({});

    return (
        <Card body>
            <Form style={{padding: "2em"}} onSubmit={(e) => {
                e.preventDefault();
                handleLogin(loginDto)
            }}>
                <Form.Group controlId="registrationEmail">
                    <Form.Label>Email address</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>@</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="email" placeholder="Email" name="email" onChange={onChange} required/>
                        <Form.Control.Feedback type="invalid">Please enter your email.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <Form.Group controlId="validationCustomUsername">
                    <Form.Label>Password</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>*</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="password" placeholder="Password" name="password" required
                                      onChange={onChange}/>
                        <Form.Control.Feedback type="invalid">Please enter the password.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <div className="d-flex justify-content-center">
                    <Button id="register" variant="info" onClick={handleRegisterLink} style={{width: "30%", margin: "1em"}}>
                        Register
                    </Button>

                    <Button id="signIn" variant="info" type="submit" style={{width: "30%", margin: "1em"}}>
                        Sign in
                    </Button>
                </div>
            </Form>
        </Card>
    );
};

export default LoginForm;