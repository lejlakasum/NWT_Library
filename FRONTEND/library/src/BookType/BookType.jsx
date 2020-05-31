import React, { useState } from 'react';
import "./BookType.css"
import axios from 'axios'
import Modal from 'react-modal'
import Dropdown from 'react-dropdown';

class BookType extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            BookTypeHeader: [
                { Id: "", Naziv: "", Naknada_za_kasnjenje: "", Akcije: "" }
            ],
            bookTypes: [],
            modalIsOpen: false,
            validToken: false,
            name: "",
            naknada: -1
        };

        this.handleChange = this.handleChange.bind(this)
        this.createBookType = this.createBookType.bind(this)
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

                    var url = "http://localhost:8090/book-service/booktypes"
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                        }
                    })
                        .then((response) => {
                            this.setState({ bookTypes: response.data._embedded.bookTypeList })

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
        let header = Object.keys(this.state.BookTypeHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    deleteBookType(id) {
        var url = "http://localhost:8090/book-service/booktypes/" + id;
        axios.delete(url)

        var TEMP = [...this.state.bookTypes];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ bookTypes: TEMP })
        alert("Uspješno obrisan tip knjige!");
    }

    prikazBookType() {
        return this.state.bookTypes.map((bookType, index) => {
            const { id, name, latencyFee } = bookType

            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{name}</td>
                    <td>{latencyFee}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deleteBookType(id)} > Obriši</button>
                    </td>
                </tr >
            )
        })
    }

    createBookType(e) {

        axios.post('http://localhost:8090/book-service/booktypes',
            {
                name: this.state.name,
                latencyFee: this.state.naknada,
                libraryReadOnly: false
            },
            {
                headers: {
                    Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                }
            })
            .then((response) => {
                var temp = this.state.bookTypes
                temp.push(response.data)
                this.setState({ bookTypes: temp })
                alert("Tip knjige uspješno dodan")

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
                <h2 id='title'>Pregled/brisanje tipa knjige</h2>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazBookType()}
                        </tbody>
                    </table>
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novi tip knjige</button>
                </div>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2>Dodavanje novog tipa knjige</h2>
                        <form >
                            <input type="text" name="name" placeholder="Naziv tipa" onChange={this.handleChange} />
                            <input type="number" name="naknada" placeholder="Iznos naknade za kasnjenje" onChange={this.handleChange} />

                            <button className="btn success" onClick={this.createBookType}>Dodaj</button>

                        </form>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div>
        )
    }
}

export default BookType