## Mini Continuous Delivery with Docker for a basic 2 tier Java EE application

Showcase for a continuous delivery based on following principles:
 - short lived environments (built and destroyed for each iteration  / run = mvn verify)
 - immutable environments (built in automated fashion, no manual jobs / touches, hands off :) )
 - infrastructure as code 

### Continuous Delivery life-cycle
 - Build war artifact
 - Start database (mysql/postgresql) container and apply database scripts with flyway
 - Build web application docker image (with latest war artifact) and start container
 - Execute integration test
 - Stop and remove web and database containers
 - INVESTIGATE: Create image from db container
 - INVESTIGATE: Push images to a registry

### Techno stack
 1. Java JEE (Web Application)
 2. Wildfly 9.0.2
 3. MySQL 5.7
 4. PostgreSQL 9.4
 5. Docker 1.9
 6. Docker Compose 1.5
 7. [Jolokia][1] docker maven plugin
 8. [Flywaydb][2]

### Prerequisites
#### What is required to run this project:
 - Linux with:
  - Java 8
  - Maven 3.3
  - Docker 1.9
  - Docker Compose 1.5

#### Project preparation:
 - The project expects base images present, following script will create them:
  - `#  ./pre_requisites.sh`

### Continuous delivery
1. With Wildfly and MySQL
 * `# mvn clean verify -Pcd-mysql`
1. With Wildfly and PostgreSQL
 * `# mvn clean verify -Pcd-postgres`
 
 Use `-Dmaven.buildNumber.doCheck=false` if project contains local changes

 Continuous delivery life cycle is defined in `cd-mysql` & `cd-postgres` maven profiles.


### How to run / deploy

For following to work:
 - Follow [instructions][3] to add jboss repository (as proxy repository) to nexus
 - Maven configuration. The project depends on [settings.xml][4]. Options to integrate:
  - Copy or merge [settings.xml][4] to your user settings (usually ~/.m2/settings.xml)
  - If above not an option, use `-s` flag to specify alternate user settings file (`mvn -s /path/to/settings.xml ...`)
#### With Wildfly & MySQL
1. Start web(wildfly) and database(mysql) containers
 1. `# docker-compose --x-networking up -d`
1. Deploy database scripts
 1. `# mvn clean compile flyway:migrate -Pdb-mysql`
1. Deploy application
 1. `# mvn clean wildfly:deploy`
1. Run integration tetss
 1. `# mvn clean integration-test -Prun-it`
 
#### With Wildfly & PostgreSQL
1. Start web(wildfly) and database(postgresql) containers
 1. `# docker-compose --x-networking -f wildfly-postgres.yml up -d`
1. Deploy database scripts
 1. `# mvn clean compile flyway:migrate -Pdb-postgres`
1. Deploy application
 1. `# mvn clean wildfly:deploy -Ddatasource.name=PostgresDS`
1. Run integration tetss
 1. `# mvn clean integration-test -Prun-it`

Go to http://localhost:8080/planets

[1]:https://github.com/rhuss/docker-maven-plugin
[2]:http://flywaydb.org
