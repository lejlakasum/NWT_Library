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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
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
        //System.out.println(userDirectory);

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
        ResponseEntity<CollectionModel<Book>> book= restTemplate.exchange("http://book-service/books/", HttpMethod.GET, null, new ParameterizedTypeReference<CollectionModel<Book>>() {});

        /*System.out.println(book.getBody().getContent().size());
        BookNEWList books= null;
        for (Book b:book.getBody().getContent()) {
            System.out.println(b.getCopy().getBookName());
            ResponseEntity<List<Impression>> impressions= restTemplate.exchange("http://book-service/books/"+b.getId()+"/impressions", HttpMethod.GET, null, new ParameterizedTypeReference<List<Impression>>() {});
            //System.out.println(impressions.getBody().size());
            double rate=0.;
            List<Member> lista=null;
            if (impressions.getBody()!=null) {
                for (int i = 0; i < impressions.getBody().size(); i++) {
                    System.out.println("Uslo je u petlju");
                    rate += impressions.getBody().get(i).getRating();
                    //lista.add(impressions.getBody().get(i).getMember());
                    //System.out.println(impressions.getBody().get(i).getRating());
                }
                rate = rate / impressions.getBody().size();
            }
            BookNEW k=new BookNEW(b.getId(),b.getIsbn(),b.getCopy().getBookName(),b.getBookType().getLibraryReadOnly(),
                    b.getGenre().getName(),b.getPublisher().getName(),b.getPublishedDate(),b.getAvailable(),
                    rate,lista);
            //books.Add(k);
        }*/
        System.out.println(book.getBody().getContent().size());
        Document document= new Document();
        Font font= FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        //reportType=1 izvjestaj sa top 5 knjiga po ratingu
        if (reportTypeId==1) {
            userDirectory = userDirectory+"/izvjestajTopRate.pdf";
            /*
            Comparator<BookNEW> compareByRate = (BookNEW o1, BookNEW o2) -> o1.getRating().compareTo(o2.getRating());
            Collections.sort(books,compareByRate);
            System.out.println(books);
            */
            PdfWriter.getInstance(document,new FileOutputStream("izvjestajTopRate.pdf"));
            document.open();
            document.add(new Paragraph("Izvjestaj sa knjigama u biblioteci, na osnovu rate-a"));
            document.add(Chunk.NEWLINE);
            for (Book b:book.getBody().getContent()) {
                //System.out.println(b.getCopy().getBookName());
                ResponseEntity<List<Impression>> impressions = restTemplate.exchange("http://book-service/books/" + b.getId() + "/impressions", HttpMethod.GET, null, new ParameterizedTypeReference<List<Impression>>() {
                });
                double rate = 0.;
                List<Member> lista = null;
                if (impressions.getBody() != null) {
                    for (int i = 0; i < impressions.getBody().size(); i++) {
                        //System.out.println("Uslo je u petlju");
                        rate += impressions.getBody().get(i).getRating();
                        //lista.add(impressions.getBody().get(i).getMember());
                    }
                    rate = rate / impressions.getBody().size();
                }
                document.add(new Paragraph("Prosjecni rate ove knjige je: "+rate));
                document.add(new Paragraph("Tip knjige: " + b.getBookType().getName()));
                document.add(new Paragraph("ISBN : " + b.getIsbn()));
                document.add(new Paragraph("Naslov knjige: " + b.getCopy().getBookName()));
                document.add(new Paragraph("Žanr knjige: " + b.getGenre().getName()));
                document.add(new Paragraph("Datum izdanja: " + b.getPublishedDate()));
                document.add(new Paragraph("Izdavac knjige: " + b.getPublisher().getName()));
                document.add(new Paragraph("Dostupnost knjige: " + b.getAvailable()));
                document.add(new Paragraph("Dozvoljeno citanje samo u biblioteci: " + b.getBookType().getLibraryReadOnly()));
                document.add(Chunk.NEWLINE);
            }
            //Chunk chunk = new Chunk(sadrzaj, font);
            //document.add(chunk);
            document.close();
            //System.out.println(userDirectory);
        }
        //reportType=2 knjige koje su library read only
        else if (reportTypeId==2) {
            userDirectory = userDirectory+"/izvjestajCitajSamoUBiblioteci.pdf";
            PdfWriter.getInstance(document,new FileOutputStream("izvjestajCitajSamoUBiblioteci.pdf"));
            document.open();
            document.add(new Paragraph("Izvjestaj sa knjigama koje je dozvoljeno citati samo u biblioteci"));
            document.add(Chunk.NEWLINE);

            for (Book b:book.getBody().getContent()) {
                //System.out.println(b.getCopy().getBookName());
                if (b.getBookType().getLibraryReadOnly() == true) {
                    document.add(new Paragraph("Tip knjige: " + b.getBookType().getName()));
                    document.add(new Paragraph("ISBN : " + b.getIsbn()));
                    document.add(new Paragraph("Naslov knjige: " + b.getCopy().getBookName()));
                    document.add(new Paragraph("Žanr knjige: " + b.getGenre().getName()));
                    document.add(new Paragraph("Datum izdanja: " + b.getPublishedDate()));
                    document.add(new Paragraph("Izdavac knjige: " + b.getPublisher().getName()));
                    document.add(new Paragraph("Dostupnost knjige: " + b.getAvailable()));
                    document.add(new Paragraph("Dozvoljeno citanje samo u biblioteci: " + b.getBookType().getLibraryReadOnly()));
                    document.add(Chunk.NEWLINE);
                }
            }
            //Chunk chunk = new Chunk(sadrzaj, font);
            //document.add(chunk);
            document.close();
            //System.out.println(userDirectory);
        }
        //reportType=3 trenutno dostupne knjige
        else if (reportTypeId==3) {
            userDirectory = userDirectory+"/izvjestajDostupneKnjige.pdf";
            PdfWriter.getInstance(document,new FileOutputStream("izvjestajDostupneKnjige.pdf"));
            document.open();
            document.add(new Paragraph("Izvjestaj sa knjigama iz biblioteke koje su trenutno dostupne na citanje"));
            document.add(Chunk.NEWLINE);

            for (Book b:book.getBody().getContent()) {
                System.out.println(b.getCopy().getBookName());
                if (b.getAvailable() == true) {
                    document.add(new Paragraph("Tip knjige: " + b.getBookType().getName()));
                    document.add(new Paragraph("ISBN : " + b.getIsbn()));
                    document.add(new Paragraph("Naslov knjige: " + b.getCopy().getBookName()));
                    document.add(new Paragraph("Žanr knjige: " + b.getGenre().getName()));
                    document.add(new Paragraph("Datum izdanja: " + b.getPublishedDate()));
                    document.add(new Paragraph("Izdavac knjige: " + b.getPublisher().getName()));
                    document.add(new Paragraph("Dostupnost knjige: " + b.getAvailable()));
                    document.add(new Paragraph("Dozvoljeno citanje samo u biblioteci: " + b.getBookType().getLibraryReadOnly()));
                    document.add(Chunk.NEWLINE);
                }
                //Chunk chunk = new Chunk(sadrzaj, font);
                //document.add(chunk);
            }
            document.close();
            System.out.println(userDirectory);
        }
        //reportType=4 trenutno izdate knjige
        else if (reportTypeId==4) {
            userDirectory = userDirectory+"/izvjestajIzdatihKnjiga.pdf";
            PdfWriter.getInstance(document,new FileOutputStream("izvjestajIzdatihKnjiga.pdf"));
            document.open();
            document.add(new Paragraph("Izvjestaj sa knjigama iz biblioteke koje su trenutno izdate"));
            document.add(Chunk.NEWLINE);

            for (Book b:book.getBody().getContent()) {
                //System.out.println(b.getCopy().getBookName());
                if (b.getAvailable() == false) {
                    document.add(new Paragraph("Tip knjige: " + b.getBookType().getName()));
                    document.add(new Paragraph("ISBN : " + b.getIsbn()));
                    document.add(new Paragraph("Naslov knjige: " + b.getCopy().getBookName()));
                    document.add(new Paragraph("Žanr knjige: " + b.getGenre().getName()));
                    document.add(new Paragraph("Datum izdanja: " + b.getPublishedDate()));
                    document.add(new Paragraph("Izdavac knjige: " + b.getPublisher().getName()));
                    document.add(new Paragraph("Dostupnost knjige: " + b.getAvailable()));
                    document.add(new Paragraph("Dozvoljeno citanje samo u biblioteci: " + b.getBookType().getLibraryReadOnly()));
                    document.add(Chunk.NEWLINE);
                }
                //Chunk chunk = new Chunk(sadrzaj, font);
                //document.add(chunk);
            }
            document.close();
            //System.out.println(userDirectory);
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
