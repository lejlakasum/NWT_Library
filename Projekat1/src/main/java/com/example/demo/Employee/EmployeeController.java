package com.example.demo.Employee;

import com.example.demo.Exception.BadRequestException;
import com.example.demo.Exception.InternalServerException;
import com.example.demo.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public CollectionModel<EntityModel<Employee>> GetAll(){
        try {
            return employeeService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Employee>> GetById(@PathVariable Integer id) {
        try {
            return employeeService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Employee>> AddEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {
        try {
            return employeeService.AddEmployee(newEmployee);
        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Employee>> ModifyEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id) throws URISyntaxException {
        try {
            return employeeService.ModifyEmployee(newEmployee,id);

        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Employee>> DeleteEmployee(@PathVariable Integer id){
        try {
            return employeeService.DeleteEmployee(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("employee",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
