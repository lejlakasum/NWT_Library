package com.example.Analytics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ExampleRepository extends CrudRepository<Example, Integer> {

    public List<Example> findAll();
}
