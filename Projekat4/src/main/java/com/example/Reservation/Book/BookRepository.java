package com.example.Reservation.Book;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends CrudRepository<Book, Integer> {
    public List<Book> findAll();
    Book findById(ID id);
    void deleteById(ID id);
}
