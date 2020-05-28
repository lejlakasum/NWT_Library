import React from 'react';
import "./Member.css"
import axios from 'axios'

class Member extends React.Component {


    constructor(props) {
        super(props)
        this.state = {
            BookHeader: [
                { Id: "", ISBN: "", Naziv: "", Tip: "", Zanr: "", Izdavac: "" }
            ],
            books: []
        }
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

    render() {
        return (
            <div className="global">
                <h3 className="naslov">Prikaz historije iznajmljivanja knjiga</h3>
                <table>
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazKnjigu()}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default Member