package com.example.systemevents;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class SystemEventImpl extends  SystemEventServiceGrpc.SystemEventServiceImplBase {

    final SystemEventRepository systemEventRepository;

    public SystemEventImpl(SystemEventRepository systemEventRepository) {
        this.systemEventRepository = systemEventRepository;
    }

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
            systemEventRepository.save(event);
            System.out.println(request.getAction());
            System.out.println(request.getMicroservice());
            System.out.println(request.getResourceName());

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
