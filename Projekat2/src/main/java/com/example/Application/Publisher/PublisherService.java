package com.example.Application.Publisher;

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
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    PublisherModelAssembler assembler;

    public CollectionModel<EntityModel<Publisher>> GetAll() {
        List<EntityModel<Publisher>> publishers = publisherRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(publishers,
                linkTo(methodOn(PublisherController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Publisher>> GetById(Integer id) {

        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("publisher", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(publisher));
    }

    public ResponseEntity<EntityModel<Publisher>> Add(Publisher newPublisher) {
        EntityModel<Publisher> entityModel = assembler.toModel(publisherRepository.save(newPublisher));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Publisher>> Update(Publisher newPublisher, Integer id) {
        Publisher updatedPublisher = publisherRepository.findById(id)
                .map(publisher -> {
                    publisher.setName(newPublisher.getName());
                    return publisherRepository.save(publisher);
                })
                .orElseGet(() -> {
                    newPublisher.setId(id);
                    return publisherRepository.save(newPublisher);
                });

        EntityModel<Publisher> entityModel = assembler.toModel(updatedPublisher);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Publisher>> Delete(Integer id) {

        publisherRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
