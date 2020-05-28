import React, { Component } from 'react'
import axios from 'axios'
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';

export class Role extends Component {
    constructor(props) {
        super(props)
        this.state = {
            Role: [
                { Uloga: "", obrisati: false }
            ],
            role: [],
            uloga: '',
            id: '',
        };
    }

    componentWillMount() {

        var url = "http://localhost:8081/roles"
        axios.get(url, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }

        }).then((response) => {
            
            var temp = [];
            for (var i = 0; i < response.data._embedded.roleList.length; i++) {
                temp.push({ name: `${response.data._embedded.roleList[i].name}`, value: response.data._embedded.roleList[i].name, id: response.data._embedded.roleList[i].id });
            }
            this.setState({ role: temp });
        }, (error) => {
            alert("GET" + error)
        });
    }

    handleChange = (e, index) => {
        this.state.role[index].obrisati = true;
    }

    handleChangeId = (e, index) => {
        this.state.id = this.state.role[index].id;
    }

    obrisiRolu = () => {
        var url="http://localhost:8081/roles/"+this.state.id;
        console.log(url);
        axios.delete(url,{
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }

        })
        .then(response =>{
        var TEMP = [...this.state.role];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === this.state.id) TEMP.splice(i, 1);
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

        axios.post('http://localhost:8081/roles',
        {
            name: this.state.uloga,
            
        }, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }
        }).then(function (response) {
            alert("Uloga uspješno dodana!");
        })
            .catch(function (error) {
                alert(error);
            });

        var TEMP = [...this.state.role];
        const temp = {
            name: this.state.uloga,
            obrisati: false
        }
        TEMP.push(temp);
        this.setState({ role: TEMP })
    }

    prikazRole() {
        return this.state.role.map((uloga, index) => {
            const { name } = uloga
            const brisati = false;
            return (
                <tr key={name}>
                    <td>{name}</td>
                    <td>{brisati}
                        <div className="brisanje">
                            <label>
                                <input type="checkbox"
                                    brisati={this.state.checked}
                                    onChange={e => this.handleChangeId(e, index)} />
                            </label>
                        </div>
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
        return (
            <div>
                <h2 id='title'>Pregled/brisanje uloge</h2>
                <table id='korisnici'>
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazRole()}
                    </tbody>
                </table>
                <div className="footer">
                    <button type="button" className="btn" onClick={this.obrisiRolu}>
                        Obriši ulogu
                </button>
                </div>
                <div className="forma">
                    <h2 id='title'>Dodavanje nove uloge</h2>
                    <div className="form-grupa">
                        <label htmlFor="username">Naziv:</label>
                        <input type="text"
                            name="uloga"
                            value={this.state.uloga}
                            onChange={e => this.unosNovog(e)} />
                    </div>
                    <button type="button" className="btn" onClick={this.kreirajRolu}>
                        Dodavanje nove uloge
                    </button>
                </div>
            </div>
        )
    }
}

