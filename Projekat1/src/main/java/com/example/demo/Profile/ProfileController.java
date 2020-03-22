package com.example.demo.Profile;

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
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping()
    CollectionModel<EntityModel<Profile>> GetAll(){
        try {
            return profileService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Profile>> GetById(@PathVariable Integer id) {
        try {
            return profileService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Profile>> AddProfile(@RequestBody Profile newProfile) throws URISyntaxException {
        try {
            return profileService.AddProfile(newProfile);
        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Profile>> ModifyProfile(@RequestBody Profile newProfile, @PathVariable Integer id) throws URISyntaxException {
        try {
            return profileService.ModifyProfile(newProfile,id);

        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Profile>> DeleteProfile(@PathVariable Integer id){
        try {
            return profileService.DeleteProfile(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("profile",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
