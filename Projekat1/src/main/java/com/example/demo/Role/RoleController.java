package com.example.demo.Role;

import com.example.demo.Exception.BadRequestException;
import com.example.demo.Exception.InternalServerException;
import com.example.demo.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping()
    CollectionModel<EntityModel<Role>> GetAll(){
        try {
            return roleService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Role>> GetById(@PathVariable Integer id) {
        try {
            return roleService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Role>> AddRole(@RequestBody Role newRole) throws URISyntaxException {
        try {
            return roleService.AddRole(newRole);
        }
        catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Role>> ModifyRole(@RequestBody Role newRole, @PathVariable Integer id) throws URISyntaxException {
        try {
            return roleService.ModifyRole(newRole,id);

        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Role>> DeleteRole(@PathVariable Integer id){
        try {
            return roleService.DeleteRole(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("role",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
