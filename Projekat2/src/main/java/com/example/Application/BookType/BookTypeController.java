package com.example.Application.BookType;

import com.example.Application.Country.Country;
import com.example.Application.Country.CountryService;
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

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/booktypes")
public class BookTypeController {
    @Autowired
    BookTypeService bookTypeService;

    @GetMapping()
    public CollectionModel<EntityModel<BookType>> GetAll() {
        try {
            return bookTypeService.GetAll();
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookType>> GetById(@PathVariable Integer id) {
        try {
            return bookTypeService.GetById(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<BookType>> Add(@RequestBody BookType newBookType) throws URISyntaxException {

        try {
            return bookTypeService.Add(newBookType);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<BookType>> Update(@RequestBody BookType newBookType, @PathVariable Integer id) throws URISyntaxException {
        try {
            return bookTypeService.Update(newBookType, id);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<BookType>> Delete(@PathVariable Integer id) {
        try {
            return bookTypeService.Delete(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("bookType", id);
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }
}
