MiniAccounting
==============

This project is an attempt to create a [GnuCash](https://www.gnucash.org) like system, i.e. it adopts the double entry [bookkeeping accounting system](https://en.wikipedia.org/wiki/Double-entry_bookkeeping_system), for the management of personal finances on-line.

The project is still in the very beginning and it is not yet useful to be used in real life.

Also, I use this project as way to experiment with the best practices of systems architecture. For example, I am learning and trying to apply the concepts of [hexagonal architecture](http://alistair.cockburn.us/Hexagonal+architecture), which has many others names, despite being basically the same thing: [onion architecture](http://jeffreypalermo.com/blog/the-onion-architecture-part-1/), [clean architecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html).

I am also trying to apply the concepts of [DDD - Domain Driven Design](http://dddcommunity.org/learning-ddd/what_is_ddd/) and  [CQRS - Command Query Responsibility Segregation](http://martinfowler.com/bliki/CQRS.html), for example, the concerns of the application's write side (domain logic and transactions) and read side are isolated. Despite using the same source of truth (the same database) the implementation of queries/reports don't share its models with the domain side. In the future it would be interesting to have a non-normalized database solely focused on the read side of the application.

Some technologies adopted on the server side
-----------

- Spring JDBC template: in this project I am avoiding the use of an ORM, Object-Relational Mapping, to see how is the old feeling of working directly with SQL.
- Spring Boot
- Jackson (JSON processor)

Client side
-----------

- The client side for this application is located in another repository: [accounting-client](https://github.com/milanogc/accounting-client).

How to run?
-----------

    mvn compile flyway:migrate
    mvn spring-boot:run
