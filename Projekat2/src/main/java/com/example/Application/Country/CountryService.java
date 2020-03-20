package com.example.Application.Country;

import com.example.Application.ExceptionClasses.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    CountryModelAssembler assembler;

    public CollectionModel<EntityModel<Country>> GetAll() {
        List<EntityModel<Country>> countries = countryRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(countries,
                linkTo(methodOn(CountryController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Country>> GetById(Integer id) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("country", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(country));
    }

    public ResponseEntity<EntityModel<Country>> Add(Country newCountry) {
        EntityModel<Country> entityModel = assembler.toModel(countryRepository.save(newCountry));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Country>> Update(Country newCountry, Integer id) {
        Country updatedCountry = countryRepository.findById(id)
                .map(country -> {
                    country.setName(newCountry.getName());
                    return countryRepository.save(country);
                })
                .orElseGet(() -> {
                    newCountry.setId(id);
                    return countryRepository.save(newCountry);
                });

        EntityModel<Country> entityModel = assembler.toModel(updatedCountry);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Country>> Delete(Integer id) {

        countryRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
