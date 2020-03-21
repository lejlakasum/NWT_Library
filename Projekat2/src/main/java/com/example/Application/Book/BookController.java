package com.example.Application.Book;

import com.example.Application.ExceptionClasses.BadRequestException;
import com.example.Application.ExceptionClasses.InternalServerException;
import com.example.Application.ExceptionClasses.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping()
    CollectionModel<EntityModel<Book>> GetAll() {
        try {
            return bookService.GetAll();
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Book>> GetById(@PathVariable Integer id) {
        try {
            return bookService.GetById(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Book>> Add(@RequestBody Book newBook) throws URISyntaxException {

        try {
            return bookService.Add(newBook);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (NotFoundException ex) {

            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Book>> Update(@RequestBody Book newBook, @PathVariable Integer id) throws URISyntaxException {
        try {
            return bookService.Update(newBook, id);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Book>> Delete(@PathVariable Integer id) {
        try {
            return bookService.Delete(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("book", id);
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }
}
