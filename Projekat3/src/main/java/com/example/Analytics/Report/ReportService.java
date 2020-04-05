package com.example.Analytics.Report;

import com.example.Analytics.Employee.Employee;
import com.example.Analytics.Employee.EmployeeRepository;
import com.example.Analytics.ReportType.ReportType;
import com.example.Analytics.ReportType.ReportTypeRepository;
import com.example.Analytics.ExceptionClass.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ReportService {

    private Integer port=8082;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ReportModelAssembler assembler;
    @Autowired
    ReportTypeRepository reportTypeRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public ResponseEntity<EntityModel<Report>> GetById(Integer id) {
        Report report = reportRepository.findById(id).orElseThrow(()->new NotFoundException("report",id));
        return ResponseEntity.ok().body(assembler.toModel(report));
    }

    public CollectionModel<EntityModel<Report>> GetAll() {
        List<EntityModel<Report>> reports = reportRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(reports, linkTo(methodOn(ReportController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Report>> Add(Report newReport) throws JsonProcessingException, FileNotFoundException, DocumentException {
        Integer reportTypeId = newReport.getReportType().getId();
        ReportType reportType = reportTypeRepository.findById(reportTypeId).orElseThrow(()->new NotFoundException("report_type", reportTypeId));
        newReport.setReportType(reportType);

        Employee employee = employeeRepository.findById(1).orElseThrow(()->new NotFoundException("employee_id",1));
        newReport.setEmployee(employee);
        newReport.setCreationDate(new Date());

        String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        System.out.println(userDirectory);

        //dohvatanje svih podatka iz baze za sve knjige
        /*
        SLJEDECE LINIJE KODA BO TREBALO DA RADE, ALI DOHVATI MI PRAZNU LISTU, PITATI IRFANA
        BookList response = restTemplate.getForObject("http://book-service/books",BookList.class);
        //List<Book> knjigeee = response.getBooks();
        List<BookNEW> books = null;
        response.getBooks().stream().map( book -> {
            Impression im=restTemplate.getForObject("http://book-service/books/"+book.getId()+"/impressions",Impression.class);
            BookNEW k=new BookNEW(book.getId(),book.getIsbn(),book.getBookType().getName(),book.getBookType().getLibraryReadOnly(),
                    book.getGenre().getName(),book.getPublisher().getName(),book.getPublishedDate(),book.getAvailable(),
                    im.getRating(),im.getMember());
            books.add(k);
            return books;
        }).collect(Collectors.toList());
        */
        ResponseEntity<Book> book=restTemplate.getForEntity("http://book-service/books/1",Book.class);
        System.out.println(book.getBody().getIsbn());
        ResponseEntity<Impression> im=restTemplate.getForEntity("http://book-service/books/"+book.getBody().getId()+"/impressions",Impression.class);
        //ResponseEntity<BookType> book_t=restTemplate.getForEntity("http://book-service/booktypes/"+book.getBody().getBookType().getId(),BookType.class);
        //ResponseEntity<Genre> genre=restTemplate.getForEntity("http://book-service/genres/"+book.getBody().getGenre().getId(),Genre.class);
        //ResponseEntity<Publisher> publisher=restTemplate.getForEntity("http://book-service/publishers/"+book.getBody().getPublisher().getId(),Publisher.class);

        System.out.println("Vraija knjizurina dosla ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        /*BookNEW k=new BookNEW(book.getBody().getId(),book.getBody().getIsbn(),book_t.getBody().getName(),book_t.getBody().getLibraryReadOnly(),
                genre.getBody().getName(),publisher.getBody().getName(),book.getBody().getPublishedDate(),book.getBody().getAvailable(),
                im.getBody().getRating(),im.getBody().getMember());
        *///System.out.println("Vraija knjizurina dosla ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //System.out.println(sve_knjige.getBody().getIsbn());

        Document document= new Document();
        Font font= FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
       //reportType=1 izvjestaj sa top 5 knjiga po ratingu
        if (reportTypeId==1) {
            userDirectory = userDirectory+"/izvjestajTop5.pdf";
            /*
            Comparator<BookNEW> compareByRate = (BookNEW o1, BookNEW o2) -> o1.getRating().compareTo(o2.getRating());
            Collections.sort(books,compareByRate);
            System.out.println(books);
            */
            PdfWriter.getInstance(document,new FileOutputStream("izvjestajTop5.pdf"));
            document.open();
       /*     String sadrzaj="Izvjestaj sa top 5 knjiga u biblioteci, na osnovu rate-a\n"+"Naziv knjige: "+k.getBookTypeName()+"\n"
                    +"ISBN: "+k.getIsbn()+"\n"+"Zanr: "+k.getGenreName()+"\n"+"Dozvoljena je za citanje samo u biblioteci: "+k.getLibraryReadOnly()+"\n"+"Naziv izdavaca: "
                    +k.getPublisherName()+"\n"+"Datum izdanja knjige: "+k.getPublishedDate()+"\n"+"Knjiga je dostupna: "+k.getAvailable();
            Chunk chunk = new Chunk(sadrzaj, font);
            document.add(chunk);
        */    document.close();
            System.out.println(userDirectory);
        }
        //reportType=2 knjige koje su library read only
        else if (reportTypeId==2) {

        }
        //reportType=3 trenutno dostupne knjige
        else if (reportTypeId==3) {

        }
        //reportType=4 lista membera i knjiga koje su oni posudjivali
        else if (reportTypeId==4) {

        }
        else throw new NotFoundException("reportType",reportTypeId);

        newReport.setPath(userDirectory);

        EntityModel<Report> entityModel = assembler.toModel(reportRepository.save(newReport));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Report>> Update(Report newReport, Integer id) {
        Integer reportTypeId = newReport.getReportType().getId();
        ReportType reportType = reportTypeRepository.findById(reportTypeId).orElseThrow(()->new NotFoundException("report_type", reportTypeId));

        Integer employeeId = newReport.getEmployee().getId();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new NotFoundException("employee_id",employeeId));

        Report updatedReport = reportRepository.findById(id)
                .map(report -> {report.setReportType(reportType);
                                report.setCreationDate(newReport.getCreationDate());
                                report.setEmployee(employee);
                                report.setPath(newReport.getPath());
                                return reportRepository.save(report);
        }).orElseThrow(()->new NotFoundException("reports",id));

        EntityModel<Report> entityModel = assembler.toModel(updatedReport);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Report>> Delete(Integer id) {
        reportRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
