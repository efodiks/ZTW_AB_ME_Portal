import React from 'react';
import Avatar from '../layout/Avatar.jsx';
import { Row, Col } from 'react-bootstrap'

const Comment = ({ authorData, commentText }) => {

    return (
        <Row className="mt-3 mb-3">
            <Col lg="2">
                <Avatar
                    imgSrc={authorData.imgSrc}
                    size="4"
                />
            </Col>
            <Col>
                <h6 style={{marginBottom: "0.1em", fontWeight: 600}}>{authorData.username}</h6>
                {commentText}
            </Col>
        </Row>
    )
}

export default Comment;