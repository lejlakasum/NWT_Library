package com.example.Application.Genre;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface GenreRepository extends CrudRepository<Genre, Integer> {

    List<Genre> findAll();

    Genre findById(ID id);

    void deleteById(ID id);
}
