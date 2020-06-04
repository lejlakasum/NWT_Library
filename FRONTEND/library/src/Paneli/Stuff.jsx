import React from "react";
import { withRouter, Redirect, BrowserRouter as Router, Route } from 'react-router-dom';
import './style.css';
import axios from 'axios'
import Author from '../Author/Author'
import Genre from '../Genre/Genre'
import BookType from '../BookType/BookType'
import Publisher from '../Publisher/Publisher'
import Copy from '../Copy/Copy'
import Book from '../Book/Book'
import Login from "../Login/Login"
import StuffNavbar from '../Navigation/StuffNavbar'

class Stuff extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            validToken: false
        };
        this.logout = this.logout.bind(this)
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
                alert("usao")
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
                        <StuffNavbar />
                        <Route path="/stuff/books" component={Book} />
                        <Route path="/stuff/copy" component={Copy} />
                        <Route path="/stuff/author" component={Author} />
                        <Route path="/stuff/genre" component={Genre} />
                        <Route path="/stuff/booktype" component={BookType} />
                        <Route path="/stuff/publisher" component={Publisher} />
                        <Route path="/" exact component={Login} />
                    </div>
                    <a href="/" onClick={this.logout} className="odjavaLink">Odjava</a>
                </Router>
            </div>
        )
    }
}

export default Stuff