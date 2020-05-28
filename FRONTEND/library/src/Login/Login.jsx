import React from 'react';
import "./Login.css"
import logo from './logo.jpg'
import axios from 'axios'

class Login extends React.Component {

    constructor() {
        super()
        this.state = {
            username: "",
            password: ""
        }

        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    handleSubmit(event) {

        axios.post('http://localhost:8090/user-service/authenticate', {
            username: this.state.username,
            password: this.state.password
        })
            .then((response) => {
                localStorage.token = response.data.token
                localStorage.username = JSON.parse(atob(localStorage.token.split('.')[1])).sub
                localStorage.logedIn = true
                var url = "http://localhost:8090/user-service/profiles/username/" + localStorage.username
                axios.get(url, {
                    headers: {
                        Authorization: "Bearer " + localStorage.token
                    }
                })
                    .then((response) => {

                        localStorage.id = response.data.id
                        localStorage.role = response.data.role.name

                    }, (error) => {
                        console.log(error)
                        alert("GET" + error)
                    });

            }, (error) => {
                alert(error)
            });

        event.preventDefault()
    }



    render() {
        return (
            <div className="login">
                <img src={logo} alt="logo" />
                <form onSubmit={this.handleSubmit}>
                    <input type="text" name="username" placeholder="Username" onChange={this.handleChange} />
                    <input type="password" name="password" placeholder="Password" onChange={this.handleChange} />
                    <input className="submit-btn" type="submit" value="Login" />
                </form>
            </div>
        )
    }
}

export default Login