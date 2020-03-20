package com.example.demo.Fee;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FeeRepository extends CrudRepository<Fee,Integer> {

    List<Fee> findAll();
    Fee findById(ID id);
    void deleteById(ID id);
}
