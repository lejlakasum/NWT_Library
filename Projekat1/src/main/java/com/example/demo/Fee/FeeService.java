package com.example.demo.Fee;

import com.example.demo.Exception.NotFoundException;
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
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    FeeModelAssembler assembler;

    public CollectionModel<EntityModel<Fee>> GetAll() {
        List<EntityModel<Fee>> fees=feeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(fees,
                linkTo(methodOn(FeeController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Fee>> GetById(Integer id){
        Fee fee=feeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("fee", id));
        return ResponseEntity
                .ok()
                .body(assembler.toModel(fee));
    }

    public ResponseEntity<EntityModel<Fee>> AddFee(Fee newFee) {
        EntityModel<Fee> entityModel = assembler.toModel(feeRepository.save(newFee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Fee>> ModifyFee(Fee newFee, Integer id){
        Fee updatedFee=feeRepository.findById(id)
                .map(fee -> {
                    fee.setName(newFee.getName());
                    fee.setValue(newFee.getValue());
                    return feeRepository.save(fee);
                })
                .orElseGet(()-> {
                    newFee.setId(id);
                    return feeRepository.save(newFee);
                });
        EntityModel<Fee> entityModel=assembler.toModel(updatedFee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Fee>> DeleteFee(Integer id) {
        feeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }




}
