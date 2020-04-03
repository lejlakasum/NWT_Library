package com.example.Reservation.Book;

import com.example.Reservation.ExceptionClass.BadRequestException;
import com.example.Reservation.ExceptionClass.InternalServerException;
import com.example.Reservation.ExceptionClass.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/bookReservation")
public class BookController {
        @Autowired
        BookService bookService;

        @GetMapping()
        public CollectionModel<EntityModel<Book>> GetAll() {
            try {
                return bookService.GetAll();
            }
            catch (Exception exeption) {
                throw new InternalServerException();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<EntityModel<Book>> GetById(@PathVariable Integer id){
            try{
                return bookService.GetById(id);
            }
            catch (NotFoundException exeption) {
                throw exeption;
            }
            catch (Exception exeption) {
                throw new InternalServerException();
            }
        }

        @PostMapping()
        ResponseEntity<EntityModel<Book>> Add(@RequestBody Book newBook) throws URISyntaxException {
            try {
                return bookService.Add(newBook);
            }
            catch (ConstraintViolationException exception) {
                throw new BadRequestException( exception.getMessage());
            }
            catch (NotFoundException exception) {
                throw exception;
            }
            catch (Exception exception){
                throw new InternalServerException();
            }
        }

        @PutMapping("/{id}")
        ResponseEntity<EntityModel<Book>> Update(@RequestBody Book newBook, @PathVariable Integer id) throws URISyntaxException {
            try {
                return bookService.Update(newBook, id);
            }
            catch (ConstraintViolationException exception) {
                throw new BadRequestException( exception.getMessage());
            }
            catch (NotFoundException exception) {
                throw exception;
            }
            catch (Exception exception){
                throw new InternalServerException();
            }
        }

        @DeleteMapping("/{id}")
        ResponseEntity<EntityModel<Book>> Delete(@PathVariable Integer id) {
            try {
                return bookService.Delete(id);
            }
            catch (EmptyResultDataAccessException exception) {
                throw new NotFoundException("book", id);
            }
            catch (Exception exception){
                throw new InternalServerException();
            }
        }
    }

