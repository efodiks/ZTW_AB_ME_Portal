import React from 'react';
import {ListGroup, Tab} from 'react-bootstrap';
import {Link} from "react-router-dom";

const SideBar = () => {

    return (
        <Tab.Container id="list-group-tabs-example" defaultActiveKey="/dashboard/posts">
            <ListGroup>
                <Link to="/dashboard/posts">
                    <ListGroup.Item action>
                        My posts
                    </ListGroup.Item>
                </Link>
                <Link to="/dashboard/addpost">
                    <ListGroup.Item action>
                        Add post
                    </ListGroup.Item>
                </Link>
            </ListGroup>
        </Tab.Container>
    )
};

export default SideBar;