package com.example.demo.Settings;

import com.example.demo.Employee.EmployeeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SettingsModelAssembler implements RepresentationModelAssembler<Settings, EntityModel<Settings>> {

    @Override
    public EntityModel<Settings> toModel(Settings settings){
        if(!settings.getEmployee().hasLinks()){
            settings.getEmployee()
                    .add(linkTo(methodOn(EmployeeController.class)
                            .GetById(settings.getEmployee().getId()))
                            .withSelfRel());

            settings.getEmployee()
                    .add(linkTo(methodOn(EmployeeController.class)
                            .GetAll())
                            .withRel("settings"));
        }
        return new EntityModel<>(settings,
                linkTo(methodOn(SettingsController.class).GetById(settings.getId())).withSelfRel(),
                linkTo(methodOn(SettingsController.class).GetAll()).withRel("settings"));
    }
}
