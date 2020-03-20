package com.example.Application.Author;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AuthorRepository extends CrudRepository<Author, Integer> {

    List<Author> findAll();

    Author findById(ID id);

    void deleteById(ID id);
}
