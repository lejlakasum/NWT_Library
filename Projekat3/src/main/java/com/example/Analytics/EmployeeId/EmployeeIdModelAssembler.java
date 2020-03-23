package com.example.Analytics.EmployeeId;



import com.example.Analytics.Report.Report;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeIdModelAssembler implements RepresentationModelAssembler<EmployeeId, EntityModel<EmployeeId>> {
    @Override
    public EntityModel<EmployeeId> toModel(EmployeeId employeeId) {
        /*if (!report.getReportType().hasLinks()){
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetById(report.getReportType().getId())).withSelfRel());
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetAll()).withRel("report_types"));
        }*/
        return new EntityModel<>(employeeId, linkTo(methodOn(EmployeeIdController.class).GetById(employeeId.getId())).withSelfRel(), linkTo(methodOn(EmployeeIdController.class).GetAll()).withRel("reports"));
    }
}
