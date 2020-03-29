export const loginRequest = 'login/request';
export const loginFailed = 'login/failed';
export const loginSuccessful = 'login/successful';

export function makeLoginRequest(loginDTO) {
    return {
        type: loginRequest,
        loginDTO: loginDTO
    }
}

export function makeLoginFailed(error) {
    return {
        type: loginFailed,
        error: error
    }
}

export function makeLoginSuccessful(token) {
    return {
        type: loginSuccessful,
        token: token
    }
}