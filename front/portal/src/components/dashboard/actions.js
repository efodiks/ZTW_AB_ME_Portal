import axios from 'axios';
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
        axios.interceptors.request.use(config => {
            const token = localStorage.getItem('token');
            config.headers.Authorization = 'Bearer' + token;
            return config;
        });

        axios.post('http://localhost:8080/api/posts/create', postDTO)
            .then(response => dispatch(onSuccessfulAddPost(postDTO)),
                error => onErrorAddPost(error));
    };
};

const onSuccessfulAddPost = postDto => {
    return (dispatch) => {
        dispatch({
            type: actionAddPostSuccess,
            post: postDto
        })
    }
};

const onErrorAddPost = error => {
    return {
        type: actionAddPostFailure,
        error: error
    }
};

export function getAllPosts () {
    return (dispatch) => {
        axios.interceptors.request.use(config => {
            const token = localStorage.getItem('token');
            config.headers.Authorization = 'Bearer' + token;
            return config;
        });

        axios.get('http://localhost:8080/api/posts')
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
    return {
        type: actionGetAllPostsFailure,
        error: error
    }
}

export function getUserPosts () {
    return (dispatch) => {
        axios.interceptors.request.use(config => {
            const token = localStorage.getItem('token');
            config.headers.Authorization = 'Bearer' + token;
            return config;
        });

        axios.get('http://localhost:8080/api/posts/me')
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
    return {
        type: actionGetAllPostsFailure,
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