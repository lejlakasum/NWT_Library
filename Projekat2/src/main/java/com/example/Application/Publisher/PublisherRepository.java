package com.example.Application.Publisher;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PublisherRepository extends CrudRepository<Publisher, Integer> {

    List<Publisher> findAll();

    Publisher findById(ID id);

    void deleteById(ID id);
}
