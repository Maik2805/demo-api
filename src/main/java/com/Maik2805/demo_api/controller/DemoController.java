package com.Maik2805.demo_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController()
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/flux")
    public Flux<String> greetings() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> "\nHello, World! #" + (i + 1))
                .take(5);
    }

    @GetMapping("/mono")
    public Mono<String> mono() {
        return Mono.just("Hello, World!").map(String::toUpperCase) ;
    }

    @GetMapping("/cluster-information")
    public Mono<String> info() {
        return Mono.fromCallable(() -> {
            String podName = System.getenv("HOSTNAME");
            String namespace = System.getenv("KUBERNETES_NAMESPACE");
            String serviceAccount = System.getenv("SERVICE_ACCOUNT");
            String nodeName = System.getenv("NODE_NAME");
            String podIp = System.getenv("POD_IP");

            return String.format(
                "Pod: %s | Namespace: %s | ServiceAccount: %s | Node: %s | PodIP: %s",
                podName != null ? podName : "unknown",
                namespace != null ? namespace : "unknown",
                serviceAccount != null ? serviceAccount : "unknown",
                nodeName != null ? nodeName : "unknown",
                podIp != null ? podIp : "unknown"
            );
        });
        
    }
}
