import React from 'react';
import "./Login.css"
import logo from './logo.jpg'
import user from './user.jpg'

class Login extends React.Component {
    render() {
        return (
            <div className="login">
                <img src={logo} alt="logo" />
                <form>
                    <input type="text" placeholder="Username" />
                    <input type="password" placeholder="Password" />
                    <input className="submit-btn" type="submit" value="Login" />
                </form>
            </div>
        )
    }
}

export default Login