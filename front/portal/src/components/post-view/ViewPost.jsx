import React from 'react';
import { Container, Card, Row, Col } from 'react-bootstrap';
import CommentsSection from './CommentsSection.jsx';
import PostContent from '../layout/PostContent.jsx';

const ViewPost = () => {

    let postData = {
        postAuthor:
        {
            username: "Username",
            imgSrc: "https://images.pexels.com/photos/3586911/pexels-photo-3586911.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
        },
        imgSrc: "https://images.pexels.com/photos/3733341/pexels-photo-3733341.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
        description: "Text text text",
        comments:
        [
            {
                authorData: 
                {
                    username: "Username",
                    imgSrc: "https://images.pexels.com/photos/3671235/pexels-photo-3671235.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
                },
                commentText: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            }
        ]
    }

    return (
        <Container>
            <Row className="justify-content-center" style={{margin:"1.5em 0em 1.5em 0em"}}>
                <Card body className="w-100">
                    <Row>
                        <Col lg="7">
                            <PostContent
                                author={postData.postAuthor}
                                imgSrc={postData.imgSrc}
                                description={postData.description}
                            />
                        </Col>
                        <div className="dark-left-divider"></div>
                        <Col>
                            <CommentsSection comments={postData.comments}/>
                        </Col>
                    </Row>
                </Card>
            </Row>
        </Container>
    )
}

export default ViewPost;