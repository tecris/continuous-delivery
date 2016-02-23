## Java Continuous Delivery by Docker

Continuous delivery demo that aims to use following principles:
 - reference images
  - build process - **fully automated**
  - images built once only (for a given source code version) and published to a docker repository
  - only images from this repository are to be used (dev, test, demo, prod, etc)
 - immutable environments
  - running integration tests requires a single task: start containers from reference images
 - short lived environments 
  - **built and destroyed** with every integration test run
 - infrastructure as code 
  - all above is achieved with **code / scripts** (also under version control)

<hr/>
- [**Continuous delivery with Jenkins**](#continuous-delivery-with-jenkins)
- [**E2E with Jolokia & Maven**](#e2e-with-jolokia-and-maven)
- [**E2E with Docker Compose and Maven**](#e2e-with-docker-compose-and-maven)
- [**Kubernetes**](#kubernetes)

<hr/>

### Techno stack
 1. Java JEE (Web Application)
 1. Wildfly 10.0.0
 1. MySQL 5.7 or PostgreSQL 9.5

### Prerequisites
#### What is required to run this project:
 - Linux with:
  - Java 8
  - Maven 3.3
  - Docker 1.10
    - [Configured to run without sudo ... ](https://docs.docker.com/engine/installation/ubuntulinux/#create-a-docker-group)
  - Docker Compose 1.6
  - [Private registry](https://github.com/tecris/docker/tree/v16.02.01/registry2/private)
  - [Mirror registry](https://github.com/tecris/docker/tree/v16.02.01/registry2/mirror)

### Continuous delivery with Jenkins
 - Enable Docker Remote API edit `/etc/default/docker` and update the DOCKER_OPTS:
  * `DOCKER_OPTS='-H tcp://0.0.0.0:4243 -H unix:///var/run/docker.sock ...'`
 - `$ ./startCdInfrastructure.sh`
 - `$ cd jenkins && ./createJob.sh localhost planets config.xml`
 - http://localhost:8088/job/continuous-delivery -> 'Build Now'

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

### E2E with [Jolokia][1] and Maven

1. One liners
    
    `$ mvn -Pcd-mysql clean verify -Dmaven.buildNumber.doCheck=false      # use mysql as database`
      - use **cd-postgres** maven profile (replace **-Pcd-mysql** with **-Pcd-postgres**) to run with postgresql as database.

1. Steps performed:
  - stop and remove web and db containers (if any)
  - build artifact
  - build web image,
  - start db container
  - apply database scripts
  - start web container
  - run integration tests
  - stop and remove web container
  - stop and remove db container
 
Use `-Dmaven.buildNumber.doCheck=false` if project contains local changes

### E2E with docker compose and Maven
#### With Wildfly & MySQL
```sh
   $ docker-compose up -d                           # start web(wildfly) and database(mysql) containers`
   $ mvn -Pdb-mysql clean compile flyway:migrate    # deploy database scripts
   $ mvn clean wildfly:deploy                       # deploy application
   $ mvn -Prun-it clean integration-test            # run integration tests
```
 
#### With Wildfly & PostgreSQL
```sh
   $ docker-compose -f wildfly-postgresql.yml up -d           # start web(wildfly) and database(postgresql) containers
   $ mvn -Pdb-postgres clean compile flyway:migrate           # deploy database scripts
   $ mvn clean wildfly:deploy -Ddatasource.name=PostgresDS    # deploy application
   $ mvn -Prun-it clean integration-test                      # run integration tests
```

Go to http://localhost:8080/planet

#### Kubernetes

 * Assumptions: 
  
  - [**kubernetes installed**](https://github.com/tecris/kubernetes/blob/v16.02.02/coreos-libvirt/README.md)
  - [**Continuous delivery with Jenkins**](#continuous-delivery-with-jenkins) step executed(2 builds are required to execute rolling update demo).

 * Deploy

   ```$ ./deployToKubernetes.sh```
   
 * Rolling update
 
   ```
   $ kubectl scale --replicas=2 rc planets-web-rc-v1        # scale to 2 pods (optional)     
   
   # Replication Controller rolling update from "planets-web-rc-v1" to "planets-web-rc-v2".
   # (image change from blue.sky/planets-web:1 to blue.sky/planets-web:2)
   $ kubectl rolling-update planets-web-rc-v1 planets-web-rc-v2 -f kubernetes/planets-web-rc-v2.yaml    
   ```
   http://192.168.122.51:30002/planet


#### Varia kubectl commands
 
   ```
    $ kubectl describe pod pod_name
    $ kubectl delete pod pod_name
    $ kubectl get rc
    $ kubectl describe rc planets-web-rc-v1
    $ kubectl delete rc planets-web-rc-v1
    $ kubectl delete svc planets-web-svc
   ```


[1]:https://github.com/fabric8io/docker-maven-plugin
[2]:http://flywaydb.org
[3]:https://github.com/tecris/docker/blob/v3.6/nexus/README.md
[4]:https://github.com/tecris/docker/blob/v3.6/nexus/settings.xml
