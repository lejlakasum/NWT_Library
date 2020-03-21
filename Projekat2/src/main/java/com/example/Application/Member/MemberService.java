package com.example.Application.Member;

import com.example.Application.ExceptionClasses.NotFoundException;
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
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    MemberModelAssembler assembler;

    public CollectionModel<EntityModel<Member>> GetAll() {
        List<EntityModel<Member>> members = memberRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(members,
                linkTo(methodOn(MemberController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Member>> GetById(Integer id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("member", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(member));
    }

    public ResponseEntity<EntityModel<Member>> Add(Member newMember) {
        EntityModel<Member> entityModel = assembler.toModel(memberRepository.save(newMember));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Member>> Update(Member newMember, Integer id) {
        Member updatedMember = memberRepository.findById(id)
                .map(member -> {
                    member.setFirstName(newMember.getFirstName());
                    member.setLastName(newMember.getLastName());
                    member.setActive(newMember.getActive());
                    return memberRepository.save(member);
                })
                .orElseThrow(()-> new NotFoundException("member", id));

        EntityModel<Member> entityModel = assembler.toModel(updatedMember);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Member>> Delete(Integer id) {

        memberRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
