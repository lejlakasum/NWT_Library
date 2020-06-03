import React, { Component } from 'react'
import axios from 'axios'
import DatePicker from "react-datepicker";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import "react-datepicker/dist/react-datepicker.css";
import Modal from 'react-modal';

export class Profile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            Profili: [
                { Ime: "", Prezime: "", Uloga: "", obrisati: false }
            ],
            profile: [],
            role: [],
            ime: '',
            prezime: '',
            tipRole: '',
            temp: '',
            id: '',
            birthDate: new Date(),
            modalIsOpen: false,
            validToken: false,
            username:'',
            password:''
        };
        this.wrapper = React.createRef();
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

                    var url = "http://localhost:8090/user-service/profiles"

                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }
                    }).then((response) => {

                        var temp = [];
                        for (var i = 0; i < response.data._embedded.profileList.length; i++) {
                            temp.push({ name: `${response.data._embedded.profileList[i].firstName}`, value: response.data._embedded.profileList[i].firstName, firstName: response.data._embedded.profileList[i].firstName, lastName: response.data._embedded.profileList[i].lastName, birthDate: response.data._embedded.profileList[i].birthDate, roleId: response.data._embedded.profileList[i].role.roleId, roleName: response.data._embedded.profileList[i].role.name, id: response.data._embedded.profileList[i].id });
                        }

                        this.setState({ profile: temp });

                    }, (error) => {
                        console.log(error)
                        alert("GET" + error)
                    });
                    var url2 = "http://localhost:8090/user-service/roles"
                    axios.get(url2, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }

                    }).then((response) => {

                        var temp = [];
                        for (var i = 0; i < response.data._embedded.roleList.length; i++) {
                            temp.push({ name: `${response.data._embedded.roleList[i].name}`, value: response.data._embedded.roleList[i].name, id: response.data._embedded.roleList[i].id });
                        }
                        this.setState({ role: temp });
                    }, (error) => {
                        console.log(error)
                        alert("GET" + error)
                    });
                }

            }, (error) => {
                this.setState({ validToken: false })
            });

    }

    handleChange = (e, index) => {
        this.state.profile[index].obrisati = true;
    }

    handleChangeId = (e, index) => {
        this.state.id = this.state.profile[index].id;
    }

    handleChangeRole = (selectedOption) => {
        if (selectedOption) {
            this.setState({ tipRole: selectedOption.value });
            this.setState({ temp: selectedOption });
        }
    }

    obrisiProfil = (id) => {
        var url = "http://localhost:8090/user-service/profiles/" + id;
        console.log(url);
        axios.delete(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }

        })
            .then(response => {
                var TEMP = [...this.state.profile];
                for (var i = 0; i < TEMP.length; i++) {
                    if (TEMP[i].id === id) TEMP.splice(i, 1);
                }
                this.setState({ profile: TEMP })
            }).then(function (response) {
                alert("Profil uspješno obrisan!");
            })
            .catch(function (error) {
                alert(error);
            });
    }

    kreirajProfile = () => {
        var idRole = -1;
        for (var i = 0; i < this.state.role.length; i++) {
            if (this.state.role[i].value === this.state.tipRole) idRole = this.state.role[i].id;
        }
        console.log(this.state.ime + " " + this.state.prezime + " " + this.state.birthDate + " " + idRole);

        axios.post('http://localhost:8090/user-service/profiles',
            {
                firstName: this.state.ime,
                lastName: this.state.prezime,
                birthDate: this.state.birthDate,
                role: {
                    id: idRole
                },
                username: this.state.username,
                password: this.state.password
            }, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        }).then(function (response) {
            alert("Profil uspješno dodan!");
        })
            .catch(function (error) {
                alert(error);
            });

        var TEMP = [...this.state.profile];
        const temp = {
            firstName: this.state.ime,
            lastName: this.state.prezime,
            birthDate: this.state.birthDate,
            role: {
                id: idRole
            },
            username: this.state.username,
            password: this.state.password,
            obrisati: false
        }
        TEMP.push(temp);
        this.setState({ profile: TEMP })
    }

    prikazProfila() {
        return this.state.profile.map((profil, index) => {
            const { id, firstName, lastName, birthDate, roleId, roleName } = profil
            const brisati = false;
            return (
                <tr key={id}>
                    <td>{firstName}</td>
                    <td>{lastName}</td>
                    <td>{roleName}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.obrisiProfil(id)} > Obriši</button>
                    </td>
                </tr>
            )
        })
    }

    headerTabele() {
        let header = Object.keys(this.state.Profili[0])
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
                <h2 id='title'>Pregled/brisanje profila</h2>
                <table >
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazProfila()}
                    </tbody>
                </table>
                <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novi profil</button>

                <Modal isOpen={this.state.modalIsOpen} >

                    <div className="modal">
                        <h2 id='title'>Dodavanje novog profila</h2>

                        <label htmlFor="username">Ime:</label>
                        <input type="text"
                            name="ime"
                            onChange={e => this.unosNovog(e)} />


                        <label htmlFor="username">Prezime:</label>
                        <input type="text"
                            name="prezime"
                            onChange={e => this.unosNovog(e)} />


                        <label htmlFor="username">Odaberite ulogu:</label>
                        <Dropdown options={this.state.role}
                            value={this.state.temp}
                            onChange={(e) => {
                                this.handleChangeRole(e);
                            }}
                            placeholder="Odaberite ponuđeni tip uloge"
                        />

                        <label htmlFor="username">Username:</label>
                        <input type="text"
                            name="username"
                            onChange={e => this.unosNovog(e)} />

                        <label htmlFor="username">Password:</label>
                        <input type="password"
                            name="password"
                            onChange={e => this.unosNovog(e)} />

                        <button type="button" className="btn success add" onClick={this.kreirajProfile}>
                            Dodavanje novog profila
                    </button>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>

                    </div>
                </Modal>
            </div>
        )
    }
}

