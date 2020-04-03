package com.example.demo.Role;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role,Integer> {

    List<Role> findAll();
    Role findById(ID id);
    void deleteById(ID id);
}
