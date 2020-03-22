package com.example.demo.Member;

import com.example.demo.Exception.NotFoundException;
import com.example.demo.MembershipType.MembershipType;
import com.example.demo.MembershipType.MembershipTypeRepository;
import com.example.demo.Profile.Profile;
import com.example.demo.Profile.ProfileRepository;
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
    MemberModelAssembler memberAssembler;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    MembershipTypeRepository membershipTypeRepository;

    public CollectionModel<EntityModel<Member>> GetAll() {
        List<EntityModel<Member>> members=memberRepository.findAll().stream()
                .map(memberAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(members,
                linkTo(methodOn(MemberController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Member>> GetById(Integer id){

        Member member=memberRepository.findById(id)
                .orElseThrow(()->new NotFoundException("member",id));
        return ResponseEntity
                .ok()
                .body(memberAssembler.toModel(member));
    }

    public ResponseEntity<EntityModel<Member>> AddMember(Member newMember){
        Integer profileId=newMember.getProfile().getId();
        Profile profile=profileRepository.findById(profileId)
                .orElseThrow(()->new NotFoundException("profile", profileId));
        Integer membershipTypeId=newMember.getMembershipTypeId().getId();
        MembershipType membershipType=membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(()->new NotFoundException("membership type", membershipTypeId));
        newMember.setProfile(profile);
        newMember.setMembershipTypeId(membershipType);
        newMember.setActive(newMember.getActive());
        newMember.setJoinDate(newMember.getJoinDate());

        EntityModel<Member> entityModel=memberAssembler.toModel(memberRepository.save(newMember));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    public ResponseEntity<EntityModel<Member>> ModifyMember(Member newMember,Integer id){
        Integer profileId=newMember.getProfile().getId();
        Profile profile=profileRepository.findById(profileId)
                .orElseThrow(()->new NotFoundException("profile", profileId));
        Integer membershipTypeId=newMember.getMembershipTypeId().getId();
        MembershipType membershipType=membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(()->new NotFoundException("membership type", membershipTypeId));

        Member modifiedMember=memberRepository.findById(id)
                .map(member -> {
                    member.setProfile(profile);
                    member.setMembershipTypeId(membershipType);
                    member.setActive(member.getActive());
                    member.setJoinDate(member.getJoinDate());
                    return memberRepository.save(member);
                })
                .orElseThrow(()->new NotFoundException("member",id));

        EntityModel<Member> entityModel=memberAssembler.toModel(modifiedMember);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Member>> DeleteMember(Integer id){
        memberRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
