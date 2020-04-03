package com.example.Reservation.Book;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {
    @Override
    public EntityModel<Book> toModel(Book book) {
        /*if (!report.getReportType().hasLinks()){
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetById(report.getReportType().getId())).withSelfRel());
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetAll()).withRel("report_types"));
        }*/
        return new EntityModel<>(book, linkTo(methodOn(BookController.class).GetById(book.getId())).withSelfRel(), linkTo(methodOn(BookController.class).GetAll()).withRel("books"));
    }
}
