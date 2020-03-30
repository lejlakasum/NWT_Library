package com.example.Reservation.Reservation;

import com.example.Reservation.ExceptionClass.BadRequestException;
import com.example.Reservation.ExceptionClass.InternalServerException;
import com.example.Reservation.ExceptionClass.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping()
    public CollectionModel<EntityModel<BookMemberReservation>> GetAll(){
        try {
            return reservationService.GetAll();
        }
        catch (Exception exception) {
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<BookMemberReservation>> GetById(@PathVariable Integer id) {
        try{
            return reservationService.GetById(id);
        }
        catch (NotFoundException exeption) {
            throw exeption;
        }
        catch (Exception exeption) {
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<BookMemberReservation>> Add(@RequestBody BookMemberReservation newBookMemberReservation) throws URISyntaxException {
        try {
            return reservationService.Add(newBookMemberReservation);
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }

    @PostMapping("/{id}")
    ResponseEntity<EntityModel<BookMemberReservation>> Update(@RequestBody BookMemberReservation newBookMemberReservation, @PathVariable Integer id) throws URISyntaxException {
        try {
            return reservationService.Update(newBookMemberReservation, id);
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<BookMemberReservation>> Delete(@PathVariable Integer id) {
        try {
            return reservationService.Delete(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("reservation", id);
        }
        catch (Exception exception){
            throw new InternalServerException();
        }
    }
}
