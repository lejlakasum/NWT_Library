package com.example.demo.Profile;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProfileRepository extends CrudRepository<Profile,Integer> {
    List<Profile> findAll();
    Profile findById(ID id);
    void deleteById(ID id);
    Profile findByUsername(String username);
}
