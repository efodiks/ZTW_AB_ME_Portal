import React from 'react';
import {CardColumns, Container} from 'react-bootstrap';
import PostCard from './PostCard.jsx';

const PostsList = ({posts}) => {

    return (
        <Container style={{marginTop: "1em", marginBottom: "1em"}}>
            <CardColumns>
                {posts.map(post =>
                    (<PostCard
                        author={post.author}
                        imgSrc={post.imgSrc}
                        description={post.description}
                    />))}
            </CardColumns>
        </Container>
    );
};

export default PostsList;