package com.example.Application.Impression;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface ImpressionRepository extends CrudRepository<Impression, Integer> {

    @Query(value = "SELECT impression.rate, impression.impression, member.id " +
            "FROM member, impression " +
            "WHERE impression.book_id=:book_id AND impression.member_id=member.id",
            nativeQuery = true)
    List<Tuple> findImpressionsByBook(@Param("book_id")Integer bookId);
}
