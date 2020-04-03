package com.example.demo.Book;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Integer> {

    List<Book> findAll();
    Book findById(ID id);
    void deleteById(ID id);
}
