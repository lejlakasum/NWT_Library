package com.example.demo.Employee;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    @Override
    public EntityModel<Employee> toModel(Employee employee){
        if(!employee.getProfile().hasLinks()){
            employee.getProfile()
                    .add(linkTo(methodOn(EmployeeController.class)
                            .GetById(employee.getProfile().getId()))
                            .withSelfRel());

            employee.getProfile()
                    .add(linkTo(methodOn(EmployeeController.class)
                            .GetAll())
                            .withRel("profile"));
        }
        return new EntityModel<>(employee,
                linkTo(methodOn(EmployeeController.class).GetById(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).GetAll()).withRel("employees"));
    }
}