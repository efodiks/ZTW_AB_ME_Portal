import React from 'react';
import AddPost from './AddPost.jsx';
import PostsList from '../layout/PostsList.jsx';
import SideBar from '../layout/SideBar.jsx';
import { Row, Col } from 'react-bootstrap';

const Dashboard = () => {  

    let posts = [
        {
            author: {
                username: "User1",
                imgSrc: "https://images.pexels.com/photos/4015752/pexels-photo-4015752.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" 
            },
            imgSrc: "https://images.pexels.com/photos/1036936/pexels-photo-1036936.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
            description: "Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic."
        }
    ]

    return (
        <div>
            <Row className="w-100">
                <Col lg="2" style={{position: "fixed"}}>
                    <SideBar/>
                </Col>
                <Col lg={{offset: 2}}>
                    <PostsList posts={posts}/>
                </Col>
            </Row>
        </div>
    )
}

export default Dashboard;