package com.example.demo.Role;

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
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    RoleModelAssembler assembler;

    public CollectionModel<EntityModel<Role>> GetAll() {
        List<EntityModel<Role>> roles=roleRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(roles,
                linkTo(methodOn(RoleController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Role>> GetById(Integer id){
        Role role=roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("role", id));
        return ResponseEntity
                .ok()
                .body(assembler.toModel(role));
    }

    public ResponseEntity<EntityModel<Role>> AddRole(Role newRole) {
        EntityModel<Role> entityModel = assembler.toModel(roleRepository.save(newRole));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Role>> ModifyRole(Role newRole, Integer id){
        Role updatedRole=roleRepository.findById(id)
                .map(role -> {
                    role.setName(newRole.getName());
                    return roleRepository.save(role);
                })
                .orElseGet(()-> {
                    newRole.setId(id);
                    return roleRepository.save(newRole);
                });
        EntityModel<Role> entityModel=assembler.toModel(updatedRole);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Role>> DeleteRole(Integer id) {
        roleRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
