package com.example.systemevents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class SystemEventsApplication {

    private static final Logger log = LoggerFactory.getLogger(SystemEventsApplication.class);

    @Autowired
    private static SystemEventRepository systemEventRepository;

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(SystemEventsApplication.class, args);

    }
}
