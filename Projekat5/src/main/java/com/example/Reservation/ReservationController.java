package com.example.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping("/all")
    public List<BookMemberReservation> getAll() {
        return reservationRepository.findAll();
    }
}
