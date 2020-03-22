package com.example.Application.Copy;

import com.example.Application.Author.Author;
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
import java.util.List;

@RestController
@RequestMapping("/copies")
public class CopyController {
    @Autowired
    CopyService copyService;

    @GetMapping()
    public CollectionModel<EntityModel<Copy>> GetAll() {
        try {
            return copyService.GetAll();
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Copy>> GetById(@PathVariable Integer id) {
        try {
            return copyService.GetById(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}/authors")
    public CollectionModel<EntityModel<Author>> GetAuthorsByCopy(@PathVariable Integer id) {
        try {
            return copyService.GetAuthors(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PostMapping("/{idcopy}/authors/{idauthor}")
    public CollectionModel<EntityModel<Author>> AddAuthorToCopy(@PathVariable Integer idcopy, @PathVariable Integer idauthor) {
        try {
            copyService.AddAuthorToCopy(idcopy, idauthor);
            return copyService.GetAuthors(idcopy);
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

    @PostMapping()
    ResponseEntity<EntityModel<Copy>> Add(@RequestBody Copy newCopy) throws URISyntaxException {

        try {
            return copyService.Add(newCopy);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Copy>> Update(@RequestBody Copy newCopy, @PathVariable Integer id) throws URISyntaxException {
        try {
            return copyService.Update(newCopy, id);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{idcopy}/authors/{idauthor}")
    public CollectionModel<EntityModel<Author>> DeleteAuthorToCopy(@PathVariable Integer idcopy, @PathVariable Integer idauthor) {
        try {
            copyService.DeleteAuthorFromCopy(idcopy, idauthor);
            return copyService.GetAuthors(idcopy);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Copy>> Delete(@PathVariable Integer id) {
        try {
            return copyService.Delete(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("copy", id);
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }
}
