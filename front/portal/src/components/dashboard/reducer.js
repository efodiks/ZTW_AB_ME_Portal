import {actionAddPostFailure, actionAddPostSuccess} from "./actions";

const initialState = {
    posts: [
        {
            author: {
                username: "User1",
                imgSrc: "https://images.pexels.com/photos/4015752/pexels-photo-4015752.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            },
            imgSrc: "https://images.pexels.com/photos/1036936/pexels-photo-1036936.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500",
            description: "Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic."
        }
    ]
};

export const dashboardReducer = (state = initialState, action) => {
    switch (action.type) {
        case actionAddPostSuccess:
            return {...state, posts: [...state.posts, action.post]};
        case actionAddPostFailure:
            throw action.error;
        default:
            return state
    }
};

