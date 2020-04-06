import {push} from "connected-react-router";
import {logOut} from "../login/actions";

export const addPostSuccess = 'dashboard/addPostSuccess';
export const addPostFailure = 'dashboard/addPostFailure';

export function makeAddPost(postDTO) {
    return (dispatch) => {
        //    axios.post('http://localhost:8080/api/post/create', postDTO)
        //        .then(response => onSuccessfulAddPost(postDTO),
        //            error => onErrorAddPost(error))
        dispatch(onSuccessfulAddPost(postDTO))
    };
}

const onSuccessfulAddPost = postDto => {
    return (dispatch) => {
        dispatch(push('posts'));
        dispatch({
            type: addPostSuccess,
            post: postDto
        })
    }
};

const onErrorAddPost = error => {
    return {
        type: addPostFailure,
        error: error
    }
};

export function doLogOut () {
    return dispatch => {
        localStorage.clear();
        dispatch({
            type: logOut
        });
        dispatch(push('/'))
    }
}