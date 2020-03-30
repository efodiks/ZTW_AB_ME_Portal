import React, {useEffect} from 'react';
import {CardColumns, Container} from 'react-bootstrap';
import PostCard from './PostCard.jsx';

const PostsList = ({posts, handleAddPost}) => {

    useEffect(() => {
        setTimeout(() => {
            handleAddPost({
                author: {
                    username: "User2",
                    imgSrc: "https://images.pexels.com/photos/4015752/pexels-photo-4015752.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                },
                imgSrc: "https://images.pexels.com/photos/1036936/pexels-photo-1036936.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
                description: "new post."

            });
        }, 5000);
    }, [handleAddPost]);

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