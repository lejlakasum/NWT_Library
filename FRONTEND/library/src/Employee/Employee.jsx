import React, { Component } from 'react'
import axios from 'axios'
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export class Employee extends Component {
    constructor(props) {
        super(props)
        this.state = {
            Uposlenici: [
                { Ime: "", Prezime: "", DatumRođenja: "", Plata: "", obrisati: false }
            ],
            profile: [],
            employee: [],
            ime: '',
            prezime: '',
            plata:'',
            tipProfila: '',
            temp: '',
            id: '',
            birthDate: new Date()
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
        var url2 = "http://localhost:8081/employees"
        axios.get(url2, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }

        }).then((response) => {
            
            var temp = [];
            for (var i = 0; i < response.data._embedded.employeeList.length; i++) {
                temp.push({ name: `${response.data._embedded.employeeList[i].profile.firstName}`, value: response.data._embedded.employeeList[i].profile.firstName,firstName:response.data._embedded.employeeList[i].profile.firstName, lastName:response.data._embedded.employeeList[i].profile.lastName, birthDate:response.data._embedded.employeeList[i].profile.birthDate, salary:response.data._embedded.employeeList[i].salary, id: response.data._embedded.employeeList[i].id });
            }
            this.setState({ employee: temp });
        }, (error) => {
            console.log(error)
            alert("GET" + error)
        });
    }

    handleChange = (e, index) => {
        this.state.employee[index].obrisati = true;
    }

    handleChangeId = (e, index) => {
        this.state.id = this.state.employee[index].id;
    }

    handleChangeDate = date => {
        this.setState({
            birthDate: date
        });
    }

    handleChangeProfile = (selectedOption) => {
        if (selectedOption) {
            this.setState({ tipProfila: selectedOption.value });
            this.setState({temp:selectedOption});
        }
    }

    obrisiEmployee = () => {
        var url="http://localhost:8081/employees/"+this.state.id;
        console.log(url);
        axios.delete(url,{
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }

        })
        .then(response =>{
        var TEMP = [...this.state.employee];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === this.state.id) TEMP.splice(i, 1);
        }
        this.setState({ employee: TEMP })
        }).then(function (response) {
            alert("Zaposlenik uspješno obrisan!");
        })
            .catch(function (error) {
                alert(error);
            });
    }

    kreirajZaposlenika = () => {
        var idProfila = -1;
        for (var i = 0; i < this.state.profile.length; i++) {
            if (this.state.profile[i].value === this.state.tipProfila) idProfila = this.state.profile[i].id;
        }
        console.log(this.state.ime+" "+this.state.prezime+" "+this.state.birthDate+" "+idProfila);

        axios.post('http://localhost:8081/employees',
        {
            
            profile: {
                id: idProfila
            },
            salary:this.state.plata
        }, {
            headers: {
                Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNjIwODMzLCJpYXQiOjE1OTA1OTIwMzN9.UXhalqCMnRhrqXufEI3V5uxKiM03TkAbX3J7iQwzva4"
            }
        }).then(function (response) {
            alert("Zaposlenik uspješno dodan!");
        })
            .catch(function (error) {
                alert(error);
            });

        var TEMP = [...this.state.employee];
        const temp = {
            profile: {
                id: idProfila
            },
            salary:this.state.salary,
            obrisati: false
        }
        TEMP.push(temp);
        this.setState({ employee:TEMP })
    }

    prikazEmployee() {
        return this.state.employee.map((uposlenik, index) => {
            const { firstName, lastName, birthDate, salary } = uposlenik
            const brisati = false;
            return (
                <tr key={firstName}>
                    <td>{firstName}</td>
                    <td>{lastName}</td>
                    <td>{birthDate}</td>
                    <td>{salary}</td>
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
        let header = Object.keys(this.state.Uposlenici[0])
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
                <h2 id='title'>Pregled/brisanje zaposlenika</h2>
                <table id='korisnici'>
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazEmployee()}
                    </tbody>
                </table>
                <div className="footer">
                    <button type="button" className="btn" onClick={this.obrisiEmployee}>
                        Obriši zaposlenika
                </button>
                </div>
                <div className="forma">
                    <h2 id='title'>Dodavanje novog zaposlenika</h2>
                    
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
                        <label htmlFor="username">Plata:</label>
                        <input type="number"
                            name="plata"
                            value={this.state.plata}
                            onChange={e => this.unosNovog(e)} />
                    </div>
                    <button type="button" className="btn" onClick={this.kreirajZaposlenika}>
                        Dodavanje novog zaposlenika
                </button>
                </div>
            </div>
        )
    }
}

