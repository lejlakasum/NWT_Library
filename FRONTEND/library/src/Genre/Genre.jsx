import React, { useState } from 'react';
import "./Genre.css"
import axios from 'axios'
import Modal from 'react-modal'
import Dropdown from 'react-dropdown';

class Genre extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            GenreHeader: [
                { Id: "", Naziv: "", Akcije: "" }
            ],
            genres: [],
            modalIsOpen: false,
            validToken: false,
            name: ""
        };

        this.handleChange = this.handleChange.bind(this)
        this.createGenre = this.createGenre.bind(this)
    }

    componentWillMount() {

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

                    var url = "http://localhost:8090/book-service/genres"
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                        }
                    })
                        .then((response) => {
                            this.setState({ genres: response.data._embedded.genreList })

                        }, (error) => {
                            console.log(error)
                            alert(error)
                        });
                }

            }, (error) => {
                this.setState({ validToken: false })
            });
    }

    //Dodavanje nove
    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    headerTabele() {
        let header = Object.keys(this.state.GenreHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    deleteGenre(id) {
        var url = "http://localhost:8090/book-service/genres/" + id;
        axios.delete(url)

        var TEMP = [...this.state.genres];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ genres: TEMP })
        alert("Uspješno obrisan zanr!");
    }

    prikazZanra() {
        return this.state.genres.map((genre, index) => {
            const { id, name } = genre

            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{name}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deleteGenre(id)} > Obrisi</button>
                    </td>
                </tr >
            )
        })
    }

    createGenre(e) {

        axios.post('http://localhost:8090/book-service/genres',
            {
                name: this.state.name,
            },
            {
                headers: {
                    Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                }
            })
            .then((response) => {
                var temp = this.state.genres
                temp.push(response.data)
                this.setState({ genres: temp })
                alert("Zanr uspjesno dodan")

            }, (error) => {
                console.log(error)
                alert(error)
            });
        e.preventDefault();

    }

    render() {

        if (!this.state.validToken) {
            return (
                <div></div>
            )
        }

        return (
            <div>
                <div className="global">
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novi zanr</button>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazZanra()}
                        </tbody>
                    </table>
                </div>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2>Dodavanje novog zanra</h2>
                        <form >
                            <input type="text" name="name" placeholder="Naziv zanra" onChange={this.handleChange} />

                            <button className="btn success" onClick={this.createGenre}>Dodaj</button>

                        </form>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div>
        )
    }
}

export default Genre