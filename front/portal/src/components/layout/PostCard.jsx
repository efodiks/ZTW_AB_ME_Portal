import React from 'react';
import Avatar from '../layout/Avatar.jsx';
import { Card } from 'react-bootstrap';

const PostCard = ({ author, imgSrc, description }) => {
    
    return (
        <Card style={{padding: "0.5em"}}>
            <div style={{display: "flex", alignItems: "center", margin: "0em 0.2em 0.4em 0.2em"}}>
                {author.url && <Avatar
                    imgSrc={author.url}
                    size="3.5"
                />}
                <Card.Title className="m-2">{author.username}</Card.Title>
            </div>
            <Card.Img variant="top" src={imgSrc}/>
            <Card.Body style={{padding: "0.2em"}}>
                <Card.Text>{description}</Card.Text>
            </Card.Body>
        </Card>
    )
}

export default PostCard;