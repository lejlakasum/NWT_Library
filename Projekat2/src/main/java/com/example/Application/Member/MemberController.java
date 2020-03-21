package com.example.Application.Member;

import com.example.Application.ExceptionClasses.BadRequestException;
import com.example.Application.ExceptionClasses.InternalServerException;
import com.example.Application.ExceptionClasses.NotFoundException;
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
        public CollectionModel<EntityModel<Member>> GetAll() {
            try {
                return memberService.GetAll();
            } catch (Exception ex) {
                throw new InternalServerException();
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<EntityModel<Member>> GetById(@PathVariable Integer id) {
            try {
                return memberService.GetById(id);
            } catch (NotFoundException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new InternalServerException();
            }
        }

        @PostMapping()
        ResponseEntity<EntityModel<Member>> Add(@RequestBody Member newMember) throws URISyntaxException {

            try {
                return memberService.Add(newMember);
            } catch (ConstraintViolationException ex) {

                throw new BadRequestException(ex.getMessage());
            } catch (Exception ex) {
                throw new InternalServerException();
            }
        }

        @PutMapping("/{id}")
        ResponseEntity<EntityModel<Member>> Update(@RequestBody Member newMember, @PathVariable Integer id) throws URISyntaxException {
            try {
                return memberService.Update(newMember, id);
            } catch (ConstraintViolationException ex) {

                throw new BadRequestException(ex.getMessage());
            } catch (Exception ex) {
                throw new InternalServerException();
            }
        }

        @DeleteMapping("/{id}")
        ResponseEntity<EntityModel<Member>> Delete(@PathVariable Integer id) {
            try {
                return memberService.Delete(id);
            } catch (EmptyResultDataAccessException ex) {
                throw new NotFoundException("member", id);
            } catch (Exception ex) {
                throw new InternalServerException();
            }
        }
    }
