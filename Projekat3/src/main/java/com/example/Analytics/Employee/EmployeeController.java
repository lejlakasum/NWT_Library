package com.example.Analytics.Employee;

import com.example.Analytics.ExceptionClass.BadRequestException;
import com.example.Analytics.ExceptionClass.InternalServerException;
import com.example.Analytics.ExceptionClass.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public CollectionModel<EntityModel<Employee>> GetAll() {
        try {
            return employeeService.GetAll();
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Employee>> GetById(@PathVariable Integer id){
        try{
            return employeeService.GetById(id);
        }
        catch (NotFoundException exeption) {
            throw exeption;
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Employee>> Add(@RequestBody Employee newEmployeeId) throws URISyntaxException {
        try {
            return employeeService.Add(newEmployeeId);
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
    ResponseEntity<EntityModel<Employee>> Update(@RequestBody Employee newEmployee, @PathVariable Integer id) throws URISyntaxException {
        try {
            return employeeService.Update(newEmployee, id);
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
    ResponseEntity<EntityModel<Employee>> Delete(@PathVariable Integer id) {
        try {
            return employeeService.Delete(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("employee", id);
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }
}
