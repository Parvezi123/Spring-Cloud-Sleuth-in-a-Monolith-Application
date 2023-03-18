package com.alam.monitoring.controller;

import com.alam.monitoring.service.SleuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

@Slf4j
@RestController
public class SleuthController {

    private final SleuthService sleuthService;

    private Executor executor;

    public SleuthController(SleuthService sleuthService) {
        this.sleuthService = sleuthService;
    }

    @GetMapping("/")
    public String helloSleuth() {
        log.info("Hello Sleuth");
        return "success";
    }

    @GetMapping("/same-span")
    public String helloSleuthSameSpan() throws InterruptedException {
        log.info("Same Span");
        sleuthService.doSomeWorkSameSpan();
        return "success: same-span";
    }

    @GetMapping("/new-span")
    public String newSpan() throws InterruptedException {
        log.info("NewSpan - Controller");
        sleuthService.doSomeWorkNewSpan();
        return "New_Span";
    }

    @GetMapping("/new-thread")
    public String newThread() {
        log.info("New Thread");
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            log.info("Inside New Thread - with a New Span");
        };
        executor.execute(runnable);

        log.info("Work Done  - with the Original Span");
        return "Success - New Thread";
    }

    @GetMapping("/async")
    public String sleuthAsync() throws InterruptedException {
        log.info("Before Async Method Call");
        sleuthService.asyncMethod();
        log.info("End Async Method Call");

        return "Success -  Async";
    }
}
