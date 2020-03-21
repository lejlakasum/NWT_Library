package com.example.Application.CopyAuthors;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CopyAuthorsRepository extends CrudRepository<CopyAuthors, Integer> {

    void deleteById(ID id);

    @Query(value = "SELECT author.id, author.first_name, author.last_name, author.birth_date, author.country_id  FROM author, copy_author WHERE author.id=copy_author.author_id AND copy_author.copy_id=:copy_id",
            nativeQuery = true)
    List<Object> findAuthorsByCopy(@Param("copy_id")Integer copyId);

    @Query(value = "SELECT copy.id, copy.book_name FROM copy, copy_author WHERE copy.id=copy_author.copy_id AND copy_author.author_id=:author_id",
            nativeQuery = true)
    List<Object> findCopiesByAuthors(@Param("author_id")Integer authorId);
}
