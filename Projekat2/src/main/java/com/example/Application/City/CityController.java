package com.example.Application.City;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @RequestMapping("/")
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
