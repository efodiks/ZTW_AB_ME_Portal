import React from 'react';
import {connect} from "react-redux";
import {Row} from 'react-bootstrap';
import PostsList from '../layout/PostsList';
import {getAllPosts} from './actions';

const mapDispatchToProps = dispatch => {
    return {
        handleGetAllPosts: () => dispatch(getAllPosts())
    }
};

const Feed = ({handleGetAllPosts}) => {

    return (
        <div>
            <Row className="w-100">
                <PostsList handleGetPosts={handleGetAllPosts} />
            </Row>
        </div>
    )
}

export default connect(null, mapDispatchToProps)(Feed);