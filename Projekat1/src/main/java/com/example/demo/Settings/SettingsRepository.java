package com.example.demo.Settings;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SettingsRepository extends CrudRepository<Settings,Integer> {

    List<Settings> findAll();
    Settings findById(ID id);
    void deleteById(ID id);
}

