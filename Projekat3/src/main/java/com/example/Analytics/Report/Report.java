package com.example.Analytics.Report;

import com.example.Analytics.Employee.Employee;
import com.example.Analytics.ReportType.ReportType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull (message = "Variable reportType must not be null")
    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @NotNull (message = "Variable employeeId must not be null")
    @ManyToOne
    @JoinColumn (name = "employee_id")
    private Employee employee;

    @NotNull(message = "Variable creationDate must not be null")
    @Column(name = "creation_date")
    private Date creationDate;

    @NotNull(message = "Variable path must not be null")
    @Column(name = "path")
    private String path;

    public Report(){}

    public Report(Integer id, @NotNull(message = "Variable reportType must not be null") ReportType reportType, @NotNull(message = "Variable employeeId must not be null") Employee employee, @NotNull(message = "Variable creationDate must not be null") Date creationDate, @NotNull(message = "Variable path must not be null") String path) {
        this.id = id;
        this.reportType = reportType;
        this.employee = employee;
        this.creationDate = creationDate;
        this.path = path;
    }

    public Report(@NotNull(message = "Variable reportType must not be null") ReportType reportType) {
        this.reportType = reportType;
    }

    public Report(@NotNull(message = "Variable reportType must not be null") ReportType reportType, @NotNull(message = "Variable employeeId must not be null") Employee employee, @NotNull(message = "Variable creationDate must not be null") Date creationDate, @NotNull(message = "Variable path must not be null") String path) {
        this.reportType = reportType;
        this.employee = employee;
        this.creationDate = creationDate;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public ReportType getReportType() {
        return reportType;
    }
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Employee getEmployee() {return employee;}
    public void setEmployee(Employee employee) {this.employee = employee;}

    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
