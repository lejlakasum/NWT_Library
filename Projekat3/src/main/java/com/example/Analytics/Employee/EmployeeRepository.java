package com.example.Analytics.Employee;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public List<Employee> findAll();
    Employee findById(ID id);
    void deleteById(ID id);
}
