import React from 'react';
import thunkMiddleware from 'redux-thunk'
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';
import {applyMiddleware, combineReducers, createStore} from "redux";
import {loginReducer} from "./components/authorization/reducer";
import {connectRouter, routerMiddleware} from "connected-react-router";
import {createBrowserHistory} from 'history'
import {composeWithDevTools} from "redux-devtools-extension";
import {dashboardReducer} from "./components/dashboard/reducer";

const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    loginState: loginReducer,
    dashboardState: dashboardReducer
});

export const history = createBrowserHistory();

const composeEnhancers = composeWithDevTools({});

const store = createStore(createRootReducer(history),
    composeEnhancers(
        applyMiddleware(
            routerMiddleware(history),
            thunkMiddleware
        )));

ReactDOM.render(<App store={store}/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
