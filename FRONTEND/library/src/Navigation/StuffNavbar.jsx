import React, { Component } from 'react'
import 'react-dropdown/style.css';
import "react-datepicker/dist/react-datepicker.css";
import { Link } from "react-router-dom"
import './style.css'



class StuffNavbar extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <nav className="navbar">
                <ul>
                    <Link to="/stuff/books">
                        <li>Knjige</li>
                    </Link>
                    <Link to="/stuff/author">
                        <li>Autori</li>
                    </Link>
                    <Link to="/stuff/copy">
                        <li>Kopije</li>
                    </Link>
                    <Link to="/stuff/genre">
                        <li>Zanrovi</li>
                    </Link>
                    <Link to="/stuff/booktype">
                        <li>Tipovi</li>
                    </Link>
                    <Link to="/stuff/publisher">
                        <li>Izdavaci</li>
                    </Link>
                </ul>
            </nav>
        )
    }
}

export default StuffNavbar