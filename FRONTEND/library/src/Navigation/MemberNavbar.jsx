import React, { Component } from 'react'
import 'react-dropdown/style.css';
import "react-datepicker/dist/react-datepicker.css";
import './style.css'



class MemberNavbar extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <nav className="navbar">
                <ul>
                    <h3>Welcome {localStorage.username}</h3>
                </ul>
            </nav>
        )
    }
}

export default MemberNavbar