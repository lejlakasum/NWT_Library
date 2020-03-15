package com.example.demo.proba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proba")
public class probaContoller {
    @Autowired
    probaRepository probaRepository;
    @RequestMapping("/all")
    public List<proba> GetAll(){
        return probaRepository.findAll();

    }
}
