package com.example.Reservation.Reservation;

import com.example.Reservation.Book.Book;
import com.example.Reservation.Book.BookRepository;
import com.example.Reservation.ExceptionClass.NotFoundException;

import com.example.Reservation.Member.Member;
import com.example.Reservation.Member.MemberRepository;
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
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    ReservationModelAssembler assembler;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookRepository bookRepository;

    public ResponseEntity<EntityModel<BookMemberReservation>> GetById(Integer id) {
        BookMemberReservation bookMemberReservation = reservationRepository.findById(id).orElseThrow(()->new NotFoundException("reservation",id));
        return ResponseEntity.ok().body(assembler.toModel(bookMemberReservation));
    }

    public CollectionModel<EntityModel<BookMemberReservation>> GetAll() {
        List<EntityModel<BookMemberReservation>> reservations = reservationRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(reservations, linkTo(methodOn(ReservationController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<BookMemberReservation>> Add(BookMemberReservation newBookMemberReservation) {
        final Integer memberId = newBookMemberReservation.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow(()->new NotFoundException("member", memberId));
        newBookMemberReservation.setMember(member);

        final Integer bookId = newBookMemberReservation.getBook().getId();
        Book book = bookRepository.findById(bookId).orElseThrow(()->new NotFoundException("book", bookId));
        newBookMemberReservation.setBook(book);

        EntityModel<BookMemberReservation> entityModel = assembler.toModel(reservationRepository.save(newBookMemberReservation));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<BookMemberReservation>> Update(final BookMemberReservation newBookMemberReservation, Integer id) {
        Integer memberId = newBookMemberReservation.getMember().getId();
        Member member = memberRepository.findById(memberId).orElseThrow(()->new NotFoundException("member", memberId));

        final Integer bookId = newBookMemberReservation.getBook().getId();
        Book book = bookRepository.findById(bookId).orElseThrow(()->new NotFoundException("book", bookId));

        BookMemberReservation updatedReservation = reservationRepository.findById(id)
                .map(bookMemberReservation -> {bookMemberReservation.setMember(member);
                                               bookMemberReservation.setBook(book);
                                               bookMemberReservation.setDateTimeOfReservation(newBookMemberReservation.getDateTimeOfReservation());
                                return reservationRepository.save(bookMemberReservation);
        }).orElseThrow(()->new NotFoundException("book member reservations",id));

        EntityModel<BookMemberReservation> entityModel = assembler.toModel(updatedReservation);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    public ResponseEntity<EntityModel<BookMemberReservation>> Delete(Integer id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
