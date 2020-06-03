import React, { useState } from 'react';
import "./Author.css"
import axios from 'axios'
import Modal from 'react-modal'
import Dropdown from 'react-dropdown';

class Author extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            AuthorHeader: [
                { Id: "", Ime: "", Prezime: "", Drzava: "", Akcije: "" }
            ],
            authors: [],
            modalIsOpen: false,
            validToken: false,
            name: "",
            surname: "",
            conutryOptions: [],
            countryTemp: "",
            selectedCountry: ""
        };

        this.handleChange = this.handleChange.bind(this)
        this.createAuthor = this.createAuthor.bind(this)
    }

    componentWillMount() {

        console.log("USAO")
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

                    var url = "http://localhost:8090/book-service/authors"
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

                    url = "http://localhost:8090/book-service/countries"
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }
                    })
                        .then((response) => {
                            var temp = []
                            var data = response.data._embedded.countryList
                            for (var i = 0; i < data.length; i++) {
                                temp.push({ name: `${data[i].name}`, value: data[i].name, id: data[i].id })
                            }
                            this.setState({ conutryOptions: temp })

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
        let header = Object.keys(this.state.AuthorHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    deleteAuthor(id) {
        var url = "http://localhost:8090/book-service/authors/" + id;
        axios.delete(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        })

        var TEMP = [...this.state.authors];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ authors: TEMP })
        alert("Uspješno obrisan autor!");
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
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deleteAuthor(id)} > Obriši</button>
                    </td>
                </tr >
            )
        })
    }

    createAuthor() {

        var idCountry = this.state.conutryOptions.find(option => option.value === this.state.selectedCountry).id

        axios.post('http://localhost:8090/book-service/authors',
            {
                firstName: this.state.name,
                lastName: this.state.surname,
                birthDate: new Date(),
                country: {
                    id: idCountry
                }
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

    handleChangeCountry = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedCountry: selectedOption.value })
            this.setState({ countryTemp: selectedOption });
        }
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
                    <h2 id='title'>Pregled/brisanje autora</h2>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazAutora()}
                        </tbody>
                    </table>
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novog autora</button>
                </div>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2>Dodavanje novog autora</h2>
                        <form >
                            <input type="text" name="name" placeholder="Ime" onChange={this.handleChange} />
                            <input type="text" name="surname" placeholder="Prezime" onChange={this.handleChange} />
                            <Dropdown options={this.state.conutryOptions}
                                value={this.state.countryTemp}
                                onChange={(e) => { this.handleChangeCountry(e); }}
                                placeholder="Država"
                                className="dropdown"
                            />
                            <button className="btn success" onClick={this.createAuthor}>Dodaj</button>

                        </form>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div>
        )
    }
}

export default Author