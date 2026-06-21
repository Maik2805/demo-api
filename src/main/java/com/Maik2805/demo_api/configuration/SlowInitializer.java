package com.Maik2805.demo_api.configuration;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SlowInitializer {

private static final Logger log =
            LoggerFactory.getLogger(SlowInitializer.class);

    @PostConstruct
    public void init() throws InterruptedException {

        long delay =
                ThreadLocalRandom.current()
                        .nextLong(4, 21);

        log.info("Simulating startup delay of {} seconds", delay);

        Thread.sleep(delay * 1000);

        log.info("Startup delay finished");
    }
}