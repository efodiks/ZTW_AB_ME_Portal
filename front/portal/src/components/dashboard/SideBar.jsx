import React from 'react';
import {Button, ListGroup, Tab} from 'react-bootstrap';
import {Link} from "react-router-dom";

const SideBar = ({handleLogOut}) => {
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
                <Button onClick={handleLogOut}>
                    Log out
                </Button>
            </ListGroup>
        </Tab.Container>
    )
};

export default SideBar;