package com.example.Application.Book;

import com.example.Application.BookType.BookTypeController;
import com.example.Application.Copy.CopyController;
import com.example.Application.Genre.GenreController;
import com.example.Application.Publisher.PublisherController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {

        if(!book.getCopy().hasLinks()) {
            book.getCopy()
                    .add(linkTo(methodOn(CopyController.class)
                            .GetById(book.getCopy().getId()))
                            .withSelfRel());

            book.getCopy()
                    .add(linkTo(methodOn(CopyController.class)
                            .GetAll())
                            .withRel("copies"));
        }

        if(!book.getBookType().hasLinks()) {
            book.getBookType()
                    .add(linkTo(methodOn(BookTypeController.class)
                            .GetById(book.getBookType().getId()))
                            .withSelfRel());

            book.getBookType()
                    .add(linkTo(methodOn(BookTypeController.class)
                            .GetAll())
                            .withRel("booktypes"));
        }

        if(!book.getGenre().hasLinks()) {
            book.getGenre()
                    .add(linkTo(methodOn(GenreController.class)
                            .GetById(book.getGenre().getId()))
                            .withSelfRel());

            book.getGenre()
                    .add(linkTo(methodOn(GenreController.class)
                            .GetAll())
                            .withRel("genres"));
        }

        if(!book.getPublisher().hasLinks()) {
            book.getPublisher()
                    .add(linkTo(methodOn(PublisherController.class)
                            .GetById(book.getPublisher().getId()))
                            .withSelfRel());

            book.getPublisher()
                    .add(linkTo(methodOn(PublisherController.class)
                            .GetAll())
                            .withRel("publishers"));
        }

        return new EntityModel<>(book,
                linkTo(methodOn(BookController.class).GetById(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).GetAll()).withRel("books"));
    }
}
