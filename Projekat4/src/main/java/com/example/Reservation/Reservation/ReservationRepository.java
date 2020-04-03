package com.example.Reservation.Reservation;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends CrudRepository<BookMemberReservation, Integer> {
    public List<BookMemberReservation> findAll();
    BookMemberReservation findById(ID id);
    void deleteById(ID id);
}
