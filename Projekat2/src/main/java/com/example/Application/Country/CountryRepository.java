package com.example.Application.Country;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findAll();
}
