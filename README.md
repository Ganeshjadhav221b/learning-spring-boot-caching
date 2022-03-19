# learning-spring-boot-caching
In this project we'll see 2 ways of caching in spring boot.
<br>
We'll assume this is used for storing student details.
<br>
Using in-memory H2 DB(needs only dependency & properties in application.properties to be specified)
<br>
We run redis in docker container, commands for same can be found in resource package -> docker-commands.


Two ways include- 
1. caching annotation
2.  redisTemplate

This is just introductory project & there are exhaustive opertaions that can be found in the docs-
https://spring.io/guides/gs/caching/
https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html
