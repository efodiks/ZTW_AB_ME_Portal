import React from 'react';
import PostsList from '../layout/PostsList.jsx';
import {Row} from 'react-bootstrap';
import {connect} from "react-redux";
import {doAddPost, getUserPosts} from "./actions";
import {Route} from "react-router-dom";
import AddPost from "./AddPost";

const mapDispatchToProps = dispatch => {
    return {
        handleAddPost: postDTO => dispatch(doAddPost(postDTO)),
        handleGetMyPosts: () => dispatch(getUserPosts())
    }
};

const Dashboard = ({handleAddPost, handleGetMyPosts, match}) => {
    return (
        <div>
            <Row className="w-100">
                <Route path={`${match.path}/posts`} render={() => <PostsList handleGetPosts={handleGetMyPosts}/>}/>
                <Route path={`${match.path}/addpost`} render={() => <AddPost handleAddPost={handleAddPost}/>}/>
            </Row>
        </div>
    )
};

export default connect(null, mapDispatchToProps)(Dashboard);