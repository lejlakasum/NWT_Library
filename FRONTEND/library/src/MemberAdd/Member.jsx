import React, { Component } from 'react'
import axios from 'axios'
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Modal from 'react-modal';

export class Member extends Component {
    constructor(props) {
        super(props)
        this.state = {
            Clanovi: [
                { Ime: "", Prezime: "", DatumRođenja: "", DatumUčlanjenja: "", obrisati: false }
            ],
            profile: [],
            members: [],
            membershiptypes: [],
            ime: '',
            prezime: '',
            plata: '',
            active: '',
            aktivnost: ['aktivan', 'neaktivan'],
            tipProfila: '',
            tipMembershipa: '',
            tipAktivnosti: '',
            temp: '',
            temp2: '',
            tempAktivan: '',
            id: '',
            joinDate: new Date(),
            modalIsOpen: false,
            validToken: false
        };
        this.kreirajMembera = this.kreirajMembera.bind(this)
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
                            temp.push({ name: `${response.data._embedded.profileList[i].firstName}`, value: response.data._embedded.profileList[i].firstName + " " + response.data._embedded.profileList[i].lastName, firstName: response.data._embedded.profileList[i].firstName, lastName: response.data._embedded.profileList[i].lastName, birthDate: response.data._embedded.profileList[i].birthDate, roleId: response.data._embedded.profileList[i].role.roleId, roleName: response.data._embedded.profileList[i].role.name, id: response.data._embedded.profileList[i].id });
                        }

                        this.setState({ profile: temp });

                    }, (error) => {
                        console.log(error)
                        alert("GET" + error)
                    });
                    var url2 = "http://localhost:8090/user-service/members"
                    axios.get(url2, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }

                    }).then((response) => {

                        this.setState({ members: response.data._embedded.memberList });
                    }, (error) => {
                        console.log(error)
                        alert("GET" + error)
                    });
                    var url = "http://localhost:8090/user-service/membershiptypes"

                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }
                    }).then((response) => {

                        var temp = [];
                        for (var i = 0; i < response.data._embedded.membershipTypeList.length; i++) {
                            temp.push({ name: `${response.data._embedded.membershipTypeList[i].name}`, value: response.data._embedded.membershipTypeList[i].name, id: response.data._embedded.membershipTypeList[i].id });
                        }

                        this.setState({ membershiptypes: temp });

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
        this.state.members[index].obrisati = true;
    }

    handleChangeId = (e, index) => {
        this.state.id = this.state.members[index].id;
    }

    handleChangeDate = date => {
        this.setState({
            joinDate: date
        });
    }

    handleChangeProfile = (selectedOption) => {
        if (selectedOption) {
            this.setState({ tipProfila: selectedOption.value });
            this.setState({ temp: selectedOption });
        }
    }

    handleChangeMembershipType = (selectedOption) => {
        if (selectedOption) {
            this.setState({ tipMembershipa: selectedOption.value });
            this.setState({ temp2: selectedOption });
        }
    }

    handleChangeAktivnost = (selectedOption) => {
        if (selectedOption === "aktivan") {
            this.setState({ tipAktivnosti: "active" });
            this.setState({ tempAktivan: "active" });
        }
    }

    obrisiMember = (id) => {
        var url = "http://localhost:8090/user-service/members/" + id;
        console.log(url);
        axios.delete(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }

        })
            .then(response => {
                var TEMP = [...this.state.members];
                for (var i = 0; i < TEMP.length; i++) {
                    if (TEMP[i].id === id) TEMP.splice(i, 1);
                }
                this.setState({ members: TEMP })
            }).then(function (response) {
                alert("Član uspješno obrisan!");
            })
            .catch(function (error) {
                alert(error);
            });
    }

    kreirajMembera = () => {
        var idProfila = -1;
        for (var i = 0; i < this.state.profile.length; i++) {
            if (this.state.profile[i].value === this.state.tipProfila) idProfila = this.state.profile[i].id;
        }
        var idMembershipa = -1;
        for (var i = 0; i < this.state.membershiptypes.length; i++) {
            if (this.state.membershiptypes[i].value === this.state.tipMembershipa) idMembershipa = this.state.membershiptypes[i].id;
        }
        console.log(idMembershipa + " " + idProfila);

        axios.post('http://localhost:8090/user-service/members',
            {

                profile: {
                    id: idProfila
                },
                membershipType: {
                    id: idMembershipa
                },
                joinDate: this.state.joinDate,
                active: true
            }, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        }).then((response) => {
            console.log(response.data.profile)
            var temp = this.state.members
            temp.push(response.data)
            this.setState({ members: temp })
            alert("Član uspješno dodan!");
        })
            .catch(function (error) {
                alert(error);
            });
    }

    prikazMembera() {
        return this.state.members.map((clan, index) => {
            const { id, profile, joinDate } = clan
            const brisati = false;
            return (
                <tr key={id}>
                    <td>{profile.firstName}</td>
                    <td>{profile.lastName}</td>
                    <td>{profile.birthDate}</td>
                    <td>{joinDate}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.obrisiMember(id)} > Obriši</button>
                    </td>
                </tr>
            )
        })
    }

    headerTabele() {
        let header = Object.keys(this.state.Clanovi[0])
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
                <h2 id='title'>Pregled/brisanje člana</h2>
                <table >
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazMembera()}
                    </tbody>
                </table>
                <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novog člana</button>

                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2 id='title'>Dodavanje novog člana</h2>


                        <Dropdown options={this.state.profile}
                            value={this.state.temp}
                            onChange={(e) => {
                                this.handleChangeProfile(e);
                            }}
                            placeholder="Odaberite člana"
                        />


                        <Dropdown options={this.state.membershiptypes}
                            value={this.state.temp2}
                            onChange={(e) => {
                                this.handleChangeMembershipType(e);
                            }}
                            placeholder="Odaberite tip članstva:"
                        />
                    </div>
                    <button type="button" className="btn success add" onClick={this.kreirajMembera}>
                        Dodavanje novog člana
                    </button>
                    <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>

                </Modal>
            </div>
        )
    }
}

