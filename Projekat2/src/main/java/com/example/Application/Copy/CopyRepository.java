package com.example.Application.Copy;

import com.example.Application.Genre.Genre;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CopyRepository extends CrudRepository<Copy, Integer> {

    List<Copy> findAll();

    Copy findById(ID id);

    void deleteById(ID id);
}
