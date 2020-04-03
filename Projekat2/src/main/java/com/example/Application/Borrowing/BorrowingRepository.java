package com.example.Application.Borrowing;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface BorrowingRepository extends CrudRepository<Borrowing, Integer> {

    Borrowing findById(ID id);

    void deleteById(ID id);

    @Query(value = "SELECT book.id, book.isbn, book.copy_id, book.genre_id, book.publisher_id, book.book_type_id, book.published_date, book.available " +
            "FROM book_member_borrowing, book " +
            "WHERE book.id=book_member_borrowing.book_id AND book_member_borrowing.member_id=:member_id",
            nativeQuery = true)
    List<Tuple> findBorrowingByMember(@Param("member_id")Integer memberId);

    @Query(value = "SELECT member.id, member.first_name, member.last_name, member.active " +
            "FROM book_member_borrowing, member " +
            "WHERE member.id=book_member_borrowing.member_id AND book_member_borrowing.book_id=:book_id",
            nativeQuery = true)
    List<Tuple> findMembersBorrowingByBook(@Param("book_id")Integer bookId);

    @Query(value = "SELECT * " +
            "FROM book_member_borrowing " +
            "WHERE :member_id=book_member_borrowing.member_id AND book_member_borrowing.book_id=:book_id",
            nativeQuery = true)
    List<Tuple> findBorrowingId(@Param("member_id")Integer memberId, @Param("book_id")Integer bookId);
}
