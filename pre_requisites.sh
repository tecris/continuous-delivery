#!/bin/bash

git clone --branch v3.6 https://github.com/tecris/docker.git
docker build --no-cache -t casa.docker/jdk:8 docker/jdk/8
docker build --no-cache -t casa.docker/wildfly:9.0.2 docker/wildfly/9
docker build --no-cache -t casa.docker/wildfly-mysql:9.0.2 docker/wildfly-mysql
docker build --no-cache -t casa.docker/mysql:5.7 docker/mysql/57
docker build --no-cache -t casa.docker/wildfly-postgres:9.0.2 docker/wildfly-postgresql
docker build --no-cache -t casa.docker/postgres:9.4 docker/postgres/94
docker build --no-cache -t casa.docker/nexus:2.11.4-01 docker/nexus
rm -rf docker
# start maven nexus repository
docker run --name nexus --restart=always -d -p 172.17.0.1:8081:8081  casa.docker/nexus:2.11.4-01
# nexus with docker volume
# mkdir /opt/docker_volumes/nexus-data && chown -R 200 /opt/docker_volumes/nexus-data
# docker run --name nexus --restart=always -d -p 172.17.0.1:8081:8081 -v /opt/docker_volumes/nexus-data:/sonatype-work casa.docker/nexus:2.11.4-01
