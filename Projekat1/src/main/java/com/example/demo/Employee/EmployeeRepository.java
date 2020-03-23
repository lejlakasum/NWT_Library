package com.example.demo.Employee;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

    List<Employee> findAll();
    Employee findById(ID id);
    void deleteById(ID id);
}
