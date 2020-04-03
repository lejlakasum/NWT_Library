package com.example.Reservation.Member;

import com.example.Reservation.ExceptionClass.BadRequestException;
import com.example.Reservation.ExceptionClass.InternalServerException;
import com.example.Reservation.ExceptionClass.NotFoundException;

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

    public ResponseEntity<EntityModel<Member>> GetById(Integer id) {
        Member Member = memberRepository.findById(id).orElseThrow(()->new NotFoundException("member_id",id));
        return ResponseEntity.ok().body(assembler.toModel(Member));
    }

    public CollectionModel<EntityModel<Member>> GetAll() {
        List<EntityModel<Member>> report_types = memberRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(report_types, linkTo(methodOn(MemberController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Member>> Add(Member newMember) {
        EntityModel<Member> entityModel = assembler.toModel(memberRepository.save(newMember));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Member>> Update(Member newMember, Integer id) {
        Member updatedMember = memberRepository.findById(id)
                .map(member -> {member.setJoinDate(newMember.getJoinDate());
                                member.setLastName(newMember.getLastName());
                                member.setFirstName(newMember.getFirstName());
                                member.setBirthDate(newMember.getBirthDate());
                                member.setActive(newMember.getActive());
                                member.setMembershipTypeName(newMember.getMembershipTypeName());
                                member.setRoleName(newMember.getRoleName());
                    return memberRepository.save(member);
                }).orElseThrow(()->new NotFoundException("member_ids",id));

        EntityModel<Member> entityModel = assembler.toModel(updatedMember);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<Member>> Delete(Integer id) {
        memberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
