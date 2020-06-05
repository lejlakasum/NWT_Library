import React from 'react'
import MyFile from './izvjestajTopRate.pdf'
import { Document, Page, pdfjs } from "react-pdf";
pdfjs.GlobalWorkerOptions.workerSrc = `https://cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


class PrikazPdf extends React.Component {

    state = { numPages: null, pageNumber: 1 };

    onDocumentLoadSuccess = ({ numPages }) => {
        this.setState({ numPages });
    };

    goToPrevPage = () =>
        this.setState(state => ({ pageNumber: state.pageNumber - 1 }));
    goToNextPage = () =>
        this.setState(state => ({ pageNumber: state.pageNumber + 1 }));

    render() {
        const { pageNumber, numPages } = this.state;

        return (
            <div>
                <nav>
                    <button onClick={this.goToPrevPage}>Prev</button>
                    <button onClick={this.goToNextPage}>Next</button>
                </nav>

                <div style={{ width: 600 }}>
                    <Document
                        file={MyFile}
                        onLoadSuccess={this.onDocumentLoadSuccess}
                    >
                        <Page pageNumber={pageNumber} width={600} />
                    </Document>
                </div>

                <p>
                    Page {pageNumber} of {numPages}
                </p>
            </div>
        );
    }
}

export default PrikazPdf