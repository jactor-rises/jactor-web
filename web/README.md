# jactor-web #

[![continuous integration](https://github.com/jactor-rises/jactor-modules/actions/workflows/ci.yaml/badge.svg)](https://github.com/jactor-rises/jactor-modules/actions/workflows/ci.yaml)
[![verify pull request](https://github.com/jactor-rises/jactor-modules/actions/workflows/pr.yaml/badge.svg)](https://github.com/jactor-rises/jactor-modules/actions/workflows/pr.yaml)

### What is this repository for?

The main purpose is to learn about programming microservices using another microservice for persistence.

This project is consisted of a microservice for web content which use another microservice in order to persist its data through a REST api.

### Set up

* a spring-boot application is created when building (`mvn install`)
    * `jactor-web` which is a web application on apache tomcat and which is dependent
      on [jactor-persistence](https://github.com/jactor-rises/jactor-persistence) to handle persistence
    * also start `jactor-persistence` in order to have a working persistence layer for the application.
* after started `jactor-web`, point a browser to [http://localhost:8080/jactor-web](http://localhost:8080/jactor-web)

### Disclaimer

This application is a playground and is made to demonstrate the working of `jactor-persistence` and to gain expertise in microservices and kotlin
programming language. It is therefore simple and not complete. And I just want to mention: I am not a front-end programmer and is only present for
interactive use of jactor-persistence

* This microservice is tested using `jactor-persistence`
* `Blog`s and `GuestBook`s is only persisted and present in `jactor-persistence` and tested through integration tests there.
* `User` (with `Person` and `Address`) is integrated with communications from `jactor-web`.
    * `jactor-web` only do a "read only" access of the persistence, and do not do other CRUD operations.

### Some technologies used on jactor-web

* [spring-boot 2.6.x](https://spring.io/projects/spring-boot)
* [kotlin](https://kotlinlang.org)
* [junit 5.x](https://junit.org/junit5/)
* [assertj](https://assertj.github.io/doc/)
* [mockito](http://site.mockito.org)
* [thymeleaf](https://www.thymeleaf.org)
