package com.example.Application.Genre;

import com.example.Application.ExceptionClasses.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    GenreModelAssembler assembler;

    public CollectionModel<EntityModel<Genre>> GetAll() {
        List<EntityModel<Genre>> genres = genreRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(genres,
                linkTo(methodOn(GenreController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Genre>> GetById(Integer id) {

        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("genre", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(genre));
    }

    public ResponseEntity<EntityModel<Genre>> AddGenre(Genre newGenre) {
        EntityModel<Genre> entityModel = assembler.toModel(genreRepository.save(newGenre));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Genre>> ModifyGenre(Genre newGenre, Integer id) {
        Genre updatedGenre = genreRepository.findById(id)
                .map(genre -> {
                    genre.setName(newGenre.getName());
                    return genreRepository.save(genre);
                })
                .orElseGet(() -> {
                    newGenre.setId(id);
                    return genreRepository.save(newGenre);
                });

        EntityModel<Genre> entityModel = assembler.toModel(updatedGenre);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Genre>> DeleteGenre(Integer id) {

        genreRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}