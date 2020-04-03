package com.example.demo.PaidFee;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PaidFeeRepository extends CrudRepository<PaidFee,Integer> {
    List<PaidFee> findAll();
    PaidFee findById(ID id);
    void deleteById(ID id);
}
