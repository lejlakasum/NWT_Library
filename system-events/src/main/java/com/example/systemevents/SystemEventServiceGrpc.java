package com.example.systemevents;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: SystemEvents.proto")
public final class SystemEventServiceGrpc {

  private SystemEventServiceGrpc() {}

  public static final String SERVICE_NAME = "SystemEventService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<SystemEvents.SystemEventRequest,
      SystemEvents.SystemEventResponse> getLogSystemEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "logSystemEvent",
      requestType = SystemEvents.SystemEventRequest.class,
      responseType = SystemEvents.SystemEventResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<SystemEvents.SystemEventRequest,
      SystemEvents.SystemEventResponse> getLogSystemEventMethod() {
    io.grpc.MethodDescriptor<SystemEvents.SystemEventRequest, SystemEvents.SystemEventResponse> getLogSystemEventMethod;
    if ((getLogSystemEventMethod = SystemEventServiceGrpc.getLogSystemEventMethod) == null) {
      synchronized (SystemEventServiceGrpc.class) {
        if ((getLogSystemEventMethod = SystemEventServiceGrpc.getLogSystemEventMethod) == null) {
          SystemEventServiceGrpc.getLogSystemEventMethod = getLogSystemEventMethod = 
              io.grpc.MethodDescriptor.<SystemEvents.SystemEventRequest, SystemEvents.SystemEventResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "SystemEventService", "logSystemEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SystemEvents.SystemEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SystemEvents.SystemEventResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SystemEventServiceMethodDescriptorSupplier("logSystemEvent"))
                  .build();
          }
        }
     }
     return getLogSystemEventMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SystemEventServiceStub newStub(io.grpc.Channel channel) {
    return new SystemEventServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SystemEventServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SystemEventServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SystemEventServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SystemEventServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SystemEventServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void logSystemEvent(SystemEvents.SystemEventRequest request,
                               io.grpc.stub.StreamObserver<SystemEvents.SystemEventResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLogSystemEventMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLogSystemEventMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                SystemEvents.SystemEventRequest,
                SystemEvents.SystemEventResponse>(
                  this, METHODID_LOG_SYSTEM_EVENT)))
          .build();
    }
  }

  /**
   */
  public static final class SystemEventServiceStub extends io.grpc.stub.AbstractStub<SystemEventServiceStub> {
    private SystemEventServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SystemEventServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemEventServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SystemEventServiceStub(channel, callOptions);
    }

    /**
     */
    public void logSystemEvent(SystemEvents.SystemEventRequest request,
                               io.grpc.stub.StreamObserver<SystemEvents.SystemEventResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogSystemEventMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SystemEventServiceBlockingStub extends io.grpc.stub.AbstractStub<SystemEventServiceBlockingStub> {
    private SystemEventServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SystemEventServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemEventServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SystemEventServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public SystemEvents.SystemEventResponse logSystemEvent(SystemEvents.SystemEventRequest request) {
      return blockingUnaryCall(
          getChannel(), getLogSystemEventMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SystemEventServiceFutureStub extends io.grpc.stub.AbstractStub<SystemEventServiceFutureStub> {
    private SystemEventServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SystemEventServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemEventServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SystemEventServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<SystemEvents.SystemEventResponse> logSystemEvent(
        SystemEvents.SystemEventRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getLogSystemEventMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOG_SYSTEM_EVENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SystemEventServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SystemEventServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOG_SYSTEM_EVENT:
          serviceImpl.logSystemEvent((SystemEvents.SystemEventRequest) request,
              (io.grpc.stub.StreamObserver<SystemEvents.SystemEventResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SystemEventServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SystemEventServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return SystemEvents.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SystemEventService");
    }
  }

  private static final class SystemEventServiceFileDescriptorSupplier
      extends SystemEventServiceBaseDescriptorSupplier {
    SystemEventServiceFileDescriptorSupplier() {}
  }

  private static final class SystemEventServiceMethodDescriptorSupplier
      extends SystemEventServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SystemEventServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SystemEventServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SystemEventServiceFileDescriptorSupplier())
              .addMethod(getLogSystemEventMethod())
              .build();
        }
      }
    }
    return result;
  }
}
