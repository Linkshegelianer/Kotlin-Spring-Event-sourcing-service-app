# Event sourcing service cluster

[![Kotlin CI](https://github.com/Linkshegelianer/Kotlin-Spring-Event-sourcing-service-app/workflows/Kotlin%20CI/badge.svg)](https://github.com/Linkshegelianer/Kotlin-Spring-Event-sourcing-service-app/actions/workflows/kotlin-ci.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/dd39cac5bc0070f0ee32/maintainability)](https://codeclimate.com/github/Linkshegelianer/Kotlin-Spring-Event-sourcing-service-app/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/dd39cac5bc0070f0ee32/test_coverage)](https://codeclimate.com/github/Linkshegelianer/Kotlin-Spring-Event-sourcing-service-app/test_coverage)

This repository contains study project for distributed scalable service cluster that is based on the event sourcing pattern. 

`Event Sourcing` is a persistence approach based on storing a stream of events instead of the current state of an object.

- `Events` from event sourcing pattern are messages informing about the change in the state of the object.
- `RabbitMQListener` parses received String to ObjectActionMessage and sends it to `Service`.
- `Service` operates the state of the object and sends action to `Actor`.
- `Actor` processes the messages in one thread via event handlers.
- `Controller` gets the current state of the classes.

```
                    +------------------+     +----------------+
                    | RabbitMQListener |◄----| RabbitMQConfig |          
                    +------------------+     +----------------+
                             | 
                             ▼
                    +------------------+    +-----------------+
                    |   Class Service  |---►|    Controller   |
                    +------------------+    +-----------------+
                              ▲
                              | 
                              ▼
+----------------+    +------------------+     +----------------+
| Event Sourcing |---►|    Class Actor   |◄----|   AkkaConfig   |        
|   Properties   |    +------------------+     +----------------+
+----------------+                             
```

##  Technologies:
1. Kotlin + Gradle 8.2
2. Spring Boot 
3. Akka
