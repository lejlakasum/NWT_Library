package com.example.Reservation.Reservation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends CrudRepository<BookMemberReservation, Integer> {
    public List<BookMemberReservation> findAll();
}
