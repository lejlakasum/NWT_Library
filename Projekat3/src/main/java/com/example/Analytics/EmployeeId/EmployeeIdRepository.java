package com.example.Analytics.EmployeeId;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface EmployeeIdRepository extends CrudRepository<EmployeeId, Integer> {

    public List<EmployeeId> findAll();
    EmployeeId findById(ID id);
    void deleteById(ID id);
}
