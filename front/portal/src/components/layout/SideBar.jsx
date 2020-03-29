import React from 'react';
import { Tab, ListGroup } from 'react-bootstrap';

const SideBar = () => {

    return (
        <Tab.Container id="list-group-tabs-example" defaultActiveKey="#my-posts">
            <ListGroup>
                <ListGroup.Item action href="#my-posts">
                My posts
                </ListGroup.Item>
                <ListGroup.Item action href="#add-post">
                Add post
                </ListGroup.Item>
            </ListGroup>
        </Tab.Container>
    )
}

export default SideBar;