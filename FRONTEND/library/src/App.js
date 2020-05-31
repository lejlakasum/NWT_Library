import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom"

import Login from "./Login/Login"
import Member from './Member/Member'
import Stuff from './Paneli/Stuff'
import AdminPage from './Paneli/AdminPage'


function App() {
  return (
    <div>
      <Router>
        <Route path="/" exact component={Login} />
        <Route path="/adminpage" component={AdminPage} />
        <Route path="/stuff" component={Stuff} />
        <Route path="/member" component={Member} />
      </Router>
    </div>
  );
}

export default App;
