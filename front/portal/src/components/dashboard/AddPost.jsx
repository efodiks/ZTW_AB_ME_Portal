import React from 'react';
import { Container, Row, Card } from 'react-bootstrap';

const AddPost = () => {

    return (
        <Container>
            <Row className="justify-content-center" style={{margin:"1.5em 0em 1.5em 0em"}}>
                <Card body className="w-100">
                    <Card.Title>
                        Add post
                    </Card.Title>
                </Card>
            </Row>
        </Container>
    )
}

export default AddPost;