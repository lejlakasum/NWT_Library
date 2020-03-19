package com.example.Application.Genre;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping()
    CollectionModel<EntityModel<Genre>> GetAll() {

        return genreService.GetAll();
    }

    @GetMapping("/{id}")
    EntityModel<Genre> GetById(@PathVariable Integer id) {

        return genreService.GetById(id);
    }

    @PostMapping()
    ResponseEntity<EntityModel<Genre>> AddGenre(@RequestBody Genre newGenre) throws URISyntaxException {

        return genreService.AddGenre(newGenre);
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Genre>> ModifyGenre(@RequestBody Genre newGenre, @PathVariable Integer id) throws URISyntaxException {

        return genreService.ModifyGenre(newGenre, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Genre>> DeleteGenre(@PathVariable Integer id) {

        return genreService.DeleteGenre(id);
    }
}
