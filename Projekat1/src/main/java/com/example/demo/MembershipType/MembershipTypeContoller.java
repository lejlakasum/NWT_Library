package com.example.demo.MembershipType;

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
@RequestMapping("/membershiptypes")
public class MembershipTypeContoller {
    @Autowired
    MembershipTypeService membershipTypeService;

    @GetMapping()
    CollectionModel<EntityModel<MembershipType>> GetAll(){
        try {
            return membershipTypeService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<MembershipType>> GetById(@PathVariable Integer id) {
        try {
            return membershipTypeService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<MembershipType>> AddMembershipType(@RequestBody MembershipType newMembershipType) throws URISyntaxException {
        try {
            return membershipTypeService.AddMembershipType(newMembershipType);
        }
        catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<MembershipType>> ModifyMembershipType(@RequestBody MembershipType newMembershipType, @PathVariable Integer id) throws URISyntaxException {
        try {
            return membershipTypeService.ModifyMembershipType(newMembershipType,id);

        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<MembershipType>> DeleteMembershipType(@PathVariable Integer id){
        try {
            return membershipTypeService.DeleteMembershipType(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("membership type",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
