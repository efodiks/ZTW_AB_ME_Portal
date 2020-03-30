import {loginFailed, loginLoading, loginSuccessful} from './actions'

const initialState = {
    loading: false,
    authorized: false,
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
            return ({...state, loading: true, authorized: false});
        case loginFailed:
            return ({...state, loading: false, error: action.error, authorized: false});
        case loginSuccessful:
            return ({...state, loading: false, error: undefined, authorized: true});
        default:
            return state;
    }
};