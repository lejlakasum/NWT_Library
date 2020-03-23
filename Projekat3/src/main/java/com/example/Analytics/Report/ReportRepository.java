package com.example.Analytics.Report;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReportRepository extends CrudRepository<Report, Integer> {

    public List<Report> findAll();
    Report findById(ID id);
    void deleteById(ID id);
}
