package com.example.Application.Member;

import com.example.Application.Author.Author;
import com.example.Application.Book.Book;
import com.example.Application.Book.BookModelAssembler;
import com.example.Application.Book.BookRepository;
import com.example.Application.Borrowing.Borrowing;
import com.example.Application.Borrowing.BorrowingService;
import com.example.Application.Copy.CopyController;
import com.example.Application.ExceptionClasses.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Date;
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
    @Autowired
    BorrowingService borrowingService;
    @Autowired
    BookModelAssembler bookModelAssembler;
    @Autowired
    BookRepository bookRepository;

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

    public CollectionModel<EntityModel<Book>> GetBorrowings(Integer memberId) {
        Boolean memberExist = memberRepository.existsById(memberId);
        if(!memberExist) {
            throw new NotFoundException("member", memberId);
        }

        List<EntityModel<Book>> books = borrowingService.GetBorrowingByMember(memberId).stream()
                .map(bookModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(books,
                linkTo(methodOn(MemberController.class).GetBorrowings(memberId)).withSelfRel());
    }

    public ResponseEntity<EntityModel<Member>> Add(Member newMember) {
        EntityModel<Member> entityModel = assembler.toModel(memberRepository.save(newMember));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public void AddBorrowingToMember(Integer memberId, Integer bookId, String token) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new NotFoundException("member", memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new NotFoundException("book", bookId));

        Borrowing borrowing = new Borrowing(book, member, new Date(), false);

        borrowingService.Add(borrowing, token);
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

    public void ReturnBook(Integer memberId, Integer bookId, String token) {
        borrowingService.UpdateReturned(bookId, memberId, token);
    }

    public ResponseEntity<EntityModel<Member>> Delete(Integer id) {

        memberRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
