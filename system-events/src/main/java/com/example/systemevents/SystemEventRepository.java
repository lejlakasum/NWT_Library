package com.example.systemevents;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

public interface SystemEventRepository extends CrudRepository<SystemEvent, Integer> {
}
