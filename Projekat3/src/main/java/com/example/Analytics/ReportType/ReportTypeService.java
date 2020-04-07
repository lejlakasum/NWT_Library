package com.example.Analytics.ReportType;

import com.example.Analytics.ExceptionClass.NotFoundException;
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
public class ReportTypeService {

    @Autowired
    ReportTypeRepository reportTypeRepository;
    @Autowired
    ReportTypeModelAssembler assembler;

    public ResponseEntity<EntityModel<ReportType>> GetById(Integer id) {
        ReportType reportType = reportTypeRepository.findById(id).orElseThrow(()->new NotFoundException("report_type",id));
        return ResponseEntity.ok().body(assembler.toModel(reportType));
    }

    public CollectionModel<EntityModel<ReportType>> GetAll() {
        List<EntityModel<ReportType>> report_types = reportTypeRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(report_types, linkTo(methodOn(ReportTypeController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<ReportType>> Add(ReportType newReportType) {
        EntityModel<ReportType> entityModel = assembler.toModel(reportTypeRepository.save(newReportType));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<ReportType>> Update(ReportType newReportType, Integer id) {
        ReportType updatedReportType = reportTypeRepository.findById(id)
                .map(reportType -> {reportType.setName(newReportType.getName());
                    return reportTypeRepository.save(reportType);
                }).orElseThrow(()->new NotFoundException("report_types",id));

        EntityModel<ReportType> entityModel = assembler.toModel(updatedReportType);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<ReportType>> Delete(Integer id) {
        reportTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
