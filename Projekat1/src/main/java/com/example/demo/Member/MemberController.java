package com.example.demo.Member;

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
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping()
    public CollectionModel<EntityModel<Member>> GetAll(){
        try {
            return memberService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Member>> GetById(@PathVariable Integer id) {
        try {
            return memberService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Member>> AddMember(@RequestHeader("Authorization") String token,@RequestBody Member newMember) throws URISyntaxException {
        try {
            return memberService.AddMember(newMember, token);
        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Member>> ModifyMember(@RequestHeader("Authorization") String token,@RequestBody Member newMember, @PathVariable Integer id) throws URISyntaxException {
        try {
            return memberService.ModifyMember(newMember,id,token);

        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Member>> DeleteMember(@PathVariable Integer id){
        try {
            return memberService.DeleteMember(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("member",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<EntityModel<Member>> GetByProfileId(@PathVariable Integer id) {
        try {
            return memberService.GetByProfileId(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }


}
