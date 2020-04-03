package com.example.Application.BookType;

import com.example.Application.Book.Book;
import com.example.Application.Genre.GenreController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookTypeModelAssembler implements RepresentationModelAssembler<BookType, EntityModel<BookType>> {

    @Override
    public EntityModel<BookType> toModel(BookType bookType) {

        return new EntityModel<>(bookType,
                linkTo(methodOn(BookTypeController.class).GetById(bookType.getId())).withSelfRel(),
                linkTo(methodOn(BookTypeController.class).GetAll()).withRel("bookTypes"));
    }
}
