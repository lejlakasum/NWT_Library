package com.example.Reservation.Member;

import com.example.Reservation.ExceptionClass.BadRequestException;
import com.example.Reservation.ExceptionClass.InternalServerException;
import com.example.Reservation.ExceptionClass.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/member")
public class MemberController {
        @Autowired
        MemberService memberService;

        @GetMapping()
        public CollectionModel<EntityModel<Member>> GetAll() {
            try {
                return memberService.GetAll();
            }
            catch (Exception exeption) {
                throw new InternalServerException();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<EntityModel<Member>> GetById(@PathVariable Integer id){
            try{
                return memberService.GetById(id);
            }
            catch (NotFoundException exeption) {
                throw exeption;
            }
            catch (Exception exeption) {
                throw new InternalServerException();
            }
        }

        @PostMapping()
        ResponseEntity<EntityModel<Member>> Add(@RequestBody Member newMember) throws URISyntaxException {
            try {
                return memberService.Add(newMember);
            }
            catch (ConstraintViolationException exception) {
                throw new BadRequestException( exception.getMessage());
            }
            catch (NotFoundException exception) {
                throw exception;
            }
            catch (Exception exception){
                throw new InternalServerException();
            }
        }

        @PostMapping("/{id}")
        ResponseEntity<EntityModel<Member>> Update(@RequestBody Member newMember, @PathVariable Integer id) throws URISyntaxException {
            try {
                return memberService.Update(newMember, id);
            }
            catch (ConstraintViolationException exception) {
                throw new BadRequestException( exception.getMessage());
            }
            catch (NotFoundException exception) {
                throw exception;
            }
            catch (Exception exception){
                throw new InternalServerException();
            }
        }

        @DeleteMapping("/{id}")
        ResponseEntity<EntityModel<Member>> Delete(@PathVariable Integer id) {
            try {
                return memberService.Delete(id);
            }
            catch (EmptyResultDataAccessException exception) {
                throw new NotFoundException("member", id);
            }
            catch (Exception exception){
                throw new InternalServerException();
            }
        }
    }

