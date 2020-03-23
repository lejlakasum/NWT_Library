package com.example.Analytics.ReportType;

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
@RequestMapping("/reportType")
public class ReportTypeController {

    @Autowired
    ReportTypeService reportTypeService;

    @GetMapping()
    public CollectionModel<EntityModel<ReportType>> GetAll() {
        try {
            return reportTypeService.GetAll();
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReportType>> GetById(@PathVariable Integer id){
        try{
            return reportTypeService.GetById(id);
        }
        catch (NotFoundException exeption) {
            throw exeption;
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<ReportType>> Add(@RequestBody ReportType newReportType) throws URISyntaxException {
        try {
            return reportTypeService.Add(newReportType);
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException( exception.getMessage());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }

    @PostMapping("/{id}")
    ResponseEntity<EntityModel<ReportType>> Update(@RequestBody ReportType newReportType, @PathVariable Integer id) throws URISyntaxException {
        try {
            return reportTypeService.Update(newReportType, id);
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException( exception.getMessage());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<ReportType>> Delete(@PathVariable Integer id) {
        try {
            return reportTypeService.Delete(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("report", id);
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }
}
