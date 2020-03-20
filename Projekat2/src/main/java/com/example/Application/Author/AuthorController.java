package com.example.Application.Author;

import com.example.Application.Country.Country;
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
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping()
    CollectionModel<EntityModel<Author>> GetAll() {
        try {
            return authorService.GetAll();
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Author>> GetById(@PathVariable Integer id) {
        try {
            return authorService.GetById(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Author>> Add(@RequestBody Author newAuthor) throws URISyntaxException {

        try {
            return authorService.Add(newAuthor);
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
    ResponseEntity<EntityModel<Author>> Update(@RequestBody Author newAuthor, @PathVariable Integer id) throws URISyntaxException {
        try {
            return authorService.Update(newAuthor, id);
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
    ResponseEntity<EntityModel<Author>> Delete(@PathVariable Integer id) {
        try {
            return authorService.Delete(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("author", id);
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }
}
