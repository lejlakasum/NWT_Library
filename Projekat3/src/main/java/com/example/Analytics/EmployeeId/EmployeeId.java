package com.example.Analytics.EmployeeId;

import javax.persistence.*;

@Entity
@Table(name = "employee_id")
public class EmployeeId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public EmployeeId() {}

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
}
