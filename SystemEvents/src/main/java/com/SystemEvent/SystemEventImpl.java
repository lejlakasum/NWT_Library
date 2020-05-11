package com.SystemEvent;

import com.BeanUtil;
import com.grpc.SystemEventServiceGrpc;
import com.grpc.SystemEvents;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SystemEventImpl extends SystemEventServiceGrpc.SystemEventServiceImplBase {

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
            SystemEventRepository eventRepository = BeanUtil.getBean(SystemEventRepository.class);
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
