package com.example.Analytics.EmployeeId;

import com.example.Analytics.ExceptionClass.BadRequestException;
import com.example.Analytics.ExceptionClass.InternalServerException;
import com.example.Analytics.ExceptionClass.NotFoundException;
import com.example.Analytics.Report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/employeeId")
public class EmployeeIdController {

    @Autowired
    EmployeeIdService employeeIdService;

    @GetMapping()
    public CollectionModel<EntityModel<EmployeeId>> GetAll() {
        try {
            return employeeIdService.GetAll();
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<EmployeeId>> GetById(@PathVariable Integer id){
        try{
            return employeeIdService.GetById(id);
        }
        catch (NotFoundException exeption) {
            throw exeption;
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<EmployeeId>> Add(@RequestBody EmployeeId newEmployeeId) throws URISyntaxException {
        try {
            return employeeIdService.Add(newEmployeeId);
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

    @PostMapping("/{id}")
    ResponseEntity<EntityModel<EmployeeId>> Update(@RequestBody EmployeeId newEmployeeId, @PathVariable Integer id) throws URISyntaxException {
        try {
            return employeeIdService.Update(newEmployeeId, id);
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
    ResponseEntity<EntityModel<EmployeeId>> Delete(@PathVariable Integer id) {
        try {
            return employeeIdService.Delete(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("report", id);
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }
}
