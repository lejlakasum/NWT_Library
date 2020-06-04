import React, { Component } from 'react'
import axios from 'axios'
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import Modal from 'react-modal';

export class Role extends Component {
    constructor(props) {
        super(props)
        this.state = {
            Role: [
                { Uloga: "", obrisati: false }
            ],
            role: [],
            uloga: '',
            modalIsOpen: '',
            validToken: false
        };

        this.kreirajRolu = this.kreirajRolu.bind(this)
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
                if (localStorage.role == "ADMIN") {
                    this.setState({ validToken: true })

                    var url = "http://localhost:8090/user-service/roles"
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }

                    }).then((response) => {
                        this.setState({ role: response.data._embedded.roleList });
                    }, (error) => {
                        alert("GET" + error)
                    });
                }

            }, (error) => {
                this.setState({ validToken: false })
            });


    }

    handleChange = (e, index) => {
        this.state.role[index].obrisati = true;
    }

    handleChangeId = (e, index) => {
        this.state.id = this.state.role[index].id;
    }

    obrisiRolu = (id) => {
        var url = "http://localhost:8090/user-service/roles/" + id;
        console.log(url);
        axios.delete(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }

        })
            .then(response => {
                var TEMP = [...this.state.role];
                for (var i = 0; i < TEMP.length; i++) {
                    if (TEMP[i].id === id) TEMP.splice(i, 1);
                }
                this.setState({ role: TEMP })
            }).then(function (response) {
                alert("Uloga uspješno obrisana!");
            })
            .catch(function (error) {
                alert(error);
            });
    }

    kreirajRolu = () => {


        axios.post('http://localhost:8090/user-service/roles',
            {
                name: this.state.uloga,

            }, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        }).then((response) => {
            var temp = this.state.role
            temp.push(response.data)
            this.setState({ role: temp })
            alert("Uloga uspješno dodana!");
        })
            .catch(function (error) {
                alert(error);
            });
    }

    prikazRole() {
        return this.state.role.map((uloga, index) => {
            const { id, name } = uloga
            const brisati = false;
            return (
                <tr key={id}>
                    <td>{name}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.obrisiRolu(id)} > Obriši</button>
                    </td>
                </tr>
            )
        })
    }

    headerTabele() {
        let header = Object.keys(this.state.Role[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    unosNovog = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    render() {
        if (!this.state.validToken) {
            return (
                <div></div>
            )
        }
        return (
            <div className="global">
                <h2 id='title'>Pregled/brisanje uloge</h2>
                <table >
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazRole()}
                    </tbody>
                </table>
                <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj ulogu</button>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2 id='title'>Dodavanje nove uloge</h2>

                        <input type="text"
                            name="uloga"
                            onChange={e => this.unosNovog(e)} />


                    </div>
                    <button type="button" className="btn success add" onClick={this.kreirajRolu}>
                        Dodavanje nove uloge
                    </button>
                    <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>

                </Modal>
            </div>
        )
    }
}

