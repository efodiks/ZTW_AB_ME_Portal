import axios from 'axios'
import {push} from 'connected-react-router'

export const actionLoginLoading = 'authorization/loginLoading';
export const actionLoginFailed = 'authorization/loginFailed';
export const actionLoginSuccessful = 'authorization/loginSuccessful';
export const actionLogOut = 'authorization/loginLogOut';

export const actionRegisterLoading = 'authorization/registerLoading';
export const actionRegisterFailed = 'authorization/registerError';
export const actionRegisterSuccessful = 'authorization/registerSuccess';


export function doLoginRequest(loginDTO) {
    return (dispatch) => {
        dispatch({
            type: actionLoginLoading
        });
        axios.post('http://localhost:8080/api/authenticate', loginDTO)
            .then(response => dispatch(doLoginSuccessful(response.data.tokenString)),
                error => dispatch(doLoginFailed(error)))
    }
}

export function doLoginFailed(error) {
    console.log(error);
    return {
        type: actionLoginFailed,
        error: error
    }
}

export function doLoginSuccessful(token) {
    localStorage.setItem('token', token);
    return {
        type: actionLoginSuccessful
    }
}

export const doRegister = userDTO => {
    return dispatch => {
        dispatch({
            type: actionRegisterLoading
        });

        axios.post('http://localhost:8080/api/register', userDTO)
            .then(
                ignore => dispatch(onRegisterSuccessful(userDTO)),
                error => dispatch(doRegisterFailed(error)));
    }
};

export function doRegisterFailed(error) {
    console.log(error);
    return {
        type: actionRegisterFailed,
        error: error
    }
}

const onRegisterSuccessful = (userDTO) => dispatch => {
    dispatch(push('/'));
        dispatch({
            type: actionRegisterSuccessful,
            userDTO: userDTO
        });
        dispatch(doLoginRequest({
            email: userDTO.email,
            password: userDTO.password
        }))
};