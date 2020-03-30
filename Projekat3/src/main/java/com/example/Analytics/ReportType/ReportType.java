package com.example.Analytics.ReportType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "report_type")
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable name must not be null")
    @Column(name = "name")
    private String name;

    public ReportType() {}

    public ReportType(Integer id, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable name must not be null") String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

