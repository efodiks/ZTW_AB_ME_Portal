import axios from 'axios'

export const loginLoading = 'login/loading';
export const loginFailed = 'login/failed';
export const loginSuccessful = 'login/successful';
export const logOut = 'login/logOut';


export function makeLoginRequest(loginDTO) {
    return (dispatch) => {
        dispatch({
            type: loginLoading
        });
        axios.post('http://localhost:8080/api/authenticate', loginDTO)
            .then(response => dispatch(makeLoginSuccessful(response.data.tokenString)),
                error => dispatch(makeLoginFailed(error)))
    }
}

export function makeLoginFailed(error) {
    console.log(error);
    return {
        type: loginFailed,
        error: error
    }
}

export function makeLoginSuccessful(token) {
    localStorage.setItem('token', token);
    return {
        type: loginSuccessful
    }
}