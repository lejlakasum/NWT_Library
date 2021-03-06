import React from 'react';
import "./Member.css"
import axios from 'axios'
import Modal from 'react-modal'
import MemberNavbar from '../Navigation/MemberNavbar'

class Member extends React.Component {


    constructor(props) {
        super(props)
        this.state = {
            BookHeader: [
                { Id: "", ISBN: "", Naziv: "", Tip: "", Zanr: "", Izdavac: "", Akcije: "" }
            ],
            books: [],
            CommentHeader: [
                { Komentar: "", Ocjena: "", Član: "" }
            ],
            komentari: [],
            commentModalIsOpen: false,
            commentAddModalIsOpen: false,
            comment: "",
            ocjena: "",
            idBook: -1,
            validToken: false,
            idMember: -1
        }
        this.handleChange = this.handleChange.bind(this)
        this.addComent = this.addComent.bind(this)
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
                if (localStorage.role == "MEMBER") {
                    this.setState({ validToken: true })

                    var url = "http://localhost:8090/user-service/members/profile/" + localStorage.id
                    axios.get(url, {
                        headers: {
                            Authorization: "Bearer " + localStorage.token
                        }
                    })
                        .then((response) => {
                            this.setState({ idMember: response.data.id })
                            var url = "http://localhost:8090/book-service/members/" + response.data.id + "/borrowings"
                            axios.get(url, {
                                headers: {
                                    Authorization: "Bearer " + localStorage.token
                                }
                            })
                                .then((response) => {
                                    if (response.data._embedded != undefined) {
                                        this.setState({ books: response.data._embedded.bookList })
                                    }

                                }, (error) => {
                                    console.log(error)
                                    alert(error)
                                });

                        }, (error) => {
                            console.log(error)
                            alert(error)
                        });
                }

            }, (error) => {
                this.setState({ validToken: false })
            });
    }

    commentModal(id) {
        this.setState({ commentModalIsOpen: true })

        var url = "http://localhost:8090/book-service/books/" + id + "/impressions"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + localStorage.token
            }
        })
            .then((response) => {
                this.setState({ komentari: response.data })

            }, (error) => {
                console.log(error)
                alert(error)
            });
    }

    prikaziKomentare() {
        return this.state.komentari.map((komentar, index) => {
            const { comment, rating, member } = komentar
            var user = member.firstName + " " + member.lastName
            return (
                <tr key={comment}>
                    <td>{comment}</td>
                    <td>{rating}</td>
                    <td>{user}</td>
                </tr >
            )
        })
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    commentAddModal(idBook) {
        this.setState({ commentAddModalIsOpen: true, idBook: idBook })
    }

    addComent(e) {

        var url = 'http://localhost:8090/book-service/books/' + this.state.idBook + "/impressions"
        axios.post(url,
            {
                comment: this.state.comment,
                rating: this.state.ocjena,
                member: {
                    id: this.state.idMember
                }
            },
            {
                headers: {
                    Authorization: "Bearer " + localStorage.token
                }
            })
            .then((response) => {
                alert("Komentar uspješno dodan")

            }, (error) => {
                console.log(error)
                alert(error)
            });

        e.preventDefault();
    }

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
                        <button className="btn warning " onClick={e => this.commentModal(id)} >Komentari</button>
                        <button className="btn success " onClick={e => this.commentAddModal(id)} >Dodaj komentar</button>
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

    headerComment() {
        let header = Object.keys(this.state.CommentHeader[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }

    logout() {
        localStorage.token = ""
    }

    render() {

        if (!this.state.validToken) {
            return (
                <div></div>
            )
        }

        return (
            <div>
                <MemberNavbar />
                <a href="/" onClick={this.logout} className="odjavaLink">Odjava</a>
                <div className="global">
                    <h3 className="naslov">Prikaz historije iznajmljivanja knjiga</h3>
                    <table>
                        <tbody>
                            <tr>{this.headerTabele()}</tr>
                            {this.prikazKnjigu()}
                        </tbody>
                    </table>
                    <Modal isOpen={this.state.commentModalIsOpen}>
                        <button className="btn btn-akcija danger" onClick={() => this.setState({ commentModalIsOpen: false })}>Zatvori</button>
                        <table>
                            <tbody>
                                <tr>{this.headerComment()}</tr>
                                {this.prikaziKomentare()}
                            </tbody>
                        </table>
                    </Modal>
                    <Modal isOpen={this.state.commentAddModalIsOpen}>
                        <h2>Dodavanje nove knjige</h2>
                        <form >
                            <input type="text" name="comment" placeholder="Komentar" onChange={this.handleChange} />
                            <input type="number" name="ocjena" placeholder="Ocjena" onChange={this.handleChange} />
                            <button className="btn success" onClick={this.addComent}>Dodaj</button>

                        </form>
                        <button className="btn btn-akcija danger" onClick={() => this.setState({ commentAddModalIsOpen: false })}>Zatvori</button>
                    </Modal>
                </div>
            </div>
        )
    }
}

export default Member