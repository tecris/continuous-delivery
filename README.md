## Java Continuous Delivery by Docker

[![Circle CI](https://circleci.com/gh/tecris/continuous-delivery.svg?style=svg)](https://circleci.com/gh/tecris/continuous-delivery)
[![Build Status](https://travis-ci.org/tecris/continuous-delivery.svg?branch=master)](https://travis-ci.org/tecris/continuous-delivery)

Continuous delivery demo that aims to use following principles:
 - immutable environments
 - short lived environments
 - **built and destroyed** with each jenkins run


[**Continuous delivery with Jenkins (on-prem)**](#continuous-delivery-with-jenkins)

[**E2E with Docker Compose and Maven**](#e2e-with-docker-compose-and-maven)

[**Swagger**](#swagger)


### Stack
| *Technology* | *Version* |
| ------------- | ------------- |
| Java | 8 |
| Wildfly | 13.0.0 |
| MySQL | 8.0 |
| Maven | 3.5 |
| Docker | 17.11.0-ce |
| Docker Compose | 1.17.1 |

### Continuous delivery with Jenkins
 - Start jenkins
   - `$ ./startJenkins.sh`
   - login to [jenkins](localhost:8080) (password can be seen in jenkins log when it starts)
   - select 'Install selected plugins'
 - Create jenkins plan
   - edit `./jenkins/data` 
   - `$ cd jenkins && ./createJob.sh localhost 8080 continuous-delivery config.xml`
 - Go to [jenkins job](http://localhost:8080/job/continuous-delivery), build will start in under 2 minutes. Alternatively the build can be started manually -> 'Build Now'

##### Continuous Delivery life-cycle
 - Source code changes pushed to git
 - Jenkins detects changes and starts job
   - Build war/jar artifact
   - Start stack (includes image build for java web app)
   - Apply db schema ([flywaydb.org](flywaydb.org))
   - Execute integration tests
   - Stop stack (includes destroy of web and database containers)


### E2E with docker compose and Maven
  * **Step-by-step**

    ```sh
      $ TAG=dev docker-compose up -d ackris-db ackris-web                     # start web and database containers
      $ mvn clean compile flyway:migrate                                      # deploy database schema
      $ mvn clean verify -Dmaven.test.failure.ignore=false -Dtest.port=8070   # run integration tests
    ```

Use `-Dmaven.buildNumber.doCheck=false` if project contains local changes

Demo: `http://localhost:8070/bookstore`

#### Swagger

   * `$ docker-compose up -d swaggerui`

   * [Swagger UI](https://github.com/swagger-api/swagger-ui) @ `http://localhost:81`



[1]:https://github.com/fabric8io/docker-maven-plugin
[2]:http://flywaydb.org
[3]:https://github.com/tecris/docker/blob/v3.6/nexus/README.md
[4]:https://github.com/tecris/docker/blob/v3.6/nexus/settings.xml
