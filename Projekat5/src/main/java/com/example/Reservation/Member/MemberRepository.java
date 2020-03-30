package com.example.Reservation.Member;

import com.example.Reservation.Reservation.BookMemberReservation;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MemberRepository extends CrudRepository<Member, Integer> {
    public List<Member> findAll();
    Member findById(ID id);
    void deleteById(ID id);
}
