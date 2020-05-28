import React, { useState } from 'react';
import "./Book.css"
import axios from 'axios'
import Modal from 'react-modal'
import Dropdown from 'react-dropdown';

class Book extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            BookHeader: [
                { Id: "", ISBN: "", Naziv: "", Tip: "", Zanr: "", Izdavac: "", Akcije: "" }
            ],
            books: [],
            modalIsOpen: false,
            isbn: '',
            copyOptions: [],
            copyTemp: '',
            selectedCopy: '',
            typeOptions: [],
            typeTemp: '',
            selectedType: '',
            genreOptions: [],
            genreTemp: '',
            selectedGenre: '',
            publisherOptions: [],
            publisherTemp: '',
            selectedPublisher: '',
            modalRentIsOpen: false,
            rentId: -1,
            rentAvailable: false,
            memberOptions: [],
            memberTemp: '',
            selectedMember: '',
            nazivKnjige: '',
            copyBooks: []
        };

        this.handleChange = this.handleChange.bind(this)
        this.createBook = this.createBook.bind(this)
        this.rentBook = this.rentBook.bind(this)
        this.searchBook = this.searchBook.bind(this)
        //this.deleteBook = this.deleteBook.bind(this)
    }

    componentWillMount() {

        var url = "http://localhost:8090/book-service/books"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                this.setState({ books: response.data._embedded.bookList, copyBooks: response.data._embedded.bookList })

            }, (error) => {
                console.log(error)
                alert(error)
            });

        var url = "http://localhost:8090/book-service/copies"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                var temp = []
                var data = response.data._embedded.copyList
                for (var i = 0; i < data.length; i++) {
                    temp.push({ bookName: `${data[i].bookName}`, value: data[i].bookName, id: data[i].id })
                }
                this.setState({ copyOptions: temp })

            }, (error) => {
                console.log(error)
                alert(error)
            });

        var url = "http://localhost:8090/book-service/booktypes"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                var temp = []
                var data = response.data._embedded.bookTypeList
                for (var i = 0; i < data.length; i++) {
                    temp.push({ name: `${data[i].name}`, value: data[i].name, id: data[i].id })
                }
                this.setState({ typeOptions: temp })

            }, (error) => {
                console.log(error)
                alert(error)
            });

        var url = "http://localhost:8090/book-service/genres"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                var temp = []
                var data = response.data._embedded.genreList
                for (var i = 0; i < data.length; i++) {
                    temp.push({ name: `${data[i].name}`, value: data[i].name, id: data[i].id })
                }
                this.setState({ genreOptions: temp })

            }, (error) => {
                console.log(error)
                alert(error)
            });

        var url = "http://localhost:8090/book-service/publishers"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                var temp = []
                var data = response.data._embedded.publisherList
                for (var i = 0; i < data.length; i++) {
                    temp.push({ name: `${data[i].name}`, value: data[i].name, id: data[i].id })
                }
                this.setState({ publisherOptions: temp })

            }, (error) => {
                console.log(error)
                alert(error)
            });
    }

    deleteBook(id) {
        var url = "http://localhost:8090/book-service/books/" + id;
        axios.delete(url)

        var TEMP = [...this.state.books];
        for (var i = 0; i < TEMP.length; i++) {
            if (TEMP[i].id === id) TEMP.splice(i, 1);
        }
        this.setState({ books: TEMP })
        alert("Uspješno obrisana knjiga!");
    }

    rentModal(id, available) {
        this.setState({ rentId: id })
        this.setState({ rentAvailable: available })
        this.setState({ modalRentIsOpen: true })

        var url = "http://localhost:8090/book-service/members"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                var temp = []
                var data = response.data._embedded.memberList
                for (var i = 0; i < data.length; i++) {
                    temp.push({
                        firstName: `${data[i].firstName}`,
                        lastName: `${data[i].lastName}`,
                        value: data[i].id + " " + data[i].firstName + " " + data[i].lastName,
                        id: data[i].id
                    })
                }
                this.setState({ memberOptions: temp })

            }, (error) => {
                console.log(error)
                alert(error)
            });
    }

    rentBook() {

        var idMember = this.state.memberOptions.find(option => option.value === this.state.selectedMember).id
        var url = 'http://localhost:8090/book-service/members/' + idMember + '/borrowings/' + this.state.rentId
        if (this.state.rentAvailable) {

            axios.post(url,
                {},
                {
                    headers: {
                        Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNzE3MzM1LCJpYXQiOjE1OTA2ODg1MzV9.DP8RPvApKX4vELiMvQia76SjVWkH0ieP0tbKpstbh_s"
                    }
                })
                .then((response) => {

                    var temp = this.state.books
                    for (var i = 0; i < temp.length; i++) {
                        if (temp[i].id === this.state.rentId) {
                            temp[i].available = false
                        }
                    }
                    this.setState({ books: temp })

                }, (error) => {
                    console.log(error)
                    alert(error)
                });
            alert("Knjiga uspješno iznajmljena")
        }
        else {
            axios.put(url,
                {},
                {
                    headers: {
                        Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2xvcmVzIiwiZXhwIjoxNTkwNzE3MzM1LCJpYXQiOjE1OTA2ODg1MzV9.DP8RPvApKX4vELiMvQia76SjVWkH0ieP0tbKpstbh_s"
                    }
                })
                .then((response) => {
                    var temp = this.state.books
                    for (var i = 0; i < temp.length; i++) {
                        if (temp[i].id === this.state.rentId) {
                            temp[i].available = true
                        }
                    }
                    this.setState({ books: temp })

                }, (error) => {
                    console.log(error)
                    alert(error)
                });
            alert("Knjiga uspješno vraćena")
        }
    }

    //Prikaz u tabeli
    prikazKnjigu() {
        return this.state.books.map((book, index) => {
            const { id, isbn, copy, bookType, genre, publisher, publishedDate, available } = book
            var rent = "Iznajmi"
            if (!available) {
                rent = "Vrati"
            }
            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{isbn}</td>
                    <td>{copy.bookName}</td>
                    <td>{bookType.name}</td>
                    <td>{genre.name}</td>
                    <td>{publisher.name}</td>
                    <td>
                        <button className="btn danger btn-akcija" onClick={e => this.deleteBook(id)} > Obrisi</button>
                        <p> </p>
                        <button className="btn warning btn-akcija" onClick={e => this.rentModal(id, available)} >{rent}</button>
                    </td>
                </tr >
            )
        })
    }

    headerTabele() {
        let header = Object.keys(this.state.BookHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    //Dodavanje nove
    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    handleChangeCopy = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedCopy: selectedOption.value })
            this.setState({ copyTemp: selectedOption });
        }
    }

    handleChangeType = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedType: selectedOption.value })
            this.setState({ typeTemp: selectedOption });
        }
    }

    handleChangeGenre = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedGenre: selectedOption.value })
            this.setState({ genreTemp: selectedOption });
        }
    }

    handleChangePublisher = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedPublisher: selectedOption.value })
            this.setState({ publisherTemp: selectedOption });
        }
    }

    handleChangeMember = (selectedOption) => {
        if (selectedOption) {
            this.setState({ selectedMember: selectedOption.value })
            this.setState({ memberTemp: selectedOption });
        }
    }

    createBook() {

        var idCopy = this.state.copyOptions.find(option => option.value === this.state.selectedCopy).id
        var idType = this.state.typeOptions.find(option => option.value === this.state.selectedType).id
        var idGenre = this.state.genreOptions.find(option => option.value === this.state.selectedGenre).id
        var idPublisher = this.state.publisherOptions.find(option => option.value === this.state.selectedPublisher).id

        axios.post('http://localhost:8090/book-service/books',
            {
                isbn: this.state.isbn,
                copy: {
                    id: idCopy
                },
                bookType: {
                    id: idType
                },
                genre: {
                    id: idGenre
                },
                publisher: {
                    id: idPublisher
                },
                publishedDate: new Date(),
                available: true
            },
            {
                headers: {
                    Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
                }
            })
            .then((response) => {
                var temp = this.state.books
                temp.push(response.data)
                this.setState({ books: temp })
                alert("Knjiga uspjesno dodana")

            }, (error) => {
                console.log(error)
                alert(error)
            });

    }

    searchBook(e) {
        e.preventDefault();

        if (this.state.nazivKnjige.length == 0) {
            this.setState({ books: this.state.copyBooks })
        }
        else {
            var temp = []
            for (var i = 0; i < this.state.copyBooks.length; i++) {
                if (this.state.copyBooks[i].copy.bookName == this.state.nazivKnjige) {
                    temp.push(this.state.copyBooks[i])
                }
            }
            this.setState({ books: temp })
        }
    }

    render() {
        return (
            <div>
                <div className="global">
                    <button className="btn success add" onClick={() => this.setState({ modalIsOpen: true })}>Dodaj novu knjigu</button>
                    <div>
                        <form className="seacrh">
                            <input type="search" name="nazivKnjige" placeholder="Naziv knjige" onChange={this.handleChange} />
                            <button className="btn info" onClick={this.searchBook}>Pretraga</button>
                        </form>
                    </div>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazKnjigu()}
                        </tbody>
                    </table>
                </div>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div className="modal">
                        <h2>Dodavanje nove knjige</h2>
                        <form >
                            <input type="text" name="isbn" placeholder="Isbn" onChange={this.handleChange} />

                            <Dropdown options={this.state.copyOptions}
                                value={this.state.copyTemp}
                                onChange={(e) => { this.handleChangeCopy(e); }}
                                placeholder="Kopija"
                                className="dropdown"
                            />
                            <Dropdown options={this.state.typeOptions}
                                value={this.state.typeTemp}
                                onChange={(e) => { this.handleChangeType(e); }}
                                placeholder="Tip"
                                className="dropdown"
                            />
                            <Dropdown options={this.state.genreOptions}
                                value={this.state.genreTemp}
                                onChange={(e) => { this.handleChangeGenre(e); }}
                                placeholder="Zanr"
                                className="dropdown"
                            />
                            <Dropdown options={this.state.publisherOptions}
                                value={this.state.publisherTemp}
                                onChange={(e) => { this.handleChangePublisher(e); }}
                                placeholder="Izdavac"
                                className="dropdown"
                            />
                            <button className="btn success" onClick={this.createBook}>Dodaj</button>

                        </form>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>

                <Modal isOpen={this.state.modalRentIsOpen}>
                    <div className="modal">
                        <h2>Iznajmljivanje/vraćanje knjige</h2>
                        <form>
                            <Dropdown
                                options={this.state.memberOptions}
                                value={this.state.memberTemp}
                                onChange={(e) => { this.handleChangeMember(e); }}
                                placeholder="Odaberite člana"
                                className="dropdown"
                            />
                        </form>
                        <button className="btn btn-akcija success" onClick={this.rentBook}>Završi</button>
                        <p></p>
                        <button className="btn btn-akcija danger" onClick={() => this.setState({ modalRentIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div>
        )
    }
}


export default Book