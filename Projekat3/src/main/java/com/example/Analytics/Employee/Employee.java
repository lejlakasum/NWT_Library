package com.example.Analytics.Employee;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "employee")
public class Employee extends RepresentationModel<Employee> {

    @Id
    //@NotNull(message = "Variable employee id must not be null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Employee() {}

    public Employee(@NotNull(message = "Variable employee id must not be null") Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }


}
