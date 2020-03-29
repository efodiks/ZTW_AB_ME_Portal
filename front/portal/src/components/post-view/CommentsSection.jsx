import React from 'react';
import Comment from './Comment.jsx';
import { Form, Button } from 'react-bootstrap';

const CommentsSection = ({ comments }) => {

    return (
        <div>
            <h3>Comments</h3>
            <div>
                {comments.map(comment => 
                    (<Comment
                        authorData={comment.authorData}
                        commentText={comment.commentText}
                    />))}
            </div>
            <Form>
                <Form.Group controlId="commentForm">
                    <Form.Control as="textarea" placeholder="Add a comment..." rows="2" />
                </Form.Group>
                <Button id="addComment" variant="info" type="submit" style={{width: "100%"}}>
                    Add comment
                </Button>
            </Form>
        </div>
    )
}

export default CommentsSection;