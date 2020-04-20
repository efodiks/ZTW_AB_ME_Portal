import api from '../../config/apiConfiguration';
import {push} from "connected-react-router";
import {actionLogOut} from "../authorization/actions";

export const actionAddPostSuccess = 'dashboard/addPostSuccess';
export const actionAddPostFailure = 'dashboard/addPostFailure';
export const actionGetAllPostsSuccess = 'dashboard/getAllPostsSuccess';
export const actionGetAllPostsFailure = 'dashboard/getAllPostsFailure';
export const actionGetUserPostsSuccess = 'dashboard/getUserPostsSuccess';
export const actionGetUserPostsFailure = 'dashboard/getUserPostsFailure';

export const doAddPost = postDTO => {
    return (dispatch) => {
        api.post('posts/create', postDTO)
            .then(response => dispatch(onSuccessfulAddPost(response.data)),
                error => onErrorAddPost(error));
    };
};

const onSuccessfulAddPost = postDto => {
    return (dispatch) => {
        dispatch({
            type: actionAddPostSuccess,
            post: postDto
        })
        dispatch(push('/dashboard/posts'))
    }
};

const onErrorAddPost = error => {
    console.log(error)
    return {
        type: actionAddPostFailure,
        error: error
    }
};

export function getAllPosts () {
    return (dispatch) => {
        api.get('posts')
            .then(response => dispatch(onSuccessfulGetAllPosts(response.data)),
               error => onErrorGetAllPosts(error));
    }
}

const onSuccessfulGetAllPosts = posts => {
    return {
        type: actionGetAllPostsSuccess,
        posts: posts
    }
}

const onErrorGetAllPosts = error => {
    console.log(error)
    return {
        type: actionGetAllPostsFailure,
        error: error
    }
}

export function getUserPosts () {
    return (dispatch) => {
        api.get('posts/me')
            .then(response => dispatch(onSuccessfulGetUserPosts(response.data)),
               error => onErrorGetUserPosts(error));
    }
}

const onSuccessfulGetUserPosts = posts => {
    return {
        type: actionGetUserPostsSuccess,
        posts: posts
    }
}

const onErrorGetUserPosts = error => {
    console.log(error)
    return {
        type: actionGetUserPostsFailure,
        error: error
    }
}

export function doLogOut () {
    return dispatch => {
        localStorage.clear();
        dispatch({
            type: actionLogOut
        });
        dispatch(push('/'))
    }
}