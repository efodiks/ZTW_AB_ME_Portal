import {loginFailed, loginRequest, loginSuccessful} from './actions'

const initialState = {
    loading: false,
    authorized: false,
    error: undefined,
    token: '',
    user: {
        email: '',
        firstName: 'Adam',
        lastName: 'Nowak'
    }
};

export const loginReducer = (state = initialState, action) => {
    switch (action.type) {
        case loginRequest:
            return ({...state, loading: true, authorized: false});
        case loginFailed:
            return ({...state, loading: false, error: action.error});
        case loginSuccessful:
            return ({...state, loading: false, error: undefined, token: action.token});
        default:
            return state;
    }
};