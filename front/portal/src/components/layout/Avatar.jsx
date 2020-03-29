import React from 'react';
import { Image } from 'react-bootstrap';

const Avatar = ({ imgSrc, size = "3.5" }) => {

    return (
        <div className="profile-picture" style={{width: size+"em", height: size+"em"}}>
            <Image src={imgSrc} responsive />
        </div>
    )
}

export default Avatar;