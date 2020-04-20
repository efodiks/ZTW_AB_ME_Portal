import React, {useState} from 'react';
import {Button, Card, Col, Container, Form, Row} from 'react-bootstrap';

const AddPost = ({handleAddPost}) => {

    const [postDto, setPostDto] = useState({});

    const onChange = (e) => {
        e.persist();
        const {name, value} = e.target;
        setPostDto((prevState => {
            return {
                ...prevState,
                [name]: value
            }
        }))
    };

    return (
        <Container>
            <Row className="justify-content-center" style={{margin: "1.5em 0em 1.5em 0em"}}>
                <Card body className="w-100">
                    <h1>Add post</h1>
                    <Form onSubmit={(e) => {
                        e.preventDefault();
                        handleAddPost({
                            ...postDto,
                            author: {
                                //username: "User1",
                                imgSrc: "https://images.pexels.com/photos/4015752/pexels-photo-4015752.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                            }
                        })
                    }}>
                        <Form.Row style={{marginTop: "1em"}}>
                            <Form.Label column lg={2}>
                                Picture URL
                            </Form.Label>
                            <Col>
                                <Form.Control type="text" placeholder="URL" name="url" onChange={onChange}/>
                            </Col>
                        </Form.Row>
                        <Form.Row style={{marginTop: "1em", marginBottom: "1em"}}>
                            <Form.Label column lg={2}>
                                Description
                            </Form.Label>
                            <Col>
                                <Form.Control as="textarea" placeholder="Description..." name="description"
                                              onChange={onChange}/>
                            </Col>
                        </Form.Row>
                        <div align="right">
                            <Button id="addPost" variant="info" type="submit" style={{width: "30%"}}>
                                Add post
                            </Button>
                        </div>
                    </Form>
                </Card>
            </Row>
        </Container>
    )
};

export default AddPost;