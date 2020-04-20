import React from 'react';
import {Form, Button, InputGroup, Card} from "react-bootstrap";
import {useFormState} from "../../hooks/useFormState";

const RegistrationForm = ({handleRegister}) => {

    const signFormStyle = {
        width: "3em",
        display: "inline"
    };

    const [registerDto, onChange] = useFormState({});

    return (

        <Card body>
            <Form style={{padding: "2em"}} onSubmit={(e) => {
                e.preventDefault();
                console.log("submitting");
                handleRegister(registerDto)
            }}>
                <Form.Group controlId="registrationUsername">
                    <Form.Label>Username</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>#</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="text" placeholder="Username" required name="username" onChange={onChange}/>
                        <Form.Control.Feedback type="invalid">Please choose a username.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <Form.Group controlId="registrationEmail">
                    <Form.Label>Email address</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>@</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="email" placeholder="Email address" required name="email" onChange={onChange}/>
                        <Form.Control.Feedback type="invalid">Please enter your email.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <Form.Group controlId="registrationPassword">
                    <Form.Label>Password</Form.Label>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text id="inputGroupPrepend" style={signFormStyle}>*</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control type="password" placeholder="Password" required name="password" onChange={onChange}/>
                        <Form.Control.Feedback type="invalid">Please enter the password.</Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <div className="d-flex justify-content-center">
                    <Button id="signUp" variant="info" type="submit" style={{width: "30%"}}>
                        Sign up
                    </Button>
                </div>
            </Form>
        </Card>
    );
};

export default RegistrationForm;