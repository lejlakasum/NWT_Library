package com.example.demo.Employee;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.Profile.Profile;
import com.example.demo.Profile.ProfileRepository;
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
    private EmployeeRepository employeeRepository;

    @Autowired
    EmployeeModelAssembler employeeAssembler;

    @Autowired
    ProfileRepository profileRepository;

    public CollectionModel<EntityModel<Employee>> GetAll() {
        List<EntityModel<Employee>> employees=employeeRepository.findAll().stream()
                .map(employeeAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(employees,
                linkTo(methodOn(EmployeeController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Employee>> GetById(Integer id){

        Employee employees=employeeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("employee",id));
        return ResponseEntity
                .ok()
                .body(employeeAssembler.toModel(employees));
    }

    public ResponseEntity<EntityModel<Employee>> AddEmployee(Employee newEmployee){
        Integer profileId=newEmployee.getProfile().getId();
        Profile profile=profileRepository.findById(profileId)
                .orElseThrow(()->new NotFoundException("profile", profileId));
        newEmployee.setProfile(profile);
        newEmployee.setSalary(newEmployee.getSalary());

        EntityModel<Employee> entityModel=employeeAssembler.toModel(employeeRepository.save(newEmployee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    public ResponseEntity<EntityModel<Employee>> ModifyEmployee(Employee newEmployee,Integer id){
        Integer profileId=newEmployee.getProfile().getId();
        Profile profile=profileRepository.findById(profileId)
                .orElseThrow(()->new NotFoundException("profile",profileId));

        Employee modifiedemployee=employeeRepository.findById(id)
                .map(employee -> {
                    employee.setProfile(profile);
                    employee.setSalary(newEmployee.getSalary());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(()->new NotFoundException("employee",id));

        EntityModel<Employee> entityModel=employeeAssembler.toModel(modifiedemployee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Employee>> DeleteEmployee(Integer id){
        employeeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
