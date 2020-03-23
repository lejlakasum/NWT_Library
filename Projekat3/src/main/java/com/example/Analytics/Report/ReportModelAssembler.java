package com.example.Analytics.Report;



import com.example.Analytics.ReportType.ReportTypeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReportModelAssembler implements RepresentationModelAssembler<Report, EntityModel<Report>> {
    @Override
    public EntityModel<Report> toModel(Report report) {
        /*if (!report.getReportType().hasLinks()){
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetById(report.getReportType().getId())).withSelfRel());
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetAll()).withRel("report_types"));
        }*/
        return new EntityModel<>(report, linkTo(methodOn(ReportController.class).GetById(report.getId())).withSelfRel(), linkTo(methodOn(ReportController.class).GetAll()).withRel("reports"));
    }
}
