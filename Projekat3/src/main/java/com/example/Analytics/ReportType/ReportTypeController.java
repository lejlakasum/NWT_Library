package com.example.Analytics.ReportType;

import com.example.Analytics.ExceptionClass.NotFoundException;
import com.example.Analytics.ExceptionClass.BadRequestException;
import com.example.Analytics.ExceptionClass.InternalServerException;
import com.example.grpc.SystemEvents;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
@RequestMapping("/reportType")
public class ReportTypeController {

    @Autowired
    ReportTypeService reportTypeService;

    @GetMapping()
    public CollectionModel<EntityModel<ReportType>> GetAll() {
        try {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "GET", "report type", true, 200);
            return reportTypeService.GetAll();
        }
        catch (Exception exeption) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "GET", "report type", false, 500);
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReportType>> GetById(@PathVariable Integer id){
        try{
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "GET", "report type", true, 200);
            return reportTypeService.GetById(id);
        }
        catch (NotFoundException exeption) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "GET", "report type", false, 404);
            throw exeption;
        }
        catch (Exception exeption) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "GET", "report type", false, 500);
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<ReportType>> Add(@RequestBody ReportType newReportType) throws URISyntaxException {
        try {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "POST", "report type", true, 201);
            return reportTypeService.Add(newReportType);
        }
        catch (ConstraintViolationException exception) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "POST", "report type", false, 404);
            throw new BadRequestException( exception.getMessage());
        }
        catch (NotFoundException exception) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "POST", "report type", false, 404);
            throw exception;
        }
        catch (Exception exception){
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "POST", "report type", false, 500);
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<ReportType>> Update(@RequestBody ReportType newReportType, @PathVariable Integer id) throws URISyntaxException {
        try {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "PUT", "report type", true, 204);
            return reportTypeService.Update(newReportType, id);
        }
        catch (ConstraintViolationException exception) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "PUT", "report type", false, 400);
            throw new BadRequestException( exception.getMessage());
        }
        catch (NotFoundException exception) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "PUT", "report type", false, 400);
            throw exception;
        }
        catch (Exception exception){
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "PUT", "report type", false, 500);
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<ReportType>> Delete(@PathVariable Integer id) {
        try {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "DELETE", "report type", true, 204);
            return reportTypeService.Delete(id);
        }
        catch (EmptyResultDataAccessException exception) {
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "DELETE", "report type", false, 404);
            throw new NotFoundException("report", id);
        }
        catch (Exception exception){
            LogActivity(new Date().toString(),"Analytics Microservice", "Default", "DELETE", "report type", false, 500);
            throw new InternalServerException();
        }
    }

    private void LogActivity(String eventTimeStamp, String microservice, String user,
                             String action, String resourceName, Boolean success, Integer responseCode) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                    .usePlaintext()
                    .build();

            com.example.grpc.SystemEventServiceGrpc.SystemEventServiceBlockingStub stub =
                    com.example.grpc.SystemEventServiceGrpc.newBlockingStub(channel);

            SystemEvents.SystemEventResponse response = stub.logSystemEvent(SystemEvents.SystemEventRequest.newBuilder()
                    .setEventTimeStamp(eventTimeStamp)
                    .setMicroservice(microservice)
                    .setUser(user)
                    .setAction(action)
                    .setResourceName(resourceName)
                    .setSuccess(success)
                    .setResponseCode(responseCode)
                    .build());

            channel.shutdown();
        }
        catch (Exception ex) {

        }
    }
}
