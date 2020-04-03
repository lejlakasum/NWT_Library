package com.example.demo.Book;

import com.example.demo.Exception.NotFoundException;
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

    public CollectionModel<EntityModel<Book>> GetAll() {
        List<EntityModel<Book>> books=bookRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(books,
                linkTo(methodOn(BookController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Book>> GetById(Integer id){
        Book book=bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("book", id));
        return ResponseEntity
                .ok()
                .body(assembler.toModel(book));
    }

    public ResponseEntity<EntityModel<Book>> AddBook(Book newBook) {
        EntityModel<Book> entityModel = assembler.toModel(bookRepository.save(newBook));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> ModifyBook(Book newBook, Integer id){
        Book updatedBook=bookRepository.findById(id)
                .map(book -> {
                    book.setIsbn(newBook.getIsbn());
                    return bookRepository.save(book);
                })
                .orElseGet(()-> {
                    newBook.setId(id);
                    return bookRepository.save(newBook);
                });
        EntityModel<Book> entityModel=assembler.toModel(updatedBook);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> DeleteBook(Integer id) {
        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
