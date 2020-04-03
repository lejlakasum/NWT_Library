package com.example.demo.Fee;


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
@RequestMapping("/fees")
public class FeeController {

    @Autowired
    FeeService feeService;

    @GetMapping()
    public CollectionModel<EntityModel<Fee>> GetAll(){
        try {
            return feeService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Fee>> GetById(@PathVariable Integer id) {
        try {
            return feeService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Fee>> AddFee(@RequestBody Fee newFee) throws URISyntaxException{
        try {
            return feeService.AddFee(newFee);
        }
        catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Fee>> ModifyFee(@RequestBody Fee newFee, @PathVariable Integer id) throws URISyntaxException {
        try {
            return feeService.ModifyFee(newFee,id);

        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Fee>> DeleteFee(@PathVariable Integer id){
        try {
            return feeService.DeleteFee(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("fee",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
