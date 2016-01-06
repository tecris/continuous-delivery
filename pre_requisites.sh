#!/bin/bash

git clone --branch v3.6.1 https://github.com/tecris/docker.git
docker build --no-cache -t casadocker/jdk:8 docker/jdk/8
docker build --no-cache -t casadocker/wildfly:9.0.2 docker/wildfly/9
docker build --no-cache -t casadocker/wildfly-mysql:9.0.2 docker/wildfly-mysql
docker build --no-cache -t casadocker/mysql:5.7 docker/mysql/57
docker build --no-cache -t casadocker/wildfly-postgresql:9.0.2 docker/wildfly-postgresql
docker build --no-cache -t casadocker/postgres:9.4 docker/postgres/94
docker build --no-cache -t casadocker/nexus:2.11.4-01 docker/nexus
rm -rf docker
# start maven nexus repository
docker run --name nexus --restart=always -d -p 172.17.0.1:8081:8081  casadocker/nexus:2.11.4-01
# nexus with docker volume
# mkdir /opt/docker_volumes/nexus-data && chown -R 200 /opt/docker_volumes/nexus-data
# docker run --name nexus --restart=always -d -p 172.17.0.1:8081:8081 -v /opt/docker_volumes/nexus-data:/sonatype-work casadocker/nexus:2.11.4-01
