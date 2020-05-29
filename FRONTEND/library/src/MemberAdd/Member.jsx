import React, { Component } from 'react'
import axios from 'axios'
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export class Member extends Component {
    constructor(props) {
        super(props)
        this.state = {
            Clanovi: [
                { Ime: "", Prezime: "", DatumRođenja: "", DatumUčlanjenja: "",Aktivan: "", obrisati: false }
            ],
            profile: [],
            members: [],
            membershiptypes:[],
            ime: '',
            prezime: '',
            plata:'',
            active:'',
            aktivnost:['aktivan','neaktivan'],
            tipProfila: '',
            tipMembershipa:'',
            tipAktivnosti:'',
            temp: '',
            temp2:'',
            tempAktivan: '',
            id: '',
            joinDate: new Date()
        };
        
    }

    componentWillMount() {
        var url = "http://localhost:8081/profiles"

        axios.get(url, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }
        }).then((response) => {
           
            var temp = [];
            for (var i = 0; i < response.data._embedded.profileList.length; i++) {
                temp.push({ name: `${response.data._embedded.profileList[i].firstName}`, value: response.data._embedded.profileList[i].firstName+" "+response.data._embedded.profileList[i].lastName,firstName:response.data._embedded.profileList[i].firstName, lastName:response.data._embedded.profileList[i].lastName, birthDate:response.data._embedded.profileList[i].birthDate, roleId:response.data._embedded.profileList[i].role.roleId , roleName:response.data._embedded.profileList[i].role.name, id: response.data._embedded.profileList[i].id });
            }
            
            this.setState({ profile: temp });

        }, (error) => {
            console.log(error)
            alert("GET" + error)
        });
        var url2 = "http://localhost:8081/members"
        axios.get(url2, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }

        }).then((response) => {
            
            var temp = [];
            for (var i = 0; i < response.data._embedded.memberList.length; i++) {
                temp.push({ name: `${response.data._embedded.memberList[i].profile.firstName}`, value: response.data._embedded.memberList[i].profile.firstName+response.data._embedded.memberList[i].profile.lastName ,firstName:response.data._embedded.memberList[i].profile.firstName, lastName:response.data._embedded.memberList[i].profile.lastName, birthDate:response.data._embedded.memberList[i].profile.birthDate, membershipType:response.data._embedded.memberList[i].membershipType.name, joinDate:response.data._embedded.memberList[i].joinDate, active:response.data._embedded.memberList[i].active, id: response.data._embedded.memberList[i].id });
            }
            this.setState({ members: temp });
        }, (error) => {
            console.log(error)
            alert("GET" + error)
        });
        var url = "http://localhost:8081/membershiptypes"

        axios.get(url, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
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
            this.setState({temp:selectedOption});
        }
    }

    handleChangeMembershipType = (selectedOption) => {
        if (selectedOption) {
            this.setState({ tipMembershipa: selectedOption.value });
            this.setState({temp2:selectedOption});
        }
    }

    handleChangeAktivnost = (selectedOption) => {
        if (selectedOption==="aktivan") {
            this.setState({ tipAktivnosti: "active" });
            this.setState({tempAktivan:"active"});
        }
    }

    obrisiMember = () => {
        var url="http://localhost:8081/members/"+this.state.id;
        console.log(url);
        axios.delete(url,{
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }

        })
        .then(response =>{
        var TEMP = [...this.state.members];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === this.state.id) TEMP.splice(i, 1);
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
        console.log(idMembershipa+" "+idProfila);

        axios.post('http://localhost:8081/members',
        {
            
            profile: {
                id: idProfila
            },
            membershipType:{
                id: idMembershipa
            },
            joinDate:this.state.joinDate,
            active:this.state.tipAktivnosti
        }, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }
        }).then(function (response) {
            alert("Član uspješno dodan!");
        })
            .catch(function (error) {
                alert(error);
            });

        var TEMP = [...this.state.members];
        const temp = {
            profile: {
                id: idProfila
            },
            membershipType:{
                id: idMembershipa
            },
            joinDate:this.state.joinDate,
            active:this.state.tipAktivnosti,
            obrisati: false
        }
        TEMP.push(temp);
        this.setState({ members:TEMP })
    }

    prikazMembera() {
        return this.state.members.map((clan, index) => {
            const { name, firstName, lastName, birthDate, joinDate,active } = clan
            const brisati = false;
            return (
                <tr key={name}>
                    <td>{firstName}</td>
                    <td>{lastName}</td>
                    <td>{birthDate}</td>
                    <td>{joinDate}</td>
                    <td>{active}</td>
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
        return (
            <div>
                <h2 id='title'>Pregled/brisanje člana</h2>
                <table id='korisnici'>
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazMembera()}
                    </tbody>
                </table>
                <div className="footer">
                    <button type="button" className="btn" onClick={this.obrisiMember}>
                        Obriši člana
                </button>
                </div>
                <div className="forma">
                    <h2 id='title'>Dodavanje novog člana</h2>
                    
                    <div className="form-grupa">
                        <label htmlFor="username">Odaberite profil:</label>
                        <Dropdown options={this.state.profile}
                            value={this.state.temp}
                            onChange={(e) => {
                                this.handleChangeProfile(e);
                            }}
                            placeholder="Odaberite zaposlenika"
                        />
                    </div>
                    <div className="form-grupa">
                        <label htmlFor="username">Odaberite tip članstva:</label>
                        <Dropdown options={this.state.membershiptypes}
                            value={this.state.temp2}
                            onChange={(e) => {
                                this.handleChangeMembershipType(e);
                            }}
                            placeholder="Odaberite tip"
                        />
                    </div>

                    <div className="form-grupa">
                        <label htmlFor="username">Odaberite aktivnost članstva:</label>
                        <Dropdown options={this.state.aktivnost}
                            value={this.state.tempAktivan}
                            onChange={(e) => {
                                this.handleChangeAktivnost(e);
                            }}
                            placeholder="Odaberite vrstu"
                        />
                    </div>

                    <button type="button" className="btn" onClick={this.kreirajMembera}>
                        Dodavanje novog člana
                </button>
                </div>
            </div>
        )
    }
}

