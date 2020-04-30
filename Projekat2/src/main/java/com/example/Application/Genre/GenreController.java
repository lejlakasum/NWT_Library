package com.example.Application.Genre;


import com.example.Application.ExceptionClasses.BadRequestException;
import com.example.Application.ExceptionClasses.InternalServerException;
import com.example.Application.ExceptionClasses.NotFoundException;
import com.example.grpc.SystemEvents;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping()
    public CollectionModel<EntityModel<Genre>> GetAll() {
        try {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "GET", "Genre", true, 200);
            return genreService.GetAll();
        }
        catch (Exception ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "GET", "Genre", false, 500);
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Genre>> GetById(@PathVariable Integer id) {
        try {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "GET", "Genre", true, 200);
            return genreService.GetById(id);
        }
        catch (NotFoundException ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "GET", "Genre", false, 404);
            throw ex;
        }
        catch (Exception ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "GET", "Genre", false, 500);
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Genre>> AddGenre(@RequestBody Genre newGenre) throws URISyntaxException {

        try {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "POST", "Genre", true, 201);
            return genreService.AddGenre(newGenre);
        }
        catch (ConstraintViolationException ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "POST", "Genre", false, 400);
            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "POST", "Genre", false, 500);
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Genre>> ModifyGenre(@RequestBody Genre newGenre, @PathVariable Integer id) throws URISyntaxException {
        try {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "PUT", "Genre", true, 200);
            return genreService.ModifyGenre(newGenre, id);
        }
        catch (ConstraintViolationException ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "PUT", "Genre", false, 400);
            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "PUT", "Genre", false, 500);
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Genre>> DeleteGenre(@PathVariable Integer id) {
        try {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "DELETE", "Genre", true, 200);
            return genreService.DeleteGenre(id);
        }
        catch (EmptyResultDataAccessException ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "DELETE", "Genre", false, 404);
            throw new NotFoundException("genre", id);
        }
        catch (Exception ex) {
            LogActivity(new Date().toString(), "BookMicroservice", "Default", "DELETE", "Genre", false, 500);
            throw new InternalServerException();
        }
    }

    private void LogActivity(String eventTimeStamp, String microservice, String user,
                             String action, String resourceName, Boolean success, Integer responseCode) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                    .usePlaintext()
                    .build();

            com.example.grpc.SystemEventServiceGrpc.SystemEventServiceBlockingStub stub =
                    com.example.grpc.SystemEventServiceGrpc.newBlockingStub(channel);

            SystemEvents.SystemEventResponse response = stub.logSystemEvent(SystemEvents.SystemEventRequest.newBuilder()
                    .setEventTimeStamp(eventTimeStamp)
                    .setMicroservice(microservice)
                    .setUser(user)
                    .setAction(action)
                    .setResourceName(resourceName)
                    .setSuccess(success)
                    .setResponseCode(responseCode)
                    .build());

            channel.shutdown();
        }
        catch (Exception ex) {

        }
    }
}
