package com.example.Application.BookType;

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
public class BookTypeService {
    @Autowired
    private BookTypeRepository bookTypeRepository;
    @Autowired
    BookTypeModelAssembler assembler;

    public CollectionModel<EntityModel<BookType>> GetAll() {
        List<EntityModel<BookType>> bookTypes = bookTypeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(bookTypes,
                linkTo(methodOn(BookTypeController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<BookType>> GetById(Integer id) {

        BookType bookType = bookTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("bookType", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(bookType));
    }

    public ResponseEntity<EntityModel<BookType>> Add(BookType newBookType) {
        EntityModel<BookType> entityModel = assembler.toModel(bookTypeRepository.save(newBookType));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<BookType>> Update(BookType newBookType, Integer id) {
        BookType updatedBookType = bookTypeRepository.findById(id)
                .map(bookType -> {
                    bookType.setName(newBookType.getName());
                    bookType.setLatencyFee(newBookType.getLatencyFee());
                    bookType.setLibraryReadOnly(newBookType.getLibraryReadOnly());
                    return bookTypeRepository.save(bookType);
                })
                .orElseGet(() -> {
                    newBookType.setId(id);
                    return bookTypeRepository.save(newBookType);
                });

        EntityModel<BookType> entityModel = assembler.toModel(updatedBookType);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<BookType>> Delete(Integer id) {

        bookTypeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
