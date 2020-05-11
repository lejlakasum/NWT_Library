package com.example.demo.Role;

import com.example.demo.Exception.BadRequestException;
import com.example.demo.Exception.InternalServerException;
import com.example.demo.Exception.NotFoundException;
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
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping()
    CollectionModel<EntityModel<Role>> GetAll(){
        try {
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "GET", "Role", true, 200);
            return roleService.GetAll();
        }
        catch (Exception ex) {
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "GET", "Role", false, 500);
            throw new InternalServerException();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<EntityModel<Role>> GetById(@PathVariable Integer id) {
        try {
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "GET", "Role", true, 200);
            return roleService.GetById(id);
        }catch (NotFoundException e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "GET", "Role", false, 404);
            throw e;
        }catch (Exception e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "GET", "Role", false, 500);
            throw new InternalServerException();
        }
    }

    @PostMapping()
    ResponseEntity<EntityModel<Role>> AddRole(@RequestBody Role newRole) throws URISyntaxException {
        try {
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "POST", "Role", true, 200);
            return roleService.AddRole(newRole);
        }
        catch (ConstraintViolationException e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "POST", "Role", false, 400);
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "POST", "Role", false, 500);
            throw new InternalServerException();
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Role>> ModifyRole(@RequestBody Role newRole, @PathVariable Integer id) throws URISyntaxException {
        try {
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "PUT", "Role", true, 200);
            return roleService.ModifyRole(newRole,id);

        }catch (ConstraintViolationException e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "PUT", "Role", false, 400);
            throw new BadRequestException(e.getMessage());
        }catch (Exception e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "PUT", "Role", false, 500);
            throw new InternalServerException();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EntityModel<Role>> DeleteRole(@PathVariable Integer id){
        try {
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "DELETE", "Role", true, 200);
            return roleService.DeleteRole(id);
        }catch (EmptyResultDataAccessException e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "DELETE", "Role", false, 404);
            throw new NotFoundException("role",id);
        }catch (Exception e){
            LogActivity(new Date().toString(), "UserMicroservice", "Default", "DELETE", "Role", false, 500);
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
