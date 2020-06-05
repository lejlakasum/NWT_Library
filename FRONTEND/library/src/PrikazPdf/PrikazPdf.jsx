import React from 'react'
import MyFile1 from './izvjestajCitajSamoUBiblioteci.pdf'
import MyFile2 from './izvjestajDostupneKnjige.pdf'
import MyFile3 from './izvjestajTopRate.pdf'
import { Document, Page, pdfjs } from "react-pdf";
import Modal from 'react-modal';
pdfjs.GlobalWorkerOptions.workerSrc = `https://cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;
class PrikazPdf extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            Izvjestaji: [
                { Naziv_izvještaja: "", Akcije: "" }
            ],
            izvjestaji: ["Knjige za čitanje u biblioteci", "Izvještaj o dostupnim knjigama", "Top rate"],
            modalIsOpen: false,
            prikaz: '',
            odabraniIzvjestaj: '',
            numPages: null,
            pageNumber: 1
        };
    }
    prikazIzvjestaja() {
        return this.state.izvjestaji.map((izvjestaj) => {
            return (
                <tr key={izvjestaj}>
                    <td name="odabraniIzvjestaj">
                        {izvjestaj}
                    </td>
                    <td>
                        <button className="btn success add" onClick={() => { this.setState({ modalIsOpen: true }, this.handleChange(izvjestaj)) }}>Pregled
                        </button>
                    </td>
                </tr>
            )
        })
    }
    headerTabele() {
        let header = Object.keys(this.state.Izvjestaji[0])
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        })
    }
    onDocumentLoadSuccess = ({ numPages }) => {
        this.setState({ numPages });
    };
    goToPrevPage = () =>
        this.setState({ pageNumber: this.state.pageNumber - 1 });
    goToNextPage = () =>
        this.setState({ pageNumber: this.state.pageNumber + 1 });
    postaviNaNula = () => this.setState({ pageNumber: 1 });
    handleChange = (odabraniIzvjestaj) => {
        if (odabraniIzvjestaj === "Knjige za čitanje u biblioteci") this.state.prikaz = MyFile1
        else if (odabraniIzvjestaj === "Izvještaj o dostupnim knjigama") this.state.prikaz = MyFile2
        else this.state.prikaz = MyFile3
        {this.postaviNaNula()}
    }
    render() {
        return (
            <div className="global">
                <h2 id='title'>Izvještaji</h2>
                <table >
                    <tbody>
                        <tr>{this.headerTabele()}</tr>
                        {this.prikazIzvjestaja()}
                    </tbody>
                </table>
                <Modal isOpen={this.state.modalIsOpen} >
                    <div>
                        <nav>
                            <button onClick={this.goToPrevPage}>Prev</button>
                            <button onClick={this.goToNextPage}>Next</button>
                        </nav>
                        <div style={{ width: 600 }}>
                            <Document
                                file={this.state.prikaz}
                                onLoadSuccess={this.onDocumentLoadSuccess}
                            >
                                <Page pageNumber={this.state.pageNumber} width={600} />
                            </Document>
                        </div>
                        <p>
                            Page {this.state.pageNumber} of {this.state.numPages}
                        </p>
                        <button className="btn danger close" onClick={() => this.setState({ modalIsOpen: false })}>Zatvori</button>
                    </div>
                </Modal>
            </div >
        )
    }
}
export default PrikazPdf
