import React from 'react';
import './App.css';
import NavigationBar from './components/layout/NavigationBar.jsx';
import Login from './components/login/Login.jsx';
import Registration from './components/registration/Registration.jsx';
import Dashboard from './components/dashboard/Dashboard.jsx';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {Provider} from "react-redux";

function App({store}) {
    return (
        <Provider store={store}>
            <Router>
                <NavigationBar/>
                <Switch>
                    <Route path="/dashboard" component={Dashboard}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/" exact component={Registration}/>
                </Switch>
            </Router>
        </Provider>
    );
}

export default App;
