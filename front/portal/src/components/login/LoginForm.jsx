import React, {useState} from 'react';
import {Button, Card, Form, InputGroup} from "react-bootstrap";

const LoginForm = ({handleLogin}) => {

    const signFormStyle = {
        width: "3em",
        display: "inline"
    };

    const [loginDto, setloginDto] = useState({});

    const onChange = (e) => {
        e.persist();
        const {name, value} = e.target;
        setloginDto((prevState => {
            return {
                ...prevState,
                [name]: value
            }
        }))
    };

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
                    <Button id="signIn" variant="info" type="submit" style={{width: "30%"}}>
                        Sign in
                    </Button>
                </div>
            </Form>
        </Card>
    );
}

export default LoginForm;