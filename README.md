## Java Continuous Delivery by Docker

[![Circle CI](https://circleci.com/gh/tecris/continuous-delivery.svg?style=svg)](https://circleci.com/gh/tecris/continuous-delivery)
[![Build Status](https://travis-ci.org/tecris/continuous-delivery.svg?branch=master)](https://travis-ci.org/tecris/continuous-delivery)

Continuous delivery demo that aims to use following principles:
 - immutable environments
 - short lived environments 
 - **built and destroyed** every time jenkins builds project


[**Continuous delivery with Jenkins (on-prem)**](#continuous-delivery-with-jenkins)

[**E2E with Docker Compose and Maven**](#e2e-with-docker-compose-and-maven)

[**Swagger**](#swagger)


### Stack
| *Technology* | *Version* |
| ------------- | ------------- |
| Java | 8 |
| Wildfly | 10.0.0 |
| MySQL | 5.7 |
| Maven | 3.3 |
| Docker | 1.10 |
| Docker Compose | 1.6 |

### Continuous delivery with Jenkins
 - Start [continuous delivery stack](https://github.com/tecris/infrastructure-as-code)
 - Create jenkins plan
   - `$ cd jenkins && ./createJob.sh localhost bookstore config.xml`
 - http://localhost:8088/job/continuous-delivery -> 'Build Now'

##### Continuous Delivery life-cycle
 - Source code changes pushed to git
 - Jenkins detects changes and starts job
   - Build war/jar artifact
   - Upload artifact to maven repository
   - Start vanilla MySQL
   - Apply db schema ([flywaydb.org](flywaydb.org))
   - Create web application image with latest war artifact
   - Push web application image to private docker registry
   - Start web application container
   - Execute integration tests
   - Stop and remove web and database containers

 
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
