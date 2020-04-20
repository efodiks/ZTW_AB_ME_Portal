import {
    actionLoginFailed,
    actionLoginLoading,
    actionLoginSuccessful,
    actionLogOut, actionRegisterFailed,
    actionRegisterLoading, actionRegisterSuccessful
} from './actions'

const initialState = {
    loading: false,
    authorized: localStorage.getItem('token'),
    error: undefined,
    user: {
        email: '',
        username: '',
        firstName: 'Adam',
        lastName: 'Nowak',
        url: ''
    }
};

export const loginReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionLoginLoading:
        case actionRegisterLoading:
            return {...state, loading: true, error: undefined, authorized: false};
        case actionLoginFailed:
        case actionRegisterFailed:
            return {...state, loading: false, error: action.error, authorized: false};
        case actionRegisterSuccessful:
            return {...state, loading: false, error: undefined, authorized: false};
        case actionLoginSuccessful:
            return {...state, loading: false, error: undefined, authorized: true};
        case actionLogOut:
            return {...state, loading: false, error: undefined, authorized: false};
        default:
            return state;
    }
};