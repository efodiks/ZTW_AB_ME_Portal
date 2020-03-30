import React from 'react';
import PostsList from '../layout/PostsList.jsx';
import SideBar from '../layout/SideBar.jsx';
import {Col, Row} from 'react-bootstrap';
import {connect} from "react-redux";
import {makeAddPost, onSuccessfulAddPost} from "./actions";

const mapStateToProps = state => {
    return {
        posts: state.dashboardState.posts
    }
};

const mapDispatchToProps = dispatch => {
    return {
        handleAddPost: postDTO => dispatch(makeAddPost(postDTO))
    }
};

const Dashboard = ({posts, handleAddPost}) => {
    return (
        <div>
            <Row className="w-100">
                <Col lg="2" style={{position: "fixed"}}>
                    <SideBar/>
                </Col>
                <Col lg={{offset: 2}}>
                    <PostsList posts={posts} handleAddPost={handleAddPost}/>
                </Col>
            </Row>
        </div>
    )
};

export default connect(mapStateToProps, mapDispatchToProps)(Dashboard);