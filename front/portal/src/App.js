import React from 'react';
import './App.css';
import NavigationBar from './components/layout/NavigationBar.jsx';
import Login from './components/login/Login.jsx';
import Registration from './components/registration/Registration.jsx';
import Dashboard from './components/dashboard/Dashboard.jsx';
import {Route, Switch} from 'react-router-dom';
import {Provider} from "react-redux";
import {ConnectedRouter} from "connected-react-router";
import {history} from "./index";

function App({store}) {
    return (
        <Provider store={store}>
            <ConnectedRouter history={history}>
                <NavigationBar/>
                <Switch>
                    <Route path="/dashboard" component={Dashboard}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/" exact component={Registration}/>
                </Switch>
            </ConnectedRouter>
        </Provider>
    );
}

export default App;
