package com.example.demo.Settings;

import com.example.demo.Employee.Employee;
import com.example.demo.Employee.EmployeeRepository;
import com.example.demo.Exception.NotFoundException;
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
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    SettingsModelAssembler settingsAssembler;

    @Autowired
    EmployeeRepository employeeRepository;

    public CollectionModel<EntityModel<Settings>> GetAll() {
        List<EntityModel<Settings>> settings=settingsRepository.findAll().stream()
                .map(settingsAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(settings,
                linkTo(methodOn(SettingsController.class).GetAll()).withSelfRel());
    }

    public ResponseEntity<EntityModel<Settings>> GetById(Integer id){

        Settings settings=settingsRepository.findById(id)
                .orElseThrow(()->new NotFoundException("settings",id));
        return ResponseEntity
                .ok()
                .body(settingsAssembler.toModel(settings));
    }

    public ResponseEntity<EntityModel<Settings>> AddSettings(Settings newSettings){
        Integer employeeId=newSettings.getEmployee().getId();
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(()->new NotFoundException("employee", employeeId));
        newSettings.setEmployee(employee);
        newSettings.setName(newSettings.getName());
        newSettings.setValue(newSettings.getValue());
        newSettings.setDescription(newSettings.getDescription());

        EntityModel<Settings> entityModel=settingsAssembler.toModel(settingsRepository.save(newSettings));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    public ResponseEntity<EntityModel<Settings>> ModifySettings(Settings newSettings,Integer id){
        Integer employeeId=newSettings.getEmployee().getId();
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(()->new NotFoundException("employee",employeeId));

        Settings modifiedsettings=settingsRepository.findById(id)
                .map(settings -> {
                    settings.setEmployee(employee);
                    settings.setName(newSettings.getName());
                    settings.setValue(newSettings.getValue());
                    settings.setDescription(newSettings.getDescription());
                    return settingsRepository.save(settings);
                })
                .orElseThrow(()->new NotFoundException("settings",id));

        EntityModel<Settings> entityModel=settingsAssembler.toModel(modifiedsettings);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Settings>> DeleteSettings(Integer id){
        settingsRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
