package com.example.Application.Member;

import com.example.Application.Country.Country;
import com.example.Application.Country.CountryController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<Member, EntityModel<Member>> {

    @Override
    public EntityModel<Member> toModel(Member member) {

        return new EntityModel<>(member,
                linkTo(methodOn(MemberController.class).GetById(member.getId())).withSelfRel(),
                linkTo(methodOn(MemberController.class).GetAll()).withRel("members"));
    }
}
