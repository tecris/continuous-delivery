## Mini Continuous Delivery with Docker for a basic 2 tier Java EE application

Showcase for a continuous delivery based on following principles:
 - short lived environments (environments built and destroyed for each iteration  / run = mvn verify)
 - immutable environments (environemnts built in automated fashion, no manual jobs / touches, hands off :) )
 - infrastructure as code 

### Continuous Delivery life-cycle
 - Build war artifact
 - Upload war to maven repository
 - Start back-end database (mysql/postgresql) container and apply database scripts with flyway
 - Build web application docker image (with latest war artifact) and start container
 - Execute integration test
 - Stop and remove web and database containers
 - INVESTIGATE: Create image from db container

### Techno stack
 1. Java JEE (Web Application)
 2. Wildfly 9.0.2
 3. MySQL 5.7 or PostgreSQL 9.4
 4. Docker 1.9
 5. Docker Compose 1.5
 6. Maven 3.3
 7. [Jolokia docker maven plugin](https://github.com/rhuss/docker-maven-plugin)
 8. [Flywaydb](http://flywaydb.org/)

### Prerequisites
#### Generate project with jboss forge.
1. Install forge
2. Install AngularJS plugin
 1. start forge shell and execute `addon-install-from-git --url https://github.com/forge/angularjs-addon.git`
3. Generate project:
 1. `runForgeScript.sh buildProject.sh`
 1. Edit src/main/resources/META-INF/persistence.xml set property hibernate.hbm2ddl.auto to none

#### Prepare web and database images.
1. TODO

#### Prepare maven repository image.
1. TODO

### How to run / deploy
1. Start web(wildfly) and database(mysql) containers
 1. `docker-compose --x-networking up -d`
1. Deploy database scripts
 1. `mvn compile flyway:migrate`
1. Deploy application
 1. `mvn clean wildfly:deploy -Dwildfly.username=admin -Dwildfly.password=1admin!`
 
Go to http://localhost:8080/planets

### Continuous delivery
1. With wildfly and MySQL
 * `mvn verify -Pcd-mysql`
1. With wildfly and PostgreSQL
 * `mvn verify -Pcd-postgres`
