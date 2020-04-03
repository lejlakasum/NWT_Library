package com.example.demo.Book;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book){
        return new EntityModel<>(book,
                linkTo(methodOn(BookController.class).GetById(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).GetAll()).withRel("book"));
    }
}
