import React from "react";
import { withRouter, Redirect, Router, Route } from 'react-router-dom';
import './style.css';
import axios from 'axios'
import Admin from '../Admin/Admin';
import { Profile } from "../Profile/index"
import { Role } from "../Role/index"
import { Employee } from "../Employee/index"
import Login from "../Login/Login"
import AdminNavbar from '../Navigation/AdminNavbar'

class AdminPage extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            validToken: false
        };
    }

    componentWillMount() {
        //CHECK TOKEN
        var url = "http://localhost:8090/user-service/validate-token"
        axios.post(url, {
            token: localStorage.token,
            username: localStorage.username
        })
            .then((response) => {
                localStorage.role = response.data.role
                localStorage.id = response.data.userId
                if (localStorage.role == "ADMIN") {
                    this.setState({ validToken: true })
                    //DO LOADING IF NEEDED
                }

            }, (error) => {
                this.setState({ validToken: false })
            });
    }

    logout() {
        localStorage.token = ""
    }
    render() {
        if (!this.state.validToken) {
            return (
                <div></div>
            )
        }
        return (
            <div>
                <Router>
                    <div>
                        <AdminNavbar />
                        <Route path="/adminpage/profiles" component={Profile} />
                        <Route path="/adminpage/roles" component={Role} />
                        <Route path="/adminpage/employees" component={Employee} />
                        <Route path="/" exact component={Login} />
                    </div>
                    <a href="/" onClick={this.logout} className="odjavaLink">Odjava</a>
                </Router>
            </div>
        )
    }
}

export default AdminPage