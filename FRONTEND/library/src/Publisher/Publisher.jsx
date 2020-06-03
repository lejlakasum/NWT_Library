import React, { useState } from 'react';
import "./Publisher.css"
import axios from 'axios'
import Modal from 'react-modal'
import Dropdown from 'react-dropdown';

class Publisher extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            PublisherHeader: [
                { Id: "", Naziv: "", Akcije: "" }
            ],
            publishers: [],
            modalIsOpen: false,
            validToken: false,
            name: ""
        };

        this.handleChange = this.handleChange.bind(this)
        this.createPublisher = this.createPublisher.bind(this)
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

                    var url = "http://localhost:8090/book-service/publishers"
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }
                    })
                        .then((response) => {
                            this.setState({ publishers: response.data._embedded.publisherList })

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
        let header = Object.keys(this.state.PublisherHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    deletePublisher(id) {
        var url = "http://localhost:8090/book-service/publishers/" + id;
        axios.delete(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        })

        var TEMP = [...this.state.publishers];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ publishers: TEMP })
        alert("Uspješno obrisan izdavač!");
    }

    prikazIzdavaca() {
        return this.state.publishers.map((publisher, index) => {
            const { id, name } = publisher

            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{name}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deletePublisher(id)} > Obriši</button>
                    </td>
                </tr >
            )
        })
    }

    createPublisher(e) {

        axios.post('http://localhost:8090/book-service/publishers',
            {
                name: this.state.name,
            },
            {
                headers: {
                    Authorization: "Bearer " + localStorage.token
                }
            })
            .then((response) => {
                var temp = this.state.publishers
                temp.push(response.data)
                this.setState({ publishers: temp })
                alert("Izdavač uspješno dodan")

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
                    <h2 id='title'>Pregled/brisanje izdavača</h2>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazIzdavaca()}
                        </tbody>
                    </table>
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novog izdavača</button>
                </div>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2>Dodavanje novog izdavača</h2>
                        <form >
                            <input type="text" name="name" placeholder="Naziv izdavača" onChange={this.handleChange} />

                            <button className="btn success" onClick={this.createPublisher}>Dodaj</button>

                        </form>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div>
        )
    }
}

export default Publisher