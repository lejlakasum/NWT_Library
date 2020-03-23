package com.example.Analytics.ReportType;

import com.example.Analytics.Report.Report;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReportTypeRepository extends CrudRepository<ReportType, Integer> {

    public List<ReportType> findAll();
    ReportType findById(ID id);
    void deleteById(ID id);
}
