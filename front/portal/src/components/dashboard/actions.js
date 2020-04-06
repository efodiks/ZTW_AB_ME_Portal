import {push} from "connected-react-router";
import {actionLogOut} from "../authorization/actions";

export const actionAddPostSuccess = 'dashboard/addPostSuccess';
export const actionAddPostFailure = 'dashboard/addPostFailure';

export const doAddPost = postDTO => {
    return (dispatch) => {
        //    axios.post('http://localhost:8080/api/post/create', postDTO)
        //        .then(response => onSuccessfulAddPost(postDTO),
        //            error => onErrorAddPost(error))
        dispatch(onSuccessfulAddPost(postDTO))
    };
};

const onSuccessfulAddPost = postDto => {
    return (dispatch) => {
        dispatch(push('posts'));
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

export function doLogOut () {
    return dispatch => {
        localStorage.clear();
        dispatch({
            type: actionLogOut
        });
        dispatch(push('/'))
    }
}