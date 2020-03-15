package com.example.Application.City;


import com.example.Application.Country.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CityRepository extends CrudRepository<City, Integer> {

    List<City> findAll();
}
