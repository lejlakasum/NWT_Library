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
            idBook: -1
        }
        this.handleChange = this.handleChange.bind(this)
        this.addComent = this.addComent.bind(this)
    }

    componentWillMount() {
        //dodati id iz local storage
        var url = "http://localhost:8090/book-service/members/1/borrowings"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
            }
        })
            .then((response) => {
                this.setState({ books: response.data._embedded.bookList })

            }, (error) => {
                console.log(error)
                alert(error)
            });
    }

    commentModal(id) {
        this.setState({ commentModalIsOpen: true })

        var url = "http://localhost:8090/book-service/books/" + id + "/impressions"
        axios.get(url, {
            headers: {
                Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
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

        //DODATI USERA IZ LOCAL STORAGE-a OVO JE HARDKODIRANO
        var url = 'http://localhost:8090/book-service/books/' + this.state.idBook + "/impressions"
        axios.post(url,
            {
                comment: this.state.comment,
                rating: this.state.ocjena,
                member: {
                    id: 1
                }
            },
            {
                headers: {
                    Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWpsYWEiLCJleHAiOjE1OTA2MjA4MDAsImlhdCI6MTU5MDU5MjAwMH0.MplqOJowkXHcRUqkmRr6zoGxJEwHifzGmBP0ffDTVFk"
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