package com.example.demo.Profile;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfileModelAssembler implements RepresentationModelAssembler<Profile, EntityModel<Profile>> {

    @Override
    public EntityModel<Profile> toModel(Profile profile){
        if(!profile.getRole().hasLinks()){
            profile.getRole()
                    .add(linkTo(methodOn(ProfileController.class)
                    .GetById(profile.getRole().getId()))
                    .withSelfRel());

            profile.getRole()
                    .add(linkTo(methodOn(ProfileController.class)
                    .GetAll())
                    .withRel("profiles"));
        }
        return new EntityModel<>(profile,
                linkTo(methodOn(ProfileController.class).GetById(profile.getId())).withSelfRel(),
                linkTo(methodOn(ProfileController.class).GetAll()).withRel("profiles"));
    }
}
