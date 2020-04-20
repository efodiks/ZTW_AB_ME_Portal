import React from 'react';
import {CardColumns, Container} from 'react-bootstrap';
import PostCard from './PostCard.jsx';
import { useEffect } from 'react';
import {connect} from 'react-redux';

const mapStateToProps = state => {
    return {
        posts: state.dashboardState.posts
    }
}

const PostsList = ({posts, handleGetPosts}) => {

    useEffect(handleGetPosts, []);

    return (
        <Container style={{marginTop: "1em", marginBottom: "1em"}}>
            <CardColumns>
                {posts.map(post =>
                    (<PostCard
                        key={post.id}
                        author={post.author}
                        imgSrc={post.url}
                        description={post.description}
                    />))}
            </CardColumns>
        </Container>
    );
};

export default connect(mapStateToProps, null)(PostsList);