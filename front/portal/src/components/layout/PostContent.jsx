import React from 'react';
import Avatar from './Avatar.jsx';
import { Row, Col, Image } from 'react-bootstrap';

const PostContent = ({ author, imgSrc, description }) => {

    return (
        <div>
            <div style={{display: "flex", alignItems: "center", margin: "0em 0.2em 0.4em 0.2em"}}>
                <Avatar
                    imgSrc={author.imgSrc}
                    size="4"
                />
                <h4 className="m-2">{author.username}</h4>
            </div>
            <Image style={{width: "100%", margin: "1em 0em 1em 0em"}} src={imgSrc} rounded responsive/>
            <Row>
                <Col lg="2">
                    <h6 style={{fontWeight: 600}}>2 stars</h6>
                </Col>
                <Col>
                    <h6 style={{fontWeight: 600}}>2 comments</h6>
                </Col>
            </Row>
            {description}
        </div>
    )
}

export default PostContent;