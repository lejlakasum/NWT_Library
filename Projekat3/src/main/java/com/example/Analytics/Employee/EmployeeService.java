package com.example.Analytics.Employee;

import com.example.Analytics.ExceptionClass.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeModelAssembler assembler;

    public ResponseEntity<EntityModel<Employee>> GetById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new NotFoundException("employee_id",id));
        return ResponseEntity.ok().body(assembler.toModel(employee));
    }

    public CollectionModel<EntityModel<Employee>> GetAll() {
        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(employees, linkTo(methodOn(EmployeeController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Employee>> Add(Employee newEmployee) {
        EntityModel<Employee> entityModel = assembler.toModel(employeeRepository.save(newEmployee));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Employee>> Update(Employee newEmployee, Integer id) {
        Employee updatedEmployee = employeeRepository.findById(id)
                .map(employee -> {
                                return employeeRepository.save(employee);
        }).orElseThrow(()->new NotFoundException("employees",id));

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Employee>> Delete(Integer id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
