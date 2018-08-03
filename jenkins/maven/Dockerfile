# docker build -f Dockerfile -t trivialis/maven:3 ./
FROM maven:3-alpine

ENV DOCKER_COMPOSE_VERSION=1.21.2
 
RUN apk --update add --no-cache git mysql-client python py-pip \
    && pip install docker-compose==${DOCKER_COMPOSE_VERSION}

COPY wait-for-mysql.sh ./
