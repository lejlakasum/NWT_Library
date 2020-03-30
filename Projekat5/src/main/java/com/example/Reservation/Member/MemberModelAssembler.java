package com.example.Reservation.Member;

import com.example.Reservation.Reservation.ReservationController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<Member, EntityModel<Member>> {
    @Override
    public EntityModel<Member> toModel(Member member) {
        /*if (!report.getReportType().hasLinks()){
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetById(report.getReportType().getId())).withSelfRel());
            report.getReportType().add(linkTo(methodOn(ReportTypeController.class).GetAll()).withRel("report_types"));
        }*/
        return new EntityModel<>(member, linkTo(methodOn(MemberController.class).GetById(member.getId())).withSelfRel(), linkTo(methodOn(ReservationController.class).GetAll()).withRel("members"));
    }
}
