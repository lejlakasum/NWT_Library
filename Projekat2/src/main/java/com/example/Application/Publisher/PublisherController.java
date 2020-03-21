package com.example.Application.Publisher;

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
@RequestMapping("/publishers")
public class PublisherController {


    @Autowired
    PublisherService publisherService;

    @GetMapping()
    public CollectionModel<EntityModel<Publisher>> GetAll() {
        try {
            return publisherService.GetAll();
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Publisher>> GetById(@PathVariable Integer id) {
        try {
            return publisherService.GetById(id);
        }
        catch (NotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Publisher>> Add(@RequestBody Publisher newPublisher) throws URISyntaxException {

        try {
            return publisherService.Add(newPublisher);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Publisher>> Update(@RequestBody Publisher newPublisher, @PathVariable Integer id) throws URISyntaxException {
        try {
            return publisherService.Update(newPublisher, id);
        }
        catch (ConstraintViolationException ex) {

            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Publisher>> Delete(@PathVariable Integer id) {
        try {
            return publisherService.Delete(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("publisher", id);
        }
        catch (Exception ex) {
            throw new InternalServerException();
        }
    }
}
