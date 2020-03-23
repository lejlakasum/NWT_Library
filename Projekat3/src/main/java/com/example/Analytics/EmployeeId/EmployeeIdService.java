package com.example.Analytics.EmployeeId;

import com.example.Analytics.ExceptionClass.NotFoundException;
import com.example.Analytics.Report.Report;
import com.example.Analytics.Report.ReportRepository;
import com.example.Analytics.ReportType.ReportType;
import com.example.Analytics.ReportType.ReportTypeRepository;
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
public class EmployeeIdService {

    @Autowired
    private EmployeeIdRepository employeeIdRepository;
    @Autowired
    EmployeeIdModelAssembler assembler;

    public ResponseEntity<EntityModel<EmployeeId>> GetById(Integer id) {
        EmployeeId employeeId = employeeIdRepository.findById(id).orElseThrow(()->new NotFoundException("employee_id",id));
        return ResponseEntity.ok().body(assembler.toModel(employeeId));
    }

    public CollectionModel<EntityModel<EmployeeId>> GetAll() {
        List<EntityModel<EmployeeId>> employeeIds = employeeIdRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(employeeIds, linkTo(methodOn(EmployeeIdController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<EmployeeId>> Add(EmployeeId newEmployeeId) {
        EntityModel<EmployeeId> entityModel = assembler.toModel(employeeIdRepository.save(newEmployeeId));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<EmployeeId>> Update(EmployeeId newEmployeeId, Integer id) {
        EmployeeId updatedEmployeeId = employeeIdRepository.findById(id)
                .map(employeeId -> {employeeId.setId(newEmployeeId.getId());
                                return employeeIdRepository.save(employeeId);
        }).orElseThrow(()->new NotFoundException("reports",id));

        EntityModel<EmployeeId> entityModel = assembler.toModel(updatedEmployeeId);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<EmployeeId>> Delete(Integer id) {
        employeeIdRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
