package com.example.Analytics.ReportType;


import com.example.Analytics.Report.Report;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReportTypeModelAssembler implements RepresentationModelAssembler<ReportType, EntityModel<ReportType>> {
    @Override
    public EntityModel<ReportType> toModel(ReportType reportType) {

        return new EntityModel<>(reportType, linkTo(methodOn(ReportTypeController.class).GetById(reportType.getId())).withSelfRel(), linkTo(methodOn(ReportTypeController.class).GetAll()).withRel("report_types"));
    }
}
