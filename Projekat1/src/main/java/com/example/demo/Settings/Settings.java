package com.example.demo.Settings;

import com.example.demo.Employee.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NotNull
    private String name;

    @NotNull
    private String value;

    private String description;

    public Settings() {
    }

    public Settings(Employee employee,String name, String value,String description){
        this.employee=employee;
        this.name=name;
        this.value=value;
        this.description=description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
