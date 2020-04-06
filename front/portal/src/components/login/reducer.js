import {loginFailed, loginLoading, loginSuccessful, logOut} from './actions'

const initialState = {
    loading: false,
    authorized: localStorage.getItem('token'),
    error: undefined,
    user: {
        email: '',
        firstName: 'Adam',
        lastName: 'Nowak'
    }
};

export const loginReducer = (state = initialState, action) => {
    switch (action.type) {
        case loginLoading:
            return {...state, loading: true, error: undefined, authorized: false};
        case loginFailed:
            return {...state, loading: false, error: action.error, authorized: false};
        case loginSuccessful:
            return {...state, loading: false, error: undefined, authorized: true};
        case logOut:
            return {...state, loading: false, error: undefined, authorized: false};
        default:
            return state;
    }
};