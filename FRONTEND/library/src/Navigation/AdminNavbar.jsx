import React, { Component } from 'react'
import 'react-dropdown/style.css';
import "react-datepicker/dist/react-datepicker.css";
import { Link } from "react-router-dom"
import './style.css'



class AdminNavbar extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <nav className="navbar">
                <ul>
                    <Link to="/adminpage/profiles">
                        <li>Profili</li>
                    </Link>
                    <Link to="/adminpage/roles">
                        <li>Uloge</li>
                    </Link>
                    <Link to="/adminpage/employees">
                        <li>Uposlenici</li>
                    </Link>
                    <Link to="/adminpage/addmember">
                        <li>Dodavanje člana</li>
                    </Link>
                    <Link to="/adminpage/izvjestaj">
                        <li>Izvjestaj</li>
                    </Link>
                </ul>
            </nav>
        )
    }
}

export default AdminNavbar