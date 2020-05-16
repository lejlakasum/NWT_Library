package com.example.demo.Profile;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.Role.Role;
import com.example.demo.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    ProfileModelAssembler profileAssembler;

    @Autowired
    RoleRepository roleRepository;

    @Bean
    private PasswordEncoder passwordEncoderP() {
        return new BCryptPasswordEncoder();
    }

    public CollectionModel<EntityModel<Profile>> GetAll() {
        List<EntityModel<Profile>> profiles=profileRepository.findAll().stream()
                .map(profileAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(profiles,
                linkTo(methodOn(ProfileController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Profile>> GetById(Integer id){

        Profile profile=profileRepository.findById(id)
                .orElseThrow(()->new NotFoundException("profile",id));
        return ResponseEntity
                .ok()
                .body(profileAssembler.toModel(profile));
    }

    public ResponseEntity<EntityModel<Profile>> AddProfile(Profile newProfile){
        Integer roleId=newProfile.getRole().getId();
        Role role=roleRepository.findById(roleId)
                .orElseThrow(()->new NotFoundException("role", roleId));
        newProfile.setRole(role);
        newProfile.setFirstName(newProfile.getFirstName());
        newProfile.setLastName(newProfile.getLastName());
        newProfile.setBirthDate(newProfile.getBirthDate());
        newProfile.setPassword("{bcrypt}" + passwordEncoderP().encode(newProfile.getPassword()));
        newProfile.setUsername(newProfile.getUsername());

        EntityModel<Profile> entityModel=profileAssembler.toModel(profileRepository.save(newProfile));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    public ResponseEntity<EntityModel<Profile>> ModifyProfile(Profile newProfile,Integer id){
        Integer roleId=newProfile.getRole().getId();
        Role role=roleRepository.findById(roleId)
                .orElseThrow(()->new NotFoundException("role",roleId));

        Profile modifiedProfile=profileRepository.findById(id)
                .map(profile -> {
                    profile.setRole(role);
                    profile.setFirstName(newProfile.getFirstName());
                    profile.setLastName(newProfile.getLastName());
                    profile.setBirthDate(newProfile.getBirthDate());
                    profile.setUsername(newProfile.getUsername());
                    profile.setPassword(newProfile.getPassword());
                    return profileRepository.save(profile);
                })
                .orElseThrow(()->new NotFoundException("profile",id));

        EntityModel<Profile> entityModel=profileAssembler.toModel(modifiedProfile);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Profile>> DeleteProfile(Integer id){
        profileRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
