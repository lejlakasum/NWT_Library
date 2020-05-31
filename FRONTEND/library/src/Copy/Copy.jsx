import React, { useState } from 'react';
import "./Copy.css"
import axios from 'axios'
import Modal from 'react-modal'
import Dropdown from 'react-dropdown';

class Copy extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            CopyHeader: [
                { Id: "", Naziv: "", Akcije: "" }
            ],
            copies: [],
            modalIsOpen: false,
            validToken: false,
            name: ""
        };

        this.handleChange = this.handleChange.bind(this)
        this.createCopy = this.createCopy.bind(this)
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

                    var url = "http://localhost:8090/book-service/copies"
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                        }
                    })
                        .then((response) => {
                            this.setState({ copies: response.data._embedded.copyList })

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
        let header = Object.keys(this.state.CopyHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    deleteCopy(id) {
        var url = "http://localhost:8090/book-service/copies/" + id;
        axios.delete(url)

        var TEMP = [...this.state.copies];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ copies: TEMP })
        alert("Uspješno obrisana kopija!");
    }

    prikazKopije() {
        return this.state.copies.map((copy, index) => {
            const { id, bookName } = copy

            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{bookName}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deleteCopy(id)} > Obrisi</button>
                    </td>
                </tr >
            )
        })
    }

    createCopy() {

        axios.post('http://localhost:8090/book-service/copies',
            {
                bookName: this.state.name,
            },
            {
                headers: {
                    Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                }
            })
            .then((response) => {
                var temp = this.state.copies
                temp.push(response.data)
                this.setState({ copies: temp })
                alert("Kopija uspjesno dodana")

            }, (error) => {
                console.log(error)
                alert(error)
            });

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
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novu kopiju</button>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazKopije()}
                        </tbody>
                    </table>
                </div>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2>Dodavanje nove kopije</h2>
                        <form >
                            <input type="text" name="name" placeholder="Naziv knjige" onChange={this.handleChange} />

                            <button className="btn success" onClick={this.createCopy}>Dodaj</button>

                        </form>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div>
        )
    }
}

export default Copy