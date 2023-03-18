# Spring Cloud Sleuth in a Monolith Application
## 1. Overview
In this tutorial, we created a simple Spring Cloud Sleuth demonstration – a powerful tool for enhancing logs in any application, especially in a system of multiple services.

And for this write-up, we're going to focus on using Sleuth in a monolith application, not across microservices.

We've all had the unfortunate experience of trying to diagnose a problem with a scheduled task, a multi-threaded operation, or a complex web request. Often, even when there is logging, it is hard to tell what actions need to be correlated together to create a single request.

This can make diagnosing a complex action very difficult or even impossible. Often resulting in solutions like passing a unique id to each method in the request to identify the logs.

In comes Sleuth. This library makes it possible to identify logs pertaining to a specific job, thread, or request. Sleuth integrates effortlessly with logging frameworks like Logback and SLF4J to add unique identifiers that help track and diagnose issues using logs.

## 2.Setup
We'll start by creating a Spring Boot web project in our favourite IDE and adding this dependency to our pom.xml file:
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```
Our application runs with Spring Boot and the parent pom provides versions for each entry. The latest version of this dependency can be found here: spring-cloud-starter-sleuth.

Additionally, let's add an application name to instruct Sleuth to identify this application's logs.

In our application.properties file add this line:
```
spring.application.name=Sleuth Tutorial
```
## 3. Sleuth Configurations
Sleuth is capable of enhancing logs in many situations. Starting with version 2.0.0, Spring Cloud Sleuth uses Brave as the tracing library that adds unique ids to each web request that enters our application. Furthermore, the Spring team has added support for sharing these ids across thread boundaries.

Traces can be thought of like a single request or job that is triggered in an application. All the various steps in that request, even across application and thread boundaries, will have the same traceId.

Spans, on the other hand, can be thought of as sections of a job or request. A single trace can be composed of multiple spans each correlating to a specific step or section of the request. Using trace and span ids we can pinpoint exactly when and where our application is as it processes a request. Making reading our logs much easier.

In our examples, we will explore these capabilities in a single application.
