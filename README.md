## Java Continuous Delivery by Docker

Showcase for a continuous delivery based on following principles:
 - short lived environments (built and destroyed for each iteration  / run = mvn verify)
 - immutable environments (built in automated fashion, no manual jobs / touches, hands off :) )
 - infrastructure as code

### Techno stack
#### Application
 1. Java JEE (Web Application)
 1. Wildfly 9.0.2
 1. MySQL 5.7 or PostgreSQL 9.5

### Prerequisites
#### What is required to run this project:
 - Linux with:
  - Java 8
  - Maven 3.3
  - Docker 1.9
    - [Configured to run without sudo ... ](https://docs.docker.com/engine/installation/ubuntulinux/#create-a-docker-group)
  - Docker Compose 1.5

### Continuous delivery with Jenkins
 - Enable Docker Remote API edit `/etc/default/docker` and update the DOCKER_OPTS:
  * `DOCKER_OPTS='-H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock ...'`
 - `# docker-compose -f continuous-delivery.yml up -d`
 - `# cd jenkins`
 - `# ./createJob.sh localhost planets config.xml`
 - http://localhost:8088/job/planets -> 'Build Now'

##### Continuous Delivery life-cycle
 - Build war artifact
 - Upload artifact to maven repository
 - Create database image with latest database scripts (flyway)
 - Push database image to private docker registry
 - Create web application image with latest war artifact
 - Push web application image to private docker registry
 - Start database container
 - Start web application container
 - Execute integration tests
 - Stop and remove web and database containers

### Run app & integration tests with [Jolokia][1] docker maven plugin
 1. With Wildfly and MySQL
  * `# mvn -Pcd-mysql clean integration-test`
 1. With Wildfly and PostgreSQL
  * `# mvn -Pcd-postgres clean integration-test`

 *  `integration-test` will:
  - stop and remove web and db containers (if any)
  - build artifact
  - build web image,
  - start db container
  - apply database scripts
  - start web container
  - run integration tests.
 *  `verify` will do `integration-test` plus:
  - stop and remove web container
  - stop and remove db container
 
Use `-Dmaven.buildNumber.doCheck=false` if project contains local changes

### Run app & integration tests with Docker Compose and maven

For following to work:
 - Follow [instructions][3] to add jboss repository (as proxy repository) to nexus
 - Maven configuration. The project depends on [settings.xml][4]. Options to integrate:
  - Copy or merge [settings.xml][4] to your user settings (usually ~/.m2/settings.xml)
  - If above not an option, use `-s` flag to specify alternate user settings file (`mvn -s /path/to/settings.xml ...`)

#### With Wildfly & MySQL
1. Start web(wildfly) and database(mysql) containers
 1. `# docker-compose --x-networking up -d`
1. Deploy database scripts
 1. `# mvn -Pdb-mysql clean compile flyway:migrate`
1. Deploy application
 1. `# mvn clean wildfly:deploy`
1. Run integration tests
 1. `# mvn  -Prun-it clean integration-test`
 
#### With Wildfly & PostgreSQL
1. Start web(wildfly) and database(postgresql) containers
 1. `# docker-compose --x-networking -f wildfly-postgresql.yml up -d`
1. Deploy database scripts
 1. `# mvn  -Pdb-postgres clean compile flyway:migrate`
1. Deploy application
 1. `# mvn clean wildfly:deploy -Ddatasource.name=PostgresDS`
1. Run integration tetss
 1. `# mvn -Prun-it clean integration-test`

Go to http://localhost:8080/planet

[1]:https://github.com/rhuss/docker-maven-plugin
[2]:http://flywaydb.org
[3]:https://github.com/tecris/docker/blob/v3.6/nexus/README.md
[4]:https://github.com/tecris/docker/blob/v3.6/nexus/settings.xml
