package com.example.demo.PaidFee;

import com.example.demo.Book.Book;
import com.example.demo.Book.BookRepository;
import com.example.demo.Exception.NotFoundException;
import com.example.demo.Fee.Fee;
import com.example.demo.Fee.FeeRepository;
import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
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
public class PaidFeeService {

    @Autowired
    private PaidFeeRepository paidFeeRepository;

    @Autowired
    PaidFeeModelAssembler paidFeeAssembler;

    @Autowired
    FeeRepository feeRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MemberRepository memberRepository;

    public CollectionModel<EntityModel<PaidFee>> GetAll() {
        List<EntityModel<PaidFee>> paidFee=paidFeeRepository.findAll().stream()
                .map(paidFeeAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(paidFee,
                linkTo(methodOn(PaidFeeController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<PaidFee>> GetById(Integer id){

        PaidFee paidFee=paidFeeRepository.findById(id)
                .orElseThrow(()->new NotFoundException("paid fee",id));
        return ResponseEntity
                .ok()
                .body(paidFeeAssembler.toModel(paidFee));
    }

    public ResponseEntity<EntityModel<PaidFee>> AddPaidFee(PaidFee newPaidFee){
        Integer bookId=newPaidFee.getBook().getId();
        Book book=bookRepository.findById(bookId)
                .orElseThrow(()->new NotFoundException("book", bookId));
        Integer feeId=newPaidFee.getFee().getId();
        Fee fee=feeRepository.findById(feeId)
                .orElseThrow(()->new NotFoundException("fee", feeId));
        Integer memberId=newPaidFee.getMember().getId();
        Member member=memberRepository.findById(memberId)
                .orElseThrow(()->new NotFoundException("member", feeId));
        newPaidFee.setBook(book);
        newPaidFee.setFee(fee);
        newPaidFee.setMember(member);
        newPaidFee.setPaymentDate(newPaidFee.getPaymentDate());

        EntityModel<PaidFee> entityModel=paidFeeAssembler.toModel(paidFeeRepository.save(newPaidFee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    public ResponseEntity<EntityModel<PaidFee>> ModifyPaidFee(PaidFee newPaidFee,Integer id){
        Integer bookId=newPaidFee.getBook().getId();
        Book book=bookRepository.findById(bookId)
                .orElseThrow(()->new NotFoundException("book", bookId));
        Integer feeId=newPaidFee.getFee().getId();
        Fee fee=feeRepository.findById(feeId)
                .orElseThrow(()->new NotFoundException("fee", feeId));
        Integer memberId=newPaidFee.getMember().getId();
        Member member=memberRepository.findById(memberId)
                .orElseThrow(()->new NotFoundException("member", feeId));

        PaidFee modifiedPaidFee=paidFeeRepository.findById(id)
                .map(paidFee -> {
                    paidFee.setMember(member);
                    paidFee.setFee(fee);
                    paidFee.setBook(book);
                    paidFee.setPaymentDate(paidFee.getPaymentDate());
                    return paidFeeRepository.save(paidFee);
                })
                .orElseThrow(()->new NotFoundException("paid fee",id));

        EntityModel<PaidFee> entityModel=paidFeeAssembler.toModel(modifiedPaidFee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<PaidFee>> DeletePaidFee(Integer id){
        paidFeeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
