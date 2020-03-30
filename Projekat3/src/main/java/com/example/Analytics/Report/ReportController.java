package com.example.Analytics.Report;

import com.example.Analytics.ExceptionClass.NotFoundException;
import com.example.Analytics.ExceptionClass.BadRequestException;
import com.example.Analytics.ExceptionClass.InternalServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/report")
public class ReportController {


    @Autowired
    ReportService reportService;

    @GetMapping()
    public CollectionModel<EntityModel<Report>> GetAll() {
        try {
            return reportService.GetAll();
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Report>> GetById(@PathVariable Integer id){
        try{
            return reportService.GetById(id);
        }
        catch (NotFoundException exeption) {
            throw exeption;
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Report>> Add(@RequestBody Report newReport) throws URISyntaxException {
        try {
            return reportService.Add(newReport);
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Report>> Update(@RequestBody Report newReport, @PathVariable Integer id) throws URISyntaxException {
        try {
            return reportService.Update(newReport, id);
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Report>> Delete(@PathVariable Integer id) {
        try {
            return reportService.Delete(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("report", id);
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }
}
