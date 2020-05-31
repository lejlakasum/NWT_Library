import React from 'react';
import "./Login.css"
import logo from './logo.jpg'
import axios from 'axios'

class Login extends React.Component {

    constructor() {
        super()
        this.state = {
            username: "",
            password: "",
            errorMessage: ""
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
                this.setState({ errorMessage: "" })
                localStorage.token = response.data.token
                localStorage.username = JSON.parse(atob(localStorage.token.split('.')[1])).sub

                var url = "http://localhost:8090/user-service/validate-token"
                axios.post(url, {
                    token: localStorage.token,
                    username: localStorage.username
                })
                    .then((response) => {
                        if (response.data.role == "STUFF") {
                            this.props.history.push('/stuff')
                        }
                        else if (response.data.role == "ADMIN") {
                            this.props.history.push('/adminpage')
                        }
                        else if (response.data.role == "MEMBER") {
                            this.props.history.push('/member')
                        }
                    })

            }, (error) => {
                this.setState({ errorMessage: "Pogrešni podaci" })
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
                    <label style={{ color: "red" }}>{this.state.errorMessage}</label>
                    <input className="submit-btn" type="submit" value="Login" />
                </form>
            </div>
        )
    }
}

export default Login