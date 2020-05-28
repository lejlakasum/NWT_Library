import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom"

import Login from "./Login/Login"
import Admin from './Admin/Admin';
import Book from './Book/Book'
import Genre from "./Genre/Genre"
import { Profile } from "./Profile/index"
import { Role } from "./Role/index"
import { Employee } from "./Employee/index"


function App() {
  return (
    <div>
      <Router>
        <Route path="/login" component={Login} />
        <Route path="/admin" component={Admin} />
        <Route path="/books" component={Book} />
        <Route path="/profiles" component={Profile} />
        <Route path="/roles" component={Role} />
        <Route path="/employees" component={Employee} />
      </Router>
    </div>
  );
}

export default App;
