package com;

import com.SystemEvent.SystemEventImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.IOException;


public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder
                .forPort(9090)
                .addService(new SystemEventImpl())
                .build();

        server.start();
        System.out.println("Server started at port: " + server.getPort());
        server.awaitTermination();
    }
}
