import React from "react";
import { withRouter, Redirect, Router, Route } from 'react-router-dom';
import './style.css';
import axios from 'axios'

class Stuff extends React.Component {

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
                if (localStorage.role == "STUFF") {
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
                Welcome stuff
                
            <a href="/" onClick={this.logout} className="odjavaLink">Odjava</a>
            </div>
        )
    }
}

export default Stuff