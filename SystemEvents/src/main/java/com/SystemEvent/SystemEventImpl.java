package com.SystemEvent;

import com.grpc.SystemEventServiceGrpc;
import com.grpc.SystemEvents;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemEventImpl extends SystemEventServiceGrpc.SystemEventServiceImplBase {

    @Autowired
    private SystemEventRepository eventRepository;

    @Override
    public void logSystemEvent(SystemEvents.SystemEventRequest request, StreamObserver<SystemEvents.SystemEventResponse> responseObserver) {

        SystemEvent event = new SystemEvent(
                0,
                request.getEventTimeStamp(),
                request.getMicroservice(),
                request.getUser(),
                request.getAction(),
                request.getResourceName(),
                request.getSuccess(),
                request.getResponseCode()
        );

        Boolean success = true;
        try {
            eventRepository.save(event);
            System.out.println(request.getAction());
        }
        catch (Exception ex) {
            success = false;
        }


        SystemEvents.SystemEventResponse response = SystemEvents.SystemEventResponse.newBuilder()
                .setSuccess(success)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
