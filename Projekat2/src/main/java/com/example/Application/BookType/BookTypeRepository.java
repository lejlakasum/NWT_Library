package com.example.Application.BookType;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookTypeRepository extends CrudRepository<BookType, Integer> {

    List<BookType> findAll();

    BookType findById(ID id);

    void deleteById(ID id);
}
