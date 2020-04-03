package com.example.demo.MembershipType;

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
public class MembershipTypeService {
    @Autowired
    private MembershipTypeRepository membershipTypeRepository;

    @Autowired
    MembershipTypeModelAssembler assembler;

    public CollectionModel<EntityModel<MembershipType>> GetAll() {
        List<EntityModel<MembershipType>> membershipTypes=membershipTypeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(membershipTypes,
                linkTo(methodOn(MembershipTypeContoller.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<MembershipType>> GetById(Integer id){
        MembershipType membershipType=membershipTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("membership type", id));
        return ResponseEntity
                .ok()
                .body(assembler.toModel(membershipType));
    }

    public ResponseEntity<EntityModel<MembershipType>> AddMembershipType(MembershipType newMembershipType) {
        EntityModel<MembershipType> entityModel = assembler.toModel(membershipTypeRepository.save(newMembershipType));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<MembershipType>> ModifyMembershipType(MembershipType newMembershipType, Integer id){
        MembershipType updatedMembershipType=membershipTypeRepository.findById(id)
                .map(membershipType -> {
                    membershipType.setName(newMembershipType.getName());
                    return membershipTypeRepository.save(membershipType);
                })
                .orElseGet(()-> {
                    newMembershipType.setId(id);
                    return membershipTypeRepository.save(newMembershipType);
                });
        EntityModel<MembershipType> entityModel=assembler.toModel(updatedMembershipType);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<MembershipType>> DeleteMembershipType(Integer id) {
        membershipTypeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
