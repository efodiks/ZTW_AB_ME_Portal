import React from 'react';
import { Container, Row, Card, Form, Col, Button } from 'react-bootstrap';

const AddPost = () => {

    return (
        <Container>
            <Row className="justify-content-center" style={{margin:"1.5em 0em 1.5em 0em"}}>
                <Card body className="w-100">
                    <h1>Add post</h1>
                    <Form >
                        <Form.Row style={{marginTop: "1em"}}>
                            <Form.Label column lg={2}>
                                Picture URL
                            </Form.Label>
                            <Col>
                                <Form.Control type="text" placeholder="URL" />
                            </Col>
                        </Form.Row>
                        <Form.Row style={{marginTop: "1em", marginBottom: "1em"}}>
                            <Form.Label column lg={2}>
                                Description
                            </Form.Label>
                            <Col>
                                <Form.Control as="textarea" placeholder="Description..." />
                            </Col>
                        </Form.Row>
                        <div align="right">
                            <Button id="addPost" variant="info" type="submit" style={{width:"30%"}}>
                                Add post
                            </Button>
                        </div>
                    </Form>
                </Card>
            </Row>
        </Container>
    )
}

export default AddPost;