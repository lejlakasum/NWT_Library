package com.example.Reservation.Reservation;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReservationModelAssembler implements RepresentationModelAssembler<BookMemberReservation, EntityModel<BookMemberReservation>> {
    @Override
    public EntityModel<BookMemberReservation> toModel(BookMemberReservation bookMemberReservation) {
        /*if (!report.getReportType().hasLinks()){
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetById(report.getReportType().getId())).withSelfRel());
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetAll()).withRel("report_types"));
        }*/
        return new EntityModel<>(bookMemberReservation, linkTo(methodOn(ReservationController.class).GetById(bookMemberReservation.getId())).withSelfRel(), linkTo(methodOn(ReservationController.class).GetAll()).withRel("reservations"));
    }
}
