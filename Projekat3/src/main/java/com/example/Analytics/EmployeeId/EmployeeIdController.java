package com.example.Analytics.EmployeeId;

import com.example.Analytics.Report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class EmployeeIdController {

    @Autowired
    EmployeeIdRepository employeeIdRepository;

    @RequestMapping("/all")
    public List<EmployeeId> getAll() {
        return employeeIdRepository.findAll();
    }
}
