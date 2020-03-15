package com.example.demo.proba;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface probaRepository extends CrudRepository<proba,Integer> {

    List<proba> findAll();

}
