import {actionAddPostFailure, actionAddPostSuccess,
        actionGetAllPostsSuccess, actionGetAllPostsFailure,
        actionGetUserPostsSuccess, actionGetUserPostsFailure} from "./actions";

const initialState = {
    posts: []
};

export const dashboardReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionAddPostSuccess:
            return {...state, posts: [...state.posts, action.post]};
        case actionAddPostFailure:
            throw action.error;
        case actionGetAllPostsSuccess:
            return {...state, posts: action.posts}
        case actionGetAllPostsFailure:
            throw action.error;
        case actionGetUserPostsSuccess:
            return {...state, posts: action.posts}
        case actionGetUserPostsFailure:
            throw action.error;
        default:
            return state
    }
};

