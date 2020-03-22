package com.example.demo.PaidFee;

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
@RequestMapping("/paidfees")
public class PaidFeeController {

    @Autowired
    PaidFeeService paidFeeService;

    @GetMapping()
    public CollectionModel<EntityModel<PaidFee>> GetAll(){
        try {
            return paidFeeService.GetAll();
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PaidFee>> GetById(@PathVariable Integer id) {
        try {
            return paidFeeService.GetById(id);
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<PaidFee>> AddPaidFee(@RequestBody PaidFee newPaidFee) throws URISyntaxException {
        try {
            return paidFeeService.AddPaidFee(newPaidFee);
        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<PaidFee>> ModifyPaidFee(@RequestBody PaidFee newPaidFee, @PathVariable Integer id) throws URISyntaxException {
        try {
            return paidFeeService.ModifyPaidFee(newPaidFee,id);

        }catch (NotFoundException e){
            throw e;
        }catch (ConstraintViolationException e){
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<PaidFee>> DeletePaidFee(@PathVariable Integer id){
        try {
            return paidFeeService.DeletePaidFee(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("paid fee",id);
        }catch (Exception e){
            throw new InternalServerException();
        }
    }
}
