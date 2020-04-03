package com.example.demo.PaidFee;

import com.example.demo.Book.BookController;
import com.example.demo.Fee.FeeController;
import com.example.demo.Member.MemberController;
import com.example.demo.Profile.ProfileController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaidFeeModelAssembler implements RepresentationModelAssembler<PaidFee, EntityModel<PaidFee>> {

    @Override
    public EntityModel<PaidFee> toModel(PaidFee paidFee){
        if(!paidFee.getBook().hasLinks()){
            paidFee.getBook()
                    .add(linkTo(methodOn(BookController.class)
                            .GetById(paidFee.getBook().getId()))
                            .withSelfRel());

            paidFee.getBook()
                    .add(linkTo(methodOn(BookController.class)
                            .GetAll())
                            .withRel("books"));
        }
        if(!paidFee.getFee().hasLinks()){
            paidFee.getFee()
                    .add(linkTo(methodOn(FeeController.class)
                            .GetById(paidFee.getFee().getId()))
                            .withSelfRel());

            paidFee.getFee()
                    .add(linkTo(methodOn(FeeController.class)
                            .GetAll())
                            .withRel("fees"));
        }
        if(!paidFee.getMember().hasLinks()){
            paidFee.getMember()
                    .add(linkTo(methodOn(MemberController.class)
                            .GetById(paidFee.getMember().getId()))
                            .withSelfRel());

            paidFee.getMember()
                    .add(linkTo(methodOn(MemberController.class)
                            .GetAll())
                            .withRel("members"));
        }
        return new EntityModel<>(paidFee,
                linkTo(methodOn(PaidFeeController.class).GetById(paidFee.getId())).withSelfRel(),
                linkTo(methodOn(PaidFeeController.class).GetAll()).withRel("paid fees"));
    }
}

