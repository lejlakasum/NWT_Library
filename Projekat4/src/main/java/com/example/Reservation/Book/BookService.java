package com.example.Reservation.Book;


import com.example.Reservation.ExceptionClass.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    BookModelAssembler assembler;



    public ResponseEntity<EntityModel<Book>> GetById(Integer id) {
        Book Book = bookRepository.findById(id).orElseThrow(()->new NotFoundException("book",id));
        return ResponseEntity.ok().body(assembler.toModel(Book));
    }

    public CollectionModel<EntityModel<Book>> GetAll() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(books, linkTo(methodOn(BookController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Book>> Add(Book newBook) {
        EntityModel<Book> entityModel = assembler.toModel(bookRepository.save(newBook));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> Update(final Book newBook, Integer id) {
        Book updatedReservation = bookRepository.findById(id)
                .map(book -> {book.setAvailable(newBook.getAvailable());
                              book.setBookName(newBook.getBookName());
                              book.setDatePublisher(newBook.getDatePublisher());
                              book.setIsbn(newBook.getIsbn());
                              book.setLibraryReadOnly(newBook.getLibraryReadOnly());
                              book.setPublisherName(newBook.getPublisherName());
                              book.setGenreName(newBook.getGenreName());
                                return bookRepository.save(book);
        }).orElseThrow(()->new NotFoundException("books",id));

        EntityModel<Book> entityModel = assembler.toModel(updatedReservation);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> Delete(Integer id) {
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
