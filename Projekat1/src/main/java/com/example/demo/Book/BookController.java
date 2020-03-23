package com.example.demo.Book;

import com.example.demo.Exception.BadRequestException;
import com.example.demo.Exception.InternalServerException;
import com.example.demo.Exception.NotFoundException;
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
    public CollectionModel<EntityModel<Book>> GetAll(){
        try {
            return bookService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> GetById(@PathVariable Integer id) {
        try {
            return bookService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Book>> AddBook(@RequestBody Book newBook) throws URISyntaxException {
        try {
            return bookService.AddBook(newBook);
        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Book>> ModifyBook(@RequestBody Book newBook, @PathVariable Integer id) throws URISyntaxException {
        try {
            return bookService.ModifyBook(newBook,id);

        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Book>> DeleteBook(@PathVariable Integer id){
        try {
            return bookService.DeleteBook(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("book",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
