package com.example.Application.Country;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findAll();

    Country findById(ID id);

    void deleteById(ID id);
}
