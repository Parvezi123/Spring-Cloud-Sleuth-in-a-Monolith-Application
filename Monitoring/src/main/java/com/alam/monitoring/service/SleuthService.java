package com.alam.monitoring.service;

import brave.Span;
import brave.Tracer;
import brave.Tracer.SpanInScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SleuthService {

    private final Tracer tracer;

    public SleuthService(Tracer tracer) {
        this.tracer = tracer;
    }
    public void doSomeWorkSameSpan() throws InterruptedException {
        Thread.sleep(1000L);
        log.info("SleuthService-doSomeWorkSameSpan-Doing some work");
    }

    public void doSomeWorkNewSpan() throws InterruptedException {
        log.info("From - Original New Span");

        Span newSpan = tracer.nextSpan().name("New Span").start();
        log.info("New Span Initialized");
        try (SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            Thread.sleep(1000L);
            log.info("I'm in new Span  - Service");
        }
        finally {
            newSpan.finish();
        }
    }

    @Async
    public void asyncMethod() throws InterruptedException {
        log.info("Start Async Method");
        Thread.sleep(1000L);
        log.info("End Async Method");
    }
}
