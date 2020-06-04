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
            AuthorHeader: [
                { Id: "", Ime: "", Prezime: "", Drzava: "" }
            ],
            copies: [],
            modalIsOpen: false,
            validToken: false,
            name: "",
            modalAddIsOpen: false,
            idCopy: -1,
            authorOptions: [],
            authorTemp: "",
            selectedAuthor: "",
            modalShowIsOpen: false,
            authors: []
        };

        this.handleChange = this.handleChange.bind(this)
        this.createCopy = this.createCopy.bind(this)
        this.addAuthor = this.addAuthor.bind(this)
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
                            Authorization: "Bearer " + localStorage.token
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
        console.log(localStorage.token)
        var url = "http://localhost:8090/book-service/copies/" + id;
        axios.delete(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        })


        var TEMP = [...this.state.copies];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ copies: TEMP })
        alert("Uspješno obrisana kopija!");
    }

    addAuthorModal(id) {
        this.setState({ modalAddIsOpen: true, idCopy: id })
        var url = "http://localhost:8090/book-service/authors"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        })
            .then((response) => {
                var temp = []
                var data = response.data._embedded.authorList
                for (var i = 0; i < data.length; i++) {
                    temp.push({ firstName: `${data[i].firstName}`, lastName: `${data[i].lastName}`, value: data[i].firstName + " " + data[i].lastName, id: data[i].id })
                }
                this.setState({ authorOptions: temp })

            }, (error) => {
                console.log(error)
                alert(error)
            });
    }

    handleChangeAuthor = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedAuthor: selectedOption.value })
            this.setState({ authorTemp: selectedOption });
        }
    }

    addAuthor(e) {

        var idAuthor = this.state.authorOptions.find(option => option.value === this.state.selectedAuthor).id

        var url = 'http://localhost:8090/book-service/copies/' + this.state.idCopy + '/authors/' + idAuthor
        axios.post(url,
            {},
            {
                headers: {
                    Authorization: "Bearer " + localStorage.token
                }
            })
            .then((response) => {
                alert("Autor uspješno dodan")

            }, (error) => {
                console.log(error)
                alert(error)
            });
        e.preventDefault();
    }

    showAuthorModal(id) {
        this.setState({ modalShowIsOpen: true })
        var url = "http://localhost:8090/book-service/copies/" + id + '/authors'
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        })
            .then((response) => {
                this.setState({ authors: response.data._embedded.authorList })

            }, (error) => {
                console.log(error)
                alert(error)
            });
    }

    headerAuthor() {
        let header = Object.keys(this.state.AuthorHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    prikazAutora() {
        return this.state.authors.map((author, index) => {
            const { id, firstName, lastName, country } = author

            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{firstName}</td>
                    <td>{lastName}</td>
                    <td>{country.name}</td>
                </tr >
            )
        })
    }

    prikazKopije() {
        return this.state.copies.map((copy, index) => {
            const { id, bookName } = copy

            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{bookName}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deleteCopy(id)} > Obriši</button>
                        <button className="btn warning btn-akcija" onClick={e => this.addAuthorModal(id)} > Dodaj autora</button>
                        <button className="btn warning btn-akcija" onClick={e => this.showAuthorModal(id)} > Prikaz autora</button>
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
                    Authorization: "Bearer " + localStorage.token
                }
            })
            .then((response) => {
                var temp = this.state.copies
                temp.push(response.data)
                this.setState({ copies: temp })
                alert("Kopija uspješno dodana")

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
                    <h2 id='title'>Pregled/brisanje kopije knjige</h2>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazKopije()}
                        </tbody>
                    </table>
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novu kopiju</button>

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
                <Modal isOpen={this.state.modalAddIsOpen}>
                    <h2>Dodavanje novog autora</h2>
                    <form >
                        <Dropdown options={this.state.authorOptions}
                            value={this.state.authorTemp}
                            onChange={(e) => { this.handleChangeAuthor(e); }}
                            placeholder="Odaberi autora"
                            className="dropdown"
                        />
                        <button className="btn success" onClick={this.addAuthor}>Dodaj</button>

                    </form>
                    <button className="btn danger close" onClick={() => this.setState({ modalAddIsOpen: false })}>Zatvori</button>
                </Modal>
                <Modal isOpen={this.state.modalShowIsOpen}>
                    <button className="btn danger close" onClick={() => this.setState({ modalShowIsOpen: false })}>Zatvori</button>

                    <table>
                        <tbody>
                            <tr>{this.headerAuthor()}</tr>
                            {this.prikazAutora()}
                        </tbody>
                    </table>
                </Modal>
            </div>
        )
    }
}

export default Copy