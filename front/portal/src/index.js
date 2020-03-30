import React from 'react';
import thunkMiddleware from 'redux-thunk'
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';
import {applyMiddleware, combineReducers, compose, createStore} from "redux";
import {loginReducer} from "./components/login/reducers";
import {connectRouter, routerMiddleware} from "connected-react-router";
import {createBrowserHistory} from 'history'

const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    loginState: loginReducer
});

export const history = createBrowserHistory();

const store = createStore(createRootReducer(history),
    compose(
        applyMiddleware(
            routerMiddleware(history),
            thunkMiddleware
        )));

ReactDOM.render(<App store={store}/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
