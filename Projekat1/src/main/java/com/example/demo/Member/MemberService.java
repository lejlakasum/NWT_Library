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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    RestTemplate restTemplate;

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

    public ResponseEntity<EntityModel<Member>> AddMember(Member newMember,String token){
        Integer profileId=newMember.getProfile().getId();

        Profile profile=profileRepository.findById(profileId)
                .orElseThrow(()->new NotFoundException("profile", profileId));

        Integer membershipTypeId=newMember.getMembershipType().getId();

        MembershipType membershipType=membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(()->new NotFoundException("membership type", membershipTypeId));

        newMember.setMembershipType(membershipType);
        newMember.setProfile(profile);

        EntityModel<Member> entityModel=memberAssembler.toModel(memberRepository.save(newMember));

        ResponseEntity<EntityModel<Member>> result= ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

        MemberDTO newMemberDTO=new MemberDTO(result.getBody().getContent().getId(), profile.getFirstName(),profile.getLastName(),newMember.getActive());
        InsertMemberBookService(newMemberDTO,token);

        return result;

    }

    public ResponseEntity<EntityModel<Member>> ModifyMember(Member newMember,Integer id,String token){
        Integer profileId=newMember.getProfile().getId();
        Profile profile=profileRepository.findById(profileId)
                .orElseThrow(()->new NotFoundException("profile", profileId));
        Integer membershipTypeId=newMember.getMembershipType().getId();
        MembershipType membershipType=membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(()->new NotFoundException("membership type", membershipTypeId));

        Member modifiedMember=memberRepository.findById(id)
                .map(member -> {
                    member.setProfile(profile);
                    member.setMembershipType(membershipType);
                    member.setActive(newMember.getActive());
                    member.setJoinDate(newMember.getJoinDate());
                    return memberRepository.save(member);
                })
                .orElseThrow(()->new NotFoundException("member",id));

        EntityModel<Member> entityModel=memberAssembler.toModel(modifiedMember);

        MemberDTO newMemberDTO=new MemberDTO(id, profile.getFirstName(),profile.getLastName(),newMember.getActive());
        UpdateMemberBookService(id,newMemberDTO,token);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Member>> DeleteMember(Integer id, String token){
        DeleteMemberBookService(id, token);
        memberRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<EntityModel<Member>> GetByProfileId(Integer id){

        List<Member> members = memberRepository.findAll();
        Member member = new Member();
        for(int i=0; i<members.size(); i++) {
            System.out.println(members.get(i).getProfile().getId().intValue() + " " + id);
            if(members.get(i).getProfile().getId().intValue()==id) {
                member = members.get(i);
                break;
            }
        }
        return ResponseEntity
                .ok()
                .body(memberAssembler.toModel(member));
    }

    //private methods

    private void InsertMemberBookService(MemberDTO newMember, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization",token);
        HttpEntity<MemberDTO> request = new HttpEntity<>(newMember, headers);

        ResponseEntity<MemberDTO> result=restTemplate.postForEntity("http://book-service/members",request,MemberDTO.class);
    }

    private void UpdateMemberBookService(Integer id,MemberDTO newMember,String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization",token);
        HttpEntity<MemberDTO> request = new HttpEntity<>(newMember, headers);

        restTemplate.put("http://book-service/members/"+id,request);
    }

    private void DeleteMemberBookService(Integer id, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.split(" ")[1]);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        restTemplate.exchange("http://book-service/members/"+id, HttpMethod.DELETE, entity, String.class);
    }
}
