## Java Continuous Delivery by Docker

Showcase for a continuous delivery solution based on following principles:
 - short lived environments (built and destroyed for each iteration  / run = mvn verify)
 - immutable environments (built in automated fashion, no manual jobs / touches, hands off :) )
 - infrastructure as code

### Techno stack
#### Application
 1. Java JEE (Web Application)
 1. Wildfly 10.0.0
 1. MySQL 5.7 or PostgreSQL 9.5

### Prerequisites
#### What is required to run this project:
 - Linux with:
  - Java 8
  - Maven 3.3
  - Docker 1.9
    - [Configured to run without sudo ... ](https://docs.docker.com/engine/installation/ubuntulinux/#create-a-docker-group)
  - Docker Compose 1.5
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

### Run app & integration tests with [Jolokia][1] docker maven plugin
 1. With Wildfly and MySQL
  * `$ mvn -Pcd-mysql clean integration-test`
 1. With Wildfly and PostgreSQL
  * `$ mvn -Pcd-postgres clean integration-test`

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
```sh
   $ docker-compose --x-networking up -d          # start web(wildfly) and database(mysql) containers`
   $ mvn -Pdb-mysql clean compile flyway:migrate  # deploy database scripts
   $ mvn clean wildfly:deploy                     # deploy application
   $ mvn -Prun-it clean integration-test          # run integration tests
```
 
#### With Wildfly & PostgreSQL
```sh
   $ docker-compose --x-networking -f wildfly-postgresql.yml up -d   # start web(wildfly) and database(postgresql) containers
   $ mvn -Pdb-postgres clean compile flyway:migrate                  # deploy database scripts
   $ mvn clean wildfly:deploy                                        # deploy application
   $ mvn -Prun-it clean integration-test                             # run integration tests
```

Go to http://localhost:8080/planet

#### Kubernetes

 * Assumptions: 
  
  - [kubernetes installed](https://github.com/tecris/kubernetes/tree/v1.1.3-2/coreos-libvirt)
  - Continuous delivery with Jenkins step executed

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


[1]:https://github.com/rhuss/docker-maven-plugin
[2]:http://flywaydb.org
[3]:https://github.com/tecris/docker/blob/v3.6/nexus/README.md
[4]:https://github.com/tecris/docker/blob/v3.6/nexus/settings.xml
