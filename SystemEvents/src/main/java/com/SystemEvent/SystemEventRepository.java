package com.SystemEvent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SystemEventRepository extends CrudRepository<SystemEvent, Integer> {

    List<SystemEvent> findAll();
}
