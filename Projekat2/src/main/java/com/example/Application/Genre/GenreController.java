package com.example.Application.Genre;


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
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping()
    public CollectionModel<EntityModel<Genre>> GetAll() {
        try {
            return genreService.GetAll();
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Genre>> GetById(@PathVariable Integer id) {
        try {
            return genreService.GetById(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Genre>> AddGenre(@RequestBody Genre newGenre) throws URISyntaxException {

        try {
            return genreService.AddGenre(newGenre);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Genre>> ModifyGenre(@RequestBody Genre newGenre, @PathVariable Integer id) throws URISyntaxException {
        try {
            return genreService.ModifyGenre(newGenre, id);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Genre>> DeleteGenre(@PathVariable Integer id) {
        try {
            return genreService.DeleteGenre(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("genre", id);
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }
}
