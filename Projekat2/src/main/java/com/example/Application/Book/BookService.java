package com.example.Application.Book;

import com.example.Application.BookType.BookType;
import com.example.Application.BookType.BookTypeRepository;
import com.example.Application.Copy.Copy;
import com.example.Application.Copy.CopyRepository;
import com.example.Application.ExceptionClasses.NotFoundException;
import com.example.Application.Genre.Genre;
import com.example.Application.Genre.GenreRepository;
import com.example.Application.Publisher.Publisher;
import com.example.Application.Publisher.PublisherRepository;
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
    @Autowired
    CopyRepository copyRepository;
    @Autowired
    BookTypeRepository bookTypeRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    PublisherRepository  publisherRepository;

    public CollectionModel<EntityModel<Book>> GetAll() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(books,
                linkTo(methodOn(BookController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Book>> GetById(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("book", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(book));
    }

    public ResponseEntity<EntityModel<Book>> Add(Book newBook) {

        Integer copyId = newBook.getCopy().getId();
        Integer bookTypeId = newBook.getBookType().getId();
        Integer genreId = newBook.getGenre().getId();
        Integer publisherId = newBook.getPublisher().getId();

        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NotFoundException("copy", copyId));

        BookType bookType = bookTypeRepository.findById(bookTypeId)
                .orElseThrow(() -> new NotFoundException("bookType", bookTypeId));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("genre", genreId));

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new NotFoundException("publisher", publisherId));

        newBook.setCopy(copy);
        newBook.setBookType(bookType);
        newBook.setGenre(genre);
        newBook.setPublisher(publisher);

        EntityModel<Book> entityModel = assembler.toModel(bookRepository.save(newBook));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> Update(Book newBook, Integer id) {
        Integer copyId = newBook.getCopy().getId();
        Integer bookTypeId = newBook.getBookType().getId();
        Integer genreId = newBook.getGenre().getId();
        Integer publisherId = newBook.getPublisher().getId();

        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NotFoundException("copy", copyId));

        BookType bookType = bookTypeRepository.findById(bookTypeId)
                .orElseThrow(() -> new NotFoundException("bookType", bookTypeId));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("genre", genreId));

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new NotFoundException("publisher", publisherId));

        Book updatedBook = bookRepository.findById(id)
                .map(book -> {
                    book.setIsbn(newBook.getIsbn());
                    book.setCopy(copy);
                    book.setBookType(bookType);
                    book.setGenre(genre);
                    book.setPublisher(publisher);
                    book.setPublishedDate(newBook.getPublishedDate());
                    book.setAvailable(newBook.getAvailable());
                    return bookRepository.save(book);
                })
                .orElseThrow(()->new NotFoundException("book", id));

        EntityModel<Book> entityModel = assembler.toModel(updatedBook);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> Delete(Integer id) {

        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
