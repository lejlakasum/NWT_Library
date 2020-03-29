package com.example.demo.Member;

import com.example.demo.MembershipType.MembershipTypeContoller;
import com.example.demo.Profile.ProfileController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<Member, EntityModel<Member>> {

    @Override
    public EntityModel<Member> toModel(Member member){
        if(!member.getProfile().hasLinks()){
            member.getProfile()
                    .add(linkTo(methodOn(ProfileController.class)
                            .GetById(member.getProfile().getId()))
                            .withSelfRel());

            member.getProfile()
                    .add(linkTo(methodOn(ProfileController.class)
                            .GetAll())
                            .withRel("profiles"));
        }
        if(!member.getMembershipType().hasLinks()){
                member.getMembershipType()
                        .add(linkTo(methodOn(MembershipTypeContoller.class)
                                .GetById(member.getProfile().getId()))
                                .withSelfRel());

                member.getMembershipType()
                        .add(linkTo(methodOn(MembershipTypeContoller.class)
                                .GetAll())
                                .withRel("membership types"));
            }
        return new EntityModel<>(member,
                linkTo(methodOn(MemberController.class).GetById(member.getId())).withSelfRel(),
                linkTo(methodOn(MemberController.class).GetAll()).withRel("members"));
    }
}