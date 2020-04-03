package com.example.demo.Settings;

import com.example.demo.Exception.BadRequestException;
import com.example.demo.Exception.InternalServerException;
import com.example.demo.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/settingsbase")
public class SettingsController {
    @Autowired
    SettingsService settingsService;

    @GetMapping()
    CollectionModel<EntityModel<Settings>> GetAll(){
        try {
            return settingsService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Settings>> GetById(@PathVariable Integer id) {
        try {
            return settingsService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Settings>> AddSettings(@RequestBody Settings newSettings) throws URISyntaxException {
        try {
            return settingsService.AddSettings(newSettings);
        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Settings>> ModifySettings(@RequestBody Settings newSettings, @PathVariable Integer id) throws URISyntaxException {
        try {
            return settingsService.ModifySettings(newSettings,id);

        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Settings>> DeleteSettings(@PathVariable Integer id){
        try {
            return settingsService.DeleteSettings(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("settings",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
