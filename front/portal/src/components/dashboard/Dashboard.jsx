import React from 'react';
import PostsList from '../layout/PostsList.jsx';
import SideBar from './SideBar.jsx';
import {Col, Row} from 'react-bootstrap';
import {connect} from "react-redux";
import {doLogOut, doAddPost, getAllPosts, getUserPosts} from "./actions";
import {Route} from "react-router-dom";
import AddPost from "./AddPost";

const mapDispatchToProps = dispatch => {
    return {
        handleAddPost: postDTO => dispatch(doAddPost(postDTO)),
        handleGetAllPosts: () => dispatch(getAllPosts()),
        handleGetMyPosts: () => dispatch(getUserPosts()),
        handleLogOut: () => dispatch(doLogOut())
    }
};

const Dashboard = ({handleGetMyPosts, handleAddPost, handleLogOut, match}) => {
    return (
        <div>
            <Row className="w-100">
                <Col lg="2" style={{position: "fixed"}}>
                    <SideBar handleLogOut={handleLogOut}/>
                </Col>
                <Col lg={{offset: 2}}>
                    <Route path={`${match.path}/posts`} render={() => <PostsList handleGetPosts={handleGetMyPosts}/>}/>
                    <Route path={`${match.path}/addpost`} render={() => <AddPost handleAddPost={handleAddPost}/>}/>
                </Col>
            </Row>
        </div>
    )
};

export default connect(null, mapDispatchToProps)(Dashboard);