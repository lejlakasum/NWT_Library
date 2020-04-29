package com.SystemEvent;

import javax.persistence.*;

@Entity
@Table(name = "system_event")
public class SystemEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "time")
    private String eventTimeStamp;

    private String microservice;

    private String user;

    private String action;

    @JoinColumn(name = "resource")
    private String resourceName;

    private Boolean success;

    @JoinColumn(name = "response_code")
    private Integer responseCode;

    public SystemEvent() {
    }

    public SystemEvent(Integer id, String eventTimeStamp, String microservice, String user, String action, String resourceName, Boolean success, Integer responseCode) {
        this.id = id;
        this.eventTimeStamp = eventTimeStamp;
        this.microservice = microservice;
        this.user = user;
        this.action = action;
        this.resourceName = resourceName;
        this.success = success;
        this.responseCode = responseCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventTimeStamp() {
        return eventTimeStamp;
    }

    public void setEventTimeStamp(String eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }

    public String getMicroservice() {
        return microservice;
    }

    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}